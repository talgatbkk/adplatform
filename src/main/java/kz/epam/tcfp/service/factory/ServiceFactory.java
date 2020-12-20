package kz.epam.tcfp.service.factory;

import kz.epam.tcfp.service.AdvertisementService;
import kz.epam.tcfp.service.CustomerService;
import kz.epam.tcfp.service.implementation.AdvertisementServiceImpl;
import kz.epam.tcfp.service.implementation.CustomerServiceImpl;
import org.apache.log4j.Logger;

/**
 * @author Talgat Bekkaliyev
 * @project AdPlatform
 */
public class ServiceFactory {
//    private static final Logger logger = Logger.getLogger(ServiceFactory.class);

    private static ServiceFactory instance;

    private AdvertisementService advertisementService = new AdvertisementServiceImpl();

    private CustomerService customerService = new CustomerServiceImpl();


    private ServiceFactory() {
    }

    public static AdvertisementService getAdvertisementService() {
        return getInstance().advertisementService;
    }

    public static CustomerService getCustomerService() {
        return getInstance().customerService;
    }


    private static synchronized ServiceFactory getInstance() {
        if (instance == null) {
            instance = new ServiceFactory();
        }
        return instance;
    }
}
