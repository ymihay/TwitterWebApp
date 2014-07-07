package core.service.post;

import core.domain.Post;
import core.repository.post.PostDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by Admin on 06.06.2014.
 */
@Service("PostService")
public class PostServiceImpl implements PostService {
    @Autowired
    private PostDAO repository;

    @Autowired
    public PostServiceImpl(PostDAO postDAO) {
        this.repository = postDAO;
    }

    @Override
    public List<Post> findByPhrase(String phrase) {
        return repository.findByPhrase(phrase);
    }

    @Override
    public List<Post> findByUser(String login) {
        return repository.findByUser(login);
    }

    @Override
    public List<Post> findByUser(Integer userId) {
        return repository.findByUser(userId);
    }

    @Override
    public Integer userPostCount(Integer userID) {
        return repository.userPostCount(userID);
    }

    @Override
    public List<Post> findByUserPagination(Integer userId, Integer startPos, Integer endPos) {
        if (endPos >= startPos) {
            return repository.findByUserPagination(userId, startPos, endPos);
        }
        return null;
    }

    @Override
    public List<Post> findAvailablePosts(String login) {
        return repository.findAvailablePosts(login);
    }

    @Override
    public List<Post> findAvailablePosts(Integer userId) {
        return repository.findAvailablePosts(userId);
    }

    @Override
    public Integer availablePostCount(Integer userID) {
        return repository.availablePostCount(userID);
    }

    @Override
    public List<Post> findAvailablePostsPagination(Integer userId, Integer startPos, Integer endPos) {
        if (endPos >= startPos) {
            return repository.findAvailablePostsPagination(userId, startPos, endPos);
        }
        return null;
    }

    @Override
    public List<Post> findFriendsPosts(Integer userId) {
        return repository.findFriendsPosts(userId);
    }

    @Override
    public List<Post> findAll() {
        return repository.findAll();
    }

    @Override
    public Post findById(Integer postId) {
        return repository.findById(postId);
    }

    @Override
    public boolean isUserPost(Integer postId, Integer userId) {
        Post post = repository.findById(postId);
        return (post.getUser().getUserId().compareTo(userId) == 0) ? true : false;
    }

    @Override
    public boolean create(Post post) {
        return repository.create(post);
    }

    @Override
    public boolean update(Post post) {
        return repository.update(post);
    }

    @Override
    public boolean delete(Post post) {
        return repository.delete(post);
    }
}
