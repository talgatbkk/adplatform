package kz.epam.tcfp.connection;

/**
 * @author Talgat Bekkaliyev
 * @project AdPlatform
 */
public class DBConstants {


    public static final String AD_ID = "advertisement_id";
    public static final String AD_TITLE = "advertisement_title";
    public static final String AD_DESCRIPTION = "description";
    public static final String AD_CITY_ID = "location_id";
    public static final String AD_POSTED_DATE = "posted_date";
    public static final String AD_CATEGORY_ID = "category_id";
    public static final String AD_PRICE = "price";

    public static final String USER_ID = "user_id";
    public static final String USER_LOGIN = "login";
    public static final String USER_ROLE_ID = "role_id";
    public static final String USER_PASSWORD = "password";
    public static final String USER_FIRST_NAME = "first_name";
    public static final String USER_LAST_NAME = "last_name";
    public static final String USER_EMAIL = "email";
    public static final String USER_REGISTRATION_DATE = "created_date";
    public static final String USER_IS_BANNED = "ban";
    public static final String USER_PHONE_NUMBER = "phone_number";
    public static final String USER_PHONE_NUMBER_ID = "ID";




    public static final String GET_ADS_BY_USER_ID = "{call get_ads_by_user_id(?)}";
    public static final String GET_AD_BY_ID = "SELECT * FROM advertisement WHERE advertisement_id = ?";
    public static final String GET_CUSTOMER_BY_ID = "SELECT * FROM user WHERE user_id = ?";
    public static final String GET_CUSTOMER_ID_BY_LOGIN = "SELECT * FROM user WHERE login = ?";
    public static final String GET_PHONE_NUMBER_BY_CUSTOMER_ID = "SELECT * FROM user_phone WHERE user_id = ?";
    public static final String AUTHENTICATE_CUSTOMER = "SELECT * FROM user WHERE login = ? AND password = ?";
    public static final String INSERT_NEW_CUSTOMER = "{call add_new_user(?, ?, ?, ?, ?, ?, ?, ?, ?, ?)}";
    public static final String CHECK_IF_LOGIN_TAKEN = "SELECT login FROM user WHERE login = ?";
    public static final String CHECK_IF_EMAIL_TAKEN = "SELECT email FROM user WHERE email = ?";
    public static final String CHECK_IF_PHONE_NUMBER_TAKEN = "SELECT phone_number FROM user_phone WHERE phone_number = ?";


    public static final String SQL_QUERY_ERROR = "SQL query error";
}
