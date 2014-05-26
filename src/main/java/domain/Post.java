package main.java.domain;

/**
 * Created with IntelliJ IDEA.
 * User: Admin
 * Date: 16.03.14
 * Time: 17:39
 * To change this template use File | Settings | File Templates.
 */
public class Post {
    private Integer postId;
    private String postMessage;
    private User user;

    public Post() {
    }

    public Post(Integer postId, String postMessage, User user) {
        this.postId = postId;
        this.postMessage = postMessage;
        this.user = user;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Post)) {
            return false;
        }

        Post post = (Post) o;

        if (!postMessage.equals(post.postMessage)) {
            return false;
        }
        /*if (!user.equals(post.user)) {
            return false;
        }*/

        return true;
    }

    @Override
    public int hashCode() {
        int result = postMessage.hashCode();
        result = 31 * result + user.hashCode();
        return result;
    }

    public Post(String postMessage, User user) {
        this.postMessage = postMessage;
        this.user = user;
    }

    public Integer getPostId() {
        return postId;
    }

    public void setPostId(Integer postId) {
        this.postId = postId;
    }

    public String getPostMessage() {
        return postMessage;
    }

    public void setPostMessage(String postMessage) {
        this.postMessage = postMessage;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
