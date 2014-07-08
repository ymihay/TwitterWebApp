package web.rest.home.post;

import core.domain.Post;
import core.domain.User;
import core.service.post.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;
import web.usermanager.UserManager;

/**
 * Created by Admin on 07.07.2014.
 */
@Controller
@RequestMapping(value = "/users/me/tweets")
public class PostCommandsController {

    @Autowired
    private PostService postService;

    @Autowired
    private UserManager userManager;

    //curl -v -X POST -H content-type:application/json --data '{"postId":null,"postMessage":"ggg","user":{"userId":3,"firstName":"Yana :)","patronymic":"Mikhaylovna :)","lastName":"Mikhaylenko :)","login":"ymikhaylenko","password":"yanayana","sex":null,"country":null,"birthdate":null,"subscribedList":null,"subscribedOnUserList":null,"postList":null}}' http://localhost:8080/rest/users/me/tweets
    @RequestMapping(method = RequestMethod.POST, consumes = "application/json", produces = "application/json")
    public
    @ResponseBody
    ResponseEntity<Post> addPost(@RequestBody Post post, UriComponentsBuilder builder) {
        User currentUser = userManager.getUser();

        if (post.getUser().equals(currentUser)) {
            return new ResponseEntity<Post>(HttpStatus.CONFLICT);
        }

        Integer newPostId = postService.create(post);

        if (!(newPostId > 0)) {
            return new ResponseEntity<Post>(HttpStatus.FORBIDDEN);
        }

        Post newPost = postService.findById(newPostId);

        if (newPost == null) {
            return new ResponseEntity<Post>(HttpStatus.FORBIDDEN);
        }

        HttpHeaders headers = new HttpHeaders();
        headers.setLocation(
                builder.path("/rest/users/tweets/{id}")
                        .buildAndExpand(newPostId.toString()).toUri()
        );

        return new ResponseEntity<Post>(newPost, headers, HttpStatus.CREATED);
    }

    /**
     * Delete user post
     *
     * @param id
     * @return
     */
    //curl -v -X DELETE -H content-type:application/json http://localhost:8080/rest/users/me/tweets/{id}
    @RequestMapping(method = RequestMethod.DELETE)
    public
    @ResponseBody
    ResponseEntity deletePost(@PathVariable Integer id) {
        User user = userManager.getUser();
        Post post = postService.findById(id);
        if (post == null) {
            return new ResponseEntity(HttpStatus.CONFLICT);
        }

        if (!post.getUser().equals(user)) {
            return new ResponseEntity(HttpStatus.FORBIDDEN);
        }

        if (postService.delete(post)) {
            return new ResponseEntity(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity(HttpStatus.METHOD_NOT_ALLOWED);
    }
}