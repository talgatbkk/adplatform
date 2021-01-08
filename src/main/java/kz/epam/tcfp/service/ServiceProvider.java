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
    public static final String USER_UNBAN = "/user/unban";
    public static final String LANGUAGE = "/language";

    private final Map<String, Service> services;


    public ServiceProvider() {
        services = new HashMap<>();
        services.put(HOME, new FindAdsService());
        services.put(LOGIN, new SignInService());
        services.put(ADVERTISEMENT_SIGN_IN, new SignInService());
        services.put(REGISTER_USER, new SignUpService());
        services.put(USER_VIEW_PROFILE, new OpenProfileService());
        services.put(ADVERTISEMENT_VIEW, new ViewAdvertisementService());
        services.put(COMMENT_POST, new PostCommentService());
        services.put(ADVERTISEMENT_POST, new PostAdvertisementService());
        services.put(ADVERTISEMENT_ADD, new InputAdvertisementService());
        services.put(ADVERTISEMENT_SEARCH, new SearchAdvertisementService());
        services.put(ADVERTISEMENT_DELETE, new DeleteAdvertisementService());
        services.put(USER_LOGOUT, new LogOutService());
        services.put(USER_DELETE, new DeleteUserAccountService());
        services.put(USER_BAN, new BanUserAccountService());
        services.put(USER_UNBAN, new UnbanUserAccountService());
        services.put(LANGUAGE, new ChangeLanguageService());

    }

    public Service getService(String serviceName) {
        return services.get(serviceName.toLowerCase());
    }
}
