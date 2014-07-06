package web.rest.user;

import core.domain.User;
import core.service.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import web.usermanager.UserManager;

import java.util.List;

/**
 * Created by Admin on 05.07.2014.
 */
@Controller
@RequestMapping("users")
public class UserQueriesController {

    @Autowired
    private UserService userService;

    @Autowired
    private UserManager userManager;

    @RequestMapping(method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    //curl -H Accept:application/json http://localhost:8080/rest/users
    public
    @ResponseBody
    List<User> getAllUsers() {
        return userService.findAll();
    }

    @RequestMapping(method = RequestMethod.GET,
            value = "/{id}",
            headers = "Accept=application/json")
    public
    @ResponseBody
    ResponseEntity<User> getUser(@PathVariable Integer id) {
        User user = userService.findById(id);
        if (user == null) {
            return new ResponseEntity<User>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<User>(user, HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.GET,
            value = "/{login}/{login}",
            headers = "Accept=application/json")
    public
    @ResponseBody
    ResponseEntity<User> viewUser(@PathVariable String login) {
        User user = userService.findByLogin(login);
        if (user == null) {
            return new ResponseEntity<User>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<User>(user, HttpStatus.OK);
    }
}
