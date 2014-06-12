package service.sex;

import domain.Sex;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import repository.sex.SexDAO;

import java.util.List;

/**
 * Created by Admin on 11.06.2014.
 */
@Service("SexService")
public class SexServiceImpl implements SexService {
    @Autowired
    private SexDAO repository;

    @Override
    public Sex find(Integer id) {
        return repository.find(id);
    }

    @Override
    public Sex find(String name) {
        return repository.find(name);
    }

    @Override
    public List<Sex> findAll() {
        return repository.findAll();
    }

    @Override
    public boolean create(Sex sex) {
        return repository.create(sex);
    }

    @Override
    public boolean update(Sex sex) {
        return repository.update(sex);
    }

    @Override
    public boolean delete(Sex sex) {
        return repository.delete(sex);
    }
}
