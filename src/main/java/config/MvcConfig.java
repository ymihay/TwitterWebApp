package config;

import org.springframework.context.annotation.Bean;
import org.springframework.web.servlet.config.annotation.InterceptorRegistration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.servlet.view.InternalResourceViewResolver;
import web.usermanager.AuthenticationInterceptor;

/**
 * Created by Admin on 01.07.2014.
 */
/*@Configuration
@EnableWebMvc
@ComponentScan(basePackages = {"web.mvc", "web.usermanager"})*/
public class MvcConfig extends WebMvcConfigurerAdapter {

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        InterceptorRegistration registration = registry.addInterceptor(new AuthenticationInterceptor());
        registration.addPathPatterns("/*");
        registration.excludePathPatterns("/rest");
        registration.excludePathPatterns("/login*");
        registration.excludePathPatterns("/logout");
    }

    @Bean
    public InternalResourceViewResolver getViewResolver() {
        InternalResourceViewResolver viewResolver = new InternalResourceViewResolver();
        viewResolver.setPrefix("/WEB-INF/view/");
        viewResolver.setSuffix(".jsp");
        return viewResolver;
    }
}
