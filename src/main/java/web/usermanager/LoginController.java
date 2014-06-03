package web.usermanager;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * Created by Admin on 01.06.2014.
 */
@Controller
public class LoginController {
    private final String LOGIN_PAGE = "authentication/LoginPage";
    private final String LOGIN_REDIRECT = "redirect:login";
    private final String VIEW_ALL_REDIRECT = "redirect:viewall";

    @Autowired
    private UserManager userManager;

    @RequestMapping("/loginpage")
    public String login(){
        return LOGIN_PAGE;
    }

    @RequestMapping(value="/login")
    public String tryToLogin(@RequestParam(required = false) String user){
        if (user == null) {
            return LOGIN_PAGE;
        }
        if (userManager.isLoggedIn()) {
            return VIEW_ALL_REDIRECT;
        }
        userManager.login(user);
        return VIEW_ALL_REDIRECT;
    }

    @RequestMapping("/logout")
    public String logout(){
        userManager.logout();
        return LOGIN_REDIRECT;
    }
}
