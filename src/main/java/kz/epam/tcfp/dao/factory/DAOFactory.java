package kz.epam.tcfp.dao.factory;

import kz.epam.tcfp.dao.*;
import kz.epam.tcfp.dao.connection.ConnectionPool;
import kz.epam.tcfp.dao.implemenation.*;

/**
 * @author Talgat Bekkaliyev
 * @project AdPlatform
 */
public class DAOFactory {

    private static DAOFactory instance;

    private static ConnectionPool connectionPool = new ConnectionPool();

    private static AdvertisementDAO advertisementDAO = new AdvertisementDAOImpl();

    private static UserDAO userDAO = new UserDAOImpl();

    private static CommentDAO commentDAO = new CommentDAOImpl();

    private static CategoryDAO categoryDAO = new CategoryDAOImpl();

    private static LocationDAO locationDAO = new LocationDAOImpl();

    private static ImageDAO imageDAO = new ImageDAOImpl();


    private DAOFactory() {
    }

    public static DAOFactory getInstance() {
        if (instance == null) {
            synchronized (DAOFactory.class) {
                if (instance == null) {
                    instance = new DAOFactory();
                }
            }
        }
        return instance;
    }




    public static ConnectionPool getConnectionPool() {
        return getInstance().connectionPool;
    }

    public static AdvertisementDAO getAdvertisementDAO() {
        return getInstance().advertisementDAO;
    }

    public static UserDAO getUserDAO() {
        return getInstance().userDAO;
    }

    public static CategoryDAO getCategoryDAO() {
        return  getInstance().categoryDAO;
    }

    public static CommentDAO getCommentDAO() {
        return getInstance().commentDAO;
    }

    public static LocationDAO getLocationDAO() {
        return getInstance().locationDAO;
    }

    public static ImageDAO getImageDAO() {
        return getInstance().imageDAO;
    }
}
