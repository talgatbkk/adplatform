package kz.epam.tcfp.service.implementation;

import kz.epam.tcfp.service.Service;
import kz.epam.tcfp.dao.UserDAO;
import kz.epam.tcfp.dao.exception.DAOException;
import kz.epam.tcfp.dao.factory.DAOFactory;
import kz.epam.tcfp.model.User;
import kz.epam.tcfp.model.inputform.SignInInput;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 * @author Talgat Bekkaliyev
 * @project AdPlatform
 */
public class SignIn implements Service {

    private static final String REDIRECT_TO_HOME_PAGE = "/home?page=view_profile";
    private static final String SESSION_USER_ID = "userId";
    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        session.setAttribute("local", "ru");
        SignInInput signInInput = new SignInInput();
        signInInput.setLogin(request.getParameter("login"));
        signInInput.setPassword(request.getParameter("password"));
        UserDAO userDAO = DAOFactory.getUserDAO();
        User user = null;
        try {
            if (!userDAO.authenticateUser(signInInput)){
                if (signInInput.getLogin() != null) {
                    request.setAttribute("incorrect_auth", true);
                }
                request.getRequestDispatcher("/signin").forward(request,response);
                return;
            } else {
                    user = userDAO.getUserIdByLogin(signInInput.getLogin());

            }
        } catch (DAOException e) {
            e.printStackTrace();
        }

        request.setAttribute("incorrect_auth", false);
        session.setAttribute("role_id", user.getRoleId());
        session.setAttribute("is_banned", user.isBanned());
        session.setAttribute(SESSION_USER_ID, user.getUserId());
        response.sendRedirect(REDIRECT_TO_HOME_PAGE);
    }
}
