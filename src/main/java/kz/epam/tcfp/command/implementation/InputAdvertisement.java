package kz.epam.tcfp.command.implementation;

import kz.epam.tcfp.command.Command;
import kz.epam.tcfp.dao.AdvertisementDAO;
import kz.epam.tcfp.dao.CustomerDAO;
import kz.epam.tcfp.dao.exception.DAOException;
import kz.epam.tcfp.dao.factory.DAOFactory;
import kz.epam.tcfp.model.Category;
import kz.epam.tcfp.model.Customer;
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
public class InputAdvertisement implements Command {
    private static final String SESSION_USER_ID = "userId";

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


        try {
            Integer languageId = advertisementDAO.getLanguageIdByName(localLanguage);
            categories = advertisementDAO.getCategories(languageId);
            locations = advertisementDAO.getLocations(languageId);
        } catch (DAOException e) {
            e.printStackTrace();
        }

        request.setAttribute("categories", categories);
        request.setAttribute("locations", locations);
        request.getRequestDispatcher("/jsp/AddAdvertisement.jsp").forward(request, response);

    }
}