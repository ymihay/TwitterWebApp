package web.rest.home.user;

import core.domain.User;
import core.service.post.PostService;
import core.service.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
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

    //curl -H Accept:application/json http://localhost:8080/rest/users/
    @RequestMapping
    @ResponseStatus(HttpStatus.OK)
    public
    @ResponseBody
    List<User> getAllUsers() {
        List<User> users = userService.findAll();
        return users;
    }

    /*@RequestMapping
    @ResponseStatus(HttpStatus.OK)
    public
    @ResponseBody
    List<User> getAllUsersPaginated(@RequestParam("page") final int page, @RequestParam("size") final int size,
                                    final UriComponentsBuilder uriBuilder) {
        List<User> users = userService.findAll();
        //final Page<User> resultPage = userService.findservice.findPaginated(page, size);
        return users;
    }*/

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
    ResponseEntity<User> getMyUserId(HttpServletRequest request) {
        User user = userService.findByLogin(request.getRemoteUser());
        if (user != null) {
            return new ResponseEntity<User>(user, HttpStatus.OK);
        }
        return new ResponseEntity<User>(HttpStatus.NO_CONTENT);
    }
}
