package kz.epam.tcfp.service;

import kz.epam.tcfp.service.implementation.*;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Talgat Bekkaliyev
 * @project AdPlatform
 */
public class ServiceSupplier {

    private final Map<String, Service> services;



    public ServiceSupplier() {
        services = new HashMap<>();
        services.put("/home", new FindAds());
        services.put("/login", new SignIn());
        services.put("/advertisement/signin", new SignIn());
        services.put("/user/post", new SignUp());
        services.put("/user/view", new OpenProfile());
        services.put("/advertisement/view", new ViewAdvertisement());
        services.put("/advertisement/view/comment/post", new PostComment());
        services.put("/advertisement/post", new PostAdvertisement());
        services.put("/advertisement/add", new InputAdvertisement());
        services.put("/advertisement/search", new SearchAdvertisement());
        services.put("/advertisement/delete", new DeleteAdvertisement());
        services.put("/user/logout", new LogOut());
        services.put("/user/delete", new DeleteUserAccount());
        services.put("/user/ban", new BanUserAccount());

    }

    public Service getService(String serviceName) {
        return services.get(serviceName.toLowerCase());
    }
}
