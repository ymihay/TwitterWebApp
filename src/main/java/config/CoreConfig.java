package config;

import core.repository.jdbc.ConnectionFactory;
import core.repository.jdbc.DriverManagerDAOJDBC;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import java.sql.SQLException;

/**
 * Created by Admin on 01.07.2014.
 */
@Configuration
@ComponentScan(basePackages = {"core.repository", "core.service", "web"})
//@PropertySource("classpath:/main/resources/jdbc.properties")
public class CoreConfig {
    //@Autowired
    //Environment env;

    @Bean(name = "driverManagerDAO", initMethod = "createConnectionPool", destroyMethod = "closeAllConnections")
    public ConnectionFactory createDriverManagerDAO() throws SQLException, InterruptedException {
        // TODO: use property bean instaed explicit assigning
        DriverManagerDAOJDBC driverManager = new DriverManagerDAOJDBC();
        driverManager.setDriver("oracle.jdbc.OracleDriver");
        driverManager.setPassword("Tamvisoko111");
        driverManager.setPoolSize(10);
        driverManager.setUrl("jdbc:oracle:thin:@127.0.0.1:1521:XE");
        driverManager.setUser("TWITTER_ADM");
        return driverManager;
    }
}
