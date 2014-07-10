package repository;

import repository.post.PostDAO;
import repository.user.UserDAO;
import domain.Post;
import domain.User;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: Admin
 * Date: 27.04.14
 * Time: 20:45
 * To change this template use File | Settings | File Templates.
 */
public class PostDAOJDBCTest extends DAOTestTemplate {
    @Autowired
    private PostDAO postDAO;
    @Autowired
    private UserDAO userDAO;
    private User userFirst;
    private User userSecond;

    @Before
    public void clearDB() throws Exception {
        jdbcTemplate.execute("TRUNCATE TABLE twt_post");
        userFirst = new User("login", "pswd");
        userFirst.setUserId(1);
        userSecond = new User("testUser", "password");
        userSecond.setUserId(2);
        userDAO.create(userFirst);
        userDAO.create(userSecond);
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void testCreatePostNoException() throws Exception {
        Post post = new Post("text", userFirst);
        postDAO.create(post);
    }

    @Test
    public void testCreatePostSingle() throws Exception {
        Post post = new Post("text", userFirst);
        postDAO.create(post);
        int size = jdbcTemplate.queryForObject("select count(*) from twt_post", Integer.class);

        Assert.assertEquals("Just one row was created for create() method", 1, size);
    }

    @Test
    public void testFindByName() throws Exception {
        Post expectedPost = new Post("text", userFirst);
        postDAO.create(expectedPost);
        List<Post> actualPosts = postDAO.findByPhrase("text");
        Assert.assertEquals("Method 'find by name' finds created row", expectedPost, actualPosts.get(0));
    }


    @Test
    public void testFindAll() throws Exception {
        Post postFirst = new Post("text1", userFirst);
        Post postSecond = new Post("tex2", userFirst);
        postDAO.create(postFirst);
        postDAO.create(postSecond);
        List<Post> actualResult = postDAO.findAll();

        Assert.assertEquals("findAll() returns all values in DB", 2, actualResult.size());
    }

    @Test
    public void testFindByPhraseCorrectCount() throws Exception {
        Post postFirst = new Post("text1", userFirst);
        Post postSecond = new Post("tex2", userFirst);
        postDAO.create(postFirst);
        postDAO.create(postSecond);
        List<Post> actualResult = postDAO.findByPhrase("text");

        Assert.assertEquals("find by phrase method returns those rows that contain phrase. One row",
                1, actualResult.size());
    }

    @Test
    public void testFindByPhraseCorrectCountSeveralRows() throws Exception {
        Post postFirst = new Post("text1", userFirst);
        Post postSecond = new Post("tex2", userFirst);
        Post postThird = new Post("test", userFirst);
        postDAO.create(postFirst);
        postDAO.create(postSecond);
        postDAO.create(postThird);
        List<Post> actualResult = postDAO.findByPhrase("ex");

        Assert.assertEquals("find by phrase method returns those rows that contain phrase. Several rows",
                2, actualResult.size());
    }

    @Test
    public void testFindPostsByUser() throws Exception {
        Post postFirst = new Post("text1", userFirst);
        Post postSecond = new Post("tex2", userFirst);
        Post postThird = new Post("test", userSecond);
        postDAO.create(postFirst);
        postDAO.create(postSecond);
        postDAO.create(postThird);
        List<Post> actualResult = postDAO.findByUser("login");

        Assert.assertEquals("find post by User login returns correct count of User's posts", 2, actualResult.size());
    }

    @Test
    public void testFindPostByUser() throws Exception {
        Post postFirst = new Post("text1", userFirst);
        Post postSecond = new Post("test", userSecond);
        postDAO.create(postFirst);
        postDAO.create(postSecond);
        List<Post> actualResult = postDAO.findByUser("login");

        Assert.assertEquals("find post by User login returns correct count of User's posts", postFirst, actualResult.get(0));
    }

    @Test
    public void testFindPostsByUserId() throws Exception {
        Post postFirst = new Post("text1", userFirst);
        Post postSecond = new Post("tex2", userFirst);
        Post postThird = new Post("test", userSecond);
        postDAO.create(postFirst);
        postDAO.create(postSecond);
        postDAO.create(postThird);
        List<Post> actualResult = postDAO.findByUser(1);

        Assert.assertEquals("find post by User Id returns correct count of User's posts", 2, actualResult.size());
    }

    @Test
    public void testFindPostByUserId() throws Exception {
        Post postFirst = new Post("text1", userFirst);
        Post postSecond = new Post("test", userSecond);
        postDAO.create(postFirst);
        postDAO.create(postSecond);
        List<Post> actualResult = postDAO.findByUser(1);

        Assert.assertEquals("find post by User Id returns correct count of User's posts", postFirst, actualResult.get(0));
    }

    @Test
    public void testFindAvailablePostsOneRowCreated() throws Exception {
        userDAO.setSubscription("login", "testUser");
        postDAO.create(new Post("textUserFirst", userFirst));
        postDAO.create(new Post("textUserSecond", userSecond));
        List<Post> posts = postDAO.findAvailablePosts("testUser");

        Assert.assertEquals("FindAvailablePosts. Row was found in db", 1, posts.size());
    }

    @Test
    public void testFindAvailablePostsSeveralRowsCreated() throws Exception {
        userDAO.setSubscription("login", "testUser");
        postDAO.create(new Post("textUserFirst", userFirst));
        postDAO.create(new Post("textUserSecond", userSecond));
        postDAO.create(new Post("test", userSecond));
        List<Post> posts = postDAO.findAvailablePosts("testUser");

        Assert.assertEquals("FindAvailablePosts. Row was found in db", 2, posts.size());
    }

    @Test
    public void testFindAvailablePostsAvailableTheSame() throws Exception {
        userDAO.setSubscription("login", "testUser");
        Post postFirst = new Post("textUserFirst", userFirst);
        Post postSecond = new Post("textUserSecond", userSecond);
        postDAO.create(postFirst);
        postDAO.create(postSecond);
        List<Post> posts = postDAO.findAvailablePosts("testUser");

        Assert.assertEquals("findSubscribedOnUser. Correct available post returned", postSecond, posts.get(0));
    }

    @Test
    public void testFindAvailablePostsByUserIdOneRowCreated() throws Exception {
        userDAO.setSubscription("login", "testUser");
        postDAO.create(new Post("textUserFirst", userFirst));
        postDAO.create(new Post("textUserSecond", userSecond));
        List<Post> posts = postDAO.findAvailablePosts(2);

        Assert.assertEquals("FindAvailablePosts. Row was found in db", 1, posts.size());
    }

    @Test
    public void testFindAvailablePostsByUserIdSeveralRowsCreated() throws Exception {
        userDAO.setSubscription("login", "testUser");
        postDAO.create(new Post("textUserFirst", userFirst));
        postDAO.create(new Post("textUserSecond", userSecond));
        postDAO.create(new Post("test", userSecond));
        List<Post> posts = postDAO.findAvailablePosts(2);

        Assert.assertEquals("FindAvailablePostsByUserId. Row was found in db", 2, posts.size());
    }

    @Test
    public void testFindAvailablePostsAvailableByUserIdTheSame() throws Exception {
        userDAO.setSubscription("login", "testUser");
        Post postFirst = new Post("textUserFirst", userFirst);
        Post postSecond = new Post("textUserSecond", userSecond);
        postDAO.create(postFirst);
        postDAO.create(postSecond);
        List<Post> posts = postDAO.findAvailablePosts(2);

        Assert.assertEquals("findSubscribedOnUser. Correct available post returned", postSecond, posts.get(0));
    }
}
