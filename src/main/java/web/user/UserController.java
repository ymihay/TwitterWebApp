package web.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
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
    private UserManager userManager;

    @RequestMapping("/register")
    public String createStudent(){
        return "authentication/AddUser";
    }

    @RequestMapping("/viewall")
    public String viewAllStudents(Model model){
        model.addAttribute("users", userService.findAll());
        System.out.print(userService.findAll().size());
        model.addAttribute("user", userManager.getUser());
        return "ViewAllUsers";
    }
}
