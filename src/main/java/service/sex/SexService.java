package service.sex;

import domain.Sex;

import java.util.List;

/**
 * Created by Admin on 11.06.2014.
 */
public interface SexService {
    public Sex find(Integer id);

    public Sex find(String name);

    public List<Sex> findAll();

    public boolean create(Sex sex);

    public boolean update(Sex sex);

    public boolean delete(Sex sex);
}
