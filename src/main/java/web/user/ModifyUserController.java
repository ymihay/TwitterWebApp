package web.user;

import domain.Country;
import domain.Sex;
import domain.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import service.user.UserService;
import web.usermanager.UserManager;

import javax.servlet.http.HttpServletRequest;
import java.sql.Timestamp;


/**
 * Created by Admin on 24.05.2014.
 */

@Controller
public class ModifyUserController {
    @Autowired
    private UserService userService;

    @Autowired
    private UserManager userManager;

    private static final String errorTemplate = "error/error";
    private static final String viewAllUsersRedirect = "redirect:viewall";
    private static final String viewUserRedirect = "redirect:viewuser";
    private static final String modifyUserRedirect = "redirect:modifyuser";
    private static final String logoutReirect = "redirect:logout";

    @ExceptionHandler(Exception.class)
    public String handleExceptions(Exception exception) {
        exception.printStackTrace();
        return errorTemplate;
    }

    @Autowired
    public ModifyUserController(UserService userService) {
        this.userService = userService;
    }

    @ModelAttribute
    private User createUser(
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

    @RequestMapping(value = "/updateuser")
    public String updateUser(@ModelAttribute User user, Model model, HttpServletRequest request) {
        model.addAttribute("userid", userManager.getUser().getUserId());
        //set non-updatable values
        user.setUserId(userManager.getUser().getUserId());
        user.setLogin(userManager.getUser().getLogin());
        userService.update(user);
        // update info in User manager via relogin
        if (userManager.login(user.getLogin(), user.getPassword())) {
            request.getSession().setAttribute("loggedUserId", userManager.getUser().getUserId());
        }
        return viewUserRedirect;
    }

    @RequestMapping(value = "/removeuser")
    public String deleteUser(Model model) {
        userService.delete(userManager.getUser());
        return logoutReirect;
    }
}
