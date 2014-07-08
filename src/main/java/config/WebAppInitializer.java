package config;

import org.springframework.web.WebApplicationInitializer;
import org.springframework.web.context.ContextLoaderListener;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;
import org.springframework.web.servlet.DispatcherServlet;
import org.springframework.web.util.Log4jConfigListener;

import javax.servlet.ServletContext;
import javax.servlet.ServletRegistration;
import java.util.Set;

/**
 * Created by Admin on 01.07.2014.
 */
public class WebAppInitializer implements WebApplicationInitializer {

    //private static Logger LOG = LoggerFactory.getLogger(WebAppInitializer.class);

    @Override
    public void onStartup(ServletContext servletContext) {
        WebApplicationContext coreContext = createCoreContext(servletContext);

        configureRest(servletContext, coreContext);
    }

    private WebApplicationContext createCoreContext(ServletContext servletContext) {
        AnnotationConfigWebApplicationContext coreContext = new AnnotationConfigWebApplicationContext();
        coreContext.register(CoreConfig.class);
        coreContext.refresh();

        servletContext.addListener(new Log4jConfigListener());
        servletContext.addListener(new ContextLoaderListener(coreContext));
        servletContext.setInitParameter("defaultHtmlEscape", "true");

        return coreContext;
    }

    private void configureSpringMvc(ServletContext servletContext, WebApplicationContext coreContext) {
        AnnotationConfigWebApplicationContext mvcContext = new AnnotationConfigWebApplicationContext();
        mvcContext.register(MvcConfig.class);
        configureWebContext(servletContext, coreContext, mvcContext, "/pages/*");
    }

    private void configureRest(ServletContext servletContext, WebApplicationContext coreContext) {
        AnnotationConfigWebApplicationContext restContext = new AnnotationConfigWebApplicationContext();
        restContext.register(RestWebConfig.class);
        configureWebContext(servletContext, coreContext, restContext, "/rest/*");
    }

    private void configureWebContext(ServletContext servletContext,
                                     WebApplicationContext coreContext,
                                     AnnotationConfigWebApplicationContext annotationWebContext,
                                     String mappingUrl) {
        annotationWebContext.setParent(coreContext);
        ServletRegistration.Dynamic appServlet = servletContext.addServlet(
                "webservice", new DispatcherServlet(annotationWebContext));
        appServlet.setLoadOnStartup(1);
        Set<String> mappingConflicts = appServlet.addMapping(mappingUrl);

        if (!mappingConflicts.isEmpty()) {
            for (String s : mappingConflicts) {
                //LOG.error("Mapping conflict: " + s);
            }
            throw new IllegalStateException(
                    "'webservice' cannot be mapped to " + mappingUrl);
        }
    }
}
