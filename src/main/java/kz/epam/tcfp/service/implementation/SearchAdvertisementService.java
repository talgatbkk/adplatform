package kz.epam.tcfp.service.implementation;

import kz.epam.tcfp.dao.AdvertisementDAO;
import kz.epam.tcfp.dao.CategoryDAO;
import kz.epam.tcfp.dao.LocationDAO;
import kz.epam.tcfp.dao.exception.DAOException;
import kz.epam.tcfp.dao.factory.DAOFactory;
import kz.epam.tcfp.dao.util.DBConstants;
import kz.epam.tcfp.model.AdvertisementPage;
import kz.epam.tcfp.model.Category;
import kz.epam.tcfp.model.Location;
import kz.epam.tcfp.service.PagePath;
import kz.epam.tcfp.service.Service;
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
public class SearchAdvertisementService extends PreviousPage implements Service {
    private static final Logger LOGGER = Logger.getLogger(SearchAdvertisementService.class);
    private static final Long LOCATION_ID_DEFAULT = 1L;
    private static final String EMPTY_STRING = "";
    public static final String PAGE_NUMBER = "page";
    private final AdvertisementDAO advertisementDAO = DAOFactory.getAdvertisementDAO();
    private final CategoryDAO categoryDAO = DAOFactory.getCategoryDAO();
    private final LocationDAO locationDAO = DAOFactory.getLocationDAO();

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
            try {
                locationId = Long.parseLong(locationInput);
            } catch (NumberFormatException e) {
                LOGGER.warn("Error while parsing a number", e);
                response.sendRedirect(PagePath.ERROR_JSP);
                return;
            }
            if (locationId.equals(LOCATION_ID_DEFAULT)) {
                locationId = null;
                request.setAttribute(ServiceConstants.LOCATION_SELECTED, LOCATION_ID_DEFAULT);
            } else {
                request.setAttribute(ServiceConstants.LOCATION_SELECTED, locationId);
            }
        }
        Long searchUserId = null;
        searchUserId = NumberUtil.tryParseLong(request.getParameter(ServiceConstants.USER_ID_TO_SEARCH));
        if (searchUserId == null) {
            response.sendRedirect(PagePath.ERROR_JSP);
            return;
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
        List<Category> categories = new ArrayList<>();
        List<Location> locations = new ArrayList<>();
        AdvertisementPage advertisementPage = null;

        try {
            Long languageId = advertisementDAO.getLanguageIdByName(localLanguage);
            categories = categoryDAO.getCategories(languageId);
            locations = locationDAO.getLocations(languageId);
            if (searchInput == null || searchInput.isEmpty()) {
                advertisementPage = getAdvertisementsWithoutSearchTextInput(searchUserId, categoryId, locationId, page);
            } else {
                advertisementPage = getAdvertisementsWithSearchTextInput(searchInput, categoryId, locationId, page);
            }
        } catch (DAOException e) {
            LOGGER.warn("Error in DAO while getting advertisement search results", e);
        }
        request.setAttribute(ServiceConstants.ADVERTISEMENT_LIST, advertisementPage.getCurrentPageAdvertisements());
        request.setAttribute(ServiceConstants.CATEGORY_LIST, categories);
        request.setAttribute(ServiceConstants.LOCATION_LIST, locations);
        request.setAttribute(ServiceConstants.CATEGORY_SELECTED, categoryId);
        request.setAttribute(ServiceConstants.TOTAL_ADVERTISEMENT_COUNT, advertisementPage.getTotalNumberOfAdvertisements());
        request.setAttribute(ServiceConstants.CURRENT_PAGE_NUMBER, page);
        request.setAttribute(ServiceConstants.PER_PAGE_ADVERTISEMENT_COUNT, DBConstants.ADVERTISEMENT_PER_PAGE);

        RequestDispatcher dispatcher = request.getRequestDispatcher(PagePath.SEARCH_JSP);
        dispatcher.forward(request, response);
    }

    private AdvertisementPage getAdvertisementsWithoutSearchTextInput(Long searchUserId, Long categoryId,
                                                                      Long locationId, Integer page)
                                                                        throws DAOException {
        AdvertisementPage advertisementPage = new AdvertisementPage();
        if (searchUserId != null) {
            advertisementPage.setCurrentPageAdvertisements(advertisementDAO.getAdvertisementByUserId(searchUserId, page));
            advertisementPage.setTotalNumberOfAdvertisements(advertisementDAO.countGetAdvertisementByUserId(searchUserId));
        } else if (categoryId != null && locationId != null) {
            advertisementPage.setCurrentPageAdvertisements(advertisementDAO.searchAdvertisementsByCategoryAndLocation(categoryId, locationId, page));
            advertisementPage.setTotalNumberOfAdvertisements(advertisementDAO.countSearchAdvertisementsByCategoryAndLocation(categoryId, locationId));
        } else if (categoryId != null) {
            advertisementPage.setCurrentPageAdvertisements(advertisementDAO.searchAdvertisementsByCategory(categoryId, page));
            advertisementPage.setTotalNumberOfAdvertisements(advertisementDAO.countSearchAdvertisementsByCategory(categoryId));
        } else if (locationId != null) {
            advertisementPage.setCurrentPageAdvertisements(advertisementDAO.searchAdvertisementsByLocation(locationId, page));
            advertisementPage.setTotalNumberOfAdvertisements(advertisementDAO.countSearchAdvertisementsByLocation(locationId));
        } else {
            advertisementPage.setCurrentPageAdvertisements(advertisementDAO.getAllAdvertisements(page));
            advertisementPage.setTotalNumberOfAdvertisements(advertisementDAO.getCountAllAdvertisements());
        }
            return advertisementPage;
    }


    private AdvertisementPage getAdvertisementsWithSearchTextInput(String searchInput, Long categoryId, Long locationId, Integer page) throws DAOException {
        AdvertisementPage advertisementPage = new AdvertisementPage();
        if (categoryId != null && locationId != null) {
            advertisementPage.setCurrentPageAdvertisements(advertisementDAO.searchAdvertisementsByDescriptionAndCategoryAndLocation(searchInput, categoryId, locationId, page));
            advertisementPage.setTotalNumberOfAdvertisements(advertisementDAO.countSearchAdvertisementsByDescriptionAndCategoryAndLocation(searchInput, categoryId, locationId));
        } else if (categoryId != null) {
            advertisementPage.setCurrentPageAdvertisements(advertisementDAO.searchAdvertisementsByDescriptionAndCategory(searchInput, categoryId, page));
            advertisementPage.setTotalNumberOfAdvertisements(advertisementDAO.countSearchAdvertisementsByDescriptionAndCategory(searchInput, categoryId));
        } else if (locationId != null) {
            advertisementPage.setCurrentPageAdvertisements(advertisementDAO.searchAdvertisementsByDescriptionAndLocation(searchInput, locationId, page));
            advertisementPage.setTotalNumberOfAdvertisements(advertisementDAO.countSearchAdvertisementsByDescriptionAndLocation(searchInput, locationId));
        } else {
            advertisementPage.setCurrentPageAdvertisements(advertisementDAO.searchAdvertisementsByDescription(searchInput, page));
            advertisementPage.setTotalNumberOfAdvertisements(advertisementDAO.countSearchAdvertisementsByDescription(searchInput));
        }
        return advertisementPage;
    }

}
