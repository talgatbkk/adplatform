package kz.epam.tcfp.dao.implemenation;

import kz.epam.tcfp.dao.CustomerDAO;
import kz.epam.tcfp.dao.connection.ClosingUtil;
import kz.epam.tcfp.dao.connection.ConnectionPool;
import kz.epam.tcfp.dao.connection.DBConstants;
import kz.epam.tcfp.dao.exception.ConnectionPoolException;
import kz.epam.tcfp.dao.exception.DAOException;
import kz.epam.tcfp.dao.factory.DAOFactory;
import kz.epam.tcfp.model.Customer;
import kz.epam.tcfp.model.PhoneNumber;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Talgat Bekkaliyev
 * @project AdPlatform
 */
public class CustomerDAOImpl implements CustomerDAO {

    public final static Integer CUSTOMER_ID_COLUMN_INDEX = 1;
    ConnectionPool connectionPool = DAOFactory.getConnectionPool();

    @Override
    public Customer getCustomerById(Integer customerId) throws DAOException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        Customer customer = null;
        try {
            connection = connectionPool.getExistingConnectionFromPool();
            preparedStatement = connection.prepareStatement(DBConstants.GET_CUSTOMER_BY_ID);
            preparedStatement.setInt(CUSTOMER_ID_COLUMN_INDEX, customerId);
            resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                customer = buildCustomer(resultSet);
            }
        } catch (SQLException ex) {
            throw new DAOException(DBConstants.SQL_QUERY_ERROR, ex);
        } catch (ConnectionPoolException ex){
            throw new DAOException(ex);
        } finally {
            ClosingUtil.closeAll(connection, preparedStatement, resultSet);
            connectionPool.putBackConnectionToPool(connection);
        }
        return customer;
    }

    private Customer buildCustomer(ResultSet resultSet) throws SQLException {
        Customer customer = new Customer();
        customer.setUserId(resultSet.getInt(DBConstants.USER_ID));
        customer.setLogin(resultSet.getString(DBConstants.USER_LOGIN));
        customer.setFirstName(resultSet.getString(DBConstants.USER_FIRST_NAME));
        customer.setLastName(resultSet.getString(DBConstants.USER_LAST_NAME));
        customer.setCreatedTime(resultSet.getDate(DBConstants.USER_REGISTRATION_DATE));
        customer.setEmail(resultSet.getString(DBConstants.USER_EMAIL));
        customer.setBanned(resultSet.getBoolean(DBConstants.USER_IS_BANNED));
        return customer;
    }

    @Override
    public List<PhoneNumber> getPhoneNumberByCustomerId(Integer customerId) throws DAOException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        List<PhoneNumber> phoneNumbers = new ArrayList<>();
        try {
            connection = connectionPool.getExistingConnectionFromPool();
            preparedStatement = connection.prepareStatement(DBConstants.GET_PHONE_NUMBER_BY_CUSTOMER_ID);
            preparedStatement.setInt(CUSTOMER_ID_COLUMN_INDEX, customerId);
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                String customerPhoneNumber = resultSet.getString(DBConstants.USER_PHONE_NUMBER);
                Integer customerPhoneNumberId = resultSet.getInt(DBConstants.USER_PHONE_NUMBER_ID);
                phoneNumbers.add(new PhoneNumber(customerPhoneNumberId, customerPhoneNumber));
            }
        } catch (SQLException ex) {
            throw new DAOException(DBConstants.SQL_QUERY_ERROR, ex);
        } catch (ConnectionPoolException ex){
            throw new DAOException(ex);
        } finally {
            ClosingUtil.closeAll(connection, preparedStatement, resultSet);
            connectionPool.putBackConnectionToPool(connection);
        }
        return phoneNumbers;
    }

}
