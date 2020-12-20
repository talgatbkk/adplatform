package kz.epam.tcfp.dao.factory;

import kz.epam.tcfp.dao.AdvertisementDAO;
import kz.epam.tcfp.dao.CustomerDAO;
import kz.epam.tcfp.dao.connection.ConnectionPool;
import kz.epam.tcfp.dao.implemenation.AdvertisementDAOImpl;
import kz.epam.tcfp.dao.implemenation.CustomerDAOImpl;
import org.apache.log4j.Logger;

/**
 * @author Talgat Bekkaliyev
 * @project AdPlatform
 */
public class DAOFactory {
//    private static final Logger logger = Logger.getLogger(DAOFactory.class);

    private static DAOFactory instance = new DAOFactory();

    private static ConnectionPool connectionPool = new ConnectionPool();

    private static AdvertisementDAO advertisementDAO = new AdvertisementDAOImpl();

    private static CustomerDAO customerDAO = new CustomerDAOImpl();


    private static synchronized DAOFactory getInstance() {
        if (instance == null){
            instance = new DAOFactory();
        }
        return instance;
    }

    public static ConnectionPool getConnectionPool() {
        return getInstance().connectionPool;
    }

    public static AdvertisementDAO getAdvertisementDAO() {
        return getInstance().advertisementDAO;
    }

    public static CustomerDAO getCustomerDAO() {
        return getInstance().customerDAO;
    }


}
