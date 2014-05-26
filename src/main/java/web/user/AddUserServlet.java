package main.java.web.user;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * Created by Admin on 24.05.2014.
 */

@Controller
@RequestMapping(value="/pages/adduser")
public class AddUserServlet {
    /*private UserService userService;

    @Autowired
    public AddUserServlet(UserService userService) {
        this.userService = userService;
    }

    @ModelAttribute
    private User createUser(
            @RequestParam String firstName,
            @RequestParam String patronymic,
            @RequestParam String lastName,
            @RequestParam String login,
            @RequestParam String password,
            @RequestParam Integer sexId,
            @RequestParam Integer countryId,
            @RequestParam Timestamp birthdate) {
        User user = new User(login, password);
        user.setFirstName(firstName);
        user.setLastName(lastName);
        user.setPatronymic(patronymic);
        user.setBirthdate(birthdate);
        user.setCountry(new Country(countryId, "C"));
        user.setSex(new Sex(sexId, "S"));
        return user;
    }*/

    @RequestMapping(method = RequestMethod.GET)
    public String addUser(/*@ModelAttribute User user*/){
        System.out.println("I'm here!");
        /*if (!userService.checkIfExists(user)) {
            userService.create(user);
        }*/
        return "ViewAll";
    }
}
