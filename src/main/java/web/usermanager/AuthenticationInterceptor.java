package web.usermanager;

/**
 * Created by Admin on 25.05.2014.
 */

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Component
public class AuthenticationInterceptor extends HandlerInterceptorAdapter {

    @Autowired
    private UserManager userManager;

    private static Logger LOG = LoggerFactory.getLogger(AuthenticationInterceptor.class);
    private static final String loginRedirect = "login";
    private static final String registerTemplate = "register";
    private static final String addUserTemplate = "adduser";

    @Override
    public boolean preHandle(HttpServletRequest request,
                             HttpServletResponse response, Object handler) throws Exception {
        String uri = request.getRequestURI();
        if (!userManager.isLoggedIn() && !uri.endsWith(registerTemplate) && !uri.endsWith(addUserTemplate)) {
            response.sendRedirect(loginRedirect);
            return false;
        }
        return true;
    }
}