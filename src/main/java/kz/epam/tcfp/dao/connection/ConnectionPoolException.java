package kz.epam.tcfp.dao.connection;

/**
 * @author Talgat Bekkaliyev
 * @project AdPlatform
 */
public class ConnectionPoolException extends Exception{

    public ConnectionPoolException() {
        super();
    }

    public ConnectionPoolException(String message) {
        super(message);
    }

}
