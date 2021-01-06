package kz.epam.tcfp.service;

/**
 * @author Talgat Bekkaliyev
 * @project AdPlatform
 */
public class ServiceFactory {

    private static ServiceFactory instance = new ServiceFactory();
    private ServiceProvider serviceProvider = new ServiceProvider();

    public ServiceFactory() {
    }


    public static Service getService(String serviceName) {
        return getInstance().serviceProvider.getService(serviceName);
    }


    private static synchronized ServiceFactory getInstance() {
        if (instance == null){
            instance = new ServiceFactory();
        }
        return instance;
    }
}
