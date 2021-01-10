package kz.epam.tcfp.dao.implemenation;

import kz.epam.tcfp.dao.AdvertisementDAO;
import kz.epam.tcfp.dao.connection.ClosingUtil;
import kz.epam.tcfp.dao.connection.ConnectionPool;
import kz.epam.tcfp.dao.util.DBConstants;
import kz.epam.tcfp.dao.connection.ConnectionPoolException;
import kz.epam.tcfp.dao.exception.DAOException;
import kz.epam.tcfp.dao.factory.DAOFactory;
import kz.epam.tcfp.model.*;
import org.apache.log4j.Logger;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Talgat Bekkaliyev
 * @project AdPlatform
 */
public class AdvertisementDAOImpl implements AdvertisementDAO {
    private static final Logger LOGGER = Logger.getLogger(AdvertisementDAOImpl.class);
    private static final Character PERCENT_SIGN = '%';
    ConnectionPool connectionPool = DAOFactory.getConnectionPool();

    public AdvertisementDAOImpl() {
    }

    @Override
    public List<Advertisement> getAdvertisementByUserId(Long userId) throws DAOException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        List<Advertisement> allAdvertisements = new ArrayList<>();
        try {
            connection = connectionPool.getExistingConnectionFromPool();
            preparedStatement = connection.prepareStatement(DBConstants.GET_ADS_BY_USER_ID);
            preparedStatement.setLong(1, userId);
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                Advertisement advertisement = buildAdvertisement(resultSet);
                allAdvertisements.add(advertisement);
            }
        } catch (SQLException ex) {
            throw new DAOException(DBConstants.SQL_QUERY_ERROR, ex);
        } catch (ConnectionPoolException ex) {
            throw new DAOException(ex);
        } finally {
            ClosingUtil.closeAll(preparedStatement, resultSet);
            connectionPool.putBackConnectionToPool(connection);
        }
        return allAdvertisements;
    }

    @Override
    public Advertisement getAdvertisementById(Long adId) throws DAOException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        Advertisement advertisement = null;
        try {
            connection = connectionPool.getExistingConnectionFromPool();
            preparedStatement = connection.prepareStatement(DBConstants.GET_AD_BY_ID);
            preparedStatement.setLong(1, adId);
            resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                advertisement = buildAdvertisement(resultSet);
            }
        } catch (SQLException ex) {
            throw new DAOException(DBConstants.SQL_QUERY_ERROR, ex);
        } catch (ConnectionPoolException ex){
            throw new DAOException(ex);
        } finally {
            ClosingUtil.closeAll(preparedStatement, resultSet);
            connectionPool.putBackConnectionToPool(connection);
        }
        return advertisement;
    }

    @Override
    public List<Advertisement> searchAdvertisementsByCategory(Long categoryId) throws DAOException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        List<Advertisement> advertisements = new ArrayList<>();

        try {
            connection = connectionPool.getExistingConnectionFromPool();
            preparedStatement = connection.prepareStatement(DBConstants.GET_AD_BY_CATEGORY);
            preparedStatement.setLong(1, categoryId);
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                Advertisement advertisement = new Advertisement();
                advertisement = buildAdvertisement(resultSet);
                advertisements.add(advertisement);
            }
        } catch (SQLException ex) {
            throw new DAOException(DBConstants.SQL_QUERY_ERROR, ex);
        } catch (ConnectionPoolException ex){
            throw new DAOException(ex);
        } finally {
            ClosingUtil.closeAll(preparedStatement, resultSet);
            connectionPool.putBackConnectionToPool(connection);
        }
        return advertisements;
    }

    @Override
    public List<Advertisement> searchAdvertisementsByCategoryAndLocation(Long categoryId, Long locationId) throws DAOException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        List<Advertisement> advertisements = new ArrayList<>();

        try {
            connection = connectionPool.getExistingConnectionFromPool();
            preparedStatement = connection.prepareStatement(DBConstants.GET_AD_BY_LOCATION_AND_CATEGORY);
            preparedStatement.setLong(1, categoryId);
            preparedStatement.setLong(2, locationId);
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                Advertisement advertisement = new Advertisement();
                advertisement = buildAdvertisement(resultSet);
                advertisements.add(advertisement);
            }
        } catch (SQLException ex) {
            throw new DAOException(DBConstants.SQL_QUERY_ERROR, ex);
        } catch (ConnectionPoolException ex){
            throw new DAOException(ex);
        } finally {
            ClosingUtil.closeAll(preparedStatement, resultSet);
            connectionPool.putBackConnectionToPool(connection);
        }
        return advertisements;
    }

    @Override
    public List<Advertisement> searchAdvertisementsByLocation(Long locationId) throws DAOException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        List<Advertisement> advertisements = new ArrayList<>();

        try {
            connection = connectionPool.getExistingConnectionFromPool();
            preparedStatement = connection.prepareStatement(DBConstants.GET_AD_BY_LOCATION);
            preparedStatement.setLong(1, locationId);
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                Advertisement advertisement = new Advertisement();
                advertisement = buildAdvertisement(resultSet);
                advertisements.add(advertisement);
            }
        } catch (SQLException ex) {
            throw new DAOException(DBConstants.SQL_QUERY_ERROR, ex);
        } catch (ConnectionPoolException ex){
            throw new DAOException(ex);
        } finally {
            ClosingUtil.closeAll(preparedStatement, resultSet);
            connectionPool.putBackConnectionToPool(connection);
        }
        return advertisements;
    }

    @Override
    public List<Advertisement> searchAdvertisementsByDescriptionAndCategoryAndLocation(String descriptionInput, Long categoryId, Long locationId) throws DAOException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        List<Advertisement> advertisements = new ArrayList<>();
        try {
            connection = connectionPool.getExistingConnectionFromPool();
            preparedStatement = connection.prepareStatement(DBConstants.SEARCH_AD_BY_TITLE_AND_DESCRIPTION_AND_LOCATION_AND_CATEGORY);
            preparedStatement.setString(1, PERCENT_SIGN + descriptionInput + PERCENT_SIGN);
            preparedStatement.setString(2, PERCENT_SIGN + descriptionInput + PERCENT_SIGN);
            preparedStatement.setLong(3, locationId);
            preparedStatement.setLong(4, categoryId);
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                Advertisement advertisement = new Advertisement();
                advertisement = buildAdvertisement(resultSet);
                advertisements.add(advertisement);
            }
        } catch (SQLException ex) {
            throw new DAOException(DBConstants.SQL_QUERY_ERROR, ex);
        } catch (ConnectionPoolException ex){
            throw new DAOException(ex);
        } finally {
            ClosingUtil.closeAll(preparedStatement, resultSet);
            connectionPool.putBackConnectionToPool(connection);
        }
        return advertisements;
    }

    @Override
    public List<Advertisement> searchAdvertisementsByDescriptionAndCategory(String descriptionInput, Long categoryId) throws DAOException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        List<Advertisement> advertisements = new ArrayList<>();
        try {
            connection = connectionPool.getExistingConnectionFromPool();
            preparedStatement = connection.prepareStatement(DBConstants.SEARCH_AD_BY_TITLE_AND_DESCRIPTION_AND_CATEGORY);
            preparedStatement.setString(1, PERCENT_SIGN + descriptionInput + PERCENT_SIGN);
            preparedStatement.setString(2, PERCENT_SIGN + descriptionInput + PERCENT_SIGN);
            preparedStatement.setLong(3, categoryId);
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                Advertisement advertisement = new Advertisement();
                advertisement = buildAdvertisement(resultSet);
                advertisements.add(advertisement);
            }
        } catch (SQLException ex) {
            throw new DAOException(DBConstants.SQL_QUERY_ERROR, ex);
        } catch (ConnectionPoolException ex){
            throw new DAOException(ex);
        } finally {
            ClosingUtil.closeAll(preparedStatement, resultSet);
            connectionPool.putBackConnectionToPool(connection);
        }
        return advertisements;
    }

    @Override
    public List<Advertisement> searchAdvertisementsByDescriptionAndLocation(String descriptionInput, Long locationId) throws DAOException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        List<Advertisement> advertisements = new ArrayList<>();
        try {
            connection = connectionPool.getExistingConnectionFromPool();
            preparedStatement = connection.prepareStatement(DBConstants.SEARCH_AD_BY_TITLE_AND_DESCRIPTION_AND_LOCATION);
            preparedStatement.setString(1, PERCENT_SIGN + descriptionInput + PERCENT_SIGN);
            preparedStatement.setString(2, PERCENT_SIGN + descriptionInput + PERCENT_SIGN);
            preparedStatement.setLong(3, locationId);
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                Advertisement advertisement = new Advertisement();
                advertisement = buildAdvertisement(resultSet);
                advertisements.add(advertisement);
            }
        } catch (SQLException ex) {
            throw new DAOException(DBConstants.SQL_QUERY_ERROR, ex);
        } catch (ConnectionPoolException ex){
            throw new DAOException(ex);
        } finally {
            ClosingUtil.closeAll(preparedStatement, resultSet);
            connectionPool.putBackConnectionToPool(connection);
        }
        return advertisements;
    }

    @Override
    public List<Advertisement> searchAdvertisementsByDescription(String descriptionInput) throws DAOException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        List<Advertisement> advertisements = new ArrayList<>();
        try {
            connection = connectionPool.getExistingConnectionFromPool();
            preparedStatement = connection.prepareStatement(DBConstants.SEARCH_AD_BY_TITLE_AND_DESCRIPTION);
            preparedStatement.setString(1, PERCENT_SIGN + descriptionInput + PERCENT_SIGN);
            preparedStatement.setString(2, PERCENT_SIGN + descriptionInput + PERCENT_SIGN);
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                Advertisement advertisement = new Advertisement();
                advertisement = buildAdvertisement(resultSet);
                advertisements.add(advertisement);
            }
        } catch (SQLException ex) {
            throw new DAOException(DBConstants.SQL_QUERY_ERROR, ex);
        } catch (ConnectionPoolException ex){
            throw new DAOException(ex);
        } finally {
            ClosingUtil.closeAll(preparedStatement, resultSet);
            connectionPool.putBackConnectionToPool(connection);
        }
        return advertisements;
    }


    @Override
    public Integer getAdvertisementCountById(Long userId) throws DAOException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        Integer advertisementCount = null;
        try {
            connection = connectionPool.getExistingConnectionFromPool();
            preparedStatement = connection.prepareStatement(DBConstants.GET_AD_COUNT_BY_ID);
            preparedStatement.setLong(1, userId);
            resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                advertisementCount = resultSet.getInt(1);
            }
        } catch (SQLException ex) {
            throw new DAOException(DBConstants.SQL_QUERY_ERROR, ex);
        } catch (ConnectionPoolException ex){
            throw new DAOException(ex);
        } finally {
            ClosingUtil.closeAll(preparedStatement, resultSet);
            connectionPool.putBackConnectionToPool(connection);
        }
        return advertisementCount;
    }

    public Location getLocationNamesById(Long locationId, String languageCode) throws DAOException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        Location location = new Location(locationId);
        try {
            connection = connectionPool.getExistingConnectionFromPool();
            preparedStatement = connection.prepareStatement(DBConstants.GET_LOCATION_BY_ID);
            preparedStatement.setLong(1, locationId);
            preparedStatement.setString(2, languageCode);
            resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                location.setName(resultSet.getString(1));
                location.setParentId(resultSet.getLong(2));
            }
        } catch (SQLException ex) {
            throw new DAOException(DBConstants.SQL_QUERY_ERROR, ex);
        } catch (ConnectionPoolException ex){
            throw new DAOException(ex);
        } finally {
            ClosingUtil.closeAll(preparedStatement, resultSet);
            connectionPool.putBackConnectionToPool(connection);
        }
        return location;
    }

    @Override
    public List<Comment> getCommentsAByAdvertisementId(Long adId) throws DAOException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        List<Comment> comments = new ArrayList<>();
        try {
            connection = connectionPool.getExistingConnectionFromPool();
            preparedStatement = connection.prepareStatement(DBConstants.GET_COMMENTS_BY_AD_ID);
            preparedStatement.setLong(1, adId);
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                Comment comment = buildComment(resultSet);
                comments.add(comment);
            }
        } catch (SQLException ex) {
            throw new DAOException(DBConstants.SQL_QUERY_ERROR, ex);
        } catch (ConnectionPoolException ex){
            throw new DAOException(ex);
        } finally {
            ClosingUtil.closeAll(preparedStatement, resultSet);
            connectionPool.putBackConnectionToPool(connection);
        }
        return comments;
    }

    @Override
    public boolean postComment(Comment comment) throws DAOException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        Integer rows = null;
        try {
            connection = connectionPool.getExistingConnectionFromPool();
            preparedStatement = connection.prepareStatement(DBConstants.POST_COMMENT);
            preparedStatement.setString(1, null);
            preparedStatement.setLong(2, comment.getAdId());
            preparedStatement.setLong(3, comment.getAuthorId());
            preparedStatement.setString(4, comment.getContent());
            preparedStatement.setTimestamp(5, new java.sql.Timestamp(new java.util.Date().getTime()));
            rows = preparedStatement.executeUpdate();
            if (rows == 1){
                return true;
            }
        } catch (SQLException ex) {
        throw new DAOException(DBConstants.SQL_QUERY_ERROR, ex);
        } catch (ConnectionPoolException ex){
            throw new DAOException(ex);
        } finally {
            ClosingUtil.closeAll(preparedStatement);
            connectionPool.putBackConnectionToPool(connection);
    }
        return false;
    }

    @Override
    public boolean deleteAdvertisementByUserIdAndAdId(Long adId) throws DAOException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        Integer rows = null;
        try {
            connection = connectionPool.getExistingConnectionFromPool();
            preparedStatement = connection.prepareStatement(DBConstants.DELETE_AD_BY_USER_ID_AND_AD_ID);
            preparedStatement.setLong(1, adId);
            rows = preparedStatement.executeUpdate();
            if (rows == 1){
                return true;
            }
        } catch (SQLException ex) {
            throw new DAOException(DBConstants.SQL_QUERY_ERROR, ex);
        } catch (ConnectionPoolException ex){
            throw new DAOException(ex);
        } finally {
            ClosingUtil.closeAll(preparedStatement);
            connectionPool.putBackConnectionToPool(connection);
        }
        return false;
    }

    @Override
    public boolean postAdvertisement(Advertisement advertisement) throws DAOException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        Integer rows = null;
        try {
            connection = connectionPool.getExistingConnectionFromPool();
            preparedStatement = connection.prepareStatement(DBConstants.POST_ADVERTISEMENT);
            preparedStatement.setString(1, null);
            preparedStatement.setLong(2, advertisement.getUserId());
            preparedStatement.setString(3, advertisement.getTitle());
            preparedStatement.setString(4, advertisement.getDescription());
            preparedStatement.setLong(5, advertisement.getLocation().getId());
            preparedStatement.setTimestamp(6, new java.sql.Timestamp(new java.util.Date().getTime()));
            preparedStatement.setLong(7, advertisement.getCategory().getCategoryId());
            preparedStatement.setInt(8, advertisement.getPrice());
            rows = preparedStatement.executeUpdate();
            if (rows == 1){
                return true;
            }
        } catch (SQLException ex) {
            throw new DAOException(DBConstants.SQL_QUERY_ERROR, ex);
        } catch (ConnectionPoolException ex){
            throw new DAOException(ex);
        } finally {
            ClosingUtil.closeAll(preparedStatement);
            connectionPool.putBackConnectionToPool(connection);
        }
        return false;
    }

    @Override
    public List<Category> getCategories(Long languageId) throws DAOException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        List<Category> categories = new ArrayList<>();
        try {
            connection = connectionPool.getExistingConnectionFromPool();
            preparedStatement = connection.prepareStatement(DBConstants.GET_CATEGORIES);
            preparedStatement.setLong(1, languageId);
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                Category category = new Category();
                category.setCategoryId(resultSet.getLong(DBConstants.CATEGORY_ID));
                category.setCategoryName(resultSet.getString(DBConstants.CATEGORY_NAME));
                categories.add(category);
            }
        } catch (SQLException ex) {
            throw new DAOException(DBConstants.SQL_QUERY_ERROR, ex);
        } catch (ConnectionPoolException ex){
            throw new DAOException(ex);
        } finally {
            ClosingUtil.closeAll(preparedStatement, resultSet);
            connectionPool.putBackConnectionToPool(connection);
        }
        return categories;
    }


    @Override
    public Long getLanguageIdByName(String languageName) throws DAOException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        Long languageId = null;
        try {
            connection = connectionPool.getExistingConnectionFromPool();
            preparedStatement = connection.prepareStatement(DBConstants.GET_LANGUAGE_ID_BY_NAME);
            preparedStatement.setString(1, languageName);
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                languageId = resultSet.getLong(DBConstants.LANGUAGE_ID);
            }
        } catch (SQLException ex) {
            throw new DAOException(DBConstants.SQL_QUERY_ERROR, ex);
        } catch (ConnectionPoolException ex){
            throw new DAOException(ex);
        } finally {
            ClosingUtil.closeAll(preparedStatement, resultSet);
            connectionPool.putBackConnectionToPool(connection);
        }
        return languageId;
    }

    @Override
    public List<Advertisement> getAllAdvertisements() throws DAOException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        List<Advertisement> advertisements = new ArrayList<>();

        try {
            connection = connectionPool.getExistingConnectionFromPool();
            preparedStatement = connection.prepareStatement(DBConstants.GET_ALL_ADS);
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                Advertisement advertisement;
                advertisement = buildAdvertisement(resultSet);
                advertisements.add(advertisement);
            }
        } catch (SQLException ex) {
            throw new DAOException(DBConstants.SQL_QUERY_ERROR, ex);
        } catch (ConnectionPoolException ex){
            throw new DAOException(ex);
        } finally {
            ClosingUtil.closeAll(preparedStatement, resultSet);
            connectionPool.putBackConnectionToPool(connection);
        }
        return advertisements;
    }


    @Override
    public List<Location> getLocations(Long languageId) throws DAOException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        List<Location> locations = new ArrayList<>();
        try {
            connection = connectionPool.getExistingConnectionFromPool();
            preparedStatement = connection.prepareStatement(DBConstants.GET_LOCATIONS);
            preparedStatement.setLong(1, languageId);
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                Location location = buildLocation(resultSet);
                locations.add(location);
            }
        } catch (SQLException ex) {
            throw new DAOException(DBConstants.SQL_QUERY_ERROR, ex);
        } catch (ConnectionPoolException ex){
            throw new DAOException(ex);
        } finally {
            ClosingUtil.closeAll(preparedStatement, resultSet);
            connectionPool.putBackConnectionToPool(connection);
        }
        return locations;
    }

    @Override
    public boolean postLocation(Location location) throws DAOException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        Integer rows = null;
        try {
            connection = connectionPool.getExistingConnectionFromPool();
            preparedStatement = connection.prepareStatement(DBConstants.POST_LOCATION);
            preparedStatement.setString(1, null);
            preparedStatement.setLong(2, location.getParentId());
            preparedStatement.setLong(3, location.getLanguageId());
            preparedStatement.setString(4, location.getName());
            rows = preparedStatement.executeUpdate();
            if (rows == 1){
                return true;
            }
        } catch (SQLException ex) {
            throw new DAOException(DBConstants.SQL_QUERY_ERROR, ex);
        } catch (ConnectionPoolException ex){
            throw new DAOException(ex);
        } finally {
            ClosingUtil.closeAll(preparedStatement);
            connectionPool.putBackConnectionToPool(connection);
        }
        return false;
    }


    private Location buildLocation(ResultSet resultSet) throws SQLException {
        Location location = new Location();
        location.setId(resultSet.getLong(DBConstants.AD_LOCATION_ID));
        location.setParentId(resultSet.getLong(DBConstants.PARENT_LOCATION_ID));
        location.setName(resultSet.getString(DBConstants.AD_LOCATION_NAME));
        return location;
    }

    private Comment buildComment(ResultSet resultSet) throws SQLException {
        Comment comment = new Comment();
        comment.setAuthorId(resultSet.getLong(DBConstants.COMMENT_USER_ID));
        comment.setAuthorFirstName(resultSet.getString(DBConstants.USER_FIRST_NAME));
        comment.setAuthorLastName(resultSet.getString(DBConstants.USER_LAST_NAME));
        comment.setAuthorLogin(resultSet.getString(DBConstants.USER_LOGIN));
        comment.setPostedDate(new DateTimeInUTC(resultSet.getTimestamp(DBConstants.COMMENT_POSTED_DATE)));
        comment.setContent(resultSet.getString(DBConstants.COMMENT_CONTENT));
        return comment;
    }

    private Advertisement buildAdvertisement(ResultSet resultSet) throws SQLException {
        Advertisement advertisement = new Advertisement();
        advertisement.setAdId(resultSet.getLong(DBConstants.AD_ID));
        advertisement.setUserId(resultSet.getLong(DBConstants.USER_ID));
        advertisement.setTitle(resultSet.getString(DBConstants.AD_TITLE));
        advertisement.setDescription(resultSet.getString(DBConstants.AD_DESCRIPTION));
        advertisement.setLocation(new Location(resultSet.getLong(DBConstants.AD_LOCATION_ID)));
        advertisement.setPostedDate(new DateTimeInUTC(resultSet.getTimestamp(DBConstants.AD_POSTED_DATE)));
        advertisement.setCategory(new Category(resultSet.getLong(DBConstants.AD_CATEGORY_ID)));
        advertisement.setPrice(resultSet.getInt(DBConstants.AD_PRICE));
        return advertisement;
    }

}
