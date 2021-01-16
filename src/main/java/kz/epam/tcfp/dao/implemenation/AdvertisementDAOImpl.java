package kz.epam.tcfp.dao.implemenation;

import kz.epam.tcfp.dao.AdvertisementDAO;
import kz.epam.tcfp.dao.ImageDAO;
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
    public static final int COLUMN_INDEX_ONE = 1;

    ConnectionPool connectionPool = DAOFactory.getConnectionPool();


    public AdvertisementDAOImpl() {
    }

    @Override
    public List<Advertisement> getAdvertisementByUserId(Long userId, Integer page) throws DAOException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        List<Advertisement> allAdvertisements = new ArrayList<>();
        try {
            connection = connectionPool.getExistingConnectionFromPool();
            preparedStatement = connection.prepareStatement(DBConstants.GET_ADS_BY_USER_ID);
            preparedStatement.setLong(1, userId);
            preparedStatement.setInt(2, (page * DBConstants.ADVERTISEMENT_PER_PAGE) - DBConstants.ADVERTISEMENT_PER_PAGE);
            preparedStatement.setInt(3, DBConstants.ADVERTISEMENT_PER_PAGE);
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
    public Integer countGetAdvertisementByUserId(Long userId) throws DAOException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try {
            connection = connectionPool.getExistingConnectionFromPool();
            preparedStatement = connection.prepareStatement(DBConstants.COUNT_GET_ADS_BY_USER_ID);
            preparedStatement.setLong(1, userId);
            resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                return resultSet.getInt(1);
            }
        } catch (SQLException ex) {
            throw new DAOException(DBConstants.SQL_QUERY_ERROR, ex);
        } catch (ConnectionPoolException ex) {
            throw new DAOException(ex);
        } finally {
            ClosingUtil.closeAll(preparedStatement, resultSet);
            connectionPool.putBackConnectionToPool(connection);
        }
        return 0;
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
    public List<Advertisement> searchAdvertisementsByCategory(Long categoryId, Integer page) throws DAOException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        List<Advertisement> advertisements = new ArrayList<>();

        try {
            connection = connectionPool.getExistingConnectionFromPool();
            preparedStatement = connection.prepareStatement(DBConstants.GET_AD_BY_CATEGORY);
            preparedStatement.setLong(1, categoryId);
            preparedStatement.setInt(2, (page * DBConstants.ADVERTISEMENT_PER_PAGE) - DBConstants.ADVERTISEMENT_PER_PAGE);
            preparedStatement.setInt(3, DBConstants.ADVERTISEMENT_PER_PAGE);
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
    public List<Advertisement> searchAdvertisementsByCategoryAndLocation(Long categoryId, Long locationId, Integer page) throws DAOException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        List<Advertisement> advertisements = new ArrayList<>();

        try {
            connection = connectionPool.getExistingConnectionFromPool();
            preparedStatement = connection.prepareStatement(DBConstants.GET_AD_BY_LOCATION_AND_CATEGORY);
            preparedStatement.setLong(1, categoryId);
            preparedStatement.setLong(2, locationId);
            preparedStatement.setInt(3, (page * DBConstants.ADVERTISEMENT_PER_PAGE) - DBConstants.ADVERTISEMENT_PER_PAGE);
            preparedStatement.setInt(4, DBConstants.ADVERTISEMENT_PER_PAGE);
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
    public List<Advertisement> searchAdvertisementsByLocation(Long locationId, Integer page) throws DAOException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        List<Advertisement> advertisements = new ArrayList<>();

        try {
            connection = connectionPool.getExistingConnectionFromPool();
            preparedStatement = connection.prepareStatement(DBConstants.GET_AD_BY_LOCATION);
            preparedStatement.setLong(1, locationId);
            preparedStatement.setInt(2, (page * DBConstants.ADVERTISEMENT_PER_PAGE) - DBConstants.ADVERTISEMENT_PER_PAGE);
            preparedStatement.setInt(3, DBConstants.ADVERTISEMENT_PER_PAGE);
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
    public List<Advertisement> searchAdvertisementsByDescriptionAndCategoryAndLocation(String descriptionInput, Long categoryId, Long locationId, Integer page) throws DAOException {
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
            preparedStatement.setInt(5, (page * DBConstants.ADVERTISEMENT_PER_PAGE) - DBConstants.ADVERTISEMENT_PER_PAGE);
            preparedStatement.setInt(6, DBConstants.ADVERTISEMENT_PER_PAGE);
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
    public List<Advertisement> searchAdvertisementsByDescriptionAndCategory(String descriptionInput, Long categoryId, Integer page) throws DAOException {
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
            preparedStatement.setInt(4, (page * DBConstants.ADVERTISEMENT_PER_PAGE) - DBConstants.ADVERTISEMENT_PER_PAGE);
            preparedStatement.setInt(5, DBConstants.ADVERTISEMENT_PER_PAGE);
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
    public List<Advertisement> searchAdvertisementsByDescriptionAndLocation(String descriptionInput, Long locationId, Integer page) throws DAOException {
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
            preparedStatement.setInt(4, (page * DBConstants.ADVERTISEMENT_PER_PAGE) - DBConstants.ADVERTISEMENT_PER_PAGE);
            preparedStatement.setInt(5, DBConstants.ADVERTISEMENT_PER_PAGE);
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
    public List<Advertisement> searchAdvertisementsByDescription(String descriptionInput, Integer page) throws DAOException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        List<Advertisement> advertisements = new ArrayList<>();
        try {
            connection = connectionPool.getExistingConnectionFromPool();
            preparedStatement = connection.prepareStatement(DBConstants.SEARCH_AD_BY_TITLE_AND_DESCRIPTION);
            preparedStatement.setString(1, PERCENT_SIGN + descriptionInput + PERCENT_SIGN);
            preparedStatement.setString(2, PERCENT_SIGN + descriptionInput + PERCENT_SIGN);
            preparedStatement.setInt(3, (page * DBConstants.ADVERTISEMENT_PER_PAGE) - DBConstants.ADVERTISEMENT_PER_PAGE);
            preparedStatement.setInt(4, DBConstants.ADVERTISEMENT_PER_PAGE);
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
    public Integer countSearchAdvertisementsByCategory(Long categoryId) throws DAOException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try {
            connection = connectionPool.getExistingConnectionFromPool();
            preparedStatement = connection.prepareStatement(DBConstants.COUNT_GET_AD_BY_CATEGORY);
            preparedStatement.setLong(1, categoryId);
            resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                return resultSet.getInt(COLUMN_INDEX_ONE);
            }
        } catch (SQLException ex) {
            throw new DAOException(DBConstants.SQL_QUERY_ERROR, ex);
        } catch (ConnectionPoolException ex){
            throw new DAOException(ex);
        } finally {
            ClosingUtil.closeAll(preparedStatement, resultSet);
            connectionPool.putBackConnectionToPool(connection);
        }
        return 0;
    }

    @Override
    public Integer countSearchAdvertisementsByCategoryAndLocation(Long categoryId, Long locationId) throws DAOException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try {
            connection = connectionPool.getExistingConnectionFromPool();
            preparedStatement = connection.prepareStatement(DBConstants.COUNT_GET_AD_BY_LOCATION_AND_CATEGORY);
            preparedStatement.setLong(1, categoryId);
            preparedStatement.setLong(2, locationId);
            resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                return resultSet.getInt(COLUMN_INDEX_ONE);
            }
        } catch (SQLException ex) {
            throw new DAOException(DBConstants.SQL_QUERY_ERROR, ex);
        } catch (ConnectionPoolException ex){
            throw new DAOException(ex);
        } finally {
            ClosingUtil.closeAll(preparedStatement, resultSet);
            connectionPool.putBackConnectionToPool(connection);
        }
        return 0;
    }

    @Override
    public Integer countSearchAdvertisementsByLocation(Long locationId) throws DAOException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try {
            connection = connectionPool.getExistingConnectionFromPool();
            preparedStatement = connection.prepareStatement(DBConstants.COUNT_GET_AD_BY_LOCATION);
            preparedStatement.setLong(1, locationId);
            resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                return resultSet.getInt(COLUMN_INDEX_ONE);
            }
        } catch (SQLException ex) {
            throw new DAOException(DBConstants.SQL_QUERY_ERROR, ex);
        } catch (ConnectionPoolException ex){
            throw new DAOException(ex);
        } finally {
            ClosingUtil.closeAll(preparedStatement, resultSet);
            connectionPool.putBackConnectionToPool(connection);
        }
        return 0;
    }

    @Override
    public Integer countSearchAdvertisementsByDescriptionAndCategoryAndLocation(String descriptionInput, Long categoryId, Long locationId) throws DAOException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try {
            connection = connectionPool.getExistingConnectionFromPool();
            preparedStatement = connection.prepareStatement(DBConstants.COUNT_SEARCH_AD_BY_TITLE_AND_DESCRIPTION_AND_LOCATION_AND_CATEGORY);
            preparedStatement.setString(1, PERCENT_SIGN + descriptionInput + PERCENT_SIGN);
            preparedStatement.setString(2, PERCENT_SIGN + descriptionInput + PERCENT_SIGN);
            preparedStatement.setLong(3, locationId);
            preparedStatement.setLong(4, categoryId);
            resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                return resultSet.getInt(COLUMN_INDEX_ONE);
            }
        } catch (SQLException ex) {
            throw new DAOException(DBConstants.SQL_QUERY_ERROR, ex);
        } catch (ConnectionPoolException ex){
            throw new DAOException(ex);
        } finally {
            ClosingUtil.closeAll(preparedStatement, resultSet);
            connectionPool.putBackConnectionToPool(connection);
        }
        return 0;
    }

    @Override
    public Integer countSearchAdvertisementsByDescriptionAndCategory(String descriptionInput, Long categoryId) throws DAOException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try {
            connection = connectionPool.getExistingConnectionFromPool();
            preparedStatement = connection.prepareStatement(DBConstants.COUNT_SEARCH_AD_BY_TITLE_AND_DESCRIPTION_AND_CATEGORY);
            preparedStatement.setString(1, PERCENT_SIGN + descriptionInput + PERCENT_SIGN);
            preparedStatement.setString(2, PERCENT_SIGN + descriptionInput + PERCENT_SIGN);
            preparedStatement.setLong(3, categoryId);
            resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                return resultSet.getInt(COLUMN_INDEX_ONE);
            }
        } catch (SQLException ex) {
            throw new DAOException(DBConstants.SQL_QUERY_ERROR, ex);
        } catch (ConnectionPoolException ex){
            throw new DAOException(ex);
        } finally {
            ClosingUtil.closeAll(preparedStatement, resultSet);
            connectionPool.putBackConnectionToPool(connection);
        }
        return 0;
    }

    @Override
    public Integer countSearchAdvertisementsByDescriptionAndLocation(String descriptionInput, Long locationId) throws DAOException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try {
            connection = connectionPool.getExistingConnectionFromPool();
            preparedStatement = connection.prepareStatement(DBConstants.COUNT_SEARCH_AD_BY_TITLE_AND_DESCRIPTION_AND_LOCATION);
            preparedStatement.setString(1, PERCENT_SIGN + descriptionInput + PERCENT_SIGN);
            preparedStatement.setString(2, PERCENT_SIGN + descriptionInput + PERCENT_SIGN);
            preparedStatement.setLong(3, locationId);
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                return resultSet.getInt(COLUMN_INDEX_ONE);
            }
        } catch (SQLException ex) {
            throw new DAOException(DBConstants.SQL_QUERY_ERROR, ex);
        } catch (ConnectionPoolException ex){
            throw new DAOException(ex);
        } finally {
            ClosingUtil.closeAll(preparedStatement, resultSet);
            connectionPool.putBackConnectionToPool(connection);
        }
        return 0;
    }

    @Override
    public Integer countSearchAdvertisementsByDescription(String descriptionInput) throws DAOException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try {
            connection = connectionPool.getExistingConnectionFromPool();
            preparedStatement = connection.prepareStatement(DBConstants.COUNT_SEARCH_AD_BY_TITLE_AND_DESCRIPTION);
            preparedStatement.setString(1, PERCENT_SIGN + descriptionInput + PERCENT_SIGN);
            preparedStatement.setString(2, PERCENT_SIGN + descriptionInput + PERCENT_SIGN);
            resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                return resultSet.getInt(COLUMN_INDEX_ONE);
            }
        } catch (SQLException ex) {
            throw new DAOException(DBConstants.SQL_QUERY_ERROR, ex);
        } catch (ConnectionPoolException ex){
            throw new DAOException(ex);
        } finally {
            ClosingUtil.closeAll(preparedStatement, resultSet);
            connectionPool.putBackConnectionToPool(connection);
        }
        return 0;
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
    public List<Advertisement> getAllAdvertisements(Integer page) throws DAOException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        List<Advertisement> advertisements = new ArrayList<>();
        try {
            connection = connectionPool.getExistingConnectionFromPool();
            preparedStatement = connection.prepareStatement(DBConstants.GET_ALL_ADS);
            preparedStatement.setInt(1, (page * DBConstants.ADVERTISEMENT_PER_PAGE) - DBConstants.ADVERTISEMENT_PER_PAGE);
            preparedStatement.setInt(2, DBConstants.ADVERTISEMENT_PER_PAGE);
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
    public Integer getCountAllAdvertisements() throws DAOException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try {
            connection = connectionPool.getExistingConnectionFromPool();
            preparedStatement = connection.prepareStatement(DBConstants.COUNT_GET_ALL_ADS);
            resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                return resultSet.getInt(COLUMN_INDEX_ONE);
            }
        } catch (SQLException ex) {
            throw new DAOException(DBConstants.SQL_QUERY_ERROR, ex);
        } catch (ConnectionPoolException ex){
            throw new DAOException(ex);
        } finally {
            ClosingUtil.closeAll(preparedStatement, resultSet);
            connectionPool.putBackConnectionToPool(connection);
        }
        return 0;
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
        Image image = buildImage(advertisement);
        if (image != null) {
            advertisement.setImage(image);
        }
        return advertisement;
    }

    private Image buildImage(Advertisement advertisement) {
        ImageDAO imageDAO = DAOFactory.getImageDAO();
        Image image= null;
        try {
            image = imageDAO.getImage(advertisement.getAdId());
            if (image != null) {
                image.setAdvertisementId(advertisement.getAdId());
            }
        } catch (DAOException e) {
            LOGGER.warn("Error while getting image", e);
        }
        return image;
    }
}
