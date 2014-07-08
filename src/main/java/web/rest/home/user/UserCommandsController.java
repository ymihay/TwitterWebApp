package web.rest.home.user;

import core.domain.User;
import core.service.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.util.UriComponentsBuilder;
import web.usermanager.UserManager;

/**
 * Created by Admin on 07.07.2014.
 */
@Controller
@RequestMapping(value = "users/me")
public class UserCommandsController {

    @Autowired
    private UserService userService;

    @Autowired
    private UserManager userManager;

    //curl -v -X POST -H content-type:application/json --data '{"userId":null,"firstName":"Yana :)","patronymic":"Mikhaylovna :)","lastName":"Mikhaylenko :)","login":"ymikhaylenko4444","password":"yanayana","sex":{"sexId":1,"sexName":"Male"},"country":{"countryId":2,"countryName":"USA"},"birthdate":630585252000,"subscribedList":null,"subscribedOnUserList":null,"postList":null}}' http://localhost:8080/rest/users/me
    @RequestMapping(method = RequestMethod.POST, consumes = "application/json", produces = "application/json")
    public
    @ResponseBody
    ResponseEntity<User> addUser(@RequestBody User user, UriComponentsBuilder builder) {

        if (user.getLogin() == null || user.getPassword() == null) {
            return new ResponseEntity<User>(HttpStatus.NOT_ACCEPTABLE);
        }

        if (userService.findByLogin(user.getLogin()) != null) {
            return new ResponseEntity<User>(HttpStatus.CONFLICT);
        }

        Integer newUserId = userService.create(user);

        if (!(newUserId > 0)) {
            return new ResponseEntity<User>(HttpStatus.FORBIDDEN);
        }

        HttpHeaders headers = new HttpHeaders();
        headers.setLocation(
                builder.path("/rest/users/{id}")
                        .buildAndExpand(newUserId.toString()).toUri()
        );

        return new ResponseEntity<User>(user, headers, HttpStatus.CREATED);
    }

    //curl -v -X PUT -H content-type:application/json --data '{"userId":4,"firstName":"Yana :)","patronymic":"Mikhaylovna :)","lastName":"Mikhaylenko :)","login":"ymikhaylenko4444","password":"yanayana","sex":{"sexId":1,"sexName":"Male"},"country":{"countryId":2,"countryName":"USA"},"birthdate":630585252000,"subscribedList":null,"subscribedOnUserList":null,"postList":null}}' http://localhost:8080/rest/users/me
    @RequestMapping(method = RequestMethod.PUT, consumes = "application/json", produces = "application/json")
    public
    @ResponseBody
    ResponseEntity<User> updateUser(@RequestBody User user, UriComponentsBuilder builder) {
        User currentUser = userManager.getUser();
        Integer userId = currentUser.getUserId();
        if (user.getUserId() == null) {
            return new ResponseEntity<User>(HttpStatus.NOT_ACCEPTABLE);
        }
        // check if user updates his own profile
        if (!user.getUserId().equals(userId)) {
            return new ResponseEntity<User>(HttpStatus.NOT_ACCEPTABLE);
        }

        // It's not allowed to change login or set password to null
        if (!user.getLogin().equals(currentUser.getLogin()) || (user.getPassword() == null)) {
            return new ResponseEntity<User>(HttpStatus.NOT_ACCEPTABLE);
        }

        if (!userService.update(user)) {
            return new ResponseEntity<User>(HttpStatus.FORBIDDEN);
        }

        HttpHeaders headers = new HttpHeaders();
        headers.setLocation(
                builder.path("/rest/users/{id}")
                        .buildAndExpand(userId.toString()).toUri()
        );

        return new ResponseEntity<User>(user, headers, HttpStatus.OK);
    }

    /**
     * Delete user account
     *
     * @return
     */
    //curl -v -X DELETE -H content-type:application/json http://localhost:8080/rest/users/me
    @RequestMapping(method = RequestMethod.DELETE)
    public
    @ResponseBody
    ResponseEntity deleteUser() {
        User user = userManager.getUser();
        if (userService.delete(user)) {
            return new ResponseEntity(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity(HttpStatus.METHOD_NOT_ALLOWED);
    }
}