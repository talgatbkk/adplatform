package kz.epam.tcfp.dao.implemenation;

import kz.epam.tcfp.dao.CategoryDAO;
import kz.epam.tcfp.dao.connection.ClosingUtil;
import kz.epam.tcfp.dao.connection.ConnectionPool;
import kz.epam.tcfp.dao.connection.ConnectionPoolException;
import kz.epam.tcfp.dao.exception.DAOException;
import kz.epam.tcfp.dao.factory.DAOFactory;
import kz.epam.tcfp.dao.util.DBConstants;
import kz.epam.tcfp.model.Category;

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
public class CategoryDAOImpl implements CategoryDAO {
    ConnectionPool connectionPool = DAOFactory.getConnectionPool();

    @Override
    public boolean postCategory(Category category) throws DAOException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        Integer rows = null;
        try {
            connection = connectionPool.getExistingConnectionFromPool();
            preparedStatement = connection.prepareStatement(DBConstants.POST_CATEGORY);
            preparedStatement.setString(DBConstants.PARAMETER_INDEX_ONE, null);
            preparedStatement.setLong(DBConstants.PARAMETER_INDEX_TWO, category.getLanguageId());
            preparedStatement.setString(DBConstants.PARAMETER_INDEX_THREE, category.getCategoryName());
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

    @Override
    public List<Category> getCategories(Long languageId) throws DAOException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        List<Category> categories = new ArrayList<>();
        try {
            connection = connectionPool.getExistingConnectionFromPool();
            preparedStatement = connection.prepareStatement(DBConstants.GET_CATEGORIES);
            preparedStatement.setLong(DBConstants.PARAMETER_INDEX_ONE, languageId);
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
}
