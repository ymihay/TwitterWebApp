package web.usermanager;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by Admin on 01.06.2014.
 */
@Controller
public class LoginController {

    private static Logger LOG = LoggerFactory.getLogger(LoginController.class);
    private static final String LOGIN_PAGE = "authentication/login";
    private static final String LOGIN_REDIRECT = "redirect:login";
    private static final String VIEW_ALL_REDIRECT = "redirect:viewall";

    @Autowired
    private UserManager userManager;

    @RequestMapping("/loginpage")
    public String login() {
        return LOGIN_PAGE;
    }

    @RequestMapping(value = "/login")
    private String tryToLogin(@RequestParam(required = false) String login,
                             @RequestParam(required = false) String password,
                             Model model, HttpServletRequest request) {
        if (login == null) {
            LOG.warn("wrong credentials.");
            model.addAttribute("errorMessage", "Credentials are invalid. Please, try again");
            return LOGIN_PAGE;
        }
        if (userManager.isLoggedIn()) {
            return VIEW_ALL_REDIRECT;
        }
        if (userManager.login(login, password)) {
            model.addAttribute("user", userManager.getUser().getUserId());
            request.getSession().setAttribute("loggedUserId", userManager.getUser().getUserId());
        }
        return VIEW_ALL_REDIRECT;
    }

    @RequestMapping("/logout")
    private String logout() {
        userManager.logout();
        return LOGIN_REDIRECT;
    }
}
