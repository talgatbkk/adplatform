package kz.epam.tcfp.service.factory;

import kz.epam.tcfp.service.AdvertisementService;
import kz.epam.tcfp.service.UserService;
import kz.epam.tcfp.service.implementation.AdvertisementServiceImpl;
import kz.epam.tcfp.service.implementation.UserServiceImpl;

/**
 * @author Talgat Bekkaliyev
 * @project AdPlatform
 */
public class ServiceFactory {
//    private static final Logger logger = Logger.getLogger(ServiceFactory.class);

    private static ServiceFactory instance;

    private AdvertisementService advertisementService = new AdvertisementServiceImpl();

    private UserService userService = new UserServiceImpl();


    private ServiceFactory() {
    }

    public static AdvertisementService getAdvertisementService() {
        return getInstance().advertisementService;
    }

    public static UserService getUserService() {
        return getInstance().userService;
    }


    private static synchronized ServiceFactory getInstance() {
        if (instance == null) {
            instance = new ServiceFactory();
        }
        return instance;
    }
}
