package kz.epam.tcfp.dao.util;

/**
 * @author Talgat Bekkaliyev
 * @project AdPlatform
 */
public class DBConstants {

    private DBConstants() {
    }

    public static final String AD_ID = "advertisement_id";
    public static final String AD_TITLE = "advertisement_title";
    public static final String AD_DESCRIPTION = "description";
    public static final String AD_LOCATION_ID = "location_id";
    public static final String PARENT_LOCATION_ID = "parent_id";
    public static final String AD_LOCATION_NAME = "location_name";
    public static final String AD_POSTED_DATE = "posted_date";
    public static final String AD_CATEGORY_ID = "category_id";
    public static final String AD_PRICE = "price";
    public static final String IMAGE_ID = "image_id";
    public static final String IMAGE_PATH = "image_path";

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
    public static final String USER_PHONE_NUMBER_ID = "id";

    public static final String COMMENT_POSTED_DATE = "posted_date";
    public static final String COMMENT_USER_ID = "c.user_id";
    public static final String COMMENT_CONTENT = "description";
    public static final String LANGUAGE_ID = "language_id";
    public static final String CATEGORY_ID = "category_id";
    public static final String CATEGORY_NAME = "category_name";
    public static final String SQL_QUERY_ERROR = "SQL query error";


    public static final String GET_ADS_BY_USER_ID = "SELECT * FROM advertisement WHERE user_id = ? ORDER BY posted_date DESC LIMIT ?, ?";
    public static final String COUNT_GET_ADS_BY_USER_ID = "SELECT COUNT(*) FROM advertisement WHERE user_id = ?";
    public static final String GET_AD_BY_ID = "SELECT * FROM advertisement WHERE advertisement_id = ?";
    public static final String GET_AD_COUNT_BY_ID = "SELECT COUNT(*) FROM advertisement WHERE user_id = ?";
    public static final String GET_LOCATION_BY_ID = "SELECT location_name, parent_id FROM location p WHERE location_id = ? and language_id = (SELECT language_id FROM language WHERE language_name = ?)";
    public static final String GET_USER_BY_ID = "SELECT * FROM user WHERE user_id = ?";
    public static final String GET_USER_ID_BY_LOGIN = "SELECT * FROM user WHERE login = ?";
    public static final String GET_PHONE_NUMBER_BY_USER_ID = "SELECT * FROM user_phone WHERE user_id = ?";
    public static final String AUTHENTICATE_USER = "SELECT * FROM user WHERE login = ? AND password = ?";
    public static final String AUTHENTICATE_USER_BY_ID = "SELECT * FROM user WHERE user_id = ? AND password = ?";
    public static final String INSERT_NEW_USER = "{call add_new_user(?, ?, ?, ?, ?, ?, ?, ?, ?, ?)}";
    public static final String CHECK_IF_LOGIN_TAKEN = "SELECT login FROM user WHERE login = ?";
    public static final String CHECK_IF_EMAIL_TAKEN = "SELECT email FROM user WHERE email = ?";
    public static final String CHECK_IF_PHONE_NUMBER_TAKEN = "SELECT phone_number FROM user_phone WHERE phone_number = ?";
    public static final String GET_COMMENTS_BY_AD_ID = "SELECT * FROM comment c INNER JOIN user u ON c.user_id = u.user_id WHERE c.advertisement_id = ? ORDER BY c.posted_date";
    public static final String POST_COMMENT = "INSERT INTO comment VALUES (?, ?, ?, ?, ?)";
    public static final String POST_ADVERTISEMENT = "INSERT INTO advertisement VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
    public static final String GET_CATEGORIES = "SELECT * FROM category WHERE language_id = ? ORDER BY category_name";
    public static final String GET_LOCATIONS = "SELECT * FROM location WHERE language_id = ? ORDER BY location_name";
    public static final String GET_LANGUAGE_ID_BY_NAME = "SELECT * FROM language WHERE language_name = ?";
    public static final String GET_AD_BY_CATEGORY = "SELECT * FROM advertisement WHERE category_id = ? ORDER BY posted_date DESC LIMIT ?, ?";
    public static final String COUNT_GET_AD_BY_CATEGORY = "SELECT COUNT(*) FROM advertisement WHERE category_id = ?";
    public static final String GET_AD_BY_LOCATION = "SELECT * FROM advertisement WHERE location_id = ? ORDER BY posted_date DESC LIMIT ?, ?";
    public static final String COUNT_GET_AD_BY_LOCATION = "SELECT COUNT(*) FROM advertisement WHERE location_id = ?";
    public static final String GET_ALL_ADS = "SELECT * FROM advertisement ORDER BY posted_date DESC LIMIT ?, ?";
    public static final String COUNT_GET_ALL_ADS = "SELECT COUNT(*) FROM advertisement";
    public static final String GET_AD_BY_LOCATION_AND_CATEGORY = "SELECT * FROM advertisement WHERE category_id = ? AND location_id = ? ORDER BY posted_date DESC LIMIT ?, ?";
    public static final String COUNT_GET_AD_BY_LOCATION_AND_CATEGORY = "SELECT COUNT(*) FROM advertisement WHERE category_id = ? AND location_id = ?";
    public static final String SEARCH_AD_BY_TITLE_AND_DESCRIPTION = "SELECT * FROM advertisement WHERE advertisement_title LIKE ? OR description LIKE ? ORDER BY posted_date DESC LIMIT ?, ?";
    public static final String COUNT_SEARCH_AD_BY_TITLE_AND_DESCRIPTION = "SELECT COUNT(*) FROM advertisement WHERE advertisement_title LIKE ? OR description LIKE ?";
    public static final String SEARCH_AD_BY_TITLE_AND_DESCRIPTION_AND_CATEGORY = "SELECT * FROM advertisement WHERE (advertisement_title LIKE ? OR description LIKE ?) AND category_id = ? ORDER BY posted_date DESC LIMIT ?, ?";
    public static final String COUNT_SEARCH_AD_BY_TITLE_AND_DESCRIPTION_AND_CATEGORY = "SELECT COUNT(*) FROM advertisement WHERE (advertisement_title LIKE ? OR description LIKE ?) AND category_id = ?";
    public static final String SEARCH_AD_BY_TITLE_AND_DESCRIPTION_AND_LOCATION = "SELECT * FROM advertisement WHERE (advertisement_title LIKE ? OR description LIKE ?) AND location_id = ? ORDER BY posted_date DESC LIMIT ?, ?";
    public static final String COUNT_SEARCH_AD_BY_TITLE_AND_DESCRIPTION_AND_LOCATION = "SELECT COUNT(*) FROM advertisement WHERE (advertisement_title LIKE ? OR description LIKE ?) AND location_id = ?";
    public static final String SEARCH_AD_BY_TITLE_AND_DESCRIPTION_AND_LOCATION_AND_CATEGORY = "SELECT * FROM advertisement WHERE (advertisement_title LIKE ? OR description LIKE ?) AND location_id = ? AND category_id = ? ORDER BY posted_date DESC LIMIT ?, ?";
    public static final String COUNT_SEARCH_AD_BY_TITLE_AND_DESCRIPTION_AND_LOCATION_AND_CATEGORY = "SELECT COUNT(*) FROM advertisement WHERE (advertisement_title LIKE ? OR description LIKE ?) AND location_id = ? AND category_id = ?";
    public static final String DELETE_AD_BY_USER_ID_AND_AD_ID = "DELETE FROM advertisement WHERE advertisement_id = ?";
    public static final String DELETE_USER_ACCOUNT = "DELETE FROM user WHERE user_id = ?";
    public static final String BAN_USER_ACCOUNT = "UPDATE user SET ban = TRUE WHERE user_id = ?";
    public static final String UNBAN_USER_ACCOUNT = "UPDATE user SET ban = FALSE WHERE user_id = ?";
    public static final String UPDATE_USER_FIRST_NAME = "UPDATE user SET first_name = ? WHERE user_id = ?";
    public static final String UPDATE_USER_LAST_NAME = "UPDATE user SET last_name = ? WHERE user_id = ?";
    public static final String UPDATE_USER_EMAIL = "UPDATE user SET email = ? WHERE user_id = ?";
    public static final String UPDATE_USER_PASSWORD = "UPDATE user SET password = ? WHERE user_id = ?";
    public static final String POST_LOCATION = "INSERT INTO location VALUES (?, ?, ?, ?)";
    public static final String POST_CATEGORY = "INSERT INTO category VALUES (?, ?, ?)";
    public static final String GET_IMAGE = "SELECT * FROM advertisement_image WHERE advertisement_id = ?";
    public static final String POST_IMAGE_PATH = "INSERT INTO advertisement_image VALUES (?, ?, ?)";

    public static final int PARAMETER_INDEX_ONE = 1;
    public static final int PARAMETER_INDEX_TWO = 2;
    public static final int PARAMETER_INDEX_THREE = 3;
    public static final int PARAMETER_INDEX_FOUR = 4;
    public static final int PARAMETER_INDEX_FIVE = 5;
    public static final int PARAMETER_INDEX_SIX = 6;
    public static final int PARAMETER_INDEX_SEVEN = 7;
    public static final int PARAMETER_INDEX_EIGHT = 8;
    public static final int PARAMETER_INDEX_NINE = 9;
    public static final int PARAMETER_INDEX_TEN = 10;
    public static final int INTEGER_ONE = 1;
    public static final int COLUMN_INDEX_ONE = 1;

    public static final Integer ADVERTISEMENT_PER_PAGE = 3;



}

