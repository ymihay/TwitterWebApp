package repository;

import domain.Post;
import domain.User;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import repository.jdbc.DriverManagerDAOJDBC;
import repository.post.PostDAO;
import repository.user.UserDAO;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * UserDAO: Admin
 * Date: 15.03.14
 * Time: 22:53
 * To change this template use File | Settings | File Templates.
 */
public class Main {
    public static void main(String... args) {
        try {
            DriverManagerDAOJDBC manager = new DriverManagerDAOJDBC();
            ApplicationContext appCtx = new ClassPathXmlApplicationContext("/persistenceContext.xml");
            PostDAO postDAO = appCtx.getBean(PostDAO.class);
            UserDAO userDAO = appCtx.getBean(UserDAO.class);

            List<Post> posts = postDAO.findByPhrase("yana");
            System.out.println(posts.get(0).getPostMessage());

            List<User> users = userDAO.findAll();
            System.out.println(users.get(0).getLogin());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
