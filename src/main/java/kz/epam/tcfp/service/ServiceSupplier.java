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
        services.put("home", new FindAds());
        services.put("login", new SignIn());
        services.put("sign_up", new SignUp());
        services.put("view_profile", new OpenProfile());
        services.put("view_ad", new ViewAdvertisement());
        services.put("post_comment", new PostComment());
        services.put("post_ad", new PostAdvertisement());
        services.put("input_ad", new InputAdvertisement());
        services.put("search", new SearchAdvertisement());
        services.put("delete_ad", new DeleteAdvertisement());
        services.put("logout", new LogOut());
        services.put("delete_user", new DeleteUserAccount());
        services.put("ban_user", new BanUserAccount());

    }

    public Service getService(String serviceName) {
        return services.get(serviceName.toLowerCase());
    }
}
