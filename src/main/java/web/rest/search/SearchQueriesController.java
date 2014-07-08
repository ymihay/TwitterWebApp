package web.rest.search;

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
@RequestMapping(value = "/search", method = RequestMethod.GET)
public class SearchQueriesController {
    @Autowired
    private UserService userService;

    @Autowired
    private PostService postService;

    //curl -H Accept:application/json http://localhost:8080/rest/search/tweets/{phrase}
    @RequestMapping(value = "/tweets/{phrase}")
    public
    @ResponseBody
    ResponseEntity<List> findPostsByPhrase(@PathVariable String phrase) {
        List<Post> posts = postService.findByPhrase(phrase);
        if (posts.isEmpty()) {
            return new ResponseEntity<List>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<List>(posts, HttpStatus.OK);
    }

    //curl -H Accept:application/json http://localhost:8080/rest/search/users/{login}
    @RequestMapping(value = "/users/{login}")
    public
    @ResponseBody
    ResponseEntity<User> findUsersByLogin(@PathVariable String login) {
        User user = userService.findByLogin(login);
        if (user == null) {
            return new ResponseEntity<User>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<User>(user, HttpStatus.OK);
    }
}
