package kz.epam.tcfp.service.implementation;

import kz.epam.tcfp.service.Service;
import kz.epam.tcfp.dao.UserDAO;
import kz.epam.tcfp.dao.exception.DAOException;
import kz.epam.tcfp.dao.factory.DAOFactory;
import kz.epam.tcfp.model.User;
import kz.epam.tcfp.model.inputform.SignInInput;
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
public class SignInService extends PreviousPage implements Service {
    private static final Logger logger = Logger.getLogger(SignInService.class);
    private static final String SIGN_IN_SERVICE = "/signin";

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        savePreviousPage(request);
        HttpSession session = request.getSession();
        if (session.getAttribute(ServiceConstants.LOCAL_LANGUAGE) == null) {
            session.setAttribute(ServiceConstants.LOCAL_LANGUAGE, ServiceConstants.RUSSIAN_LANGUAGE);
        }
        SignInInput signInInput = new SignInInput();
        signInInput.setLogin(request.getParameter(ServiceConstants.USER_LOGIN));
        signInInput.setPassword(Encryption.encrypt(request.getParameter(ServiceConstants.USER_PASSWORD)));
        UserDAO userDAO = DAOFactory.getUserDAO();
        User user = null;
        try {
            if (!userDAO.authenticateUser(signInInput)){
                if (signInInput.getLogin() != null) {
                    request.setAttribute(ServiceConstants.INCORRECT_AUTHORIZATION, true);
                }
                request.getRequestDispatcher(SIGN_IN_SERVICE).forward(request,response);
                return;
            } else {
                    user = userDAO.getUserIdByLogin(signInInput.getLogin());
            }
        } catch (DAOException e) {
            logger.warn("Error in DAO while authenticating a user", e);
        }

        request.setAttribute(ServiceConstants.INCORRECT_AUTHORIZATION, false);
        session.setAttribute(ServiceConstants.USER_ROLE_ID, user.getRoleId());
        session.setAttribute(ServiceConstants.SESSION_USER_ID, user.getUserId());
        request.getRequestDispatcher(HOME_SERVICE).forward(request,response);
    }
}
