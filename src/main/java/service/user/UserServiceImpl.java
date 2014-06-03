package service.user;

import domain.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import repository.user.UserDAO;

import java.util.List;

/**
 * Created by Admin on 24.05.2014.
 */
@Service("UserService")
public class UserServiceImpl implements UserService {
    @Autowired
    private UserDAO repository;

    @Autowired
    public UserServiceImpl(UserDAO userDAO) {
        this.repository = userDAO;
    }

    @Override
    public User findByLogin(String login) {
        return repository.find(login);
    }

    @Override
    public User findById(Integer id) {
        return repository.find(id);
    }

    @Override
    public List<User> findAll() {
        return repository.findAll();
    }

    @Override
    public List<User> findSubscribedOnUser(String login) {
        return repository.findSubscribedOnUser(login);
    }

    @Override
    public List<User> findSubscriptions(String login) {
        return repository.findSubscribedOnUser(login);
    }

    @Override
    public boolean setSubscription(String login, String loginSubscribedOn) {
        return repository.setSubscription(login, loginSubscribedOn);
    }

    @Override
    public boolean unSetSubscription(String login, String loginSubscribedOn) {
        return unSetSubscription(login, loginSubscribedOn);
    }

    @Override
    public boolean create(User user) {
        return repository.create(user);
    }

    @Override
    public boolean update(User user) {
        return repository.update(user);
    }

    @Override
    public boolean delete(User user) {
        return repository.delete(user);
    }

    @Override
    public boolean checkIfExists(User user) {
        if (user == null || user.getLogin() == null) {
            return false;
        }
        Integer userId = user.getUserId();
        String userLogin = user.getLogin();
        if (userId != null) {
            return repository.find(userId) != null;
        } else {
            User foundUser = repository.find(userLogin);
            return foundUser != null ? true : false;
        }
    }
}