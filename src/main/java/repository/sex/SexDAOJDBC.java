package repository.sex;

import domain.Sex;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import repository.jdbc.ConnectionFactory;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import static repository.jdbc.DAOJDBCUtil.close;

/**
 * Created with IntelliJ IDEA.
 * User: Admin
 * Date: 16.03.14
 * Time: 19:32
 * To change this template use File | Settings | File Templates.
 */

@Repository("sexRepository")
public class SexDAOJDBC implements SexDAO {

    private ConnectionFactory cnFactory;

    @Autowired
    public SexDAOJDBC(ConnectionFactory connection) {
        this.cnFactory = connection;
    }

    private static final String findByIdSQL = "select * from TWT_SEX where id=? and sys_delstate=0";

    private final static String findSexByNameSQL = "select * from TWT_SEX where name like ? and sys_delstate=0";

    private static final String findAllSexSQL = "select c.* from twt_sex c where c.sys_delstate = 0\n";

    private static final String createSexSQL = "insert into twt_sex (name) values (?)";

    private static final String updateSexSQL = "update twt_sex c\n" +
            "   set c.name = ?\n" +
            " where c.sys_delstate = 0\n" +
            "   and c.id = ?\n";

    private static final String deleteSexSQL = "update twt_sex c\n" +
            "   set c.sys_delstate = 1\n" +
            " where c.sys_delstate = 0\n" +
            "   and c.id = ?\n";


    public ConnectionFactory getCnFactory() {
        return cnFactory;
    }

    public void setCnFactory(ConnectionFactory cnFactory) {
        this.cnFactory = cnFactory;
    }

    private Sex map(ResultSet resultSet) throws SQLException {
        Sex sex = new Sex(resultSet.getInt("ID"), resultSet.getString("NAME"));
        return sex;
    }

    @Override
    public Sex find(Integer id) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        Sex sex = null;

        try {
            connection = cnFactory.getConnection();
            preparedStatement = connection.prepareStatement(findByIdSQL);
            preparedStatement.setInt(1, id);
            resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                sex = map(resultSet);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            close(resultSet);
            close(preparedStatement);
            cnFactory.closeConnection(connection);
        }
        return sex;
    }

    @Override
    public Sex find(String name) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        Sex sex = null;

        try {
            connection = cnFactory.getConnection();
            preparedStatement = connection.prepareStatement(findSexByNameSQL);
            preparedStatement.setString(1, name);
            resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                sex = map(resultSet);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            close(resultSet);
            close(preparedStatement);
            cnFactory.closeConnection(connection);
        }
        return sex;
    }

    @Override
    public List<Sex> findAll() {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        List<Sex> sexes = new ArrayList<Sex>();

        try {
            connection = cnFactory.getConnection();
            preparedStatement = connection.prepareStatement(findAllSexSQL);
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                sexes.add(map(resultSet));
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            close(preparedStatement);
            cnFactory.closeConnection(connection);
        }
        return sexes;
    }

    @Override
    public boolean create(Sex sex) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        try {
            connection = cnFactory.getConnection();
            preparedStatement = connection.prepareStatement(createSexSQL);
            preparedStatement.setString(1, sex.getSexName());

            return preparedStatement.execute();
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            close(preparedStatement);
            cnFactory.closeConnection(connection);
        }
        return false;
    }

    @Override
    public boolean update(Sex sex) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        try {
            connection = cnFactory.getConnection();
            preparedStatement = connection.prepareStatement(updateSexSQL);
            preparedStatement.setString(1, sex.getSexName());
            preparedStatement.setInt(2, sex.getSexId());

            return preparedStatement.execute();
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            close(preparedStatement);
            cnFactory.closeConnection(connection);
        }
        return true;
    }

    @Override
    public boolean delete(Sex sex) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        try {
            connection = cnFactory.getConnection();
            preparedStatement = connection.prepareStatement(deleteSexSQL);
            preparedStatement.setInt(1, sex.getSexId());
            return preparedStatement.execute();
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            close(preparedStatement);
            cnFactory.closeConnection(connection);
        }
        return true;
    }
}
