package kz.epam.tcfp.service.implementation;

import kz.epam.tcfp.dao.UserDAO;
import kz.epam.tcfp.dao.exception.DAOException;
import kz.epam.tcfp.dao.factory.DAOFactory;
import kz.epam.tcfp.model.inputform.SignUpInput;
import kz.epam.tcfp.service.PagePath;
import kz.epam.tcfp.service.Service;
import kz.epam.tcfp.service.util.Encryption;
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
public class PostChangedPasswordService extends PreviousPage implements Service {
    private static final Logger LOGGER = Logger.getLogger(PostChangedPasswordService.class);
    public static final String NEW_PASSWORD = "new_pass";
    public static final String OLD_PASSWORD = "old_pass";
    public static final String INCORRECT_OLD_PASSWORD = "wrong_pass";
    private final UserDAO userDAO = DAOFactory.getUserDAO();


    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        savePreviousPage(request);
        HttpSession session = request.getSession();
        Long userId = NumberUtil.tryCastToLong(session.getAttribute(ServiceConstants.SESSION_USER_ID));
        if (userId == null) {
            request.getRequestDispatcher(PagePath.SIGN_IN_SERVICE).forward(request, response);
            return;
        }

        SignUpInput userWithNewPassword = new SignUpInput();
        userWithNewPassword.setUserId(userId);
        userWithNewPassword.setPassword(Encryption.encrypt(request.getParameter(OLD_PASSWORD)));
        try {
            if (Boolean.TRUE.equals(userDAO.authenticateUserById(userWithNewPassword))) {
                userWithNewPassword.setPassword(Encryption.encrypt(request.getParameter(NEW_PASSWORD)));
                if (Boolean.TRUE.equals(userDAO.updateUserPassword(userWithNewPassword))) {
                    response.sendRedirect(PagePath.USER_VIEW_PROFILE_SERVICE + QUESTION_MARK
                            + ServiceConstants.USER_PROFILE_ID + EQUAL_SIGN + userId);
                } else {
                    response.sendRedirect(PagePath.ERROR_JSP);
                }
                return;
            } else {
                request.setAttribute(INCORRECT_OLD_PASSWORD, true);
                request.getRequestDispatcher(PagePath.INPUT_NEW_PASSWORD_JSP).forward(request, response);
                return;
            }
        } catch (DAOException e) {
            LOGGER.warn("Error in DAO while signing up a user", e);
        }
    }

}
