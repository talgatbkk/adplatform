package kz.epam.tcfp.service.implementation;

import kz.epam.tcfp.dao.UserDAO;
import kz.epam.tcfp.dao.exception.DAOException;
import kz.epam.tcfp.dao.factory.DAOFactory;
import kz.epam.tcfp.service.PagePath;
import kz.epam.tcfp.service.Service;
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
public class UnbanUserAccountService extends PreviousPage implements Service {
    private static final Logger LOGGER = Logger.getLogger(BanUserAccountService.class);
    public static final String USER_VIEW_PROFILE_SERVICE = "/user/view";
    private static final Integer ADMIN_ROLE_ID = 1;

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        savePreviousPage(request);
        HttpSession session = request.getSession(true);
        Integer userId = (Integer) session.getAttribute(ServiceConstants.SESSION_USER_ID);
        Integer userRoleId = (Integer) session.getAttribute(ServiceConstants.USER_ROLE_ID);

        Integer userIdToBan = null;
        String userIdInputToBan = request.getParameter(ServiceConstants.USER_ID_TO_BE_UNBANNED);
        if (userIdInputToBan != null && !userIdInputToBan.isEmpty()) {
            userIdToBan = Integer.parseInt(userIdInputToBan);
        }
        UserDAO userDAO = DAOFactory.getUserDAO();

        if (userRoleId == ADMIN_ROLE_ID) {
            try {
                if (userDAO.unbanUserAccount(userIdToBan)) {
                    response.sendRedirect(USER_VIEW_PROFILE_SERVICE + QUESTION_MARK
                                            + ServiceConstants.USER_PROFILE_ID + EQUAL_SIGN + userIdToBan);
                    return;
                }
            } catch (DAOException e) {
                LOGGER.warn("Error in DAO while banning user", e);
            }

        } else {
            response.sendRedirect(PagePath.ERROR_JSP);

        }
    }
}

