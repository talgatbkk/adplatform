package kz.epam.tcfp.service;

import kz.epam.tcfp.service.implementation.*;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Talgat Bekkaliyev
 * @project AdPlatform
 */
public class ServiceFactory {

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

    private static final Map<String, Service> SERVICE_MAP = new HashMap<>();
    private static ServiceFactory instance;

    private ServiceFactory() {
    }

    static {
        SERVICE_MAP.put(HOME, new FindAdsService());
        SERVICE_MAP.put(LOGIN, new SignInService());
        SERVICE_MAP.put(ADVERTISEMENT_SIGN_IN, new SignInService());
        SERVICE_MAP.put(REGISTER_USER, new SignUpService());
        SERVICE_MAP.put(USER_VIEW_PROFILE, new OpenProfileService());
        SERVICE_MAP.put(USER_VIEW_PROFILE_OPEN_EDITING, new OpenEditProfileService());
        SERVICE_MAP.put(USER_VIEW_PROFILE_POST_EDIT, new PostEditProfileService());
        SERVICE_MAP.put(ADVERTISEMENT_VIEW, new ViewAdvertisementService());
        SERVICE_MAP.put(COMMENT_POST, new PostCommentService());
        SERVICE_MAP.put(ADVERTISEMENT_POST, new PostAdvertisementService());
        SERVICE_MAP.put(ADVERTISEMENT_ADD, new InputAdvertisementService());
        SERVICE_MAP.put(ADVERTISEMENT_SEARCH, new SearchAdvertisementService());
        SERVICE_MAP.put(ADVERTISEMENT_DELETE, new DeleteAdvertisementService());
        SERVICE_MAP.put(USER_LOGOUT, new LogOutService());
        SERVICE_MAP.put(USER_DELETE, new DeleteUserAccountService());
        SERVICE_MAP.put(USER_BAN, new BanUserAccountService());
        SERVICE_MAP.put(USER_UNBAN, new UnbanUserAccountService());
        SERVICE_MAP.put(LANGUAGE, new ChangeLanguageService());
        SERVICE_MAP.put(USER_INPUT_NEW_PASSWORD, new InputNewPasswordService());
        SERVICE_MAP.put(USER_POST_NEW_PASSWORD, new PostChangedPasswordService());
        SERVICE_MAP.put(ADMIN_ADD_LOCATION, new OpenAddLocationService());
        SERVICE_MAP.put(ADMIN_POST_LOCATION, new PostAddLocationService());
        SERVICE_MAP.put(ADMIN_ADD_CATEGORY, new OpenAddCategoryService());
        SERVICE_MAP.put(ADMIN_POST_CATEGORY, new PostAddCategoryService());
    }

    public static ServiceFactory getInstance(){
        if (instance == null){
            synchronized (ServiceFactory.class){
                if (instance == null){
                    instance = new ServiceFactory();
                    return new ServiceFactory();
                }
            }
        }
        return instance;
    }

    public static Service getService(String request) {
        Service service = null;
        for (Map.Entry<String, Service> serviceSet : SERVICE_MAP.entrySet()) {
            if (request.equalsIgnoreCase(serviceSet.getKey())) {
                service = SERVICE_MAP.get(serviceSet.getKey());
            }
        }
        return service;
    }
}
