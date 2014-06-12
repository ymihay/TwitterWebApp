package web.post;

import domain.Post;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import service.post.PostService;
import web.usermanager.UserManager;

/**
 * Created by Admin on 02.06.2014.
 */
@Controller
public class PostController {
    @Autowired
    private PostService postService;

    @Autowired
    private UserManager userManager;

    private static String viewPostsTemplate = "post/viewposts";
    private static String modifyPostTemplate = "post/modifypost";

    @ExceptionHandler(Exception.class)
    public String handleExceptions(Exception exception) {
        exception.printStackTrace();
        return "Error";
    }

    @RequestMapping(value = "/viewuserposts",
            params = "userid")
    public String viewUserPosts(Model model,
                                @RequestParam("userid") Integer id) {
        model.addAttribute("posts", postService.findByUser(id));
        if (userManager.getUser().getUserId().compareTo(id) == 0){
            model.addAttribute("isLoggedUser", true);
        }
        return viewPostsTemplate;
    }

    @RequestMapping(value = "/createpost")
     public String createPost(Model model) {
        model.addAttribute("userId", userManager.getUser().getUserId());
        model.addAttribute("action" , "addpost");
        return modifyPostTemplate;
    }

    @RequestMapping(value = "/modifypost", params = "postid")
    public String modifyPost(Model model, @RequestParam("postid") Integer postId) {
        Post post = postService.findById(postId);
        if (post != null) {
            model.addAttribute("post", post);
            model.addAttribute("action" , "updatepost");
        } else {
            model.addAttribute("action" , "addpost");
        }
        return modifyPostTemplate;
    }

    @RequestMapping(value = "/viewfollowingposts",
            params = "userid")
    public String followingPost(Model model, @RequestParam("userid") Integer userId) {
        model.addAttribute("posts", postService.findAvailablePosts(userId));
        if (userManager.getUser().getUserId().compareTo(userId) == 0){
            model.addAttribute("isLoggedUser", true);
        }
        return viewPostsTemplate;
    }
}
