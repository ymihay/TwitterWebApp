package test.dao;

import main.java.domain.Country;
import main.java.repository.country.CountryDAO;
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
public class CountryDAOJDBCTest extends DAOTestTemplate {

    @Autowired
    private CountryDAO countryDAO;

    @Before
    public void clearDB() throws Exception {
        jdbcTemplate.execute("TRUNCATE TABLE twt_country");
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void testCreateCountryNoException() throws Exception {
        Country country = new Country("Ukraine");
        countryDAO.create(country);
    }

    @Test
    public void testCreateCountrySingle() throws Exception {
        Country country = new Country("Ukraine");
        countryDAO.create(country);
        int size = jdbcTemplate.queryForObject("select count(*) from twt_country", Integer.class);

        Assert.assertEquals("Just one row was created for create() method", 1, size);
    }

    @Test
    public void testFindByName() throws Exception {
        Country expectedCountry = new Country("Ukraine");
        countryDAO.create(expectedCountry);
        Country actualCountry = countryDAO.find("Ukraine");

        Assert.assertEquals("Method 'find by name' finds created row", expectedCountry, actualCountry);
    }

    @Test
    public void testFindById() throws Exception {
        Country expectedCountry = new Country("Ukraine");
        countryDAO.create(expectedCountry);
        Country country = countryDAO.find("Ukraine");

        Integer id = country.getCountryId();
        Country actualCountry = countryDAO.find(id);

        Assert.assertEquals("Method 'find by id' finds created row", expectedCountry, actualCountry);
    }

    @Test
    public void testFindAll() throws Exception {
        Country countryFirst = new Country("Ukraine");
        Country countrySecond = new Country("USA");
        countryDAO.create(countryFirst);
        countryDAO.create(countrySecond);
        List<Country> actualResult = countryDAO.findAll();

        Assert.assertEquals("findAll() returns all values in DB", 2, actualResult.size());
    }
}
