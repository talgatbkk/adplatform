package kz.epam.tcfp.dao.implemenation;

import kz.epam.tcfp.dao.AdvertisementDAO;
import kz.epam.tcfp.connection.ClosingUtil;
import kz.epam.tcfp.connection.ConnectionPool;
import kz.epam.tcfp.connection.DBConstants;
import kz.epam.tcfp.connection.ConnectionPoolException;
import kz.epam.tcfp.dao.exception.DAOException;
import kz.epam.tcfp.dao.factory.DAOFactory;
import kz.epam.tcfp.model.Advertisement;

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
            ClosingUtil.closeAll(connection, callableStatement, resultSet);
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
            ClosingUtil.closeAll(connection, preparedStatement, resultSet);
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
            ClosingUtil.closeAll(connection, preparedStatement, resultSet);
            connectionPool.putBackConnectionToPool(connection);
        }
        return advertisementCount;
    }

    private Advertisement buildAdvertisement(ResultSet resultSet) throws SQLException {
        Advertisement advertisement = new Advertisement();
        advertisement.setAdId(resultSet.getInt(DBConstants.AD_ID));
        advertisement.setTitle(resultSet.getString(DBConstants.AD_TITLE));
        advertisement.setDescription(resultSet.getString(DBConstants.AD_DESCRIPTION));
        advertisement.setLocationId(resultSet.getInt(DBConstants.AD_CITY_ID));
        advertisement.setPostedDate(resultSet.getDate(DBConstants.AD_POSTED_DATE));
        advertisement.setCategory(resultSet.getInt(DBConstants.AD_CATEGORY_ID));
        advertisement.setPrice(resultSet.getInt(DBConstants.AD_PRICE));
        return advertisement;
    }

}
