package web.mvc.user;

import core.domain.Country;
import core.domain.Sex;
import core.domain.User;
import core.service.user.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import web.usermanager.UserManager;

import javax.servlet.http.HttpSession;
import java.sql.Timestamp;


/**
 * Created by Admin on 24.05.2014.
 */

@Controller
public class UserCommandsController {

    private UserService userService;
    private UserManager userManager;

    private static Logger LOG = LoggerFactory.getLogger(UserCommandsController.class);
    private static final String viewAllUsersRedirect = "redirect:viewall";
    private static final String viewUserRedirect = "redirect:viewuser";
    private static final String modifyUserRedirect = "redirect:modifyuser";
    private static final String logoutReirect = "redirect:logout";
    private static final String registerTemplate = "user/modifyuser";

    @Autowired
    public UserCommandsController(UserService userService, UserManager userManager) {
        this.userService = userService;
        //this.userManager = userManager;
    }

    @ExceptionHandler(Exception.class)
    public void handleExceptions(Exception exception) {
        LOG.error(exception.getStackTrace().toString());
    }

    @ModelAttribute
    public User getUserModel(
            @RequestParam(required = false) String firstName,
            @RequestParam(required = false) String patronymic,
            @RequestParam(required = false) String lastName,
            @RequestParam(required = false) String login,
            @RequestParam(required = false) String password,
            @RequestParam(required = false) Integer sexId,
            @RequestParam(required = false) Integer countryId,
            @RequestParam(required = false) Timestamp birthdate) {
        User user = new User(login, password);
        user.setFirstName(firstName);
        user.setLastName(lastName);
        user.setPatronymic(patronymic);
        user.setBirthdate(birthdate);
        user.setCountry(new Country(countryId));
        user.setSex(new Sex(sexId));
        return user;
    }

    @RequestMapping("/register")
    public String registerUser(Model model) {
        model.addAttribute("action", "adduser");
        return registerTemplate;
    }

    @RequestMapping(value = "/adduser")
    public String addUser(@ModelAttribute User user, Model model) {
        if ((user.getLogin() == null) || (user.getPassword() == null)) {
            model.addAttribute("errorMessage", "Please, fill login and password");
            return modifyUserRedirect;
        }

        if (!userService.checkIfExists(user)) {
            userService.create(user);
            userManager.login(user.getLogin(), user.getPassword());
        }
        return viewAllUsersRedirect;
    }

    @RequestMapping("/modifyuser")
    public String modifyUser(Model model) {
        //initUserDictionaries(model);
        System.out.println("HERE!");
        model.addAttribute("user", userManager.getUser());
        model.addAttribute("action", "updateuser");
        System.out.println("HERE!!!");
        return registerTemplate;
    }

    @RequestMapping(value = "/updateuser")
    public String updateUser(@ModelAttribute User user, Model model, HttpSession session) {
        model.addAttribute("userid", userManager.getUser().getUserId());
        //set non-updatable values
        user.setUserId(userManager.getUser().getUserId());
        user.setLogin(userManager.getUser().getLogin());
        userService.update(user);
        // update info in User manager via relogin
        if (userManager.login(user.getLogin(), user.getPassword())) {
            session.setAttribute("loggedUserId", userManager.getUser().getUserId());
        }
        return viewUserRedirect;
    }

    @RequestMapping(value = "/removeuser")
    public String deleteUser() {
        userService.delete(userManager.getUser());
        return logoutReirect;
    }
}
