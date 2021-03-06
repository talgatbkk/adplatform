package kz.epam.tcfp.service.implementation;

import kz.epam.tcfp.service.PagePath;
import kz.epam.tcfp.service.Service;
import kz.epam.tcfp.dao.UserDAO;
import kz.epam.tcfp.dao.exception.DAOException;
import kz.epam.tcfp.dao.factory.DAOFactory;
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
public class DeleteUserAccountService extends PreviousPage implements Service {
    private static final Logger LOGGER = Logger.getLogger(DeleteUserAccountService.class);
    private final UserDAO userDAO = DAOFactory.getUserDAO();

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        savePreviousPage(request);
        HttpSession session = request.getSession(true);
        Long userId = NumberUtil.tryCastToLong(session.getAttribute(ServiceConstants.SESSION_USER_ID));
        Long userIdToDeleted = NumberUtil.tryParseLong(request.getParameter(ServiceConstants.USER_ID_TO_BE_DELETED));
        if (userIdToDeleted == null || userId == null){
            response.sendRedirect(PagePath.ERROR_JSP);
            return;
        }
        if (userIdToDeleted.equals(userId)) {
            try {
                if (Boolean.TRUE.equals(userDAO.deleteUserAccount(userId))) {
                    session.removeAttribute(ServiceConstants.SESSION_USER_ID);
                    response.sendRedirect(HOME_SERVICE);
                    return;
                } else {
                    LOGGER.warn("Failed to delete user account");
                }
            } catch (DAOException e) {
                LOGGER.warn("Error in DAO while deleting user account", e);
            }
        }
        response.sendRedirect(PagePath.ERROR_JSP);
    }
}

