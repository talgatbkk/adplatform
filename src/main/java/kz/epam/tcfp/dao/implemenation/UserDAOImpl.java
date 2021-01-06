package kz.epam.tcfp.dao.implemenation;

import kz.epam.tcfp.dao.UserDAO;
import kz.epam.tcfp.dao.connection.ClosingUtil;
import kz.epam.tcfp.dao.connection.ConnectionPool;
import kz.epam.tcfp.dao.util.DBConstants;
import kz.epam.tcfp.dao.connection.ConnectionPoolException;
import kz.epam.tcfp.dao.exception.DAOException;
import kz.epam.tcfp.dao.factory.DAOFactory;
import kz.epam.tcfp.model.PhoneNumber;
import kz.epam.tcfp.model.User;
import kz.epam.tcfp.model.inputform.SignInInput;
import kz.epam.tcfp.model.inputform.SignUpInput;
import org.apache.log4j.Logger;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Talgat Bekkaliyev
 * @project AdPlatform
 */
public class UserDAOImpl implements UserDAO {

    private static final Logger LOGGER = Logger.getLogger(UserDAOImpl.class);
    private static final Integer CUSTOMER_ROLE_ID = 2;
    private static final Integer IS_USER_BANNED = 0;
    private static final String CALLABLE_RESULT_NAME = "result";
    ConnectionPool connectionPool = DAOFactory.getConnectionPool();

    @Override
    public Boolean authenticateUser(SignInInput signInInput) throws DAOException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try {
            connection = connectionPool.getExistingConnectionFromPool();
            preparedStatement = connection.prepareStatement(DBConstants.AUTHENTICATE_USER);
            preparedStatement.setString(1, signInInput.getLogin());
            preparedStatement.setString(2, signInInput.getPassword());
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
    public Boolean isUserBanned(Integer userId) throws DAOException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try {
            connection = connectionPool.getExistingConnectionFromPool();
            preparedStatement = connection.prepareStatement(DBConstants.GET_USER_BY_ID);
            preparedStatement.setInt(1, userId);
            resultSet = preparedStatement.executeQuery();
            if (resultSet.next() && !resultSet.getBoolean(DBConstants.USER_IS_BANNED)) {
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
    public Boolean registerUser(SignUpInput signUpInput) throws DAOException {
        Connection connection = null;
        CallableStatement callableStatement = null;
        Integer result = null;
        try {
            connection = connectionPool.getExistingConnectionFromPool();
            callableStatement = connection.prepareCall(DBConstants.INSERT_NEW_USER);
            callableStatement.setInt(DBConstants.USER_ROLE_ID, CUSTOMER_ROLE_ID);
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
    public User getUserById(Integer userId) throws DAOException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        User user = null;
        try {
            connection = connectionPool.getExistingConnectionFromPool();
            preparedStatement = connection.prepareStatement(DBConstants.GET_USER_BY_ID);
            preparedStatement.setInt(1, userId);
            resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                user = buildUser(resultSet);
            }
        } catch (SQLException ex) {
            throw new DAOException(DBConstants.SQL_QUERY_ERROR, ex);
        } catch (ConnectionPoolException ex){
            throw new DAOException(ex);
        } finally {
            ClosingUtil.closeAll(preparedStatement, resultSet);
            connectionPool.putBackConnectionToPool(connection);
        }
        return user;
    }

    private User buildUser(ResultSet resultSet) throws SQLException {
        User user = new User();
        user.setUserId(resultSet.getInt(DBConstants.USER_ID));
        user.setLogin(resultSet.getString(DBConstants.USER_LOGIN));
        user.setFirstName(resultSet.getString(DBConstants.USER_FIRST_NAME));
        user.setLastName(resultSet.getString(DBConstants.USER_LAST_NAME));
        user.setCreatedTime(resultSet.getDate(DBConstants.USER_REGISTRATION_DATE));
        user.setEmail(resultSet.getString(DBConstants.USER_EMAIL));
        user.setBanned(resultSet.getBoolean(DBConstants.USER_IS_BANNED));
        user.setRoleId(resultSet.getInt(DBConstants.USER_ROLE_ID));
        return user;
    }

    @Override
    public List<PhoneNumber> getPhoneNumberByUserId(Integer userId) throws DAOException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        List<PhoneNumber> phoneNumbers = new ArrayList<>();
        try {
            connection = connectionPool.getExistingConnectionFromPool();
            preparedStatement = connection.prepareStatement(DBConstants.GET_PHONE_NUMBER_BY_USER_ID);
            preparedStatement.setInt(1, userId);
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                String userPhoneNumber = resultSet.getString(DBConstants.USER_PHONE_NUMBER);
                Integer userPhoneNumberId = resultSet.getInt(DBConstants.USER_PHONE_NUMBER_ID);
                phoneNumbers.add(new PhoneNumber(userPhoneNumberId, userPhoneNumber));
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
    public User getUserIdByLogin(String login) throws DAOException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        User user = null;
        try {
            connection = connectionPool.getExistingConnectionFromPool();
            preparedStatement = connection.prepareStatement(DBConstants.GET_USER_ID_BY_LOGIN);
            preparedStatement.setString(1, login);
            resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                user = buildUser(resultSet);
            }
        } catch (SQLException ex) {
            throw new DAOException(DBConstants.SQL_QUERY_ERROR, ex);
        } catch (ConnectionPoolException ex){
            throw new DAOException(ex);
        } finally {
            ClosingUtil.closeAll(preparedStatement, resultSet);
            connectionPool.putBackConnectionToPool(connection);
        }
        return user;
    }

    @Override
    public Boolean deleteUserAccount(Integer userId) throws DAOException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        Integer affectedRows = null;
        try {
            connection = connectionPool.getExistingConnectionFromPool();
            preparedStatement = connection.prepareStatement(DBConstants.DELETE_USER_ACCOUNT);
            preparedStatement.setInt(1, userId);
            affectedRows = preparedStatement.executeUpdate();
            if (affectedRows == 1) {
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
    public Boolean banUserAccount(Integer userId) throws DAOException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        Integer affectedRows = null;
        try {
            connection = connectionPool.getExistingConnectionFromPool();
            preparedStatement = connection.prepareStatement(DBConstants.BAN_USER_ACCOUNT);
            preparedStatement.setInt(1, userId);
            affectedRows = preparedStatement.executeUpdate();
            if (affectedRows == 1) {
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

}
