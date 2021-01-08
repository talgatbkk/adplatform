package kz.epam.tcfp.dao.connection;

import org.apache.log4j.Logger;

import java.sql.*;
import java.util.ResourceBundle;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

/**
 * @author Talgat Bekkaliyev
 * @project AdPlatform
 */
public class ConnectionPool {

    private static final Logger LOGGER = Logger.getLogger(ConnectionPool.class);

    private static final String DB_PROPERTIES_FILE = "db";
    private static final String DB_DRIVER = "db.driver";
    private static final String DB_URL = "db.url";
    private static final String DB_POOL_SIZE = "db.poolSize";
    private static final String DB_USER = "db.user";
    private static final String DB_PASSWORD = "db.password";

    private BlockingQueue<Connection> connections;

    public ConnectionPool() {
        try{
            ResourceBundle bundle = ResourceBundle.getBundle(DB_PROPERTIES_FILE);
            int poolSize = Integer.parseInt(bundle.getString(DB_POOL_SIZE));
            connections = new ArrayBlockingQueue<>(poolSize);
            for (int i = 0; i < poolSize; i++){
                Connection con = getConnection();
                if (con != null){
                    connections.offer(con);
                }
            }
        } catch (ConnectionPoolException ex){
            LOGGER.warn("Error while getting connection", ex);
        }
    }

    public Connection getConnection() throws ConnectionPoolException {
        ResourceBundle bundle = ResourceBundle.getBundle(DB_PROPERTIES_FILE);
        Connection connection;
        try {
            Class.forName(bundle.getString(DB_DRIVER));
            connection = DriverManager.getConnection(bundle.getString(DB_URL),
                    bundle.getString(DB_USER),
                    bundle.getString(DB_PASSWORD));
        } catch (ClassNotFoundException | SQLException ex){
            throw new ConnectionPoolException();
        }
        return connection;
    }

    public synchronized void putBackConnectionToPool(Connection connection) {
        if (connection != null) {
                connections.offer(connection);
            }
        }



    public synchronized Connection getExistingConnectionFromPool() throws ConnectionPoolException {
        Connection newConn = null;
        if (connections.size() == 0){
            throw new ConnectionPoolException("All connections are used");
        } else {
            newConn = connections.poll();
        }
        return newConn;
    }


}


