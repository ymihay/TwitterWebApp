package web.user;

import domain.Country;
import domain.Sex;
import domain.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import service.user.UserService;

import java.sql.Timestamp;


/**
 * Created by Admin on 24.05.2014.
 */

@Controller
@RequestMapping(value="/adduser")
public class AddUserController {
    private UserService userService;

    @ExceptionHandler(Exception.class)
    public String handleExceptions(Exception exception) {
        exception.printStackTrace(); // do something better than this ;)

        //Model.addAttribute("errorMessage" , exception.getMessage());
        return "error";
    }

    @Autowired
    public AddUserController(UserService userService) {
        this.userService = userService;
    }

    @ModelAttribute
    private User createUser(
            @RequestParam(required = false) String firstName,
            @RequestParam(required = false) String patronymic,
            @RequestParam(required = false) String lastName,
            @RequestParam String login,
            @RequestParam String password,
            @RequestParam(required = false) Integer sexId,
            @RequestParam(required = false) Integer countryId,
            @RequestParam(required = false) Timestamp birthdate) {
        User user = new User(login, password);
        user.setFirstName(firstName);
        user.setLastName(lastName);
        user.setPatronymic(patronymic);
        user.setBirthdate(birthdate);
        user.setCountry(new Country(countryId, "C"));
        user.setSex(new Sex(sexId, "S"));
        return user;
    }

    @RequestMapping
    public String addUser(@ModelAttribute User user){
        System.out.println("I'm here!");
        /*if (!userService.checkIfExists(user)) {
            userService.create(user);
        }*/
        return "ViewAllUsers";
    }
}
