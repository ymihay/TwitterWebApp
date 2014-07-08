package core.service.user;

import core.domain.User;

import java.util.List;

/**
 * Created by Admin on 24.05.2014.
 */
public interface UserService {
    public User findByLogin(String login);

    public User findById(Integer id);

    public List<User> findAll();

    public List<User> findSubscribedOnUser(String login);

    public List<User> findSubscriptions(String login);

    public List<User> findSubscribedOnUser(Integer id);

    public List<User> findSubscriptions(Integer id);

    public boolean setSubscription(String login, String loginSubscribedOn);

    public boolean unSetSubscription(String login, String loginSubscribedOn);

    public boolean setSubscription(Integer userId, Integer userSubscribedOnId);

    public boolean unSetSubscription(Integer userId, Integer userSubscribedOnId);

    public boolean isFollowingForUser(Integer userId, Integer followingUserId);

    public Integer create(User user);

    public boolean update(User user);

    public boolean delete(User user);

    public boolean checkIfExists(User user);
}
