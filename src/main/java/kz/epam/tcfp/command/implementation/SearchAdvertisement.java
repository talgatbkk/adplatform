package kz.epam.tcfp.command.implementation;

import kz.epam.tcfp.command.Command;
import kz.epam.tcfp.command.PagePath;
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
public class SearchAdvertisement implements Command {
    private static final String SESSION_USER_ID = "userId";
    private static final Integer LOCATION_ID_EVERYWHERE_IN_KZ = 1;

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
        if (!locationInput.isEmpty()){
            locationId = Integer.parseInt(locationInput);
        }

        Integer categoryId = null;
        String categoryInput = request.getParameter("category_item");
        if (!categoryInput.isEmpty()){
            categoryId = Integer.parseInt(categoryInput);
        }

        AdvertisementDAO advertisementDAO = DAOFactory.getAdvertisementDAO();
        List<Category> categories = new ArrayList<>();
        List<Location> locations = new ArrayList<>();
        List<Advertisement> advertisements = null;

        try {
            Integer languageId = advertisementDAO.getLanguageIdByName(localLanguage);
            categories = advertisementDAO.getCategories(languageId);
            locations = advertisementDAO.getLocations(languageId);
            if (categoryId != null && locationId != null) {
                advertisements = advertisementDAO.searchAdvertisementsByCategoryWithLocation(categoryId, locationId);
            } else if (categoryId != null) {
                advertisements = advertisementDAO.searchAdvertisementsByCategory(categoryId);
            } else if (locationId != null && locationId != LOCATION_ID_EVERYWHERE_IN_KZ) {
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
        RequestDispatcher dispatcher = request.getRequestDispatcher(PagePath.ADVERTISEMENT);
        dispatcher.forward(request, response);
    }
}
