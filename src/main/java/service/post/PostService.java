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

    public Integer userPostCount(Integer userID);

    public List<Post> findByUserPagination(Integer userId, Integer startPos, Integer endPos);

    public List<Post> findAvailablePosts(String login);

    public List<Post> findAvailablePosts(Integer userId);

    public Integer availablePostCount(Integer userID);

    public List<Post> findAvailablePostsPagination(Integer userId, Integer startPos, Integer endPos);

    public List<Post> findAll();

    public Post findById(Integer postId);

    public boolean isUserPost(Integer postId, Integer userId);

    public boolean create(Post post);

    public boolean update(Post post);

    public boolean delete(Post post);
}
