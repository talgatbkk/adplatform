package kz.epam.tcfp.dao.implemenation;

import kz.epam.tcfp.dao.LocationDAO;
import kz.epam.tcfp.dao.connection.ClosingUtil;
import kz.epam.tcfp.dao.connection.ConnectionPool;
import kz.epam.tcfp.dao.connection.ConnectionPoolException;
import kz.epam.tcfp.dao.exception.DAOException;
import kz.epam.tcfp.dao.factory.DAOFactory;
import kz.epam.tcfp.dao.util.DBConstants;
import kz.epam.tcfp.model.Location;
import org.apache.log4j.Logger;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Talgat Bekkaliyev
 * @project AdPlatform
 */
public class LocationDAOImpl implements LocationDAO {

    private static final Logger LOGGER = Logger.getLogger(LocationDAOImpl.class);
    ConnectionPool connectionPool = DAOFactory.getConnectionPool();

    public LocationDAOImpl() {
    }

    @Override
    public Location getLocationNamesById(Long locationId, String languageCode) throws DAOException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        Location location = new Location(locationId);
        try {
            connection = connectionPool.getExistingConnectionFromPool();
            preparedStatement = connection.prepareStatement(DBConstants.GET_LOCATION_BY_ID);
            preparedStatement.setLong(DBConstants.PARAMETER_INDEX_ONE, locationId);
            preparedStatement.setString(DBConstants.PARAMETER_INDEX_TWO, languageCode);
            resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                location.setName(resultSet.getString(DBConstants.PARAMETER_INDEX_ONE));
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
    public List<Location> getLocations(Long languageId) throws DAOException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        List<Location> locations = new ArrayList<>();
        try {
            connection = connectionPool.getExistingConnectionFromPool();
            preparedStatement = connection.prepareStatement(DBConstants.GET_LOCATIONS);
            preparedStatement.setLong(DBConstants.PARAMETER_INDEX_ONE, languageId);
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
            preparedStatement.setString(DBConstants.PARAMETER_INDEX_ONE, null);
            preparedStatement.setLong(DBConstants.PARAMETER_INDEX_TWO, location.getParentId());
            preparedStatement.setLong(DBConstants.PARAMETER_INDEX_THREE, location.getLanguageId());
            preparedStatement.setString(DBConstants.PARAMETER_INDEX_FOUR, location.getName());
            rows = preparedStatement.executeUpdate();
            if (rows.equals(DBConstants.INTEGER_ONE)){
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

}
