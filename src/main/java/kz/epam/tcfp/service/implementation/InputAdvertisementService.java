package kz.epam.tcfp.service.implementation;

import kz.epam.tcfp.dao.CategoryDAO;
import kz.epam.tcfp.dao.LocationDAO;
import kz.epam.tcfp.service.PagePath;
import kz.epam.tcfp.service.Service;
import kz.epam.tcfp.dao.AdvertisementDAO;
import kz.epam.tcfp.dao.UserDAO;
import kz.epam.tcfp.dao.exception.DAOException;
import kz.epam.tcfp.dao.factory.DAOFactory;
import kz.epam.tcfp.model.Category;
import kz.epam.tcfp.model.Location;
import kz.epam.tcfp.service.util.LanguageUtil;
import kz.epam.tcfp.service.util.NumberUtil;
import kz.epam.tcfp.service.util.PreviousPage;
import kz.epam.tcfp.service.util.ServiceConstants;
import org.apache.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Talgat Bekkaliyev
 * @project AdPlatform
 */
public class InputAdvertisementService extends PreviousPage implements Service {
    private static final Logger LOGGER = Logger.getLogger(InputAdvertisementService.class);
    private final AdvertisementDAO advertisementDAO = DAOFactory.getAdvertisementDAO();
    private final UserDAO userDAO = DAOFactory.getUserDAO();
    private final CategoryDAO categoryDAO = DAOFactory.getCategoryDAO();
    private final LocationDAO locationDAO = DAOFactory.getLocationDAO();

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        savePreviousPage(request);
        HttpSession session = request.getSession(true);
        String localLanguage = null;
        if (!LanguageUtil.validateLanguageCode(session.getAttribute(ServiceConstants.LOCAL_LANGUAGE))) {
            session.setAttribute(ServiceConstants.LOCAL_LANGUAGE, ServiceConstants.RUSSIAN_LANGUAGE);
            localLanguage = ServiceConstants.RUSSIAN_LANGUAGE;
        } else {
            localLanguage = (String) session.getAttribute(ServiceConstants.LOCAL_LANGUAGE);
        }

        Long userId = NumberUtil.tryCastToLong(session.getAttribute(ServiceConstants.SESSION_USER_ID));
        if (userId == null) {
            request.getRequestDispatcher(PagePath.SIGN_IN_SERVICE).forward(request, response);
            return;
        }
        List<Category> categories = new ArrayList<>();
        List<Location> locations = new ArrayList<>();
        try {
            if (Boolean.TRUE.equals(!userDAO.isUserBanned(userId))) {
                Long languageId = advertisementDAO.getLanguageIdByName(localLanguage);
                categories = categoryDAO.getCategories(languageId);
                locations = locationDAO.getLocations(languageId);
            } else {
                request.getRequestDispatcher(PagePath.INFORM_BANNED_JSP).forward(request, response);
                return;
            }
        } catch (DAOException e) {
            LOGGER.warn("Error in DAO while getting data for page InputAdvertisement", e);
        }
        request.setAttribute(ServiceConstants.CATEGORY_LIST, categories);
        request.setAttribute(ServiceConstants.LOCATION_LIST, locations);
        request.getRequestDispatcher(PagePath.ADD_ADVERTISEMENT_JSP).forward(request, response);

    }
}
