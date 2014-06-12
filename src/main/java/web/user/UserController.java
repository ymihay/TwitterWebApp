package web.user;

import domain.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import service.country.CountryService;
import service.sex.SexService;
import service.user.UserService;
import web.usermanager.UserManager;

/**
 * Created by Admin on 02.06.2014.
 */
@Controller
public class UserController {
    @Autowired
    private UserService userService;

    @Autowired
    private SexService sexService;

    @Autowired
    private CountryService countryService;

    @Autowired
    private UserManager userManager;

    private static String errorTemplate = "error/error";
    private static String registerTemplate = "user/modifyuser";
    private static String viewAllUsersTemplate = "user/viewusers";
    private static String viewUserTemplate = "user/viewuser";
    private static String viewNoHitsTemplate = "search/nohits";

    @ExceptionHandler(Exception.class)
    public String handleExceptions(Exception exception) {
        exception.printStackTrace();
        return errorTemplate;
    }

    @RequestMapping("/viewall")
    public String viewAllUsers(Model model) {
        model.addAttribute("users", userService.findAll());
        model.addAttribute("user", userManager.getUser());
        return viewAllUsersTemplate;
    }

    public void initModelForAvailableInteractions(Model model, Integer userId) {
        Integer loggedUserId = userManager.getUser().getUserId();
        if (loggedUserId.compareTo(userId) == 0) {
            model.addAttribute("isLoggedUser", true);
            model.addAttribute("isFollowing", null);
        } else {
            if (userService.isFollowingForUser(loggedUserId, userId)) {
                model.addAttribute("isFollowing", true);
            } else {
                model.addAttribute("isFollowing", false);
            }

        }
    }

    @RequestMapping(value = "/viewuser",params = "userid")
    public String viewUser(Model model,@RequestParam("userid") Integer userId) {
        initModelForAvailableInteractions(model, userId);
        model.addAttribute("user", userService.findById(userId));
        return viewUserTemplate;
    }

    @RequestMapping("/viewfollowingusers")
    public String viewFollowingUsers(Model model, @RequestParam("userid") Integer id) {
        model.addAttribute("users", userService.findSubscriptions(id));
        model.addAttribute("user", userManager.getUser());
        return viewAllUsersTemplate;
    }

    @RequestMapping("/viewfollowers")
    public String viewFollowers(Model model, @RequestParam("userid") Integer id) {
        model.addAttribute("users", userService.findSubscribedOnUser(id));
        model.addAttribute("user", userManager.getUser());
        return viewAllUsersTemplate;
    }

    public void initUserDictionaries(Model model) {
        model.addAttribute("sex", sexService.findAll());
        model.addAttribute("countries", countryService.findAll());
    }

    @RequestMapping("/register")
    public String registerUser(Model model) {
        initUserDictionaries(model);
        model.addAttribute("action", "adduser");
        return registerTemplate;
    }

    @RequestMapping("/modifyuser")
    public String modifyUser(Model model) {
        initUserDictionaries(model);
        model.addAttribute("user", userManager.getUser());
        model.addAttribute("action", "updateuser");
        return registerTemplate;
    }

    @RequestMapping(value = "/finduser", params = "login")
    public String viewUser(Model model, @RequestParam("login") String login) {
        User user = userService.findByLogin(login);
        if (user != null) {
            initModelForAvailableInteractions(model, user.getUserId());
            model.addAttribute("user", user);
            return viewUserTemplate;
        }
        return viewNoHitsTemplate;
    }
}