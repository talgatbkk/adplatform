package kz.epam.tcfp.service.implementation;

import kz.epam.tcfp.model.Advertisement;
import kz.epam.tcfp.service.Service;
import kz.epam.tcfp.dao.AdvertisementDAO;
import kz.epam.tcfp.dao.exception.DAOException;
import kz.epam.tcfp.dao.factory.DAOFactory;
import kz.epam.tcfp.service.util.PreviousPage;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 * @author Talgat Bekkaliyev
 * @project AdPlatform
 */
public class DeleteAdvertisement extends PreviousPage implements Service {
    private static final String SESSION_USER_ID = "userId";
    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        savePreviousPage(request);
        HttpSession session = request.getSession(true);
        Integer userId = null;
        if (session.getAttribute(SESSION_USER_ID) != null){
            userId = (Integer) session.getAttribute(SESSION_USER_ID);
        } else {
            response.sendRedirect("/signin");
            return;
        }

        Integer roleId = null;
        if (session.getAttribute("role_id") != null){
            roleId = (Integer) session.getAttribute("role_id");
        }


        Integer adId = Integer.parseInt(request.getParameter("ad_id"));
        AdvertisementDAO advertisementDAO = DAOFactory.getAdvertisementDAO();
        Advertisement advertisement = null;

        try {
            advertisement = advertisementDAO.getAdvertisementById(adId);
            if (advertisement.getUserId() == userId || roleId == 1) {
                advertisementDAO.deleteAdvertisementByUserIdAndAdId(adId);
            } else {
                response.sendRedirect("/error");
            }
        } catch (DAOException e) {
            e.printStackTrace();
        }

        request.getRequestDispatcher("/home").forward(request, response);


    }
}
