package web.usermanager;

import core.domain.User;
import core.service.user.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Component;


/**
 * Created by Admin on 01.06.2014.
 */
@Component(value = "userManager")
@Scope(value = "session", proxyMode = ScopedProxyMode.INTERFACES)
public class UserManagerImpl implements UserManager {

    private static Logger LOG = LoggerFactory.getLogger(UserManagerImpl.class);

    private User user;
    private boolean loggedIn;

    @Autowired
    private UserService userService;

    @Override
    public User getUser() {
        return user;
    }

    @Override
    public boolean isLoggedIn() {
        return loggedIn;
    }

    @Override
    public boolean login(String login, String password) {
        this.user = userService.findByLogin(login);
        if ((this.user == null) || (this.user.getPassword().compareTo(password) != 0)) {
            //return false;
        }
        loggedIn = true;
        return true;
    }

    @Override
    public void logout() {
        this.user = null;
        loggedIn = false;
    }
}
