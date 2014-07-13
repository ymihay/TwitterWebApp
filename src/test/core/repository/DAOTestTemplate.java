package core.repository;

import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * Created with IntelliJ IDEA.
 * User: Admin
 * Date: 25.04.14
 * Time: 16:12
 * To change this template use File | Settings | File Templates.
 */

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:persistenceContextTest.xml"})
//@EnableWebMvc
//@ComponentScan(basePackages = {"core.repository", "core.service", "core.domain"})
public abstract class DAOTestTemplate {

    @Autowired
    protected JdbcTemplate jdbcTemplate;
}

