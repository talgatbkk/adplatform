package kz.epam.tcfp.dao.connection;

import java.sql.*;
import java.util.ResourceBundle;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

import kz.epam.tcfp.dao.exception.ConnectionPoolException;
import kz.epam.tcfp.dao.exception.DAOException;
import org.apache.log4j.Logger;

/**
 * @author Talgat Bekkaliyev
 * @project AdPlatform
 */
public class ConnectionPool {

//    private static final Logger logger = Logger.getLogger(ConnectionPool.class);

    private static final String DB_PROPERTIES_FILE = "db";
    private static final String DB_DRIVER = "db.driver";
    private static final String DB_URL = "db.url";
    private static final String DB_POOL_SIZE = "db.poolSize";
    private static final String DB_USER = "db.user";
    private static final String DB_PASSWORD = "db.password";

    private BlockingQueue<Connection> connectionsWaitingToBeUsed;
    private BlockingQueue<Connection> connectionsInUse;

    public ConnectionPool() {
        try{
            ResourceBundle bundle = ResourceBundle.getBundle(DB_PROPERTIES_FILE);
            int poolSize = Integer.parseInt(bundle.getString(DB_POOL_SIZE));
            connectionsWaitingToBeUsed = new ArrayBlockingQueue<>(poolSize);
            connectionsInUse = new ArrayBlockingQueue<>(poolSize);
            for (int i = 0; i < poolSize; i++){
                Connection con = getConnection();
                if (con != null){
                    connectionsWaitingToBeUsed.offer(con);
                }
            }
        } catch (ConnectionPoolException ex){
//            logger.warn("Error while getting connection", ex);
            System.out.println("Error");

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
        } catch (ClassNotFoundException ex){
            throw new ConnectionPoolException();
        }
        catch (SQLException ex) {
            throw new ConnectionPoolException();
        }
        return connection;
    }

    public synchronized void putBackConnectionToPool(Connection connection) {
        if (connection != null) {
            if (connectionsInUse.remove(connection)) {
                connectionsWaitingToBeUsed.offer(connection);
            }
        }
    }


    public synchronized Connection getExistingConnectionFromPool() throws ConnectionPoolException {
        Connection newConn = null;
        if (connectionsWaitingToBeUsed.size() == 0){
            throw new ConnectionPoolException("All connections are used");
        } else {
            newConn = connectionsWaitingToBeUsed.poll();
        }
        connectionsInUse.add(newConn);
        if (newConn == null){
            throw new ConnectionPoolException("Error while getting existing connection");
        }
        return newConn;
    }


}


