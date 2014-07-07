package web.rest.friend;

import core.domain.Post;
import core.domain.User;
import core.service.post.PostService;
import core.service.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * Created by Admin on 07.07.2014.
 */
@Controller
@RequestMapping(value = "/friends", method = RequestMethod.GET)
public class FriendQueriesController {
    @Autowired
    private UserService userService;

    @Autowired
    private PostService postService;

    /**
     * Returns a list of user IDs for every user the specified user is following (otherwise known as their "friends")
     *
     * @return
     */
    @RequestMapping(value = "/{id}")
    //curl -H Accept:application/json http://localhost:8080/rest/friends/3
    public
    @ResponseBody
    ResponseEntity<List> getFriends(@PathVariable Integer id) {
        List<User> users = userService.findSubscriptions(id);
        if (users.isEmpty()) {
            return new ResponseEntity<List>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<List>(users, HttpStatus.OK);
    }

    /**
     * Returns friends posts
     *
     * @param id
     * @return
     */
    @RequestMapping(value = "/{id}/tweets")
    //curl -H Accept:application/json http://localhost:8080/rest/friends/3/tweets
    public
    @ResponseBody
    ResponseEntity<List> getFriendsPosts(@PathVariable Integer id) {
        List<Post> posts = postService.findFriendsPosts(id);
        if (posts.isEmpty()) {
            return new ResponseEntity<List>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<List>(posts, HttpStatus.OK);
    }
}
