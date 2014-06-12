package service.country;

import domain.Country;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import repository.country.CountryDAO;

import java.util.List;

/**
 * Created by Admin on 11.06.2014.
 */
@Service("CountryService")
public class CountryServiceImpl implements CountryService {
    @Autowired
    private CountryDAO repository;

    @Override
    public Country find(Integer id) {
        return repository.find(id);
    }

    @Override
    public Country find(String name) {
        return repository.find(name);
    }

    @Override
    public List<Country> findAll() {
        return repository.findAll();
    }

    @Override
    public boolean create(Country country) {
        return repository.create(country);
    }

    @Override
    public boolean update(Country country) {
        return repository.update(country);
    }

    @Override
    public boolean delete(Country country) {
        return repository.delete(country);
    }
}
