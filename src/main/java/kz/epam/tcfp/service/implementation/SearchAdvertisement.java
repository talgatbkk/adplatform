package kz.epam.tcfp.service.implementation;

import kz.epam.tcfp.service.Service;
import kz.epam.tcfp.service.PagePath;
import kz.epam.tcfp.dao.AdvertisementDAO;
import kz.epam.tcfp.dao.exception.DAOException;
import kz.epam.tcfp.dao.factory.DAOFactory;
import kz.epam.tcfp.model.Advertisement;
import kz.epam.tcfp.model.Category;
import kz.epam.tcfp.model.Location;

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
public class SearchAdvertisement implements Service {
    private static final String SESSION_USER_ID = "userId";
    private static final Integer LOCATION_ID_DEFAULT = 1;

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        HttpSession session = request.getSession(true);
        if (session.getAttribute("local") == null) {
            session.setAttribute("local", "ru");
        }
        String localLanguage = (String) session.getAttribute("local");
        Integer userId = (Integer) session.getAttribute(SESSION_USER_ID);

        String locationInput = request.getParameter("location_item");
        Integer locationId = null;
        if (locationInput != null && !locationInput.isEmpty()){
            locationId = Integer.parseInt(locationInput);
            if (locationId == LOCATION_ID_DEFAULT) {
                locationId = null;
                request.setAttribute("location_selection", LOCATION_ID_DEFAULT);
            } else {
                request.setAttribute("location_selection", locationId);
            }
        }
        Integer searchUserId = null;
        if (request.getParameter("search_user_id") != null){
            searchUserId = Integer.parseInt(request.getParameter("search_user_id"));
        }


        Integer categoryId = null;
        String categoryInput = request.getParameter("category_item");
        if (categoryInput != null && !categoryInput.isEmpty()){
            categoryId = Integer.parseInt(categoryInput);
        }

        String searchInput = request.getParameter("search_input");

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
            e.printStackTrace();
        }

        request.setAttribute("advertisements", advertisements);
        request.setAttribute("categories", categories);
        request.setAttribute("locations", locations);
        request.setAttribute("category_selection", categoryId);

        RequestDispatcher dispatcher = request.getRequestDispatcher(PagePath.ADVERTISEMENT);
        dispatcher.forward(request, response);
    }

}
