package web.rest.home.user;

import core.domain.User;
import core.service.post.PostService;
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
@RequestMapping(value = "/users", method = RequestMethod.GET)
public class UserQueriesController {

    @Autowired
    private UserService userService;

    @Autowired
    private PostService postService;

    @Autowired
    private UserManager userManager;


    @ResponseStatus(HttpStatus.OK)
    public
    @ResponseBody
    List<User> getAllUsers() {
        return userService.findAll();
    }

    //curl -H Accept:application/json http://localhost:8080/rest/users/3
    @RequestMapping(value = "/{id}",
            headers = "Accept=application/json")
    public
    @ResponseBody
    ResponseEntity<User> getUserById(@PathVariable Integer id) {
        User user = userService.findById(id);
        if (user == null) {
            return new ResponseEntity<User>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<User>(user, HttpStatus.OK);
    }

    //curl -H Accept:application/json http://localhost:8080/rest/users/me
    @RequestMapping(value = "/me",
            headers = "Accept=application/json")
    public
    @ResponseBody
    ResponseEntity<User> getMyUserId() {
        User user = userManager.getUser();
        return new ResponseEntity<User>(user, HttpStatus.OK);
    }
}
