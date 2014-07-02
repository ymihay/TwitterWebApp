package web.mvc.post;

import core.domain.Post;
import core.service.post.PostService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import web.usermanager.UserManager;

import java.util.List;

/**
 * Created by Admin on 02.06.2014.
 */
@Controller
public class PostQueriesController {
    @Autowired
    private PostService postService;
    @Autowired
    private UserManager userManager;

    private static Logger LOG = LoggerFactory.getLogger(PostQueriesController.class);
    private static String viewPostsTemplate = "post/viewposts";
    private static String viewNoHitsTemplate = "search/nohits";

    @ExceptionHandler(Exception.class)
    public void handleExceptions(Exception exception) {
        LOG.error(exception.getStackTrace().toString());
    }

    @RequestMapping(value = "/viewuserposts", params = "userid")
    private String viewUserPosts(Model model,
                                 @RequestParam(value = "userid") Integer userId) {
        model.addAttribute("posts", postService.findByUser(userId));
        if (userManager.getUser().getUserId().compareTo(userId) == 0) {
            model.addAttribute("isLoggedUser", true);
        }
        return viewPostsTemplate;
    }

    @RequestMapping(value = "/viewfollowingposts", params = "userid")
    private String followingPost(Model model,
                                 @RequestParam("userid") Integer userId) {
        model.addAttribute("posts", postService.findAvailablePosts(userId));
        if (userManager.getUser().getUserId().compareTo(userId) == 0) {
            model.addAttribute("isLoggedUser", true);
        }

        return viewPostsTemplate;
    }

    @RequestMapping(value = "/findpost", params = "phrase")
    private String findPost(Model model, @RequestParam("phrase") String phrase) {
        List<Post> posts = postService.findByPhrase(phrase);
        if (!posts.isEmpty()) {
            model.addAttribute("posts", postService.findByPhrase(phrase));
            return viewPostsTemplate;
        }
        return viewNoHitsTemplate;
    }
}
