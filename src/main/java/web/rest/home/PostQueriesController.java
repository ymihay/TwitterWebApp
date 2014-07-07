package web.rest.home;

import core.domain.Post;
import core.domain.User;
import core.service.post.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import web.usermanager.UserManager;

import java.util.List;

/**
 * Created by Admin on 07.07.2014.
 */
@Controller
@RequestMapping(value = "/users/", method = RequestMethod.GET)
public class PostQueriesController {
    @Autowired
    private PostService postService;

    @Autowired
    private UserManager userManager;

    /**
     * Returns logged in user tweets
     *
     * @return
     */
    @RequestMapping(value = "/me/tweets")
    //curl -H Accept:application/json http://localhost:8080/rest/users/me/tweets
    public
    @ResponseBody
    ResponseEntity<List> getMyTweets() {
        User user = userManager.getUser();
        if (user != null) {
            List<Post> posts = postService.findByUser(3);
            if (posts.isEmpty()) {
                return new ResponseEntity<List>(HttpStatus.NOT_FOUND);
            }
            return new ResponseEntity<List>(posts, HttpStatus.OK);
        }
        return new ResponseEntity<List>(HttpStatus.NOT_FOUND);
    }

    @RequestMapping(value = "/{id}/tweets")
    //curl -H Accept:application/json http://localhost:8080/rest/users/3/tweets
    public
    @ResponseBody
    ResponseEntity<List> getUserAvailableTweets(@PathVariable Integer id) {
        List<Post> posts = postService.findByUser(id);
        if (posts.isEmpty()) {
            return new ResponseEntity<List>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<List>(posts, HttpStatus.OK);
    }
}
