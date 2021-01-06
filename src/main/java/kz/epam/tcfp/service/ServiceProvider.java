package kz.epam.tcfp.service;

import kz.epam.tcfp.service.implementation.*;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Talgat Bekkaliyev
 * @project AdPlatform
 */
public class ServiceProvider {

    public static final String LOGIN = "/login";
    public static final String HOME = "/home";
    public static final String ADVERTISEMENT_SIGN_IN = "/advertisement/signin";
    public static final String REGISTER_USER = "/user/post";
    public static final String USER_VIEW_PROFILE = "/user/view";
    public static final String ADVERTISEMENT_VIEW = "/advertisement/view";
    public static final String COMMENT_POST = "/advertisement/view/comment/post";
    public static final String ADVERTISEMENT_POST = "/advertisement/post";
    public static final String ADVERTISEMENT_ADD = "/advertisement/add";
    public static final String ADVERTISEMENT_SEARCH = "/advertisement/search";
    public static final String ADVERTISEMENT_DELETE = "/advertisement/delete";
    public static final String USER_LOGOUT = "/user/logout";
    public static final String USER_DELETE = "/user/delete";
    public static final String USER_BAN = "/user/ban";
    public static final String LANGUAGE = "/language";

    private final Map<String, Service> services;


    public ServiceProvider() {
        services = new HashMap<>();
        services.put(HOME, new FindAds());
        services.put(LOGIN, new SignIn());
        services.put(ADVERTISEMENT_SIGN_IN, new SignIn());
        services.put(REGISTER_USER, new SignUp());
        services.put(USER_VIEW_PROFILE, new OpenProfile());
        services.put(ADVERTISEMENT_VIEW, new ViewAdvertisement());
        services.put(COMMENT_POST, new PostComment());
        services.put(ADVERTISEMENT_POST, new PostAdvertisement());
        services.put(ADVERTISEMENT_ADD, new InputAdvertisement());
        services.put(ADVERTISEMENT_SEARCH, new SearchAdvertisement());
        services.put(ADVERTISEMENT_DELETE, new DeleteAdvertisement());
        services.put(USER_LOGOUT, new LogOut());
        services.put(USER_DELETE, new DeleteUserAccount());
        services.put(USER_BAN, new BanUserAccount());
        services.put(LANGUAGE, new ChangeLanguage());

    }

    public Service getService(String serviceName) {
        return services.get(serviceName.toLowerCase());
    }
}
