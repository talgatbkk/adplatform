package kz.epam.tcfp.service.implementation;

import kz.epam.tcfp.service.Service;
import kz.epam.tcfp.dao.UserDAO;
import kz.epam.tcfp.dao.exception.DAOException;
import kz.epam.tcfp.dao.factory.DAOFactory;
import kz.epam.tcfp.model.PhoneNumber;
import kz.epam.tcfp.model.User;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;

/**
 * @author Talgat Bekkaliyev
 * @project AdPlatform
 */
public class EditProfile implements Service {
    private static final String SESSION_USER_ID = "userId";

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        Integer userId = (Integer) session.getAttribute(SESSION_USER_ID);
        UserDAO userDAO = DAOFactory.getUserDAO();
        User user = null;
        try {
            user= userDAO.getUserById(userId);
            List<PhoneNumber> phoneNumbers = userDAO.getPhoneNumberByUserId(userId);
            user.setPhoneNumbers(phoneNumbers);
            if (user == null) {
                request.setAttribute("incorrect_auth", true);
                request.getRequestDispatcher("/signin").forward(request, response);
                return;
            }
        } catch (DAOException e) {
            e.printStackTrace();
        }

        request.setAttribute("incorrect_auth", false);
        request.setAttribute("customer", user);
        session = request.getSession(true);
        request.getRequestDispatcher("/profile").forward(request, response);
    }
}
