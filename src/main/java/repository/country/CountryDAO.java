package repository.country;

import domain.Country;
import repository.jdbc.ConnectionFactory;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: Admin
 * Date: 16.03.14
 * Time: 19:23
 * To change this template use File | Settings | File Templates.
 */
public interface CountryDAO {

    public void setCnFactory(ConnectionFactory cnFactory);

    public Country find(Integer id);

    public Country find(String name);

    public List<Country> findAll();

    public boolean create(Country country);

    public boolean update(Country country);

    public boolean delete(Country country);
}
