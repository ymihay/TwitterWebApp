package web.usermanager;

import core.domain.User;

/**
 * Created by Admin on 25.05.2014.
 */
public interface UserManager {
    User getUser();

    boolean isLoggedIn();

    boolean login(String login, String password);

    void logout();
}