package kz.epam.tcfp.dao.implemenation;

import kz.epam.tcfp.dao.ImageDAO;
import kz.epam.tcfp.dao.connection.ClosingUtil;
import kz.epam.tcfp.dao.connection.ConnectionPool;
import kz.epam.tcfp.dao.connection.ConnectionPoolException;
import kz.epam.tcfp.dao.exception.DAOException;
import kz.epam.tcfp.dao.factory.DAOFactory;
import kz.epam.tcfp.dao.util.DBConstants;
import kz.epam.tcfp.model.Image;
import org.apache.log4j.Logger;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * @author Talgat Bekkaliyev
 * @project AdPlatform
 */
public class ImageDAOImpl implements ImageDAO {
    private static final Logger LOGGER = Logger.getLogger(ImageDAOImpl.class);
    ConnectionPool connectionPool = DAOFactory.getConnectionPool();

    @Override
    public boolean postImage(Image image) throws DAOException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        Integer rows = null;
        try {
            connection = connectionPool.getExistingConnectionFromPool();
            preparedStatement = connection.prepareStatement(DBConstants.POST_IMAGE_PATH);
            preparedStatement.setString(1, null);
            preparedStatement.setLong(2, image.getAdvertisementId());
            preparedStatement.setString(3, image.getPath());
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
    public Image getImage(Long adId) throws DAOException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        Image image = null;
        try {
            connection = connectionPool.getExistingConnectionFromPool();
            preparedStatement = connection.prepareStatement(DBConstants.GET_IMAGE);
            preparedStatement.setLong(1, adId);
            resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                image = new Image();
                image.setImageId(resultSet.getLong(DBConstants.IMAGE_ID));
                image.setAdvertisementId(resultSet.getLong(DBConstants.AD_ID));
                image.setPath(resultSet.getString(DBConstants.IMAGE_PATH));
            }
        } catch (SQLException ex) {
            throw new DAOException(DBConstants.SQL_QUERY_ERROR, ex);
        } catch (ConnectionPoolException ex){
            throw new DAOException(ex);
        } finally {
            ClosingUtil.closeAll(preparedStatement, resultSet);
            connectionPool.putBackConnectionToPool(connection);
        }
        return image;
    }
}
