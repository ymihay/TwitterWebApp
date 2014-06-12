package service.post;

import domain.Post;

import java.util.List;

/**
 * Created by Admin on 24.05.2014.
 */
public interface PostService {
    public List<Post> findByPhrase(String phrase);

    public List<Post> findByUser(String login);

    public List<Post> findByUser(Integer userId);

    public List<Post> findAvailablePosts(String login);

    public List<Post> findAvailablePosts(Integer userId);

    public List<Post> findAll();

    public Post findById(Integer postId);

    public boolean isUserPost(Integer postId, Integer userId);

    public boolean create(Post post);

    public boolean update(Post post);

    public boolean delete(Post post);
}
