package web.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import service.user.UserService;
import web.usermanager.UserManager;

/**
 * Created by Admin on 12.06.2014.
 */
@Controller
public class UserSubscription {

    @Autowired
    private UserService userService;

    @Autowired
    private UserManager userManager;

    private static final String errorTemplate = "error/error";
    private static final String viewUserRedirect = "redirect:viewuser";

    @ExceptionHandler(Exception.class)
    public String handleExceptions(Exception exception) {
        exception.printStackTrace();
        return errorTemplate;
    }

    @RequestMapping(value = "/setsubscription", params = "userid")
    public String setSubscription(Model model, @RequestParam("userid") Integer userSubscribedOnId) {
        userService.setSubscription(userManager.getUser().getUserId(), userSubscribedOnId);
        model.addAttribute("userid", userSubscribedOnId);
        return viewUserRedirect;
    }

    @RequestMapping(value = "/unsetsubscription", params = "userid")
    public String unSetSubscription(Model model, @RequestParam("userid") Integer userSubscribedOnId) {
        userService.unSetSubscription(userManager.getUser().getUserId(), userSubscribedOnId);
        model.addAttribute("userid", userSubscribedOnId);
        return viewUserRedirect;
    }
}