package kz.epam.tcfp.service.implementation;

import kz.epam.tcfp.service.PagePath;
import kz.epam.tcfp.service.Service;
import kz.epam.tcfp.dao.UserDAO;
import kz.epam.tcfp.dao.exception.DAOException;
import kz.epam.tcfp.dao.factory.DAOFactory;
import kz.epam.tcfp.model.PhoneNumber;
import kz.epam.tcfp.model.User;
import kz.epam.tcfp.model.inputform.SignUpInput;
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
public class SignUpService extends PreviousPage implements Service {

    private static final Logger LOGGER = Logger.getLogger(SignUpService.class);
    private final UserDAO userDAO = DAOFactory.getUserDAO();

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        savePreviousPage(request);
        HttpSession session = request.getSession();
        if (session.getAttribute(ServiceConstants.LOCAL_LANGUAGE) == null) {
            session.setAttribute(ServiceConstants.LOCAL_LANGUAGE, ServiceConstants.RUSSIAN_LANGUAGE);
        }
        SignUpInput signUpInput = buildSignUpInput(request);
        User user = null;
        try {
            boolean isEmailTaken = userDAO.isEmailTaken(signUpInput);
            boolean isLoginTaken = userDAO.isLoginTaken(signUpInput);
            boolean isPhoneNumberTaken = userDAO.isPhoneNumberTaken(signUpInput);
            if (isEmailTaken || isLoginTaken || isPhoneNumberTaken){
                request.setAttribute(ServiceConstants.PHONE_NUMBER_TAKEN, isPhoneNumberTaken);
                request.setAttribute(ServiceConstants.LOGIN_TAKEN, isLoginTaken);
                request.setAttribute(ServiceConstants.EMAIL_TAKEN, isEmailTaken);
                request.getRequestDispatcher(PagePath.SIGN_UP_SERVICE).forward(request,response);
                return;
            } else {
                userDAO.registerUser(signUpInput);
                user = userDAO.getUserIdByLogin(signUpInput.getLogin());
            }
        } catch (DAOException e) {
            LOGGER.warn("Error in DAO while signing up a user", e);
        }
        if (user == null) {
            response.sendRedirect(PagePath.ERROR_JSP);
            return;
        }
        session = request.getSession(true);
        session.setAttribute(ServiceConstants.SESSION_USER_ID, user.getUserId());
        response.sendRedirect(HOME_SERVICE);
    }

    private SignUpInput buildSignUpInput (HttpServletRequest request) {
        SignUpInput signUpInput = new SignUpInput();
        signUpInput.setLogin(request.getParameter(ServiceConstants.NEW_USER_LOGIN));
        signUpInput.setPassword(Encryption.encrypt(request.getParameter(ServiceConstants.NEW_USER_PASSWORD)));
        signUpInput.setFirstName(request.getParameter(ServiceConstants.NEW_USER_FIRST_NAME));
        signUpInput.setLastName(request.getParameter(ServiceConstants.NEW_USER_LAST_NAME));
        signUpInput.setEmail(request.getParameter(ServiceConstants.NEW_USER_EMAIL));
        signUpInput.setPhoneInfo(new PhoneNumber(request.getParameter(ServiceConstants.NEW_USER_PHONE_NUMBER)));
        return signUpInput;
    }
}
