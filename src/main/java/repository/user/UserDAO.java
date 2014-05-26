package main.java.repository.user;

import main.java.domain.User;
import main.java.repository.jdbc.ConnectionFactory;

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

    public boolean setSubscription(String login, String loginSubscribedOn);

    public boolean unSetSubscription(String login, String loginSubscribedOn);

    public boolean create(User user);

    public boolean update(User user);

    public boolean delete(User user);
}
