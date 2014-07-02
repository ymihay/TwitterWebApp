package core.repository.jdbc;

import java.sql.Connection;
import java.sql.SQLException;

/**
 * Created with IntelliJ IDEA.
 * UserDAO: Admin
 * Date: 15.03.14
 * Time: 22:22
 * To change this template use File | Settings | File Templates.
 */
public interface ConnectionFactory {

    public Connection getConnection() throws SQLException, InterruptedException;

    public void closeConnection(Connection conn);
}
