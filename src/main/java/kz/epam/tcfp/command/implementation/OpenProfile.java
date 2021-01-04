package kz.epam.tcfp.command.implementation;

import kz.epam.tcfp.command.Command;
import kz.epam.tcfp.dao.AdvertisementDAO;
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
public class OpenProfile implements Command {

    private static final String SESSION_USER_ID = "userId";
    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        HttpSession session = request.getSession(true);
        if (session.getAttribute(SESSION_USER_ID) == null){
            response.sendRedirect("/signin");
            return;
        }
        Integer userId = (Integer) session.getAttribute(SESSION_USER_ID);
        Integer profileId = null;
        if (request.getParameter("profile_id") != null) {
            profileId = Integer.parseInt(request.getParameter("profile_id"));
        } else {
            profileId = userId;
        }
        UserDAO userDAO = DAOFactory.getUserDAO();
        AdvertisementDAO advertisementDAO = DAOFactory.getAdvertisementDAO();
        User user = null;
        try {
            user= userDAO.getUserById(profileId);
            List<PhoneNumber> phoneNumbers = userDAO.getPhoneNumberByUserId(profileId);
            Integer advertisementCount = advertisementDAO.getAdvertisementCountById(profileId);
            user.setPhoneNumbers(phoneNumbers);
            user.setActiveAds(advertisementCount);
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
        request.getRequestDispatcher("/jsp/Profile.jsp").forward(request, response);

    }
}
