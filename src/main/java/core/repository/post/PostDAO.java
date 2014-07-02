package core.repository.post;

import core.domain.Post;
import core.repository.jdbc.ConnectionFactory;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: Admin
 * Date: 16.03.14
 * Time: 19:36
 * To change this template use File | Settings | File Templates.
 */
public interface PostDAO {

    public void setCnFactory(ConnectionFactory cnFactory);

    public Post findById(Integer postId);

    public List<Post> findByPhrase(String phrase);

    public List<Post> findByUser(String login);

    public List<Post> findByUser(Integer userId);

    public Integer userPostCount(Integer userID);

    public List<Post> findByUserPagination(Integer userId, Integer startPos, Integer endPos);

    public List<Post> findAvailablePosts(String login);

    public List<Post> findAvailablePosts(Integer userId);

    public Integer availablePostCount(Integer userID);

    public List<Post> findAvailablePostsPagination(Integer userId, Integer startPos, Integer endPos);

    public List<Post> findAll();

    public boolean create(Post post);

    public boolean update(Post post);

    public boolean delete(Post post);
}