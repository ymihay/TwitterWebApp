package repository.user;

import domain.User;
import repository.jdbc.ConnectionFactory;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * UserDAO: Admin
 * Date: 16.03.14
 * Time: 17:01
 * To change this template use File | Settings | File Templates.
 */
public interface UserDAO {

    public void setCnFactory(ConnectionFactory cnFactory);

    public User find(String login);

    public User find(Integer id);

    public List<User> findAll();

    public List<User> findSubscribedOnUser(String login);

    public List<User> findSubscriptions(String login);

    public List<User> findSubscribedOnUser(Integer id);

    public List<User> findSubscriptions(Integer id);

    public boolean isFollowingForUser(Integer userId, Integer followingUserId);

    public boolean setSubscription(String login, String loginSubscribedOn);

    public boolean unSetSubscription(String login, String loginSubscribedOn);

    public boolean setSubscription(Integer userId, Integer userSubscribedOnId);

    public boolean unSetSubscription(Integer userId, Integer userSubscribedOnId);

    public boolean create(User user);

    public boolean update(User user);

    public boolean delete(User user);
}
