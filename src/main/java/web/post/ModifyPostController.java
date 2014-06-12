package web.post;

import domain.Post;
import domain.User;
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
public class ModifyPostController {
    @Autowired
    private PostService postService;
    @Autowired
    private UserManager userManager;

    private static String errorTemplate = "error/error";
    private static String viewPostsRedirect = "redirect:viewuserposts";
    private static String modifyPostTemplate = "post/modifypost";

    @ExceptionHandler(Exception.class)
    public String handleExceptions(Exception exception) {
        exception.printStackTrace();
        return errorTemplate;
    }

    @Autowired
    public ModifyPostController(UserService userService, PostService postService) {
        this.postService = postService;
    }

    @ModelAttribute
    private Post createPost(
            @RequestParam(required = false) String postMessage,
            @RequestParam(required = false) Integer postId) {
        User user = userManager.getUser();
        Post post = new Post(postMessage, user);
        post.setPostId(postId);
        return post;
    }

    @RequestMapping(value = "/addpost")
    public String addPost(@ModelAttribute Post post, Model model) {
        postService.create(post);
        model.addAttribute("userid", userManager.getUser().getUserId());
        return viewPostsRedirect;
    }

    @RequestMapping(value = "/updatepost")
    public String updatePost(@ModelAttribute Post newPost, Model model) {
        model.addAttribute("userid", userManager.getUser().getUserId());
        // This is check that user modify just his own posts
        Post oldPost = postService.findById(newPost.getPostId());
        if ((newPost != null) && newPost.getUser().equals(oldPost.getUser())) {
            postService.update(newPost);
        }
        return viewPostsRedirect;
    }

    @RequestMapping(value = "/removepost",
            params = "postid")
    public String deletePost(Model model, @RequestParam("postid") Integer postId) {
        model.addAttribute("userid", userManager.getUser().getUserId());
        // This is check that user delete just his own posts
        Post post = postService.findById(postId);
        if ((post != null) && post.getUser().equals(userManager.getUser())) {
            postService.delete(post);
        }
        return viewPostsRedirect;
    }
}
