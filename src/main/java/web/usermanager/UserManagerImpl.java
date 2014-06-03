package web.usermanager;

import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Component;

/**
 * Created by Admin on 01.06.2014.
 */
@Component("userManager")
@Scope(value = "session", proxyMode = ScopedProxyMode.INTERFACES)
public class UserManagerImpl implements UserManager {
    private String user;
    private boolean loggedIn;

    @Override
    public String getUser() {
        return user;
    }

    @Override
    public boolean isLoggedIn() {
        return loggedIn;
    }

    @Override
    public void login(String user) {
        this.user = user;
        loggedIn = true;
    }

    @Override
    public void logout() {
        this.user = null;
        loggedIn = false;
    }
}
