package web.rest.follower;

import core.domain.User;
import core.service.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * Created by Admin on 07.07.2014.
 */
@Controller
@RequestMapping(value = "/followers", method = RequestMethod.GET)
public class FollowerQueriesController {

    @Autowired
    private UserService userService;

    /**
     * Returns a list of user  IDs for every user following the specified user.
     *
     * @return
     */
    //curl -H Accept:application/json http://localhost:8080/rest/followers/3
    @RequestMapping(value = "/{id}")
    public
    @ResponseBody
    ResponseEntity<List> getFollowers(@PathVariable Integer id) {
        List<User> users = userService.findSubscriptions(id);
        if (users.isEmpty()) {
            return new ResponseEntity<List>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<List>(users, HttpStatus.OK);
    }

    //curl -H Accept:application/json http://localhost:8080/rest/followers/me
    @RequestMapping(value = "/me")
    public
    @ResponseBody
    ResponseEntity<List> getMyFollowers(HttpServletRequest request) {
        List<User> users = userService.findSubscriptions(request.getRemoteUser());
        if (users.isEmpty()) {
            return new ResponseEntity<List>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<List>(users, HttpStatus.OK);
    }
}
