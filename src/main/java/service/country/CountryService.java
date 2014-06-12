package service.country;

import domain.Country;

import java.util.List;

/**
 * Created by Admin on 11.06.2014.
 */
public interface CountryService {
    public Country find(Integer id);

    public Country find(String name);

    public List<Country> findAll();

    public boolean create(Country country);

    public boolean update(Country country);

    public boolean delete(Country country);
}
