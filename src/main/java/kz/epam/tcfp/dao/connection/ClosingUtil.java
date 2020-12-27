package kz.epam.tcfp.dao.connection;

import kz.epam.tcfp.dao.exception.DAOException;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * @author Talgat Bekkaliyev
 * @project AdPlatform
 */
public class ClosingUtil {

    public static void closeAll(Statement statement, ResultSet resultSet) throws DAOException {
        try {
            if (resultSet != null) {
                resultSet.close();
            }
            if (statement != null) {
                statement.close();
            }

        } catch (SQLException ex) {
            throw new DAOException("SQL exception while closing");
        }
    }

    public static void closeAll(Statement statement) throws DAOException {
        try {
            if (statement != null) {
                statement.close();
            }
        } catch (SQLException ex) {
            throw new DAOException("SQL exception while closing");
        }
    }
}
