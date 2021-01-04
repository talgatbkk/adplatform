package kz.epam.tcfp.service.implementation;

import kz.epam.tcfp.service.Service;
import kz.epam.tcfp.dao.AdvertisementDAO;
import kz.epam.tcfp.dao.UserDAO;
import kz.epam.tcfp.dao.exception.DAOException;
import kz.epam.tcfp.dao.factory.DAOFactory;
import kz.epam.tcfp.model.Category;
import kz.epam.tcfp.model.Location;

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
public class InputAdvertisement implements Service {
    private static final String SESSION_USER_ID = "userId";

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession(true);
        if (session.getAttribute("local") == null) {
            session.setAttribute("local", "ru");
        }
        String localLanguage = (String) session.getAttribute("local");

        Integer userId = null;
        if (session.getAttribute(SESSION_USER_ID) != null) {
            userId = (Integer) session.getAttribute(SESSION_USER_ID);
        } else {
            request.getRequestDispatcher("/signin").forward(request, response);
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
                request.getRequestDispatcher("/jsp/InformBanned.jsp").forward(request, response);
            }
        } catch (DAOException e) {
            e.printStackTrace();
        }

        request.setAttribute("categories", categories);
        request.setAttribute("locations", locations);
        request.getRequestDispatcher("/jsp/AddAdvertisement.jsp").forward(request, response);

    }
}
