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
    public static final String USER_VIEW_PROFILE_OPEN_EDITING = "/user/open_editing";
    public static final String USER_VIEW_PROFILE_POST_EDIT = "/user/post_edit";
    public static final String USER_INPUT_NEW_PASSWORD = "/user/password";
    public static final String USER_POST_NEW_PASSWORD = "/user/password/post";
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
    public static final String ADMIN_ADD_LOCATION = "/location/add";
    public static final String ADMIN_POST_LOCATION = "/location/post";
    public static final String ADMIN_ADD_CATEGORY = "/category/add";
    public static final String ADMIN_POST_CATEGORY = "/category/post";



    private final Map<String, Service> services;


    public ServiceProvider() {
        services = new HashMap<>();
        services.put(HOME, new FindAdsService());
        services.put(LOGIN, new SignInService());
        services.put(ADVERTISEMENT_SIGN_IN, new SignInService());
        services.put(REGISTER_USER, new SignUpService());
        services.put(USER_VIEW_PROFILE, new OpenProfileService());
        services.put(USER_VIEW_PROFILE_OPEN_EDITING, new OpenEditProfileService());
        services.put(USER_VIEW_PROFILE_POST_EDIT, new PostEditProfileService());
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
        services.put(USER_INPUT_NEW_PASSWORD, new InputNewPasswordService());
        services.put(USER_POST_NEW_PASSWORD, new PostChangedPasswordService());
        services.put(ADMIN_ADD_LOCATION, new OpenAddLocationService());
        services.put(ADMIN_POST_LOCATION, new PostAddLocationService());
        services.put(ADMIN_ADD_CATEGORY, new OpenAddCategoryService());
        services.put(ADMIN_POST_CATEGORY, new PostAddCategoryService());
    }

    public Service getService(String serviceName) {
        return services.get(serviceName.toLowerCase());
    }
}
