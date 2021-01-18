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

    private static final Integer CUSTOMER_ROLE_ID = 2;
    private static final Integer IS_USER_BANNED = 0;
    private static final String CALLABLE_RESULT_NAME = "result";
    ConnectionPool connectionPool = DAOFactory.getConnectionPool();

    public UserDAOImpl() {
    }

    @Override
    public Boolean authenticateUser(SignInInput signInInput) throws DAOException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try {
            connection = connectionPool.getExistingConnectionFromPool();
            preparedStatement = connection.prepareStatement(DBConstants.AUTHENTICATE_USER);
            preparedStatement.setString(DBConstants.PARAMETER_INDEX_ONE, signInInput.getLogin());
            preparedStatement.setString(DBConstants.PARAMETER_INDEX_TWO, signInInput.getPassword());
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
    public Boolean authenticateUserById(SignUpInput input) throws DAOException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try {
            connection = connectionPool.getExistingConnectionFromPool();
            preparedStatement = connection.prepareStatement(DBConstants.AUTHENTICATE_USER_BY_ID);
            preparedStatement.setLong(DBConstants.PARAMETER_INDEX_ONE, input.getUserId());
            preparedStatement.setString(DBConstants.PARAMETER_INDEX_TWO, input.getPassword());
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
    public Boolean isUserBanned(Long userId) throws DAOException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try {
            connection = connectionPool.getExistingConnectionFromPool();
            preparedStatement = connection.prepareStatement(DBConstants.GET_USER_BY_ID);
            preparedStatement.setLong(DBConstants.PARAMETER_INDEX_ONE, userId);
            resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                return resultSet.getBoolean(DBConstants.USER_IS_BANNED);
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
            callableStatement.setInt(DBConstants.PARAMETER_INDEX_ONE, CUSTOMER_ROLE_ID);
            callableStatement.setString(DBConstants.PARAMETER_INDEX_TWO, signUpInput.getLogin());
            callableStatement.setString(DBConstants.PARAMETER_INDEX_THREE, signUpInput.getPassword());
            callableStatement.setString(DBConstants.PARAMETER_INDEX_FOUR, signUpInput.getFirstName());
            callableStatement.setString(DBConstants.PARAMETER_INDEX_FIVE, signUpInput.getLastName());
            callableStatement.setString(DBConstants.PARAMETER_INDEX_SIX, signUpInput.getEmail());
            callableStatement.setTimestamp(DBConstants.PARAMETER_INDEX_SEVEN, signUpInput.getCreatedTime());
            callableStatement.setInt(DBConstants.PARAMETER_INDEX_EIGHT, IS_USER_BANNED);
            callableStatement.setString(DBConstants.PARAMETER_INDEX_NINE, signUpInput.getPhoneInfo().getPhoneNumber());
            callableStatement.registerOutParameter(DBConstants.PARAMETER_INDEX_TEN, Types.INTEGER);

            callableStatement.execute();
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
            preparedStatement.setString(DBConstants.PARAMETER_INDEX_ONE, input.getLogin());
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
            preparedStatement.setString(DBConstants.PARAMETER_INDEX_ONE, input.getEmail());
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
            preparedStatement.setString(DBConstants.PARAMETER_INDEX_ONE, input.getPhoneInfo().getPhoneNumber());
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
    public User getUserById(Long userId) throws DAOException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        User user = null;
        try {
            connection = connectionPool.getExistingConnectionFromPool();
            preparedStatement = connection.prepareStatement(DBConstants.GET_USER_BY_ID);
            preparedStatement.setLong(DBConstants.PARAMETER_INDEX_ONE, userId);
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
        user.setUserId(resultSet.getLong(DBConstants.USER_ID));
        user.setLogin(resultSet.getString(DBConstants.USER_LOGIN));
        user.setFirstName(resultSet.getString(DBConstants.USER_FIRST_NAME));
        user.setLastName(resultSet.getString(DBConstants.USER_LAST_NAME));
        user.setCreatedTime(resultSet.getDate(DBConstants.USER_REGISTRATION_DATE));
        user.setEmail(resultSet.getString(DBConstants.USER_EMAIL));
        user.setBanned(resultSet.getBoolean(DBConstants.USER_IS_BANNED));
        user.setRoleId(resultSet.getLong(DBConstants.USER_ROLE_ID));
        return user;
    }

    @Override
    public List<PhoneNumber> getPhoneNumberByUserId(Long userId) throws DAOException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        List<PhoneNumber> phoneNumbers = new ArrayList<>();
        try {
            connection = connectionPool.getExistingConnectionFromPool();
            preparedStatement = connection.prepareStatement(DBConstants.GET_PHONE_NUMBER_BY_USER_ID);
            preparedStatement.setLong(DBConstants.PARAMETER_INDEX_ONE, userId);
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                String userPhoneNumber = resultSet.getString(DBConstants.USER_PHONE_NUMBER);
                Long userPhoneNumberId = resultSet.getLong(DBConstants.USER_PHONE_NUMBER_ID);
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
            preparedStatement.setString(DBConstants.PARAMETER_INDEX_ONE, login);
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
    public Boolean deleteUserAccount(Long userId) throws DAOException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        Integer affectedRows = null;
        try {
            connection = connectionPool.getExistingConnectionFromPool();
            preparedStatement = connection.prepareStatement(DBConstants.DELETE_USER_ACCOUNT);
            preparedStatement.setLong(DBConstants.PARAMETER_INDEX_ONE, userId);
            affectedRows = preparedStatement.executeUpdate();
            if (affectedRows.equals(DBConstants.INTEGER_ONE)) {
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
    public Boolean banUserAccount(Long userId) throws DAOException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        Integer affectedRows = null;
        try {
            connection = connectionPool.getExistingConnectionFromPool();
            preparedStatement = connection.prepareStatement(DBConstants.BAN_USER_ACCOUNT);
            preparedStatement.setLong(DBConstants.PARAMETER_INDEX_ONE, userId);
            affectedRows = preparedStatement.executeUpdate();
            if (affectedRows.equals(DBConstants.INTEGER_ONE)) {
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
    public Boolean unbanUserAccount(Long userId) throws DAOException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        Integer affectedRows = null;
        try {
            connection = connectionPool.getExistingConnectionFromPool();
            preparedStatement = connection.prepareStatement(DBConstants.UNBAN_USER_ACCOUNT);
            preparedStatement.setLong(DBConstants.PARAMETER_INDEX_ONE, userId);
            affectedRows = preparedStatement.executeUpdate();
            if (affectedRows.equals(DBConstants.INTEGER_ONE)) {
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
    public Boolean updateUserFirstName(SignUpInput editedUser) throws DAOException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        Integer affectedRows = null;
        try {
            connection = connectionPool.getExistingConnectionFromPool();
            preparedStatement = connection.prepareStatement(DBConstants.UPDATE_USER_FIRST_NAME);
            preparedStatement.setString(DBConstants.PARAMETER_INDEX_ONE, editedUser.getFirstName());
            preparedStatement.setLong(DBConstants.PARAMETER_INDEX_TWO, editedUser.getUserId());
            affectedRows = preparedStatement.executeUpdate();
            if (affectedRows.equals(DBConstants.INTEGER_ONE)) {
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
    public Boolean updateUserLastName(SignUpInput editedUser) throws DAOException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        Integer affectedRows = null;
        try {
            connection = connectionPool.getExistingConnectionFromPool();
            preparedStatement = connection.prepareStatement(DBConstants.UPDATE_USER_LAST_NAME);
            preparedStatement.setString(DBConstants.PARAMETER_INDEX_ONE, editedUser.getLastName());
            preparedStatement.setLong(DBConstants.PARAMETER_INDEX_TWO, editedUser.getUserId());
            affectedRows = preparedStatement.executeUpdate();
            if (affectedRows.equals(DBConstants.INTEGER_ONE)) {
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
    public Boolean updateUserEmail(SignUpInput editedUser) throws DAOException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        Integer affectedRows = null;
        try {
            connection = connectionPool.getExistingConnectionFromPool();
            preparedStatement = connection.prepareStatement(DBConstants.UPDATE_USER_EMAIL);
            preparedStatement.setString(DBConstants.PARAMETER_INDEX_ONE, editedUser.getEmail());
            preparedStatement.setLong(DBConstants.PARAMETER_INDEX_TWO, editedUser.getUserId());
            affectedRows = preparedStatement.executeUpdate();
            if (affectedRows.equals(DBConstants.INTEGER_ONE)) {
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
    public Boolean updateUserPassword(SignUpInput userWithNewPassword) throws DAOException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        Integer affectedRows = null;
        try {
            connection = connectionPool.getExistingConnectionFromPool();
            preparedStatement = connection.prepareStatement(DBConstants.UPDATE_USER_PASSWORD);
            preparedStatement.setString(DBConstants.PARAMETER_INDEX_ONE, userWithNewPassword.getPassword());
            preparedStatement.setLong(DBConstants.PARAMETER_INDEX_TWO, userWithNewPassword.getUserId());
            affectedRows = preparedStatement.executeUpdate();
            if (affectedRows.equals(DBConstants.INTEGER_ONE)) {
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
