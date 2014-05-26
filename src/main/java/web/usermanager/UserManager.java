package main.java.web.usermanager;

/**
 * Created by Admin on 25.05.2014.
 */
public interface UserManager {
    String getUser();

    boolean isLoggedIn();

    void login(String user);

    void logout();
}