package config;

import core.repository.jdbc.ConnectionFactory;
import core.repository.jdbc.DriverManagerDAOJDBC;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;

import java.sql.SQLException;

/**
 * Created by Admin on 01.07.2014.
 */
@Configuration
@ComponentScan(basePackages = {"core.repository", "core.service", "core.domain"})
@PropertySource("classpath:jdbc.properties")
public class CoreConfig {

    @Value("${driver}")
    private String driver;

    @Value("${user}")
    private String user;

    @Value("${password}")
    private String password;

    @Value("${url}")
    private String url;

    @Value("${poolSize}")
    private Integer poolSize;


    @Bean
    public static PropertySourcesPlaceholderConfigurer properties() {
        return new PropertySourcesPlaceholderConfigurer();
    }

    @Bean(initMethod = "createConnectionPool", destroyMethod = "closeAllConnections")
    public ConnectionFactory createDriverManagerDAO() throws SQLException, InterruptedException {
        DriverManagerDAOJDBC driverManager = new DriverManagerDAOJDBC();
        driverManager.setDriver(driver);
        driverManager.setPassword(password);
        driverManager.setPoolSize(poolSize);
        driverManager.setUrl(url);
        driverManager.setUser(user);
        return driverManager;
    }
}
