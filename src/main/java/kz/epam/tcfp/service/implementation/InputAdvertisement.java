package kz.epam.tcfp.service.implementation;

import kz.epam.tcfp.service.PagePath;
import kz.epam.tcfp.service.Service;
import kz.epam.tcfp.dao.AdvertisementDAO;
import kz.epam.tcfp.dao.UserDAO;
import kz.epam.tcfp.dao.exception.DAOException;
import kz.epam.tcfp.dao.factory.DAOFactory;
import kz.epam.tcfp.model.Category;
import kz.epam.tcfp.model.Location;
import kz.epam.tcfp.service.util.PreviousPage;
import kz.epam.tcfp.service.util.ServiceConstants;

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
public class InputAdvertisement extends PreviousPage implements Service {

    public static final String SIGN_IN_SERVICE = "/signin";


    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        savePreviousPage(request);
        HttpSession session = request.getSession(true);
        if (session.getAttribute(ServiceConstants.LOCAL_LANGUAGE) == null) {
            session.setAttribute(ServiceConstants.LOCAL_LANGUAGE, ServiceConstants.RUSSIAN_LANGUAGE);
        }
        String localLanguage = (String) session.getAttribute(ServiceConstants.LOCAL_LANGUAGE);

        Integer userId = null;
        if (session.getAttribute(ServiceConstants.SESSION_USER_ID) != null) {
            userId = (Integer) session.getAttribute(ServiceConstants.SESSION_USER_ID);
        } else {
            request.getRequestDispatcher(SIGN_IN_SERVICE).forward(request, response);
            return;
        }

        AdvertisementDAO advertisementDAO = DAOFactory.getAdvertisementDAO();
        UserDAO userDAO = DAOFactory.getUserDAO();
        List<Category> categories = new ArrayList<>();
        List<Location> locations = new ArrayList<>();


        try {
            if (userDAO.isUserBanned(userId)) {
                Integer languageId = advertisementDAO.getLanguageIdByName(localLanguage);
                categories = advertisementDAO.getCategories(languageId);
                locations = advertisementDAO.getLocations(languageId);
            } else {
                request.getRequestDispatcher(PagePath.INFORM_BANNED_JSP).forward(request, response);
                return;
            }
        } catch (DAOException e) {
            e.printStackTrace();
        }

        request.setAttribute(ServiceConstants.CATEGORY_LIST, categories);
        request.setAttribute(ServiceConstants.LOCATION_LIST, locations);
        request.getRequestDispatcher(PagePath.ADD_ADVERTISEMENT_JSP).forward(request, response);

    }
}
