package main.java.web.usermanager;

/**
 * Created by Admin on 25.05.2014.
 */

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Component
public class AuthenticationInterceptor extends HandlerInterceptorAdapter {

    @Autowired
    private UserManager userManager;

    @Override
    public boolean preHandle(HttpServletRequest request,
                             HttpServletResponse response, Object handler) throws Exception {
        String uri = request.getRequestURI();
        //System.out.println(uri);

        if (!userManager.isLoggedIn()) {
            response.sendRedirect("loginpage");
            return false;
        }
        //System.out.println(userManager.getUser());

        return true;
    }
}