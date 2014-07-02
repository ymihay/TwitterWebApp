package config;

import core.repository.jdbc.ConnectionFactory;
import core.repository.jdbc.DriverManagerDAOJDBC;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;

/**
 * Created by Admin on 01.07.2014.
 */
@Configuration
@ComponentScan(basePackages = {"core.repository", "core.service"})
//@PropertySource("classpath:/main/resources/jdbc.properties")
public class CoreConfig {
    @Autowired
    Environment env;

    @Bean
    public ConnectionFactory createDriverManagerDAO() {
        // TODO: use property bean instaed explicit assigning
        DriverManagerDAOJDBC driverManager = new DriverManagerDAOJDBC();
        driverManager.setDriver("jdbc:oracle.jdbc.OracleDriver");
        driverManager.setPassword("oracle:thin:@127.0.0.1:1521:XE");
        driverManager.setPoolSize(10);
        driverManager.setUrl("Tamvisoko111");
        driverManager.setUser("TWITTER_ADM");
        return driverManager;
    }
}
