package kz.epam.tcfp.service.implementation;

import kz.epam.tcfp.dao.CommentDAO;
import kz.epam.tcfp.dao.exception.DAOException;
import kz.epam.tcfp.dao.factory.DAOFactory;
import kz.epam.tcfp.model.Comment;
import kz.epam.tcfp.service.PagePath;
import kz.epam.tcfp.service.Service;
import kz.epam.tcfp.service.util.NumberUtil;
import kz.epam.tcfp.service.util.PreviousPage;
import kz.epam.tcfp.service.util.ServiceConstants;
import org.apache.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 * @author Talgat Bekkaliyev
 * @project AdPlatform
 */
public class PostCommentService extends PreviousPage implements Service {
    private static final Logger LOGGER = Logger.getLogger(PostCommentService.class);
    private final CommentDAO commentDAO = DAOFactory.getCommentDAO();
    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        savePreviousPage(request);
        HttpSession session = request.getSession(true);
        Long userId = NumberUtil.tryCastToLong(session.getAttribute(ServiceConstants.SESSION_USER_ID));
        if (userId == null) {
            request.getRequestDispatcher(PagePath.SIGN_IN_SERVICE).forward(request, response);
            return;
        }
        Long adId = NumberUtil.tryParseLong(request.getParameter(ServiceConstants.ADVERTISEMENT_ID));
        if (adId == null) {
            response.sendRedirect(PagePath.ERROR_JSP);
            return;
        }
        Comment comment = new Comment();
        comment.setAuthorId(userId);
        comment.setAdId(adId);
        comment.setContent(request.getParameter(ServiceConstants.COMMENT_CONTENT));
        try {
            commentDAO.postComment(comment);
        } catch (DAOException e) {
            LOGGER.warn("Error in DAO while posting a comment", e);
        }
        response.sendRedirect(PagePath.ADVERTISEMENT_VIEW_SERVICE + QUESTION_MARK
                + ServiceConstants.ADVERTISEMENT_ID + EQUAL_SIGN + adId);

    }
}
