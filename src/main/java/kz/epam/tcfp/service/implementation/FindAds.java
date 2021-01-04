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
public class FindAds implements Service {
    private static final String SESSION_USER_ID = "userId";
    private static final Integer LOCATION_ID_DEFAULT = 1;
    private static final Integer CATEGORY_ID_ALL = 1;

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession(true);
        if (session.getAttribute("local") == null) {
            session.setAttribute("local", "ru");
        }
        String localLanguage = (String) session.getAttribute("local");
        Integer userId = (Integer) session.getAttribute(SESSION_USER_ID);
        AdvertisementDAO advertisementDAO = DAOFactory.getAdvertisementDAO();
        List<Category> categories = new ArrayList<>();
        List<Location> locations = new ArrayList<>();
        List<Advertisement> advertisements = new ArrayList<>();

        try {
            Integer languageId = advertisementDAO.getLanguageIdByName(localLanguage);
            advertisements = advertisementDAO.getAllAdvertisements();
            categories = advertisementDAO.getCategories(languageId);
            locations = advertisementDAO.getLocations(languageId);
        } catch (DAOException e) {
            e.printStackTrace();
        }


        request.setAttribute("advertisements", advertisements);
        request.setAttribute("categories", categories);
        request.setAttribute("locations", locations);
        request.setAttribute("location_selection", LOCATION_ID_DEFAULT);
        RequestDispatcher dispatcher = request.getRequestDispatcher(PagePath.ADVERTISEMENT);
        dispatcher.forward(request, response);

    }
}
