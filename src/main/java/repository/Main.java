package main.java.repository;

import main.java.domain.Post;
import main.java.repository.jdbc.DriverManagerDAOJDBC;
import main.java.repository.post.PostDAO;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

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
            ApplicationContext appCtx = new ClassPathXmlApplicationContext("/main/resources/persistenceContext.xml");
            PostDAO postDAO = appCtx.getBean("postDAO", PostDAO.class);

            List<Post> posts = postDAO.findByPhrase("yana");
            System.out.println(posts.get(0).getPostMessage());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
