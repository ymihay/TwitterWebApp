package web.rest.home.post;

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
@RequestMapping(value = "/users", method = RequestMethod.GET)
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
    //curl -H Accept:application/json http://localhost:8080/rest/users/me/tweets
    @RequestMapping(value = "/me/tweets")
    public
    @ResponseBody
    ResponseEntity<List> getMyTweets() {
        User user = userManager.getUser();
        if (user != null) {
            List<Post> posts = postService.findByUser(user.getUserId());
            if (posts.isEmpty()) {
                return new ResponseEntity<List>(HttpStatus.NOT_FOUND);
            }
            return new ResponseEntity<List>(posts, HttpStatus.OK);
        }
        return new ResponseEntity<List>(HttpStatus.NOT_FOUND);
    }

    /**
     * Returns logged in user tweets with requested range count
     *
     * @return
     */
    //curl -H Accept:application/json http://localhost:8080/rest/users/me/tweets/pagination/1/100
    @RequestMapping(value = "/me/tweets/pagination/{start}/{end}")
    public
    @ResponseBody
    ResponseEntity<List> getMyTweetsPagination(@PathVariable Integer start,
                                               @PathVariable Integer end) {
        User user = userManager.getUser();
        if (user != null) {
            List<Post> posts = postService.findByUserPagination(user.getUserId(), start, end);
            if (posts.isEmpty()) {
                return new ResponseEntity<List>(HttpStatus.NOT_FOUND);
            }
            return new ResponseEntity<List>(posts, HttpStatus.OK);
        }
        return new ResponseEntity<List>(HttpStatus.NOT_FOUND);
    }

    //curl -H Accept:application/json http://localhost:8080/rest/users/3/tweets
    @RequestMapping(value = "/{id}/tweets")
    public
    @ResponseBody
    ResponseEntity<List> getUserAvailableTweets(@PathVariable Integer id) {
        List<Post> posts = postService.findByUser(id);
        if (posts.isEmpty()) {
            return new ResponseEntity<List>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<List>(posts, HttpStatus.OK);
    }

    /**
     * Returns user with specified {id} tweets with requested range count
     *
     * @return
     */
    //curl -H Accept:application/json http://localhost:8080/rest/users/3/tweets/pagination/1/100
    @RequestMapping(value = "/{id}/tweets/pagination/{start}/{end}")
    public
    @ResponseBody
    ResponseEntity<List> getUserAvailableTweetsPagination(@PathVariable Integer id,
                                                          @PathVariable Integer start,
                                                          @PathVariable Integer end) {
        List<Post> posts = postService.findByUserPagination(id, start, end);
        if (posts.isEmpty()) {
            return new ResponseEntity<List>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<List>(posts, HttpStatus.OK);
    }

    //curl -H Accept:application/json http://localhost:8080/rest/users/tweets/{tweetId}
    @RequestMapping(value = "tweets/{tweetId}")
    public
    @ResponseBody
    ResponseEntity<Post> getTweet(@PathVariable Integer tweetId) {
        Post post = postService.findById(tweetId);
        if (post == null) {
            return new ResponseEntity<Post>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<Post>(post, HttpStatus.OK);
    }
}
