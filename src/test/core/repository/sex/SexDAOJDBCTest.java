package core.repository.sex;


import core.domain.Sex;
import core.repository.DAOTestTemplate;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: Admin
 * Date: 25.04.14
 * Time: 15:51
 * To change this template use File | Settings | File Templates.
 */
public class SexDAOJDBCTest extends DAOTestTemplate {

    @Autowired
    private SexDAO sexDAO;

    @Before
    public void clearDB() throws Exception {
        jdbcTemplate.execute("TRUNCATE TABLE twt_sex");
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void testCreateSexNoException() throws Exception {
        Sex sex = new Sex(1, "male");
        sexDAO.create(sex);
    }

    @Test
    public void testCreateSexSingle() throws Exception {
        Sex sex = new Sex(1, "Male");
        sexDAO.create(sex);
        int size = jdbcTemplate.queryForObject("select count(*) from twt_sex", Integer.class);

        Assert.assertEquals("Just one row was created for create() method", 1, size);
    }

    @Test
    public void testFindByName() throws Exception {
        Sex expectedSex = new Sex(1, "male");
        sexDAO.create(expectedSex);
        Sex actualSex = sexDAO.find("male");

        Assert.assertEquals("Method 'find by name' finds created row", expectedSex, actualSex);
    }

    @Test
    public void testFindById() throws Exception {
        Sex expectedSex = new Sex(1, "male");
        sexDAO.create(expectedSex);
        Sex sex = sexDAO.find("male");

        Integer id = sex.getSexId();
        Sex actualSex = sexDAO.find(id);

        Assert.assertEquals("Method 'find by id' finds created row", expectedSex, actualSex);
    }

    @Test
    public void testFindAll() throws Exception {
        Sex sexFirst = new Sex(1, "male");
        Sex sexSecond = new Sex(2, "female");
        sexDAO.create(sexFirst);
        sexDAO.create(sexSecond);
        List<Sex> actualResult = sexDAO.findAll();

        Assert.assertEquals("findAll() returns all values in DB", 2, actualResult.size());
    }
}
