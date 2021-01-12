package kz.epam.tcfp.dao;

import kz.epam.tcfp.dao.exception.DAOException;
import kz.epam.tcfp.model.Comment;

import java.util.List;

/**
 * @author Talgat Bekkaliyev
 * @project AdPlatform
 */
public interface CommentDAO {

    List<Comment> getCommentsAByAdvertisementId(Long adId) throws DAOException;
    boolean postComment(Comment comment) throws DAOException;
}
