package kz.epam.tcfp.dao.implemenation;

import kz.epam.tcfp.dao.AdvertisementDAO;
import kz.epam.tcfp.dao.connection.ClosingUtil;
import kz.epam.tcfp.dao.connection.ConnectionPool;
import kz.epam.tcfp.dao.connection.DBConstants;
import kz.epam.tcfp.dao.connection.ConnectionPoolException;
import kz.epam.tcfp.dao.exception.DAOException;
import kz.epam.tcfp.dao.factory.DAOFactory;
import kz.epam.tcfp.model.Advertisement;
import kz.epam.tcfp.model.Comment;
import kz.epam.tcfp.model.Location;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Talgat Bekkaliyev
 * @project AdPlatform
 */
public class AdvertisementDAOImpl implements AdvertisementDAO {

    public static final Integer AD_ID_COLUMN_INDEX = 1;
    ConnectionPool connectionPool = DAOFactory.getConnectionPool();

    public AdvertisementDAOImpl() {
    }

    @Override
    public List<Advertisement> getAdvertisementByCustomerId(Integer customerId) throws DAOException {
        Connection connection = null;
        CallableStatement callableStatement = null;
        ResultSet resultSet = null;
        List<Advertisement> allAdvertisements = new ArrayList<>();
        try {
            connection = connectionPool.getExistingConnectionFromPool();
            callableStatement = connection.prepareCall(DBConstants.GET_ADS_BY_USER_ID);
            callableStatement.setInt(DBConstants.USER_ID, customerId);
            resultSet = callableStatement.executeQuery();
            while (resultSet.next()) {
                Advertisement advertisement = buildAdvertisement(resultSet);
                allAdvertisements.add(advertisement);
            }
        } catch (SQLException ex) {
            throw new DAOException(DBConstants.SQL_QUERY_ERROR, ex);
        } catch (ConnectionPoolException ex) {
            throw new DAOException(ex);
        } finally {
            ClosingUtil.closeAll(callableStatement, resultSet);
            connectionPool.putBackConnectionToPool(connection);
        }
        return allAdvertisements;
    }

    @Override
    public Advertisement getAdvertisementById(Integer adId) throws DAOException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        Advertisement advertisement = null;
        try {
            connection = connectionPool.getExistingConnectionFromPool();
            preparedStatement = connection.prepareStatement(DBConstants.GET_AD_BY_ID);
            preparedStatement.setInt(AD_ID_COLUMN_INDEX, adId);
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
    public Integer getAdvertisementCountById(Integer customerId) throws DAOException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        Integer advertisementCount = null;
        try {
            connection = connectionPool.getExistingConnectionFromPool();
            preparedStatement = connection.prepareStatement(DBConstants.GET_AD_COUNT_BY_ID);
            preparedStatement.setInt(1, customerId);
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

    public Location getLocationNamesById(Integer locationId, String languageCode) throws DAOException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        Location location = new Location(locationId);
        try {
            connection = connectionPool.getExistingConnectionFromPool();
            preparedStatement = connection.prepareStatement(DBConstants.GET_LOCATION_BY_ID);
            preparedStatement.setInt(1, locationId);
            preparedStatement.setString(2, languageCode);
            resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                location.setName(resultSet.getString(1));
                location.setParentId(resultSet.getInt(2));
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
    public List<Comment> getCommentsAByAdvertisementId(Integer adId) throws DAOException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        List<Comment> comments = new ArrayList<>();
        try {
            connection = connectionPool.getExistingConnectionFromPool();
            preparedStatement = connection.prepareStatement(DBConstants.GET_COMMENTS_BY_AD_ID);
            preparedStatement.setInt(1, adId);
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
            preparedStatement.setInt(2, comment.getAdId());
            preparedStatement.setInt(3, comment.getAuthorId());
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

    private Comment buildComment(ResultSet resultSet) throws SQLException {
        Comment comment = new Comment();
        comment.setAuthorId(resultSet.getInt("c.user_id"));
        comment.setAuthorFirstName(resultSet.getString("first_name"));
        comment.setAuthorLastName(resultSet.getString("last_name"));
        comment.setAuthorLogin(resultSet.getString("login"));
        comment.setPostedDate(resultSet.getDate("created_date"));
        comment.setContent(resultSet.getString("description"));
        return comment;
    }

    private Advertisement buildAdvertisement(ResultSet resultSet) throws SQLException {
        Advertisement advertisement = new Advertisement();
        advertisement.setAdId(resultSet.getInt(DBConstants.AD_ID));
        advertisement.setTitle(resultSet.getString(DBConstants.AD_TITLE));
        advertisement.setDescription(resultSet.getString(DBConstants.AD_DESCRIPTION));
        advertisement.setLocation(new Location(resultSet.getInt(DBConstants.AD_CITY_ID)));
        advertisement.setPostedDate(resultSet.getDate(DBConstants.AD_POSTED_DATE));
        advertisement.setCategory(resultSet.getInt(DBConstants.AD_CATEGORY_ID));
        advertisement.setPrice(resultSet.getInt(DBConstants.AD_PRICE));
        return advertisement;
    }

}
