package repository.jdbc;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Locale;
import java.util.concurrent.Semaphore;

/**
 * Created with IntelliJ IDEA.
 * UserDAO: Admin
 * Date: 15.03.14
 * Time: 22:17
 * To change this template use File | Settings | File Templates.
 */
//@Component
public class DriverManagerDAOJDBC implements ConnectionFactory {
    private static final Locale defaultLocale = Locale.ENGLISH;

    private String driver;
    private String url;
    private String user;
    private String password;
    private int poolSize;

    private Semaphore available;
    protected Connection[] connections;
    protected boolean[] used;

    public DriverManagerDAOJDBC() {
    }

    public void setDriver(String driver) {
        this.driver = driver;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getPoolSize() {
        return poolSize;
    }

    public void setPoolSize(int poolSize) {
        this.poolSize = poolSize;
    }

    private Connection createConnection() throws SQLException {
        return DriverManager.getConnection(url, user, password);
    }

    private Connection[] createConnectionPool() throws SQLException {
        this.available = new Semaphore(poolSize, true);
        this.used = new boolean[poolSize];
        try {
            Class.forName(driver);
        } catch (ClassNotFoundException ex) {
            ex.printStackTrace();
        }
        Locale.setDefault(defaultLocale);

        connections = new Connection[poolSize];
        for (int i = 0; i < connections.length; ++i) {
            connections[i] = createConnection();
        }
        return connections;
    }

    private void closeRealConnection(Connection connection) {
        if (connection != null) {
            try {
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    private void closeAllConnections() {
        for (Connection connection : connections) {
            try {
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public Connection getConnection() throws SQLException, InterruptedException {
        available.acquire();
        return getNextAvailableConnection();
    }

    @Override
    public void closeConnection(Connection connection) {
        if (markAsUnused(connection)) {
            available.release();
        }
    }

    protected synchronized Connection getNextAvailableConnection() throws SQLException {
        if (connections == null) {
            createConnectionPool();
        }
        for (int i = 0; i < connections.length; i++) {
            if (!used[i]) {
                used[i] = true;
                return connections[i];
            }
        }
        return null;
    }

    protected synchronized boolean markAsUnused(Connection currentConnection) {
        for (int i = 0; i < connections.length; i++) {
            if (currentConnection == connections[i]) {
                if (used[i]) {
                    used[i] = false;
                    return true;
                } else
                    return false;
            }
        }
        return false;
    }
}
