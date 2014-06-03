package repository.sex;

import domain.Sex;
import repository.jdbc.ConnectionFactory;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: Admin
 * Date: 16.03.14
 * Time: 19:31
 * To change this template use File | Settings | File Templates.
 */
public interface SexDAO {

    public void setCnFactory(ConnectionFactory cnFactory);

    public Sex find(Integer id);

    public Sex find(String name);

    public List<Sex> findAll();

    public boolean create(Sex sex);

    public boolean update(Sex sex);

    public boolean delete(Sex sex);
}
