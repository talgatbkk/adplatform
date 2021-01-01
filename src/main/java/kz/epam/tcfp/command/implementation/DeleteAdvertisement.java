package kz.epam.tcfp.command.implementation;

import kz.epam.tcfp.command.Command;
import kz.epam.tcfp.dao.AdvertisementDAO;
import kz.epam.tcfp.dao.exception.DAOException;
import kz.epam.tcfp.dao.factory.DAOFactory;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 * @author Talgat Bekkaliyev
 * @project AdPlatform
 */
public class DeleteAdvertisement implements Command {
    private static final String SESSION_USER_ID = "userId";
    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession(true);
        Integer userId = (Integer) session.getAttribute(SESSION_USER_ID);
        Integer adId = Integer.parseInt(request.getParameter("ad_id"));
        AdvertisementDAO advertisementDAO = DAOFactory.getAdvertisementDAO();

        try {
            advertisementDAO.deleteAdvertisementByUserIdAndAdId(userId, adId);
        } catch (DAOException e) {
            e.printStackTrace();
        }

        response.sendRedirect("home?forward_page=get_ads");


    }
}
