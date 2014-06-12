package repository.user;

import domain.Country;
import domain.Sex;
import domain.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import repository.jdbc.ConnectionFactory;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import static repository.jdbc.DAOJDBCUtil.close;

/**
 * Created with IntelliJ IDEA.
 * UserDAO: Admin
 * Date: 16.03.14
 * Time: 17:20
 * To change this template use File | Settings | File Templates.
 */

@Repository("userDAO")
public class UserDAOJDBC implements UserDAO {

    private ConnectionFactory cnFactory;

    private static final String findByLoginSQL = "select u.*, c.name as country_name, s.name as sex_name\n" +
            "  from TWT_USER u\n" +
            "  left join twt_country c\n" +
            "    on (c.sys_delstate = 0 and c.id = u.country_id)\n" +
            "  left join twt_sex s\n" +
            "    on (s.sys_delstate = 0 and s.id = u.sex_id)\n" +
            " where u.login like ?\n" +
            "   and u.sys_delstate = 0\n";

    private static final String findByIdSQL = "select u.*, c.name as country_name, s.name as sex_name\n" +
            "  from TWT_USER u\n" +
            "  left join twt_country c\n" +
            "    on (c.sys_delstate = 0 and c.id = u.country_id)\n" +
            "  left join twt_sex s\n" +
            "    on (s.sys_delstate = 0 and s.id = u.sex_id)\n" +
            " where u.id = ?\n" +
            "   and u.sys_delstate = 0\n";

    private static final String findAllSQL = "select u.*, c.name as country_name, s.name as sex_name\n" +
            "  from TWT_USER u\n" +
            "  left join twt_country c\n" +
            "    on (c.sys_delstate = 0 and c.id = u.country_id)\n" +
            "  left join twt_sex s\n" +
            "    on (s.sys_delstate = 0 and s.id = u.sex_id)\n" +
            " where u.sys_delstate = 0\n";

    private static final String findSubscribedOnUserSQL = "select distinct u.*, " +
            " c.name as country_name, s.name as sex_name " +
            "  from TWT_USER u" +
            "  join twt_subscription s" +
            "    on (s.user_id = u.id and s.sys_delstate = 0)" +
            "  left join twt_user su" +
            "    on (su.id = s.subscribed_on_user_id and su.sys_delstate = 0)" +
            "  left join twt_country c\n" +
            "    on (c.sys_delstate = 0 and c.id = u.country_id)\n" +
            "  left join twt_sex s\n" +
            "    on (s.sys_delstate = 0 and s.id = u.sex_id)\n" +
            " where u.sys_delstate = 0" +
            "   and su.login = ?";

    private static final String findSubscriptionsSQL = "select distinct u.*, " +
            " c.name as country_name, s.name as sex_name " +
            "  from twt_subscription s" +
            "  join twt_user u" +
            "    on (u.sys_delstate = 0 and u.id = s.subscribed_on_user_id)" +
            "  join twt_user su" +
            "    on (su.sys_delstate = 0 and su.id = s.user_id)" +
            "  left join twt_country c\n" +
            "    on (c.sys_delstate = 0 and c.id = u.country_id)\n" +
            "  left join twt_sex s\n" +
            "    on (s.sys_delstate = 0 and s.id = u.sex_id)\n" +
            " where s.sys_delstate = 0" +
            "   and su.login = ?";

    private static final String findSubscribedOnUserByIdSQL = "select distinct u.*, " +
            " c.name as country_name, s.name as sex_name " +
            "  from TWT_USER u" +
            "  join twt_subscription s" +
            "    on (s.user_id = u.id and s.sys_delstate = 0)" +
            "  left join twt_user su" +
            "    on (su.id = s.subscribed_on_user_id and su.sys_delstate = 0)" +
            "  left join twt_country c\n" +
            "    on (c.sys_delstate = 0 and c.id = u.country_id)\n" +
            "  left join twt_sex s\n" +
            "    on (s.sys_delstate = 0 and s.id = u.sex_id)\n" +
            " where u.sys_delstate = 0" +
            "   and su.id = ?";

    private static final String findSubscriptionsByIdSQL = "select distinct u.*, " +
            " c.name as country_name, s.name as sex_name " +
            "  from twt_subscription s" +
            "  join twt_user u" +
            "    on (u.sys_delstate = 0 and u.id = s.subscribed_on_user_id)" +
            "  join twt_user su" +
            "    on (su.sys_delstate = 0 and su.id = s.user_id)" +
            "  left join twt_country c\n" +
            "    on (c.sys_delstate = 0 and c.id = u.country_id)\n" +
            "  left join twt_sex s\n" +
            "    on (s.sys_delstate = 0 and s.id = u.sex_id)\n" +
            " where s.sys_delstate = 0" +
            "   and su.id = ?";

    private static final String setSubscriptionSQL = "INSERT INTO twt_subscription " +
            "  (USER_ID, SUBSCRIBED_ON_USER_ID)" +
            "VALUES" +
            "  ((select u.id" +
            "     from twt_user u" +
            "    where u.sys_delstate = 0" +
            "      and u.login like ?)," +
            "   (select s.id" +
            "      from twt_user s" +
            "     where s.sys_delstate = 0" +
            "       and s.login like ?))";

    private static final String unSetSubscriptionSQL = "update twt_subscription sb" +
            "   set sb.sys_delstate = 1" +
            " where sb.user_id = (select u.id" +
            "                       from twt_user u" +
            "                      where u.sys_delstate = 0" +
            "                        and u.login like ?)" +
            "   and sb.subscribed_on_user_id =" +
            "       (select s.id" +
            "          from twt_user s" +
            "         where s.sys_delstate = 0" +
            "           and s.login like ?)";

    private static final String createUserSQL = "insert into twt_user \n" +
            "  (first_name,\n" +
            "   patronymic,\n" +
            "   last_name,\n" +
            "   login,\n" +
            "   password,\n" +
            "   sex_id,\n" +
            "   country_id,\n" +
            "   birth_date)\n" +
            "values\n" +
            "  (?, ?, ?, ?, ?, ?, ?, ?)";

    private static final String updateUserSQL = "update twt_user u" +
            "   set u.first_name = ?," +
            "       u.patronymic = ?," +
            "       u.last_name  = ?," +
            "       u.password   = ?," +
            "       u.sex_id        = ?," +
            "       u.country_id    = ?," +
            "       u.birth_date = ?," +
            "       u.login = ?" +
            " where u.sys_delstate = 0" +
            "   and u.id = ?";

    private static final String deleteUserSQL = "update twt_user u" +
            "   set u.sys_delstate = 1" +
            " where u.sys_delstate = 0" +
            "   and u.id = ?";

    @Autowired
    public UserDAOJDBC(ConnectionFactory connection) {
        this.cnFactory = connection;
    }

    private User map(ResultSet resultSet) throws SQLException {
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
        return user;
    }

    @Override
    public User find(String login) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        User user = null;

        try {
            connection = cnFactory.getConnection();
            preparedStatement = connection.prepareStatement(findByLoginSQL);
            preparedStatement.setString(1, login);
            resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                user = map(resultSet);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            close(resultSet);
            close(preparedStatement);
            cnFactory.closeConnection(connection);
        }
        return user;
    }

    @Override
    public User find(Integer id) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        User user = null;

        try {
            connection = cnFactory.getConnection();
            preparedStatement = connection.prepareStatement(findByIdSQL);
            preparedStatement.setInt(1, id);
            resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                user = map(resultSet);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            close(resultSet);
            close(preparedStatement);
            cnFactory.closeConnection(connection);
        }
        return user;
    }

    @Override
    public List<User> findAll() {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        List<User> users = new ArrayList<User>();

        try {
            connection = cnFactory.getConnection();
            preparedStatement = connection.prepareStatement(findAllSQL);
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                users.add(map(resultSet));
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            close(resultSet);
            close(preparedStatement);
            cnFactory.closeConnection(connection);
        }

        return users;
    }

    public ConnectionFactory getCnFactory() {
        return cnFactory;
    }

    public void setCnFactory(ConnectionFactory cnFactory) {
        this.cnFactory = cnFactory;
    }

    @Override
    public List<User> findSubscribedOnUser(String login) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        List<User> users = new ArrayList<User>();

        try {
            connection = cnFactory.getConnection();
            preparedStatement = connection.prepareStatement(findSubscribedOnUserSQL);
            preparedStatement.setString(1, login);
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                users.add(map(resultSet));
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            close(resultSet);
            close(preparedStatement);
            cnFactory.closeConnection(connection);
        }
        return users;
    }

    @Override
    public List<User> findSubscriptions(String login) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        List<User> users = new ArrayList<User>();

        try {
            connection = cnFactory.getConnection();
            preparedStatement = connection.prepareStatement(findSubscriptionsSQL);
            preparedStatement.setString(1, login);
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                users.add(map(resultSet));
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            close(resultSet);
            close(preparedStatement);
            cnFactory.closeConnection(connection);
        }
        return users;
    }

    @Override
    public List<User> findSubscribedOnUser(Integer userId) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        List<User> users = new ArrayList<User>();

        try {
            connection = cnFactory.getConnection();
            preparedStatement = connection.prepareStatement(findSubscribedOnUserByIdSQL);
            preparedStatement.setInt(1, userId);
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                users.add(map(resultSet));
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            close(resultSet);
            close(preparedStatement);
            cnFactory.closeConnection(connection);
        }
        return users;
    }

    @Override
    public List<User> findSubscriptions(Integer userId) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        List<User> users = new ArrayList<User>();

        try {
            connection = cnFactory.getConnection();
            preparedStatement = connection.prepareStatement(findSubscriptionsByIdSQL);
            preparedStatement.setInt(1, userId);
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                users.add(map(resultSet));
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            close(resultSet);
            close(preparedStatement);
            cnFactory.closeConnection(connection);
        }
        return users;
    }

    @Override
    public boolean setSubscription(String login, String loginSubscribedOn) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;

        try {
            connection = cnFactory.getConnection();
            preparedStatement = connection.prepareStatement(setSubscriptionSQL);
            preparedStatement.setString(1, login);
            preparedStatement.setString(2, loginSubscribedOn);
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
    public boolean unSetSubscription(String login, String loginSubscribedOn) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        try {
            connection = cnFactory.getConnection();
            preparedStatement = connection.prepareStatement(unSetSubscriptionSQL);
            preparedStatement.setString(1, login);
            preparedStatement.setString(2, loginSubscribedOn);
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
    public boolean create(User user) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        try {
            connection = cnFactory.getConnection();
            preparedStatement = connection.prepareStatement(createUserSQL);
            preparedStatement.setString(1, user.getFirstName());
            preparedStatement.setString(2, user.getPatronymic());
            preparedStatement.setString(3, user.getLastName());
            preparedStatement.setString(4, user.getLogin());
            preparedStatement.setString(5, user.getPassword());
            if ((user.getSex() == null) || (user.getSex().getSexId() == null)) {
                preparedStatement.setNull(6, Types.INTEGER);
            } else {
                preparedStatement.setInt(6, user.getSex().getSexId());
            }
            if ((user.getCountry() == null) || (user.getCountry().getCountryId() == null)) {
                preparedStatement.setNull(7, Types.INTEGER);
            } else {
                preparedStatement.setInt(7, user.getCountry().getCountryId());
            }
            preparedStatement.setTimestamp(8, user.getBirthdate());
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
    public boolean update(User user) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        try {
            connection = cnFactory.getConnection();
            preparedStatement = connection.prepareStatement(updateUserSQL);
            preparedStatement.setString(1, user.getFirstName());
            preparedStatement.setString(2, user.getPatronymic());
            preparedStatement.setString(3, user.getLastName());
            preparedStatement.setString(4, user.getPassword());
            preparedStatement.setInt(5, user.getSex().getSexId());
            preparedStatement.setInt(6, user.getCountry().getCountryId());
            preparedStatement.setTimestamp(7, user.getBirthdate());
            preparedStatement.setString(8, user.getLogin());
            preparedStatement.setInt(9, user.getUserId());

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
    public boolean delete(User user) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        try {
            connection = cnFactory.getConnection();
            preparedStatement = connection.prepareStatement(deleteUserSQL);
            preparedStatement.setInt(1, user.getUserId());
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
