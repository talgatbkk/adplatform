package kz.epam.tcfp.service.implementation;

import kz.epam.tcfp.service.PagePath;
import kz.epam.tcfp.service.Service;
import kz.epam.tcfp.dao.UserDAO;
import kz.epam.tcfp.dao.exception.DAOException;
import kz.epam.tcfp.dao.factory.DAOFactory;
import kz.epam.tcfp.model.PhoneNumber;
import kz.epam.tcfp.model.User;
import kz.epam.tcfp.service.util.PreviousPage;
import kz.epam.tcfp.service.util.ServiceConstants;

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
public class EditProfile extends PreviousPage implements Service {


    public static final String PROFILE = "/profile";

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        savePreviousPage(request);
        HttpSession session = request.getSession();
        Integer userId = (Integer) session.getAttribute(ServiceConstants.SESSION_USER_ID);
        UserDAO userDAO = DAOFactory.getUserDAO();
        User user = null;
        try {
            user= userDAO.getUserById(userId);
            List<PhoneNumber> phoneNumbers = userDAO.getPhoneNumberByUserId(userId);
            user.setPhoneNumbers(phoneNumbers);
            if (user == null) {
                request.setAttribute(ServiceConstants.INCORRECT_AUTHORIZATION, true);
                request.getRequestDispatcher(PagePath.SIGN_IN).forward(request, response);
                return;
            }
        } catch (DAOException e) {
            e.printStackTrace();
        }

        request.setAttribute(ServiceConstants.INCORRECT_AUTHORIZATION, false);
        request.setAttribute(ServiceConstants.USER, user);
        session = request.getSession(true);
        request.getRequestDispatcher(PROFILE).forward(request, response);
    }
}
