package config;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

/**
 * Created by Admin on 14.07.2014.
 */
@EnableAutoConfiguration
@Configuration
@ComponentScan(basePackages = {"web.rest", "web.usermanager", "config", "core"})
public class Application {

    public static void main(String[] args) throws Throwable {
        SpringApplication.run(Application.class, args);
    }

}
