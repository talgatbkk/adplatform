package kz.epam.tcfp.command.implementation;

import kz.epam.tcfp.command.Command;
import kz.epam.tcfp.dao.AdvertisementDAO;
import kz.epam.tcfp.dao.exception.DAOException;
import kz.epam.tcfp.dao.factory.DAOFactory;
import kz.epam.tcfp.model.Advertisement;
import kz.epam.tcfp.model.Category;
import kz.epam.tcfp.model.Comment;
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
public class PostAdvertisement implements Command {
    private static final String SESSION_USER_ID = "userId";
    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession(true);
        Integer userId = (Integer) session.getAttribute(SESSION_USER_ID);
        Integer categoryId = Integer.parseInt(request.getParameter("category_item"));
        Integer locationId = Integer.parseInt(request.getParameter("location_item"));
        String advertisementTitle = request.getParameter("ad_title");
        String advertisementDescription = request.getParameter("ad_description");
        Integer advertisementPrice = Integer.parseInt(request.getParameter("price"));
        AdvertisementDAO advertisementDAO = DAOFactory.getAdvertisementDAO();

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

        response.sendRedirect("/home?forward_page=get_ads");
        }

}
