package kz.epam.tcfp.dao.connection;

/**
 * @author Talgat Bekkaliyev
 * @project AdPlatform
 */
public class DBConstants {


    public final static String AD_ID = "advertisement_id";
    public final static String AD_TITLE = "advertisement_title";
    public final static String AD_DESCRIPTION = "description";
    public final static String AD_CITY_ID = "location_id";
    public final static String AD_POSTED_DATE = "posted_date";
    public final static String AD_CATEGORY_ID = "category_id";
    public final static String AD_PRICE = "price";

    public final static String USER_ID = "user_id";
    public final static String USER_ROLE_ID = "role_id";
    public final static String USER_LOGIN = "login";
    public final static String USER_FIRST_NAME = "first_name";
    public final static String USER_LAST_NAME = "last_name";
    public final static String USER_EMAIL = "email";
    public final static String USER_REGISTRATION_DATE = "created_date";
    public final static String USER_IS_BANNED = "ban";
    public final static String USER_PHONE_NUMBER = "phone_number";
    public final static String USER_PHONE_NUMBER_ID = "ID";




    public final static String GET_ADS_BY_USER_ID = "{call get_ads_by_user_id(?)}";
    public final static String GET_AD_BY_ID = "SELECT * FROM advertisement where advertisement_id = ?";
    public final static String GET_CUSTOMER_BY_ID = "SELECT * FROM user where user_id = ?";
    public final static String GET_PHONE_NUMBER_BY_CUSTOMER_ID = "SELECT * FROM user_phone where user_id = ?";


    public final static String SQL_QUERY_ERROR = "SQL query error";
}
