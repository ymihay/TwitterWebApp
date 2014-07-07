package core.repository.post;

import core.domain.Country;
import core.domain.Post;
import core.domain.Sex;
import core.domain.User;
import core.repository.jdbc.ConnectionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import static core.repository.jdbc.DAOJDBCUtil.close;

/**
 * Created with IntelliJ IDEA.
 * User: Admin
 * Date: 16.03.14
 * Time: 19:52
 * To change this template use File | Settings | File Templates.
 */

@Repository("postDAO")
public class PostDAOJDBC implements PostDAO {

    private static final String findByPhraseSQL = "select p.id      as post_id,\n" +
            "       p.user_id,\n" +
            "       p.text,\n" +
            "       u.*,\n" +
            "       s.id      as sex_id,\n" +
            "       s.name    as sex_name,\n" +
            "       c.id      as country_id,\n" +
            "       c.name    as country_name" +
            "  from twt_post p\n" +
            "  left join twt_user u\n" +
            "    on (u.sys_delstate = 0 and u.id = p.user_id)\n" +
            " left join twt_sex s\n" +
            "    on (s.sys_delstate = 0 and s.id = u.sex_id)\n" +
            "  left join twt_country c on (c.sys_delstate = 0 and c.id = u.country_id) " +
            " where p.sys_delstate = 0\n" +
            "   and p.text like '%' || ? || '%'\n" +
            " order by p.sys_last_modified desc\n";

    private static final String findByIdSQL = "select p.id as post_id,       \n" +
            "       p.user_id,\n" +
            "       p.text,\n" +
            "       u.*,\n" +
            "       s.id      as sex_id,\n" +
            "       s.name    as sex_name,\n" +
            "       c.id      as country_id,\n" +
            "       c.name    as country_name\n" +
            "  from twt_post p\n" +
            "  left join twt_user u\n" +
            "    on (u.sys_delstate = 0 and u.id = p.user_id)\n" +
            "  left join twt_sex s\n" +
            "    on (s.sys_delstate = 0 and s.id = u.sex_id)\n" +
            "  left join twt_country c\n" +
            "    on (c.sys_delstate = 0 and c.id = u.country_id)\n" +
            " where p.sys_delstate = 0\n" +
            "   and p.id = ?\n" +
            " order by p.sys_last_modified desc\n";

    private static final String userPostCountSQL = "select count(p.id) as count\n" +
            "  from twt_post p\n" +
            " where p.sys_delstate = 0\n" +
            "   and p.user_id = ?\n";

    private static final String findByUserLoginSQL = "select p.id  as post_id,\n" +
            "       p.user_id,\n" +
            "       p.text,\n" +
            "       u.*,\n" +
            "       s.id      as sex_id,\n" +
            "       s.name    as sex_name,\n" +
            "       c.id      as country_id,\n" +
            "       c.name    as country_name" +
            "  from twt_post p\n" +
            "  left join twt_user u\n" +
            "    on (u.sys_delstate = 0 and u.id = p.user_id)\n" +
            " left join twt_sex s\n" +
            "    on (s.sys_delstate = 0 and s.id = u.sex_id)\n" +
            "  left join twt_country c on (c.sys_delstate = 0 and c.id = u.country_id) " +
            " where p.sys_delstate = 0\n" +
            "   and p.user_id in (select u.id\n" +
            "                       from twt_user u\n" +
            "                      where u.sys_delstate = 0\n" +
            "                        and u.login like ?)\n" +
            " order by p.sys_last_modified desc\n";

    private static final String findByUserIdSQL = "select p.id      as post_id,\n" +
            "       p.user_id,\n" +
            "       p.text,\n" +
            "       u.*,\n" +
            "       s.id      as sex_id,\n" +
            "       s.name    as sex_name,\n" +
            "       c.id      as country_id,\n" +
            "       c.name    as country_name" +
            "  from twt_post p\n" +
            "  left join twt_user u\n" +
            "    on (u.id = p.user_id and u.sys_delstate = 0)\n" +
            " left join twt_sex s\n" +
            "    on (s.sys_delstate = 0 and s.id = u.sex_id)\n" +
            "  left join twt_country c on (c.sys_delstate = 0 and c.id = u.country_id) " +
            " where p.sys_delstate = 0\n" +
            "   and p.user_id = ?\n" +
            " order by p.sys_last_modified desc\n";

    private static final String findByUserIdPaginationSQL = "select * from (select rownum as rn, p.id as post_id,\n" +
            "       p.user_id,\n" +
            "       p.text,\n" +
            "       u.*,\n" +
            "       s.name    as sex_name,\n" +
            "       c.name    as country_name" +
            "  from twt_post p\n" +
            "  left join twt_user u\n" +
            "    on (u.id = p.user_id and u.sys_delstate = 0)\n" +
            " left join twt_sex s\n" +
            "    on (s.sys_delstate = 0 and s.id = u.sex_id)\n" +
            "  left join twt_country c on (c.sys_delstate = 0 and c.id = u.country_id) " +
            " where p.sys_delstate = 0\n" +
            "   and p.user_id = ?\n" +
            " order by p.sys_last_modified desc) where rn >= ? and rn <= ?\n";


    private static final String findAllAvailablePostsSQL = "select p.id      as post_id,\n" +
            "       p.user_id,\n" +
            "       p.text,\n" +
            "       u.*,\n" +
            "       s.id      as sex_id,\n" +
            "       s.name    as sex_name,\n" +
            "       c.id      as country_id,\n" +
            "       c.name    as country_name\n" +
            "  from twt_post p\n" +
            "  left join twt_user u\n" +
            "    on (u.sys_delstate = 0 and u.id = p.user_id)\n" +
            "  left join twt_sex s\n" +
            "    on (s.sys_delstate = 0 and s.id = u.sex_id)\n" +
            "  left join twt_country c\n" +
            "    on (c.sys_delstate = 0 and c.id = u.country_id)\n" +
            " where p.sys_delstate = 0\n" +
            "   and ((p.user_id in (select u.id\n" +
            "                         from twt_user u\n" +
            "                        where u.sys_delstate = 0\n" +
            "                          and u.login like ?)) or\n" +
            "       p.user_id in\n" +
            "       (select s.subscribed_on_user_id\n" +
            "           from twt_subscription s\n" +
            "          where s.sys_delstate = 0\n" +
            "            and s.user_id in\n" +
            "                (select u.id\n" +
            "                   from twt_user u\n" +
            "                  where u.sys_delstate = 0\n" +
            "                    and u.login like ?)))\n" +
            " order by p.sys_last_modified desc\n";

    private static final String findAvailablePostByUserIdSQL = "select p.id      as post_id,\n" +
            "       p.user_id,\n" +
            "       p.text,\n" +
            "       u.*,\n" +
            "       s.id      as sex_id,\n" +
            "       s.name    as sex_name,\n" +
            "       c.id      as country_id,\n" +
            "       c.name    as country_name" +
            "  from twt_post p\n" +
            "  left join twt_user u\n" +
            "    on (u.sys_delstate = 0 and u.id = p.user_id)\n" +
            " left join twt_sex s\n" +
            "    on (s.sys_delstate = 0 and s.id = u.sex_id)\n" +
            "  left join twt_country c on (c.sys_delstate = 0 and c.id = u.country_id) " +
            " where p.sys_delstate = 0\n" +
            "   and (p.user_id = ? or\n" +
            "       p.user_id in (select s.subscribed_on_user_id\n" +
            "                        from twt_subscription s\n" +
            "                       where s.sys_delstate = 0\n" +
            "                         and s.user_id = ?))\n" +
            " order by p.sys_last_modified desc\n";

    private static final String availablePostsCountSQL = "select count(p.id) as count\n" +
            "  from twt_post p\n" +
            " where p.sys_delstate = 0\n" +
            "   and (p.user_id = ? or\n" +
            "       p.user_id in (select s.subscribed_on_user_id\n" +
            "                        from twt_subscription s\n" +
            "                       where s.sys_delstate = 0\n" +
            "                         and s.user_id = ?))\n";

    private static final String findAvailablePostByUserIdPaginationSQL = "select * from (select rownum as rn," +
            "       p.id      as post_id,\n" +
            "       p.user_id,\n" +
            "       p.text,\n" +
            "       u.*,\n" +
            "       s.name    as sex_name,\n" +
            "       c.name    as country_name" +
            "  from twt_post p\n" +
            "  left join twt_user u\n" +
            "    on (u.sys_delstate = 0 and u.id = p.user_id)\n" +
            " left join twt_sex s\n" +
            "    on (s.sys_delstate = 0 and s.id = u.sex_id)\n" +
            "  left join twt_country c on (c.sys_delstate = 0 and c.id = u.country_id) " +
            " where p.sys_delstate = 0\n" +
            "   and (p.user_id = ? or\n" +
            "       p.user_id in (select s.subscribed_on_user_id\n" +
            "                        from twt_subscription s\n" +
            "                       where s.sys_delstate = 0\n" +
            "                         and s.user_id = ?))\n" +
            " order by p.sys_last_modified desc) where  rn >= ? and rn <= ? \n";

    private static final String findFriendsPostsSQL = "select p.id      as post_id,\n" +
            "       p.user_id,\n" +
            "       p.text,\n" +
            "       u.*,\n" +
            "       s.name    as sex_name,\n" +
            "       c.name    as country_name\n" +
            "  from twt_post p\n" +
            "  left join twt_user u\n" +
            "    on (u.sys_delstate = 0 and u.id = p.user_id)\n" +
            "  left join twt_sex s\n" +
            "    on (s.sys_delstate = 0 and s.id = u.sex_id)\n" +
            "  left join twt_country c\n" +
            "    on (c.sys_delstate = 0 and c.id = u.country_id)\n" +
            " where p.sys_delstate = 0\n" +
            "   and (p.user_id in (select s.subscribed_on_user_id\n" +
            "                        from twt_subscription s\n" +
            "                       where s.sys_delstate = 0\n" +
            "                         and s.user_id = ?))\n";


    private static final String findAllSQL = "select p.id      as post_id,\n" +
            "       p.user_id,\n" +
            "       p.text,\n" +
            "       u.*,\n" +
            "       s.id      as sex_id,\n" +
            "       s.name    as sex_name,\n" +
            "       c.id      as country_id,\n" +
            "       c.name    as country_name" +
            "  from twt_post p\n" +
            "  left join twt_user u\n" +
            "    on (u.sys_delstate = 0 and u.id = p.user_id)\n" +
            " left join twt_sex s\n" +
            "    on (s.sys_delstate = 0 and s.id = u.sex_id)\n" +
            "  left join twt_country c on (c.sys_delstate = 0 and c.id = u.country_id) " +
            " where p.sys_delstate = 0\n" +
            " order by p.sys_last_modified desc\n";

    private static final String createPostSQL = "insert into twt_post (user_id, text) values (?, ?)";

    private static final String updatePostSQL = "update twt_post p " +
            "   set p.user_id = ?, p.text = ?\n" +
            " where p.sys_delstate = 0\n" +
            "   and p.id = ?\n";

    private static final String deletePostSQL = "update twt_post p\n" +
            "   set p.sys_delstate = 1\n" +
            " where p.sys_delstate = 0\n" +
            "   and p.id = ?\n";

    private ConnectionFactory cnFactory;

    @Autowired
    public PostDAOJDBC(ConnectionFactory connection) {
        this.cnFactory = connection;
    }

    public ConnectionFactory getCnFactory() {
        return cnFactory;
    }

    public void setCnFactory(ConnectionFactory cnFactory) {
        this.cnFactory = cnFactory;
    }

    @Override
    public Post findById(Integer postId) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        Post post = new Post();

        try {
            connection = cnFactory.getConnection();
            preparedStatement = connection.prepareStatement(findByIdSQL);
            preparedStatement.setInt(1, postId);
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                post = map(resultSet);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            close(resultSet);
            close(preparedStatement);
            cnFactory.closeConnection(connection);
        }
        return post;
    }


    private Post map(ResultSet resultSet) throws SQLException {
        Post post = new Post();
        post.setPostId(resultSet.getInt("POST_ID"));
        post.setPostMessage(resultSet.getString("TEXT"));
        User user = new User(resultSet.getString("LOGIN"), resultSet.getString("PASSWORD"));
        user.setUserId(resultSet.getInt("ID"));
        user.setBirthdate(resultSet.getTimestamp("BIRTH_DATE"));
        Country country = new Country(resultSet.getInt("COUNTRY_ID"), resultSet.getString("COUNTRY_NAME"));
        user.setCountry(country);
        user.setFirstName(resultSet.getString("FIRST_NAME"));
        user.setLastName(resultSet.getString("LAST_NAME"));
        user.setPatronymic(resultSet.getString("PATRONYMIC"));
        Sex sex = new Sex(resultSet.getInt("SEX_ID"), resultSet.getString("SEX_NAME"));
        user.setSex(sex);
        post.setUser(user);
        return post;
    }

    @Override
    public List<Post> findByPhrase(String phrase) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        List<Post> posts = new ArrayList<Post>();

        try {
            connection = cnFactory.getConnection();
            preparedStatement = connection.prepareStatement(findByPhraseSQL);
            preparedStatement.setString(1, phrase);
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                posts.add(map(resultSet));
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            close(resultSet);
            close(preparedStatement);
            cnFactory.closeConnection(connection);
        }
        return posts;
    }

    @Override
    public List<Post> findByUser(String login) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        List<Post> posts = new ArrayList<Post>();

        try {
            connection = cnFactory.getConnection();
            preparedStatement = connection.prepareStatement(findByUserLoginSQL);
            preparedStatement.setString(1, login);
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                posts.add(map(resultSet));
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            close(resultSet);
            close(preparedStatement);
            cnFactory.closeConnection(connection);
        }
        return posts;
    }

    @Override
    public List<Post> findByUser(Integer userId) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        List<Post> posts = new ArrayList<Post>();

        try {
            connection = cnFactory.getConnection();
            preparedStatement = connection.prepareStatement(findByUserIdSQL);
            preparedStatement.setInt(1, userId);
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                posts.add(map(resultSet));
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            close(resultSet);
            close(preparedStatement);
            cnFactory.closeConnection(connection);
        }
        return posts;
    }

    @Override
    public List<Post> findByUserPagination(Integer userId, Integer startPos, Integer endPos) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        List<Post> posts = new ArrayList<Post>();

        try {
            connection = cnFactory.getConnection();
            preparedStatement = connection.prepareStatement(findByUserIdPaginationSQL);
            preparedStatement.setInt(1, userId);
            preparedStatement.setInt(2, startPos);
            preparedStatement.setInt(3, endPos);
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                posts.add(map(resultSet));
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            close(resultSet);
            close(preparedStatement);
            cnFactory.closeConnection(connection);
        }
        return posts;
    }

    @Override
    public List<Post> findFriendsPosts(Integer id) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        List<Post> posts = new ArrayList<Post>();

        try {
            connection = cnFactory.getConnection();
            preparedStatement = connection.prepareStatement(findFriendsPostsSQL);
            preparedStatement.setInt(1, id);
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                posts.add(map(resultSet));
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            close(preparedStatement);
            cnFactory.closeConnection(connection);
        }
        return posts;
    }

    @Override
    public List<Post> findAvailablePosts(String login) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        List<Post> posts = new ArrayList<Post>();

        try {
            connection = cnFactory.getConnection();
            preparedStatement = connection.prepareStatement(findAllAvailablePostsSQL);
            preparedStatement.setString(1, login);
            preparedStatement.setString(2, login);
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                posts.add(map(resultSet));
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            close(preparedStatement);
            cnFactory.closeConnection(connection);
        }
        return posts;
    }

    @Override
    public List<Post> findAvailablePosts(Integer userID) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        List<Post> posts = new ArrayList<Post>();

        try {
            connection = cnFactory.getConnection();
            preparedStatement = connection.prepareStatement(findAvailablePostByUserIdSQL);
            preparedStatement.setInt(1, userID);
            preparedStatement.setInt(2, userID);
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                posts.add(map(resultSet));
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            close(preparedStatement);
            cnFactory.closeConnection(connection);
        }
        return posts;
    }

    @Override
    public Integer availablePostCount(Integer userID) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        Integer count = 0;

        try {
            connection = cnFactory.getConnection();
            preparedStatement = connection.prepareStatement(availablePostsCountSQL);
            preparedStatement.setInt(1, userID);
            preparedStatement.setInt(2, userID);
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                count = resultSet.getInt("COUNT");
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            close(preparedStatement);
            cnFactory.closeConnection(connection);
        }
        return count;
    }

    @Override
    public Integer userPostCount(Integer userID) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        Integer count = 0;

        try {
            connection = cnFactory.getConnection();
            preparedStatement = connection.prepareStatement(userPostCountSQL);
            preparedStatement.setInt(1, userID);
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                count = resultSet.getInt("COUNT");
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            close(preparedStatement);
            cnFactory.closeConnection(connection);
        }
        return count;
    }

    @Override
    public List<Post> findAvailablePostsPagination(Integer userId, Integer startPos, Integer endPos) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        List<Post> posts = new ArrayList<Post>();

        try {
            connection = cnFactory.getConnection();
            preparedStatement = connection.prepareStatement(findAvailablePostByUserIdPaginationSQL);
            preparedStatement.setInt(1, userId);
            preparedStatement.setInt(2, userId);
            preparedStatement.setInt(3, startPos);
            preparedStatement.setInt(4, endPos);
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                posts.add(map(resultSet));
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            close(preparedStatement);
            cnFactory.closeConnection(connection);
        }
        return posts;
    }

    @Override
    public List<Post> findAll() {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        List<Post> posts = new ArrayList<Post>();

        try {
            connection = cnFactory.getConnection();
            preparedStatement = connection.prepareStatement(findAllSQL);
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                posts.add(map(resultSet));
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            close(resultSet);
            close(preparedStatement);
            cnFactory.closeConnection(connection);
        }
        return posts;
    }

    @Override
    public boolean create(Post post) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;

        try {
            connection = cnFactory.getConnection();
            preparedStatement = connection.prepareStatement(createPostSQL);
            preparedStatement.setInt(1, post.getUser().getUserId());
            preparedStatement.setString(2, post.getPostMessage());

            return preparedStatement.execute();
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            close(preparedStatement);
            cnFactory.closeConnection(connection);
        }
        return true;
    }

    @Override
    public boolean update(Post post) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        try {
            connection = cnFactory.getConnection();
            preparedStatement = connection.prepareStatement(updatePostSQL);
            preparedStatement.setInt(1, post.getUser().getUserId());
            preparedStatement.setString(2, post.getPostMessage());
            preparedStatement.setInt(3, post.getPostId());

            return preparedStatement.execute();
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            close(preparedStatement);
            cnFactory.closeConnection(connection);
        }
        return true;
    }

    @Override
    public boolean delete(Post post) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        try {
            connection = cnFactory.getConnection();
            preparedStatement = connection.prepareStatement(deletePostSQL);
            preparedStatement.setInt(1, post.getPostId());
            return preparedStatement.execute();
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            close(preparedStatement);
            cnFactory.closeConnection(connection);
        }
        return true;
    }
}
