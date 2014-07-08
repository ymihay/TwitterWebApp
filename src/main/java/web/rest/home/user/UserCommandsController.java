package web.rest.home.user;

import core.domain.User;
import core.service.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
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