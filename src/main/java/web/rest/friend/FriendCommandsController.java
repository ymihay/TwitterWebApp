package web.rest.friend;

import core.service.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import web.usermanager.UserManager;

/**
 * Created by Admin on 07.07.2014.
 */
@Controller
@RequestMapping(value = "/friends")
public class FriendCommandsController {
    @Autowired
    private UserService userService;

    @Autowired
    private UserManager userManager;

    /**
     * Sets subscription on user. Similar to "add to friend action"
     *
     * @param id
     * @return
     */
    //curl -v -X POST -H content-type:application/json http://localhost:8080/rest/friends/3
    @RequestMapping(value = "/{id}", method = RequestMethod.POST)
    public
    @ResponseBody
    ResponseEntity addFriend(@PathVariable Integer id) {
        Integer userId = userManager.getUser().getUserId();
        if (userService.isFollowingForUser(userId, id)) {
            return new ResponseEntity(HttpStatus.CONFLICT);
        }

        if (userService.setSubscription(userId, id)) {
            return new ResponseEntity(HttpStatus.CREATED);
        }
        return new ResponseEntity(HttpStatus.METHOD_NOT_ALLOWED);
    }

    /**
     * Unset subscription on user. Similar to "remove from friends" action
     *
     * @param id
     * @return
     */
    //curl -v -X DELETE -H content-type:application/json http://localhost:8080/rest/friends/3
    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public
    @ResponseBody
    ResponseEntity deleteFriend(@PathVariable Integer id) {
        Integer userId = userManager.getUser().getUserId();
        if (!userService.isFollowingForUser(userId, id)) {
            return new ResponseEntity(HttpStatus.CONFLICT);
        }

        if (userService.unSetSubscription(userId, id)) {
            return new ResponseEntity(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity(HttpStatus.METHOD_NOT_ALLOWED);
    }
}
