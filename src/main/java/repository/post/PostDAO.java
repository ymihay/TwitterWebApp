package main.java.repository.post;

import main.java.domain.Post;
import main.java.repository.jdbc.ConnectionFactory;

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

    public List<Post> findByPhrase(String phrase);

    public List<Post> findByUser(String login);

    public List<Post> findByUser(Integer userId);

    public List<Post> findAvailablePosts(String login);

    public List<Post> findAvailablePosts(Integer userId);

    public List<Post> findAll();

    public boolean create(Post post);

    public boolean update(Post post);

    public boolean delete(Post post);
}