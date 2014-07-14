package config;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

/**
 * Created by Admin on 13.07.2014.
 */

@Configuration
@Import({CoreConfig.class, SecurityConfig.class})
public class AppConfig {

}
