package kz.epam.tcfp.service.implementation;

import kz.epam.tcfp.service.PagePath;
import kz.epam.tcfp.service.Service;
import kz.epam.tcfp.dao.AdvertisementDAO;
import kz.epam.tcfp.dao.UserDAO;
import kz.epam.tcfp.dao.exception.DAOException;
import kz.epam.tcfp.dao.factory.DAOFactory;
import kz.epam.tcfp.model.PhoneNumber;
import kz.epam.tcfp.model.User;
import kz.epam.tcfp.service.util.PreviousPage;
import kz.epam.tcfp.service.util.ServiceConstants;
import org.apache.log4j.Logger;

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
public class OpenProfile extends PreviousPage implements Service {
    private static final Logger LOGGER = Logger.getLogger(OpenProfile.class);
    private static final String SIGN_IN_SERVICE = "/signin";
    private static final String USER_PROFILE_SERVICE = "/user/profile";

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        savePreviousPage(request);
        HttpSession session = request.getSession(true);
        if (session.getAttribute(ServiceConstants.SESSION_USER_ID) == null){
            response.sendRedirect(SIGN_IN_SERVICE);
            return;
        }
        Integer userId = (Integer) session.getAttribute(ServiceConstants.SESSION_USER_ID);
        Integer profileId = null;
        if (request.getParameter(ServiceConstants.USER_PROFILE_ID) != null) {
            profileId = Integer.parseInt(request.getParameter(ServiceConstants.USER_PROFILE_ID));
        } else {
            profileId = userId;
        }
        UserDAO userDAO = DAOFactory.getUserDAO();
        AdvertisementDAO advertisementDAO = DAOFactory.getAdvertisementDAO();
        User user = null;
        try {
            user = userDAO.getUserById(profileId);
            List<PhoneNumber> phoneNumbers = userDAO.getPhoneNumberByUserId(profileId);
            Integer advertisementCount = advertisementDAO.getAdvertisementCountById(profileId);
            user.setPhoneNumbers(phoneNumbers);
            user.setActiveAds(advertisementCount);
            if (user == null) {
                response.sendRedirect(PagePath.ERROR_JSP);
                return;
            }
        } catch (DAOException e) {
            LOGGER.warn("Error in DAO while getting data for user profile", e);
        }
        request.setAttribute(ServiceConstants.INCORRECT_AUTHORIZATION, false);
        request.setAttribute(ServiceConstants.USER, user);
        session = request.getSession(true);
        request.getRequestDispatcher(USER_PROFILE_SERVICE).forward(request, response);

    }
}
