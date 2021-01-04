package kz.epam.tcfp.command.implementation;

import kz.epam.tcfp.command.Command;
import kz.epam.tcfp.dao.AdvertisementDAO;
import kz.epam.tcfp.dao.UserDAO;
import kz.epam.tcfp.dao.exception.DAOException;
import kz.epam.tcfp.dao.factory.DAOFactory;
import kz.epam.tcfp.model.*;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;

/**
 * @author Talgat Bekkaliyev
 * @project AdPlatform
 */
public class ViewAdvertisement implements Command {
    private static final String SESSION_USER_ID = "userId";

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession(true);
        String languageCode = (String) session.getAttribute("local");
        Integer userId = (Integer) session.getAttribute(SESSION_USER_ID);
        AdvertisementDAO advertisementDAO = DAOFactory.getAdvertisementDAO();
        UserDAO userDAO = DAOFactory.getUserDAO();
        Integer adId = null;
        if (request.getParameter("ad_id") == null){
            adId = (Integer) session.getAttribute("ad_id");
        } else {
            adId = Integer.parseInt(request.getParameter("ad_id"));
        }
        Advertisement advertisement = null;
        Location location = null;
        List<PhoneNumber> phoneNumbers = null;
        List<Comment> comments = null;
        try {
            advertisement = advertisementDAO.getAdvertisementById(adId);
            comments = advertisementDAO.getCommentsAByAdvertisementId(adId);
            location = advertisementDAO.getLocationNamesById(advertisement.getLocation().getId(), languageCode);
            phoneNumbers = userDAO.getPhoneNumberByUserId(advertisement.getUserId());
        } catch (DAOException e) {
            e.printStackTrace();
        }

        request.setAttribute("belongsToCurrentUser", false);
        if (advertisement.getUserId() == userId) {
            request.setAttribute("belongsToCurrentUser", true);
        }
        request.setAttribute("advertisement", advertisement);
        request.setAttribute("comments", comments);
        request.setAttribute("location", location);
        request.setAttribute("phone_numbers", phoneNumbers);
        RequestDispatcher dispatcher = request.getRequestDispatcher("/jsp/ViewAdvertisement.jsp");
        dispatcher.forward(request, response);
    }
}
