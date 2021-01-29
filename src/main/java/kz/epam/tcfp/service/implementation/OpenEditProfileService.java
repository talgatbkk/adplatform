package kz.epam.tcfp.service.implementation;

import kz.epam.tcfp.dao.AdvertisementDAO;
import kz.epam.tcfp.dao.UserDAO;
import kz.epam.tcfp.dao.exception.DAOException;
import kz.epam.tcfp.dao.factory.DAOFactory;
import kz.epam.tcfp.model.PhoneNumber;
import kz.epam.tcfp.model.User;
import kz.epam.tcfp.service.PagePath;
import kz.epam.tcfp.service.Service;
import kz.epam.tcfp.service.util.NumberUtil;
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
public class OpenEditProfileService extends PreviousPage implements Service {

    private static final Logger LOGGER = Logger.getLogger(OpenEditProfileService.class);
    private static final String SIGN_IN_SERVICE = "/signin";
    private static final String USER_PROFILE_EDIT_JSP = "/user/profile/edit";
    private final UserDAO userDAO = DAOFactory.getUserDAO();
    private final AdvertisementDAO advertisementDAO = DAOFactory.getAdvertisementDAO();

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        savePreviousPage(request);
        HttpSession session = request.getSession(true);
        Long userId = NumberUtil.tryCastToLong(session.getAttribute(ServiceConstants.SESSION_USER_ID));
        if (userId == null){
            response.sendRedirect(SIGN_IN_SERVICE);
            return;
        }
        User user = null;
        try {
            user = userDAO.getUserById(userId);
            List<PhoneNumber> phoneNumbers = userDAO.getPhoneNumberByUserId(userId);
            Integer advertisementCount = advertisementDAO.getAdvertisementCountById(userId);
            if (user == null) {
                response.sendRedirect(PagePath.ERROR_JSP);
                return;
            }
            user.setPhoneNumbers(phoneNumbers);
            user.setActiveAds(advertisementCount);

        } catch (DAOException e) {
            LOGGER.warn("Error in DAO while getting data for user profile", e);
        }
        request.setAttribute(ServiceConstants.INCORRECT_AUTHORIZATION, false);
        request.setAttribute(ServiceConstants.USER, user);
        request.getRequestDispatcher(USER_PROFILE_EDIT_JSP).forward(request, response);
    }
}
