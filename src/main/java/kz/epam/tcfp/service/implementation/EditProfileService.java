package kz.epam.tcfp.service.implementation;

import kz.epam.tcfp.service.PagePath;
import kz.epam.tcfp.service.Service;
import kz.epam.tcfp.dao.UserDAO;
import kz.epam.tcfp.dao.exception.DAOException;
import kz.epam.tcfp.dao.factory.DAOFactory;
import kz.epam.tcfp.model.PhoneNumber;
import kz.epam.tcfp.model.User;
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
public class EditProfileService extends PreviousPage implements Service {
    private static final Logger LOGGER = Logger.getLogger(EditProfileService.class);
    private static final String PROFILE = "/profile";
    private final UserDAO userDAO = DAOFactory.getUserDAO();
    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        savePreviousPage(request);
        HttpSession session = request.getSession();
        Long userId = NumberUtil.tryCastToLong(session.getAttribute(ServiceConstants.SESSION_USER_ID));
        if (userId == null) {
            response.sendRedirect(PagePath.ERROR_JSP);
            return;
        }
        User user = null;
        try {
            user = userDAO.getUserById(userId);
            if (user == null) {
                request.setAttribute(ServiceConstants.INCORRECT_AUTHORIZATION, true);
                request.getRequestDispatcher(PagePath.SIGN_IN).forward(request, response);
                return;
            }
            List<PhoneNumber> phoneNumbers = userDAO.getPhoneNumberByUserId(userId);
            user.setPhoneNumbers(phoneNumbers);
        } catch (DAOException e) {
            LOGGER.warn("Error in DAO while editing user account info", e);
        }
        request.setAttribute(ServiceConstants.INCORRECT_AUTHORIZATION, false);
        request.setAttribute(ServiceConstants.USER, user);
        request.getRequestDispatcher(PROFILE).forward(request, response);
    }
}
