package kz.epam.tcfp.service.implementation;

import kz.epam.tcfp.service.Service;
import kz.epam.tcfp.dao.AdvertisementDAO;
import kz.epam.tcfp.dao.exception.DAOException;
import kz.epam.tcfp.dao.factory.DAOFactory;
import kz.epam.tcfp.model.Advertisement;
import kz.epam.tcfp.model.Category;
import kz.epam.tcfp.model.Location;
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
public class SearchAdvertisement extends PreviousPage implements Service {
    private static final Logger LOGGER = Logger.getLogger(SearchAdvertisement.class);
    private static final Integer LOCATION_ID_DEFAULT = 1;

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        savePreviousPage(request);
        HttpSession session = request.getSession(true);
        if (session.getAttribute(ServiceConstants.LOCAL_LANGUAGE) == null) {
            session.setAttribute(ServiceConstants.LOCAL_LANGUAGE, ServiceConstants.RUSSIAN_LANGUAGE);
        }
        String localLanguage = (String) session.getAttribute(ServiceConstants.LOCAL_LANGUAGE);
        String locationInput = request.getParameter(ServiceConstants.LOCATION_PICK);
        Integer locationId = null;
        if (locationInput != null && !locationInput.isEmpty()){
            locationId = Integer.parseInt(locationInput);
            if (locationId == LOCATION_ID_DEFAULT) {
                locationId = null;
                request.setAttribute(ServiceConstants.LOCATION_SELECTED, LOCATION_ID_DEFAULT);
            } else {
                request.setAttribute(ServiceConstants.LOCATION_SELECTED, locationId);
            }
        }
        Integer searchUserId = null;
        if (request.getParameter(ServiceConstants.USER_ID_TO_SEARCH) != null){
            searchUserId = Integer.parseInt(request.getParameter(ServiceConstants.USER_ID_TO_SEARCH));
        }
        Integer categoryId = null;
        String categoryInput = request.getParameter(ServiceConstants.CATEGORY_PICK);
        if (categoryInput != null && !categoryInput.isEmpty()){
            categoryId = Integer.parseInt(categoryInput);
        }
        String searchInput = request.getParameter(ServiceConstants.SEARCH_INPUT);
        AdvertisementDAO advertisementDAO = DAOFactory.getAdvertisementDAO();
        List<Category> categories = new ArrayList<>();
        List<Location> locations = new ArrayList<>();
        List<Advertisement> advertisements = null;

        try {
            Integer languageId = advertisementDAO.getLanguageIdByName(localLanguage);
            categories = advertisementDAO.getCategories(languageId);
            locations = advertisementDAO.getLocations(languageId);
            if (searchUserId != null) {
                advertisements = advertisementDAO.getAdvertisementByUserId(searchUserId);
            } else if (categoryId != null && locationId != null) {
                advertisements = advertisementDAO.searchAdvertisementsByCategoryWithLocation(categoryId, locationId);
            } else if (categoryId != null) {
                advertisements = advertisementDAO.searchAdvertisementsByCategory(categoryId);
            } else if (locationId != null) {
                advertisements = advertisementDAO.searchAdvertisementsByLocation(locationId);
            } else {
                advertisements = advertisementDAO.getAllAdvertisements();
            }

        } catch (DAOException e) {
            LOGGER.warn("Error in DAO while getting advertisement search results", e);
        }

        request.setAttribute(ServiceConstants.ADVERTISEMENT_LIST, advertisements);
        request.setAttribute(ServiceConstants.CATEGORY_LIST, categories);
        request.setAttribute(ServiceConstants.LOCATION_LIST, locations);
        request.setAttribute(ServiceConstants.CATEGORY_SELECTED, categoryId);

        RequestDispatcher dispatcher = request.getRequestDispatcher(HOME_SERVICE);
        dispatcher.forward(request, response);
    }

}
