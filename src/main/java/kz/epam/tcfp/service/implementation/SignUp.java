package kz.epam.tcfp.service.implementation;

import kz.epam.tcfp.service.Service;
import kz.epam.tcfp.dao.UserDAO;
import kz.epam.tcfp.dao.exception.DAOException;
import kz.epam.tcfp.dao.factory.DAOFactory;
import kz.epam.tcfp.model.PhoneNumber;
import kz.epam.tcfp.model.User;
import kz.epam.tcfp.model.inputform.SignUpInput;
import org.apache.coyote.Request;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 * @author Talgat Bekkaliyev
 * @project AdPlatform
 */
public class SignUp implements Service {

    private static final String REDIRECT_TO_HOME_PAGE = "/home";
    private static final String SESSION_USER_ID = "userId";
    private static final String NEW_USER_LOGIN = "login";
    private static final String NEW_USER_PASSWORD = "password";
    private static final String NEW_USER_FIRST_NAME = "first_name";
    private static final String NEW_USER_LAST_NAME = "last_name";
    private static final String NEW_USER_EMAIL = "email";
    private static final String NEW_USER_PHONE_NUMBER = "phoneNumber";

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        session.setAttribute("local", "ru");
        SignUpInput signUpInput = buildSignUpInput(request);
        UserDAO userDAO = DAOFactory.getUserDAO();
        User user = null;
        try {
            boolean isEmailTaken =  userDAO.isEmailTaken(signUpInput);
            boolean isLoginTaken = userDAO.isLoginTaken(signUpInput);
            boolean isPhoneNumberTaken = userDAO.isPhoneNumberTaken(signUpInput);

            if (isEmailTaken || isLoginTaken || isPhoneNumberTaken){
                request.setAttribute("phone_number_taken", isPhoneNumberTaken);
                request.setAttribute("login_taken", isLoginTaken);
                request.setAttribute("email_taken", isEmailTaken);
                request.getRequestDispatcher("/signup").forward(request,response);
                return;
            } else {
                userDAO.registerUser(signUpInput);
                user = userDAO.getUserIdByLogin(signUpInput.getLogin());
            }
        } catch (DAOException e) {
            e.printStackTrace();
        }

        request.setAttribute("incorrect_registration", false);
        session = request.getSession(true);
        session.setAttribute(SESSION_USER_ID, user.getUserId());
        response.sendRedirect(REDIRECT_TO_HOME_PAGE);
    }

    private SignUpInput buildSignUpInput (HttpServletRequest request) {
        SignUpInput signUpInput = new SignUpInput();
        signUpInput.setLogin(request.getParameter(NEW_USER_LOGIN));
        signUpInput.setPassword(request.getParameter(NEW_USER_PASSWORD));
        signUpInput.setFirstName(request.getParameter(NEW_USER_FIRST_NAME));
        signUpInput.setLastName(request.getParameter(NEW_USER_LAST_NAME));
        signUpInput.setEmail(request.getParameter(NEW_USER_EMAIL));
        signUpInput.setPhoneInfo(new PhoneNumber(request.getParameter(NEW_USER_PHONE_NUMBER)));
        return signUpInput;
    }
}
