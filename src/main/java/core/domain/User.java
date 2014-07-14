package core.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.springframework.hateoas.ResourceSupport;
import web.rest.friend.FriendQueriesController;
import web.rest.home.user.UserQueriesController;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.List;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

/**
 * Created with IntelliJ IDEA.
 * User: Admin
 * Date: 16.03.14
 * Time: 17:27
 * To change this template use File | Settings | File Templates.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class User extends ResourceSupport implements Serializable {
    private Integer userId;
    private String firstName;
    private String patronymic;
    private String lastName;
    private String login;
    private String password;
    private Sex sex;
    private Country country;
    private Timestamp birthdate;
    private List<User> subscribedList;
    private List<User> subscribedOnUserList;
    private List<Post> postList;

    public User() {
    }

    public User(Integer userId) {
        this.userId = userId;
    }

    public User(String login, String password) {
        this.login = login;
        this.password = password;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
        this.add(linkTo(methodOn(UserQueriesController.class).getUserById(userId)).withSelfRel());
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getPatronymic() {
        return patronymic;
    }

    public void setPatronymic(String patronymic) {
        this.patronymic = patronymic;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Sex getSex() {
        return sex;
    }

    public void setSex(Sex sex) {
        this.sex = sex;
    }

    public Country getCountry() {
        return country;
    }

    public void setCountry(Country country) {
        this.country = country;
    }

    public Timestamp getBirthdate() {
        return birthdate;
    }

    public void setBirthdate(Timestamp birthdate) {
        this.birthdate = birthdate;
    }

    public List<User> getSubscribedList() {
        return subscribedList;
    }

    public void setSubscribedList(List<User> subscribedList) {
        this.subscribedList = subscribedList;
        this.add(linkTo(methodOn(FriendQueriesController.class).getFriends(this.userId)).withSelfRel());
    }

    public List<User> getSubscribedOnUserList() {
        return subscribedOnUserList;
    }

    public void setSubscribedOnUserList(List<User> subscribedOnUserList) {
        this.subscribedOnUserList = subscribedOnUserList;

    }

    public List<Post> getPostList() {
        return postList;
    }

    public void setPostList(List<Post> postList) {
        this.postList = postList;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof User)) {
            return false;
        }

        User user = (User) o;

        if (userId.equals(user.getUserId())) {
            return true;
        }

        if (!login.equals(user.login)) {
            return false;
        }

        return true;
    }

    @Override
    public int hashCode() {
        return login.hashCode();
    }
}
