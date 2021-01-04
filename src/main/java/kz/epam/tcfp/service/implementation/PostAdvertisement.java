package kz.epam.tcfp.service.implementation;

import kz.epam.tcfp.service.Service;
import kz.epam.tcfp.dao.AdvertisementDAO;
import kz.epam.tcfp.dao.UserDAO;
import kz.epam.tcfp.dao.exception.DAOException;
import kz.epam.tcfp.dao.factory.DAOFactory;
import kz.epam.tcfp.model.Advertisement;
import kz.epam.tcfp.model.Category;
import kz.epam.tcfp.model.Location;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 * @author Talgat Bekkaliyev
 * @project AdPlatform
 */
public class PostAdvertisement implements Service {
    private static final String SESSION_USER_ID = "userId";
    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession(true);
        if (session.getAttribute(SESSION_USER_ID) == null){
            response.sendRedirect("/login");
            return;
        }
        Integer userId = (Integer) session.getAttribute(SESSION_USER_ID);
        Integer categoryId = Integer.parseInt(request.getParameter("category_item"));
        Integer locationId = Integer.parseInt(request.getParameter("location_item"));
        String advertisementTitle = request.getParameter("ad_title");
        String advertisementDescription = request.getParameter("ad_description");
        Integer advertisementPrice = Integer.parseInt(request.getParameter("price"));
        AdvertisementDAO advertisementDAO = DAOFactory.getAdvertisementDAO();
        UserDAO userDAO = DAOFactory.getUserDAO();

        Advertisement advertisement = new Advertisement();
        advertisement.setUserId(userId);
        advertisement.setLocation(new Location(locationId));
        advertisement.setCategory(new Category(categoryId));
        advertisement.setTitle(advertisementTitle);
        advertisement.setDescription(advertisementDescription);
        advertisement.setPrice(advertisementPrice);


        try {
            advertisementDAO.postAdvertisement(advertisement);

        } catch (DAOException e) {
            e.printStackTrace();
        }

        response.sendRedirect("/home");
        }

}
