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

/**
 * @author Talgat Bekkaliyev
 * @project AdPlatform
 */
public class PostAddLocationService extends PreviousPage implements Service {
    private static final Logger LOGGER = Logger.getLogger(PostAddLocationService.class);
    private static final String SIGN_IN_SERVICE = "/signin";
    public static final String ADMIN_ADD_LOCATION = "/location/add";
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
        Long parentLocationId = NumberUtil.tryParseLong(request.getParameter(ServiceConstants.PARENT_LOCATION_ID));
        String locationName = request.getParameter(ServiceConstants.NEW_LOCATION_NAME);
        if (parentLocationId == null || locationName == null
                                     || locationName.isEmpty()) {
            response.sendRedirect(PagePath.ERROR_JSP);
            return;
        }
        Location location = new Location();
        location.setName(locationName);
        location.setParentId(parentLocationId);
        try {
            Long languageId = advertisementDAO.getLanguageIdByName(localLanguage);
            location.setLanguageId(languageId);
            if (!locationDAO.postLocation(location)) {
                response.sendRedirect(PagePath.ERROR_JSP);
                return;
            }
        } catch (DAOException e) {
            LOGGER.warn("Error in DAO while posting an advertisement", e);
        }
        response.sendRedirect(ADMIN_ADD_LOCATION);
    }

}

