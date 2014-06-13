package web.post;

import domain.Post;
import domain.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import service.post.PostService;
import service.user.UserService;
import web.usermanager.UserManager;

/**
 * Created by Admin on 06.06.2014.
 */
@Controller
public class PostCommandsController {

    private PostService postService;
    private UserManager userManager;

    private static Logger LOG = LoggerFactory.getLogger(PostCommandsController.class);
    private static final String viewPostsRedirect = "redirect:viewuserposts";
    private static final String modifyPostTemplate = "post/modifypost";

    @ExceptionHandler(Exception.class)
    public void handleExceptions(Exception exception) {
        LOG.error(exception.getStackTrace().toString());
    }

    @Autowired
    public PostCommandsController(UserService userService, PostService postService) {
        this.postService = postService;
    }

    @ModelAttribute
    private Post getPostModel(
            @RequestParam(required = false) String postMessage,
            @RequestParam(required = false) Integer postId) {
        User user = userManager.getUser();
        Post post = new Post(postMessage, user);
        post.setPostId(postId);
        return post;
    }

    @RequestMapping(value = "/createpost")
    private String createPost(Model model) {
        model.addAttribute("userId", userManager.getUser().getUserId());
        model.addAttribute("action" , "addpost");
        return modifyPostTemplate;
    }

    @RequestMapping(value = "/addpost")
    private String addPost(@ModelAttribute Post post, Model model) {
        postService.create(post);
        model.addAttribute("userid", userManager.getUser().getUserId());
        return viewPostsRedirect;
    }

    @RequestMapping(value = "/modifypost", params = "postid")
    private String modifyPost(Model model, @RequestParam("postid") Integer postId) {
        Post post = postService.findById(postId);
        if (post != null) {
            model.addAttribute("post", post);
            model.addAttribute("action" , "updatepost");
        } else {
            model.addAttribute("action" , "addpost");
        }
        return modifyPostTemplate;
    }

    @RequestMapping(value = "/updatepost")
    private String updatePost(@ModelAttribute Post newPost, Model model) {
        model.addAttribute("userid", userManager.getUser().getUserId());
        // This is check that user modify just his own posts
        Post oldPost = postService.findById(newPost.getPostId());
        if ((newPost != null) && newPost.getUser().equals(oldPost.getUser())) {
            postService.update(newPost);
        }
        return viewPostsRedirect;
    }

    @RequestMapping(value = "/removepost", params = "postid")
    private String deletePost(Model model, @RequestParam("postid") Integer postId) {
        model.addAttribute("userid", userManager.getUser().getUserId());
        // This is check that user delete just his own posts
        Post post = postService.findById(postId);
        if ((post != null) && post.getUser().equals(userManager.getUser())) {
            postService.delete(post);
        }
        return viewPostsRedirect;
    }
}
