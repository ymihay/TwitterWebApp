package main.java.repository.country;

import main.java.domain.Country;
import main.java.repository.jdbc.ConnectionFactory;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import static main.java.repository.jdbc.DAOJDBCUtil.close;

/**
 * Created with IntelliJ IDEA.
 * User: Admin
 * Date: 16.03.14
 * Time: 19:23
 * To change this template use File | Settings | File Templates.
 */
public class CountryDAOJDBC implements CountryDAO {

    private final static String findCountryByIDSQL = "select * from TWT_COUNTRY where id=? and sys_delstate=0";

    private final static String findCountryByNameSQL = "select * from TWT_COUNTRY where name like ? and sys_delstate=0";

    private static final String findAllCountrySQL = "select c.* from twt_country c where c.sys_delstate = 0\n";

    private static final String createCountrySQL = "insert into twt_country (name) values (?)";

    private static final String updateCountrySQL = "update twt_country c\n" +
            "   set c.name = ?\n" +
            " where c.sys_delstate = 0\n" +
            "   and c.id = ?\n";

    private static final String deleteCountrySQL = "update twt_country c\n" +
            "   set c.sys_delstate = 1\n" +
            " where c.sys_delstate = 0\n" +
            "   and c.id = ?\n";

    private ConnectionFactory cnFactory;

    public CountryDAOJDBC(ConnectionFactory connection) {
        this.cnFactory = connection;
    }

    public ConnectionFactory getCnFactory() {
        return cnFactory;
    }

    public void setCnFactory(ConnectionFactory cnFactory) {
        this.cnFactory = cnFactory;
    }

    private Country map(ResultSet resultSet) throws SQLException {
        Country country = new Country(resultSet.getInt("ID"), resultSet.getString("NAME"));
        return country;
    }

    @Override
    public Country find(Integer id) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        Country country = null;

        try {
            connection = cnFactory.getConnection();
            preparedStatement = connection.prepareStatement(findCountryByIDSQL);
            preparedStatement.setInt(1, id);
            resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                country = map(resultSet);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            close(resultSet);
            close(preparedStatement);
            cnFactory.closeConnection(connection);
        }
        return country;
    }

    @Override
    public Country find(String name) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        Country country = null;

        try {
            connection = cnFactory.getConnection();
            preparedStatement = connection.prepareStatement(findCountryByNameSQL);
            preparedStatement.setString(1, name);
            resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                country = map(resultSet);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            close(resultSet);
            close(preparedStatement);
            cnFactory.closeConnection(connection);
        }
        return country;
    }

    @Override
    public List<Country> findAll() {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        List<Country> countries = new ArrayList<Country>();

        try {
            connection = cnFactory.getConnection();
            preparedStatement = connection.prepareStatement(findAllCountrySQL);
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                countries.add(map(resultSet));
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            close(preparedStatement);
            cnFactory.closeConnection(connection);
        }
        return countries;
    }

    @Override
    public boolean create(Country country) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        try {
            connection = cnFactory.getConnection();
            preparedStatement = connection.prepareStatement(createCountrySQL);
            preparedStatement.setString(1, country.getCountryName());

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
    public boolean update(Country country) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        try {
            connection = cnFactory.getConnection();
            preparedStatement = connection.prepareStatement(updateCountrySQL);
            preparedStatement.setString(1, country.getCountryName());
            preparedStatement.setInt(2, country.getCountryId());

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
    public boolean delete(Country country) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        try {
            connection = cnFactory.getConnection();
            preparedStatement = connection.prepareStatement(deleteCountrySQL);
            preparedStatement.setInt(1, country.getCountryId());
            return preparedStatement.execute();
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            close(preparedStatement);
            cnFactory.closeConnection(connection);
        }
        return false;
    }
}
