package config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.annotation.Order;
import org.springframework.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer;
import org.springframework.web.util.Log4jConfigListener;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;

/**
 * Created by Admin on 01.07.2014.
 */
@Order(2)
public class WebAppInitializer extends AbstractAnnotationConfigDispatcherServletInitializer {

    private static Logger LOG = LoggerFactory.getLogger(WebAppInitializer.class);

    @Override
    public void onStartup(ServletContext servletContext) throws ServletException {
        super.onStartup(servletContext);
        servletContext.addListener(new Log4jConfigListener());
        servletContext.setInitParameter("defaultHtmlEscape", "true");
    }

    @Override
    protected Class<?>[] getRootConfigClasses() {
        return new Class[]{AppConfig.class};
    }

    @Override
    protected Class<?>[] getServletConfigClasses() {
        return new Class[]{RestWebConfig.class};
    }

    @Override
    protected String[] getServletMappings() {
        return new String[]{"/rest/*"};
    }
}
