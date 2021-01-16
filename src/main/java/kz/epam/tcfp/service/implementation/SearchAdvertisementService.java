package kz.epam.tcfp.service.implementation;

import kz.epam.tcfp.dao.CategoryDAO;
import kz.epam.tcfp.dao.LocationDAO;
import kz.epam.tcfp.dao.util.DBConstants;
import kz.epam.tcfp.service.PagePath;
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
public class SearchAdvertisementService extends PreviousPage implements Service {
    private static final Logger LOGGER = Logger.getLogger(SearchAdvertisementService.class);
    private static final Long LOCATION_ID_DEFAULT = 1L;
    private static final String EMPTY_STRING = "";
    public static final String PAGE_NUMBER = "page";

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        savePreviousPage(request);
        HttpSession session = request.getSession(true);
        if (session.getAttribute(ServiceConstants.LOCAL_LANGUAGE) == null) {
            session.setAttribute(ServiceConstants.LOCAL_LANGUAGE, ServiceConstants.RUSSIAN_LANGUAGE);
        }
        String localLanguage = (String) session.getAttribute(ServiceConstants.LOCAL_LANGUAGE);
        String locationInput = request.getParameter(ServiceConstants.LOCATION_PICK);
        Long locationId = null;
        if (locationInput != null && !locationInput.isEmpty()){
            locationId = Long.parseLong(locationInput);
            if (locationId == LOCATION_ID_DEFAULT) {
                locationId = null;
                request.setAttribute(ServiceConstants.LOCATION_SELECTED, LOCATION_ID_DEFAULT);
            } else {
                request.setAttribute(ServiceConstants.LOCATION_SELECTED, locationId);
            }
        }
        Long searchUserId = null;
        if (request.getParameter(ServiceConstants.USER_ID_TO_SEARCH) != null){
            searchUserId = Long.parseLong(request.getParameter(ServiceConstants.USER_ID_TO_SEARCH));
        }
        Long categoryId = null;
        String categoryInput = request.getParameter(ServiceConstants.CATEGORY_PICK);
        if (categoryInput != null && !categoryInput.isEmpty()){
            categoryId = Long.parseLong(categoryInput);
        }
        String searchInput = request.getParameter(ServiceConstants.SEARCH_INPUT);
        if (searchInput != null && !searchInput.isEmpty()) {
            request.setAttribute(ServiceConstants.PREVIOUS_SEARCH_INPUT, searchInput);
        } else {
            request.setAttribute(ServiceConstants.PREVIOUS_SEARCH_INPUT, EMPTY_STRING);
        }
        String pageInput = request.getParameter(PAGE_NUMBER);
        Integer page = 1;
        if (pageInput != null && !pageInput.isEmpty()) {
            page = Integer.parseInt(pageInput);
        }

        AdvertisementDAO advertisementDAO = DAOFactory.getAdvertisementDAO();
        CategoryDAO categoryDAO = DAOFactory.getCategoryDAO();
        LocationDAO locationDAO = DAOFactory.getLocationDAO();
        List<Category> categories = new ArrayList<>();
        List<Location> locations = new ArrayList<>();
        List<Advertisement> advertisements = null;
        Integer totalNumberOfAds = 0;
        try {
            Long languageId = advertisementDAO.getLanguageIdByName(localLanguage);
            categories = categoryDAO.getCategories(languageId);
            locations = locationDAO.getLocations(languageId);
            if (searchInput == null || searchInput.isEmpty()) {
                if (searchUserId != null) {
                    advertisements = advertisementDAO.getAdvertisementByUserId(searchUserId, page);
                    totalNumberOfAds = advertisementDAO.countGetAdvertisementByUserId(searchUserId);
                } else if (categoryId != null && locationId != null) {
                    advertisements = advertisementDAO.searchAdvertisementsByCategoryAndLocation(categoryId, locationId, page);
                    totalNumberOfAds = advertisementDAO.countSearchAdvertisementsByCategoryAndLocation(categoryId, locationId);
                } else if (categoryId != null) {
                    advertisements = advertisementDAO.searchAdvertisementsByCategory(categoryId, page);
                    totalNumberOfAds = advertisementDAO.countSearchAdvertisementsByCategory(categoryId);
                } else if (locationId != null) {
                    advertisements = advertisementDAO.searchAdvertisementsByLocation(locationId, page);
                    totalNumberOfAds = advertisementDAO.countSearchAdvertisementsByLocation(locationId);
                } else {
                    advertisements = advertisementDAO.getAllAdvertisements(page);
                    totalNumberOfAds = advertisementDAO.getCountAllAdvertisements();
                }
            } else {
                if (categoryId != null && locationId != null) {
                    advertisements = advertisementDAO.searchAdvertisementsByDescriptionAndCategoryAndLocation(searchInput, categoryId, locationId, page);
                    totalNumberOfAds = advertisementDAO.countSearchAdvertisementsByDescriptionAndCategoryAndLocation(searchInput, categoryId, locationId);
                } else if (categoryId != null) {
                    advertisements = advertisementDAO.searchAdvertisementsByDescriptionAndCategory(searchInput, categoryId, page);
                    totalNumberOfAds = advertisementDAO.countSearchAdvertisementsByDescriptionAndCategory(searchInput, categoryId);
                } else if (locationId != null) {
                    advertisements = advertisementDAO.searchAdvertisementsByDescriptionAndLocation(searchInput, locationId, page);
                    totalNumberOfAds = advertisementDAO.countSearchAdvertisementsByDescriptionAndLocation(searchInput, locationId);
                } else {
                    advertisements = advertisementDAO.searchAdvertisementsByDescription(searchInput, page);
                    totalNumberOfAds = advertisementDAO.countSearchAdvertisementsByDescription(searchInput);
                }
            }
        } catch (DAOException e) {
            LOGGER.warn("Error in DAO while getting advertisement search results", e);
        }
        request.setAttribute(ServiceConstants.ADVERTISEMENT_LIST, advertisements);
        request.setAttribute(ServiceConstants.CATEGORY_LIST, categories);
        request.setAttribute(ServiceConstants.LOCATION_LIST, locations);
        request.setAttribute(ServiceConstants.CATEGORY_SELECTED, categoryId);
        request.setAttribute(ServiceConstants.TOTAL_ADVERTISEMENT_COUNT, totalNumberOfAds);
        request.setAttribute(ServiceConstants.CURRENT_PAGE_NUMBER, page);
        request.setAttribute(ServiceConstants.PER_PAGE_ADVERTISEMENT_COUNT, DBConstants.ADVERTISEMENT_PER_PAGE);

        RequestDispatcher dispatcher = request.getRequestDispatcher(PagePath.SEARCH_JSP);
        dispatcher.forward(request, response);
    }

}
