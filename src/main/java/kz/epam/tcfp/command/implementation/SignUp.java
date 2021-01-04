package kz.epam.tcfp.command.implementation;

import kz.epam.tcfp.command.Command;
import kz.epam.tcfp.dao.UserDAO;
import kz.epam.tcfp.dao.exception.DAOException;
import kz.epam.tcfp.dao.factory.DAOFactory;
import kz.epam.tcfp.model.PhoneNumber;
import kz.epam.tcfp.model.User;
import kz.epam.tcfp.model.inputform.SignUpInput;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 * @author Talgat Bekkaliyev
 * @project AdPlatform
 */
public class SignUp implements Command {

    private static final String REDIRECT_TO_HOME_PAGE = "/home?forward_page=get_ads";
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
        SignUpInput signUpInput = new SignUpInput();
        signUpInput.setLogin(request.getParameter(NEW_USER_LOGIN));
        signUpInput.setPassword(request.getParameter(NEW_USER_PASSWORD));
        signUpInput.setFirstName(request.getParameter(NEW_USER_FIRST_NAME));
        signUpInput.setLastName(request.getParameter(NEW_USER_LAST_NAME));
        signUpInput.setEmail(request.getParameter(NEW_USER_EMAIL));
        signUpInput.setPhoneInfo(new PhoneNumber(request.getParameter(NEW_USER_PHONE_NUMBER)));
        UserDAO userDAO = DAOFactory.getUserDAO();
        User user = null;
        try {
            if (signUpInput.getFirstName() == null || !userDAO.registerUser(signUpInput)){
                request.setAttribute("incorrect_registration", true);
                request.getRequestDispatcher("/signup").forward(request,response);
                return;
            } else {
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
}
