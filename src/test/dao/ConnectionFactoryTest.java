package test.dao;

import main.java.repository.jdbc.ConnectionFactory;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

/**
 * Created with IntelliJ IDEA.
 * User: Admin
 * Date: 26.04.14
 * Time: 16:51
 * To change this template use File | Settings | File Templates.
 */
public class ConnectionFactoryTest implements ConnectionFactory {

    private DataSource dataSource;

    public ConnectionFactoryTest(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public Connection getConnection() throws SQLException, InterruptedException {
        return dataSource.getConnection();
    }

    @Override
    public void closeConnection(Connection connection) {
        if (connection != null) {
            try {
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}
