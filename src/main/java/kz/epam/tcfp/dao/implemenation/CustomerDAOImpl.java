package kz.epam.tcfp.dao.implemenation;

import kz.epam.tcfp.dao.CustomerDAO;
import kz.epam.tcfp.dao.connection.ClosingUtil;
import kz.epam.tcfp.dao.connection.ConnectionPool;
import kz.epam.tcfp.dao.connection.DBConstants;
import kz.epam.tcfp.dao.connection.ConnectionPoolException;
import kz.epam.tcfp.dao.exception.DAOException;
import kz.epam.tcfp.dao.factory.DAOFactory;
import kz.epam.tcfp.model.Customer;
import kz.epam.tcfp.model.PhoneNumber;
import kz.epam.tcfp.model.inputform.SignInInput;
import kz.epam.tcfp.model.inputform.SignUpInput;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Talgat Bekkaliyev
 * @project AdPlatform
 */
public class CustomerDAOImpl implements CustomerDAO {

    public static final Integer COLUMN_INDEX_ONE = 1;
    public static final Integer COLUMN_INDEX_TWO = 2;
    public static final Integer ROLE_ID = 2;
    public static final Integer IS_USER_BANNED = 0;
    public static final String CALLABLE_RESULT_NAME = "result";
    ConnectionPool connectionPool = DAOFactory.getConnectionPool();

    @Override
    public Boolean authenticateCustomer(SignInInput signInInput) throws DAOException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try {
            connection = connectionPool.getExistingConnectionFromPool();
            preparedStatement = connection.prepareStatement(DBConstants.AUTHENTICATE_CUSTOMER);
            preparedStatement.setString(COLUMN_INDEX_ONE, signInInput.getLogin());
            preparedStatement.setString(COLUMN_INDEX_TWO, signInInput.getPassword());
            resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                return true;
            }
        } catch (SQLException ex) {
            throw new DAOException(DBConstants.SQL_QUERY_ERROR, ex);
        } catch (ConnectionPoolException ex){
            throw new DAOException(ex);
        } finally {
            ClosingUtil.closeAll(preparedStatement, resultSet);
            connectionPool.putBackConnectionToPool(connection);
        }
        return false;
    }

    @Override
    public Boolean registerCustomer(SignUpInput signUpInput) throws DAOException {
        Connection connection = null;
        CallableStatement callableStatement = null;
        Integer result = null;
        try {
            connection = connectionPool.getExistingConnectionFromPool();
            callableStatement = connection.prepareCall(DBConstants.INSERT_NEW_CUSTOMER);
            callableStatement.setInt(DBConstants.USER_ROLE_ID, ROLE_ID);
            callableStatement.setString(DBConstants.USER_LOGIN, signUpInput.getLogin());
            callableStatement.setString(DBConstants.USER_PASSWORD, signUpInput.getPassword());
            callableStatement.setString(DBConstants.USER_FIRST_NAME, signUpInput.getFirstName());
            callableStatement.setString(DBConstants.USER_LAST_NAME, signUpInput.getLastName());
            callableStatement.setString(DBConstants.USER_EMAIL, signUpInput.getEmail());
            callableStatement.setTimestamp(DBConstants.USER_REGISTRATION_DATE, signUpInput.getCreatedTime());
            callableStatement.setInt(DBConstants.USER_IS_BANNED, IS_USER_BANNED);
            callableStatement.setString(DBConstants.USER_PHONE_NUMBER, signUpInput.getPhoneInfo().getPhoneNumber());
            callableStatement.registerOutParameter(CALLABLE_RESULT_NAME, Types.INTEGER);

            callableStatement.executeUpdate();
            result = callableStatement.getInt(CALLABLE_RESULT_NAME);
            if (result == 0) {
                return true;
            }
        } catch (SQLException ex) {
            throw new DAOException(DBConstants.SQL_QUERY_ERROR, ex);
        } catch (ConnectionPoolException ex){
            throw new DAOException(ex);
        } finally {
            ClosingUtil.closeAll(callableStatement);
            connectionPool.putBackConnectionToPool(connection);
        }
        return false;
    }

    @Override
    public Boolean isLoginTaken(SignUpInput input) throws DAOException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try {
            connection = connectionPool.getExistingConnectionFromPool();
            preparedStatement = connection.prepareStatement(DBConstants.CHECK_IF_LOGIN_TAKEN);
            preparedStatement.setString(1, input.getLogin());
            resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                return true;
            }
        } catch (SQLException ex) {
            throw new DAOException(DBConstants.SQL_QUERY_ERROR, ex);
        } catch (ConnectionPoolException ex){
            throw new DAOException(ex);
        } finally {
            ClosingUtil.closeAll(preparedStatement, resultSet);
            connectionPool.putBackConnectionToPool(connection);
        }
        return false;

    }

    @Override
    public Boolean isEmailTaken(SignUpInput input) throws DAOException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try {
            connection = connectionPool.getExistingConnectionFromPool();
            preparedStatement = connection.prepareStatement(DBConstants.CHECK_IF_EMAIL_TAKEN);
            preparedStatement.setString(1, input.getEmail());
            resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                return true;
            }
        } catch (SQLException ex) {
            throw new DAOException(DBConstants.SQL_QUERY_ERROR, ex);
        } catch (ConnectionPoolException ex){
            throw new DAOException(ex);
        } finally {
            ClosingUtil.closeAll(preparedStatement, resultSet);
            connectionPool.putBackConnectionToPool(connection);
        }
        return false;
    }

    @Override
    public Boolean isPhoneNumberTaken(SignUpInput input) throws DAOException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try {
            connection = connectionPool.getExistingConnectionFromPool();
            preparedStatement = connection.prepareStatement(DBConstants.CHECK_IF_PHONE_NUMBER_TAKEN);
            preparedStatement.setString(1, input.getPhoneInfo().getPhoneNumber());
            resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                return true;
            }
        } catch (SQLException ex) {
            throw new DAOException(DBConstants.SQL_QUERY_ERROR, ex);
        } catch (ConnectionPoolException ex){
            throw new DAOException(ex);
        } finally {
            ClosingUtil.closeAll(preparedStatement, resultSet);
            connectionPool.putBackConnectionToPool(connection);
        }
        return false;
    }


    @Override
    public Customer getCustomerById(Integer customerId) throws DAOException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        Customer customer = null;
        try {
            connection = connectionPool.getExistingConnectionFromPool();
            preparedStatement = connection.prepareStatement(DBConstants.GET_CUSTOMER_BY_ID);
            preparedStatement.setInt(COLUMN_INDEX_ONE, customerId);
            resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                customer = buildCustomer(resultSet);
            }
        } catch (SQLException ex) {
            throw new DAOException(DBConstants.SQL_QUERY_ERROR, ex);
        } catch (ConnectionPoolException ex){
            throw new DAOException(ex);
        } finally {
            ClosingUtil.closeAll(preparedStatement, resultSet);
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
            preparedStatement.setInt(COLUMN_INDEX_ONE, customerId);
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
            ClosingUtil.closeAll(preparedStatement, resultSet);
            connectionPool.putBackConnectionToPool(connection);
        }
        return phoneNumbers;
    }

    @Override
    public Customer getCustomerIdByLogin(String login) throws DAOException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        Customer customer = null;
        try {
            connection = connectionPool.getExistingConnectionFromPool();
            preparedStatement = connection.prepareStatement(DBConstants.GET_CUSTOMER_ID_BY_LOGIN);
            preparedStatement.setString(1, login);
            resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                customer = buildCustomer(resultSet);
            }
        } catch (SQLException ex) {
            throw new DAOException(DBConstants.SQL_QUERY_ERROR, ex);
        } catch (ConnectionPoolException ex){
            throw new DAOException(ex);
        } finally {
            ClosingUtil.closeAll(preparedStatement, resultSet);
            connectionPool.putBackConnectionToPool(connection);
        }
        return customer;
    }


}
