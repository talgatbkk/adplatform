package kz.epam.tcfp.service.implementation;

import kz.epam.tcfp.dao.CommentDAO;
import kz.epam.tcfp.service.Service;
import kz.epam.tcfp.dao.AdvertisementDAO;
import kz.epam.tcfp.dao.exception.DAOException;
import kz.epam.tcfp.dao.factory.DAOFactory;
import kz.epam.tcfp.model.Comment;
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
    private static final String ADVERTISEMENT_VIEW_SERVICE = "/advertisement/view";
    private CommentDAO commentDAO = DAOFactory.getCommentDAO();
    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        savePreviousPage(request);
        HttpSession session = request.getSession(true);
        Long userId = (Long) session.getAttribute(ServiceConstants.SESSION_USER_ID);
        Long adId = Long.parseLong(request.getParameter(ServiceConstants.ADVERTISEMENT_ID));


        Comment comment = new Comment();
        comment.setAuthorId(userId);
        comment.setAdId(adId);
        comment.setContent(request.getParameter(ServiceConstants.COMMENT_CONTENT));
        session.setAttribute(ServiceConstants.ADVERTISEMENT_ID, adId);
        try {
            commentDAO.postComment(comment);
        } catch (DAOException e) {
            LOGGER.warn("Error in DAO while posting a comment", e);
        }
        response.sendRedirect(ADVERTISEMENT_VIEW_SERVICE);

    }
}
