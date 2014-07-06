package config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.json.MappingJacksonHttpMessageConverter;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

/**
 * Created by Admin on 05.07.2014.
 */

@Configuration
@EnableWebMvc
@ComponentScan(basePackages = {"web.rest", "web.usermanager"})
public class RestWebConfig {

    @Bean
    public MappingJacksonHttpMessageConverter getMappingJacksonJsonConverter() {
        return new MappingJacksonHttpMessageConverter();
    }
}
