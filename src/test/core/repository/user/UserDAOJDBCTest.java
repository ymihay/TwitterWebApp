package core.repository.user;

import core.domain.Country;
import core.domain.Sex;
import core.domain.User;
import core.repository.DAOTestTemplate;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.sql.Timestamp;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: Admin
 * Date: 25.04.14
 * Time: 15:51
 * To change this template use File | Settings | File Templates.
 */
public class UserDAOJDBCTest extends DAOTestTemplate {

    @Autowired
    private UserDAO userDAO;
    private Country country;
    private Sex sex;

    @Before
    public void clearDB() throws Exception {
        jdbcTemplate.execute("TRUNCATE TABLE twt_user");
        jdbcTemplate.execute("TRUNCATE TABLE twt_subscription");
        sex = new Sex(1, "male");
        country = new Country(1, "Ukraine");
    }

    @Test
    public void testCreateUserNoException() throws Exception {
        User user = new User("login", "password");
        userDAO.create(user);
    }

    @Test
    public void testCreateUserSingle() throws Exception {
        User user = new User("login", "password");
        userDAO.create(user);
        int size = jdbcTemplate.queryForObject("select count(*) from twt_user", Integer.class);

        Assert.assertEquals("Just one row was created for create() method", 1, size);
    }

    @Test
    public void testFindByName() throws Exception {
        User expectedUser = new User("login", "password");
        userDAO.create(expectedUser);
        User actualUser = userDAO.find("login");

        Assert.assertEquals("Method 'find by name' finds created row", expectedUser, actualUser);
    }

    @Test
    public void testFindById() throws Exception {
        User expectedUser = new User("login", "password");
        userDAO.create(expectedUser);
        User user = userDAO.find("login");

        Integer id = user.getUserId();
        User actualUser = userDAO.find(id);

        Assert.assertEquals("Method 'find by id' finds created row", expectedUser, actualUser);
    }

    @Test
    public void testCreateSeveralUsersWithSameLogin() throws Exception {
        User userFirst = new User("login", "password1");
        User userSecond = new User("login", "password2");
        userDAO.create(userFirst);
        try {
            userDAO.create(userSecond);
        } catch (Exception e) {
            e.printStackTrace();
        }
        int size = jdbcTemplate.queryForObject("select count(*) from twt_user where login like 'login'", Integer.class);

        Assert.assertEquals("Just one user with the same login was created.", 1, size);
    }

    private User createUserHelper() {
        User expectedUser = new User("login", "password1");
        expectedUser.setBirthdate(new Timestamp(123456789));
        expectedUser.setCountry(this.country);
        expectedUser.setFirstName("first name");
        expectedUser.setSex(this.sex);
        expectedUser.setLastName("last name");
        expectedUser.setPatronymic("patronymic");
        return expectedUser;
    }

    @Test
    public void testCreateUserPasswordTheSameInCodeAndDB() throws Exception {
        User expectedUser = createUserHelper();
        userDAO.create(expectedUser);
        User actualUser = userDAO.find("login");
        Assert.assertEquals("Password of user in DB and code is the same",
                expectedUser.getPassword(), actualUser.getPassword());
    }

    @Test
    public void testCreateUserFirstNameTheSameInCodeAndDB() throws Exception {
        User expectedUser = createUserHelper();
        userDAO.create(expectedUser);
        User actualUser = userDAO.find("login");
        Assert.assertEquals("First name of user in DB and code is the same",
                expectedUser.getFirstName(), actualUser.getFirstName());
    }

    @Test
    public void testCreateUserLastNameTheSameInCodeAndDB() throws Exception {
        User expectedUser = createUserHelper();
        userDAO.create(expectedUser);
        User actualUser = userDAO.find("login");
        Assert.assertEquals("Last name of user in DB and code is the same",
                expectedUser.getLastName(), actualUser.getLastName());
    }

    @Test
    public void testCreateUserPatronymicTheSameInCodeAndDB() throws Exception {
        User expectedUser = createUserHelper();
        userDAO.create(expectedUser);
        User actualUser = userDAO.find("login");
        Assert.assertEquals("Patronymic of user in DB and code is the same",
                expectedUser.getPatronymic(), actualUser.getPatronymic());
    }

    @Test
    public void testCreateUserBirthDateTheSameInCodeAndDB() throws Exception {
        User expectedUser = createUserHelper();
        userDAO.create(expectedUser);
        User actualUser = userDAO.find("login");
        Assert.assertEquals("Birth date of user in DB and code is the same",
                expectedUser.getBirthdate(), actualUser.getBirthdate());
    }

    @Test
    public void testFindAll() throws Exception {
        User userFirst = new User("loginFirst", "passwordFirst");
        User userSecond = new User("loginSecond", "passwordSecond");
        userDAO.create(userFirst);
        userDAO.create(userSecond);
        List<User> actualResult = userDAO.findAll();

        Assert.assertEquals("findAll() returns all values in DB", 2, actualResult.size());
    }

    private void setUserSubscribed() {
        User userFirst = new User("loginFirst", "passwordFirst");
        User userSecond = new User("loginSecond", "passwordSecond");
        userDAO.create(userFirst);
        userDAO.create(userSecond);
        userDAO.setSubscription("loginFirst", "loginSecond");
    }

    @Test
    public void testSetSubscription() throws Exception {
        setUserSubscribed();
        int size = jdbcTemplate.queryForObject("select count(*) from twt_subscription where sys_delstate=0", Integer.class);

        Assert.assertEquals("set subscription. Row was created in db", 1, size);
    }

    @Test
    public void testSetSubscriptionUser() throws Exception {
        setUserSubscribed();
        int size = jdbcTemplate.queryForObject("select count(*)\n" +
                        "  from twt_subscription\n" +
                        " where user_id in (select id from twt_user where login like 'loginFirst')\n" +
                        "   and subscribed_on_user_id in\n" +
                        "       (select id from twt_user where login like 'loginSecond')\n" +
                        "   and sys_delstate = 0\n",
                Integer.class
        );

        Assert.assertEquals("set subscription. subscription was set correctly.", 1, size);
    }

    @Test
    public void testUnSetSubscriptionUser() throws Exception {
        setUserSubscribed();
        userDAO.unSetSubscription("loginFirst", "loginSecond");
        int size = jdbcTemplate.queryForObject("select count(*) from twt_subscription where sys_delstate=0",
                Integer.class);

        Assert.assertEquals("unset subscription. subscription was unset correctly.", 0, size);
    }

    @Test
    public void testFindSubscribedOnUserOneRowCreated() throws Exception {
        setUserSubscribed();
        List<User> users = userDAO.findSubscribedOnUser("loginSecond");

        Assert.assertEquals("findSubscribedOnUser. Row was found in db", 1, users.size());
    }

    @Test
    public void testFindSubscribedOnUserTheSame() throws Exception {
        User userFirst = new User("loginFirst", "passwordFirst");
        User userSecond = new User("loginSecond", "passwordSecond");
        userDAO.create(userFirst);
        userDAO.create(userSecond);
        userDAO.setSubscription("loginFirst", "loginSecond");
        List<User> users = userDAO.findSubscribedOnUser("loginSecond");

        Assert.assertEquals("findSubscribedOnUser. Correct user was returned", userFirst, users.get(0));
    }

    @Test
    public void testFindSubscribedOnUserSeveralRowCreated() throws Exception {
        User userFirst = new User("loginFirst", "passwordFirst");
        User userSecond = new User("loginSecond", "passwordSecond");
        User userThird = new User("loginThird", "password");
        userDAO.create(userFirst);
        userDAO.create(userSecond);
        userDAO.create(userThird);
        userDAO.setSubscription("loginFirst", "loginSecond");
        userDAO.setSubscription("loginThird", "loginSecond");
        List<User> users = userDAO.findSubscribedOnUser("loginSecond");

        Assert.assertEquals("findSubscribedOnUser. Several rows were created.", 2, users.size());
    }

    @Test
    public void testFindSubscriptionsOneRowCreated() throws Exception {
        setUserSubscribed();
        List<User> users = userDAO.findSubscriptions("loginFirst");

        Assert.assertEquals("findSubscribedOnUser. Row was found in db", 1, users.size());
    }

    @Test
    public void testFindSubscriptionsTheSame() throws Exception {
        User userFirst = new User("loginFirst", "passwordFirst");
        User userSecond = new User("loginSecond", "passwordSecond");
        userDAO.create(userFirst);
        userDAO.create(userSecond);
        userDAO.setSubscription("loginFirst", "loginSecond");
        List<User> users = userDAO.findSubscriptions("loginFirst");

        Assert.assertEquals("FindSubscriptions. Correct user was returned", userSecond, users.get(0));
    }

    @Test
    public void testFindSubscriptionsSeveralRowCreated() throws Exception {
        User userFirst = new User("loginFirst", "passwordFirst");
        User userSecond = new User("loginSecond", "passwordSecond");
        User userThird = new User("loginThird", "password");
        userDAO.create(userFirst);
        userDAO.create(userSecond);
        userDAO.create(userThird);
        userDAO.setSubscription("loginFirst", "loginSecond");
        userDAO.setSubscription("loginThird", "loginSecond");
        List<User> users = userDAO.findSubscribedOnUser("loginSecond");

        Assert.assertEquals("FindSubscriptions.Several rows were created.", 2, users.size());
    }

}
