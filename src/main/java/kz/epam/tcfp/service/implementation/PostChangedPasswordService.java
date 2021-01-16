package kz.epam.tcfp.service.implementation;

import kz.epam.tcfp.dao.UserDAO;
import kz.epam.tcfp.dao.exception.DAOException;
import kz.epam.tcfp.dao.factory.DAOFactory;
import kz.epam.tcfp.model.User;
import kz.epam.tcfp.model.inputform.SignUpInput;
import kz.epam.tcfp.service.PagePath;
import kz.epam.tcfp.service.Service;
import kz.epam.tcfp.service.util.Encryption;
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
public class PostChangedPasswordService extends PreviousPage implements Service {
    private static final Logger LOGGER = Logger.getLogger(PostChangedPasswordService.class);
    private static final String SIGN_IN_SERVICE = "/signin";
    public static final String USER_VIEW_PROFILE_SERVICE = "/user/view";
    public static final String INPUT_NEW_PASSWORD_JSP = "/jsp/InputNewPassword.jsp";
    public static final String NEW_PASSWORD = "new_pass";
    public static final String OLD_PASSWORD = "old_pass";
    public static final String INCORRECT_OLD_PASSWORD = "wrong_pass";
    private UserDAO userDAO = DAOFactory.getUserDAO();


    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        savePreviousPage(request);
        HttpSession session = request.getSession();
        if (session.getAttribute(ServiceConstants.LOCAL_LANGUAGE) == null) {
            session.setAttribute(ServiceConstants.LOCAL_LANGUAGE, ServiceConstants.RUSSIAN_LANGUAGE);
        }
        Long userId = null;
        if (session.getAttribute(ServiceConstants.SESSION_USER_ID) != null) {
            userId = (Long) session.getAttribute(ServiceConstants.SESSION_USER_ID);
        } else {
            request.getRequestDispatcher(SIGN_IN_SERVICE).forward(request, response);
            return;
        }
        SignUpInput userWithNewPassword = new SignUpInput();
        userWithNewPassword.setUserId(userId);
        userWithNewPassword.setPassword(Encryption.encrypt(request.getParameter(OLD_PASSWORD)));
        User user = null;
        try {
            if (userDAO.authenticateUserById(userWithNewPassword)) {
                userWithNewPassword.setPassword(Encryption.encrypt(request.getParameter(NEW_PASSWORD)));
                if (!userDAO.updateUserPassword(userWithNewPassword)) {
                    response.sendRedirect(PagePath.ERROR_JSP);
                    return;
                }
            } else {
                request.setAttribute(INCORRECT_OLD_PASSWORD, true);
                request.getRequestDispatcher(INPUT_NEW_PASSWORD_JSP).forward(request, response);
                return;
            }
        } catch (DAOException e) {
            LOGGER.warn("Error in DAO while signing up a user", e);
        }
        response.sendRedirect(USER_VIEW_PROFILE_SERVICE + QUESTION_MARK
                + ServiceConstants.USER_PROFILE_ID + EQUAL_SIGN + userId);
    }

}
