package kz.epam.tcfp.dao.implemenation;

import kz.epam.tcfp.dao.CommentDAO;
import kz.epam.tcfp.dao.connection.ClosingUtil;
import kz.epam.tcfp.dao.connection.ConnectionPool;
import kz.epam.tcfp.dao.connection.ConnectionPoolException;
import kz.epam.tcfp.dao.exception.DAOException;
import kz.epam.tcfp.dao.factory.DAOFactory;
import kz.epam.tcfp.dao.util.DBConstants;
import kz.epam.tcfp.model.Comment;
import kz.epam.tcfp.model.DateTimeInUTC;
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
public class CommentDAOImpl implements CommentDAO {
    private static final Logger LOGGER = Logger.getLogger(CommentDAOImpl.class);
    ConnectionPool connectionPool = DAOFactory.getConnectionPool();

    public CommentDAOImpl() {
    }

    @Override
    public List<Comment> getCommentsAByAdvertisementId(Long adId) throws DAOException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        List<Comment> comments = new ArrayList<>();
        try {
            connection = connectionPool.getExistingConnectionFromPool();
            preparedStatement = connection.prepareStatement(DBConstants.GET_COMMENTS_BY_AD_ID);
            preparedStatement.setLong(1, adId);
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
            preparedStatement.setLong(2, comment.getAdId());
            preparedStatement.setLong(3, comment.getAuthorId());
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
        comment.setAuthorId(resultSet.getLong(DBConstants.COMMENT_USER_ID));
        comment.setAuthorFirstName(resultSet.getString(DBConstants.USER_FIRST_NAME));
        comment.setAuthorLastName(resultSet.getString(DBConstants.USER_LAST_NAME));
        comment.setAuthorLogin(resultSet.getString(DBConstants.USER_LOGIN));
        comment.setPostedDate(new DateTimeInUTC(resultSet.getTimestamp(DBConstants.COMMENT_POSTED_DATE)));
        comment.setContent(resultSet.getString(DBConstants.COMMENT_CONTENT));
        return comment;
    }
}