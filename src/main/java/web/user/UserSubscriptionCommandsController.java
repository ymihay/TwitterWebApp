package web.user;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
public class UserSubscriptionCommandsController {

    @Autowired
    private UserService userService;

    @Autowired
    private UserManager userManager;

    private static Logger LOG = LoggerFactory.getLogger(UserSubscriptionCommandsController.class);
    private static final String viewUserRedirect = "redirect:viewuser";

    @ExceptionHandler(Exception.class)
    public void handleExceptions(Exception exception) {
        LOG.error(exception.getStackTrace().toString());
    }

    @RequestMapping(value = "/setsubscription", params = "userid")
    private String setSubscription(Model model, @RequestParam("userid") Integer userSubscribedOnId) {
        // TODO: add check that set subscription for authorized user
        userService.setSubscription(userManager.getUser().getUserId(), userSubscribedOnId);
        model.addAttribute("userid", userSubscribedOnId);
        return viewUserRedirect;
    }

    @RequestMapping(value = "/unsetsubscription", params = "userid")
    public String unSetSubscription(Model model, @RequestParam("userid") Integer userSubscribedOnId) {
        // TODO: add check that unset subscription for authorized user
        userService.unSetSubscription(userManager.getUser().getUserId(), userSubscribedOnId);
        model.addAttribute("userid", userSubscribedOnId);
        return viewUserRedirect;
    }
}