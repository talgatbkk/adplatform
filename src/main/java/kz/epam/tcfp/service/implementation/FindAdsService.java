package kz.epam.tcfp.service.implementation;

import kz.epam.tcfp.dao.CategoryDAO;
import kz.epam.tcfp.dao.LocationDAO;
import kz.epam.tcfp.dao.util.DBConstants;
import kz.epam.tcfp.service.Service;
import kz.epam.tcfp.service.PagePath;
import kz.epam.tcfp.dao.AdvertisementDAO;
import kz.epam.tcfp.dao.exception.DAOException;
import kz.epam.tcfp.dao.factory.DAOFactory;
import kz.epam.tcfp.model.Advertisement;
import kz.epam.tcfp.model.Category;
import kz.epam.tcfp.model.Location;
import kz.epam.tcfp.service.util.LanguageUtil;
import kz.epam.tcfp.service.util.NumberUtil;
import kz.epam.tcfp.service.util.PreviousPage;
import kz.epam.tcfp.service.util.ServiceConstants;
import org.apache.log4j.Logger;


import javax.servlet.RequestDispatcher;
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
public class FindAdsService extends PreviousPage implements Service {
    private static final Logger LOGGER = Logger.getLogger(FindAdsService.class);
    private static final Integer LOCATION_ID_DEFAULT = 1;
    public static final String PAGE_NUMBER = "page";
    private final AdvertisementDAO advertisementDAO = DAOFactory.getAdvertisementDAO();
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
        Integer page = NumberUtil.tryParsePageNumber(request.getParameter(PAGE_NUMBER));
        List<Category> categories = new ArrayList<>();
        List<Location> locations = new ArrayList<>();
        List<Advertisement> advertisements = new ArrayList<>();
        Integer countAllAds = 0;
        try {
            Long languageId = advertisementDAO.getLanguageIdByName(localLanguage);
            advertisements = advertisementDAO.getAllAdvertisements(page);
            countAllAds = advertisementDAO.getCountAllAdvertisements();
            categories = categoryDAO.getCategories(languageId);
            locations = locationDAO.getLocations(languageId);
        } catch (DAOException e) {
            LOGGER.warn("Error in DAO while getting recent advertisement info", e);
        }

        request.setAttribute(ServiceConstants.ADVERTISEMENT_LIST, advertisements);
        request.setAttribute(ServiceConstants.TOTAL_ADVERTISEMENT_COUNT, countAllAds);
        request.setAttribute(ServiceConstants.CURRENT_PAGE_NUMBER, page);
        request.setAttribute(ServiceConstants.PER_PAGE_ADVERTISEMENT_COUNT, DBConstants.ADVERTISEMENT_PER_PAGE);
        request.setAttribute(ServiceConstants.CATEGORY_LIST, categories);
        request.setAttribute(ServiceConstants.LOCATION_LIST, locations);
        request.setAttribute(ServiceConstants.LOCATION_SELECTED, LOCATION_ID_DEFAULT);
        RequestDispatcher dispatcher = request.getRequestDispatcher(PagePath.HOME_JSP);
        dispatcher.forward(request, response);

    }
}
