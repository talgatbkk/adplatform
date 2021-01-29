package kz.epam.tcfp.service.implementation;

import kz.epam.tcfp.dao.AdvertisementDAO;
import kz.epam.tcfp.dao.LocationDAO;
import kz.epam.tcfp.dao.exception.DAOException;
import kz.epam.tcfp.dao.factory.DAOFactory;
import kz.epam.tcfp.model.Location;
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
import java.util.ArrayList;
import java.util.List;

/**
 * @author Talgat Bekkaliyev
 * @project AdPlatform
 */
public class OpenAddLocationService extends PreviousPage implements Service {
    private static final Logger LOGGER = Logger.getLogger(OpenAddLocationService.class);
    private static final String SIGN_IN_SERVICE = "/signin";
    private final AdvertisementDAO advertisementDAO = DAOFactory.getAdvertisementDAO();
    private final LocationDAO locationDAO = DAOFactory.getLocationDAO();

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        savePreviousPage(request);
        HttpSession session = request.getSession(true);
        if (session.getAttribute(ServiceConstants.LOCAL_LANGUAGE) == null) {
            session.setAttribute(ServiceConstants.LOCAL_LANGUAGE, ServiceConstants.RUSSIAN_LANGUAGE);
        }
        String localLanguage = (String) session.getAttribute(ServiceConstants.LOCAL_LANGUAGE);
        Long userId = NumberUtil.tryCastToLong(session.getAttribute(ServiceConstants.SESSION_USER_ID));
        Long roleId = NumberUtil.tryCastToLong(session.getAttribute(ServiceConstants.USER_ROLE_ID));
        if (userId == null || !roleId.equals(ServiceConstants.ADMIN_ROLE_ID)) {
            request.getRequestDispatcher(SIGN_IN_SERVICE).forward(request, response);
            return;
        }
        List<Location> locations = new ArrayList<>();
        try {
            Long languageId = advertisementDAO.getLanguageIdByName(localLanguage);
            locations = locationDAO.getLocations(languageId);
        } catch (DAOException e) {
            LOGGER.warn("Error in DAO while getting data for page InputAdvertisement", e);
        }
        request.setAttribute(ServiceConstants.LOCATION_LIST, locations);
        request.getRequestDispatcher(PagePath.ADD_LOCATION_JSP).forward(request, response);
    }
}

