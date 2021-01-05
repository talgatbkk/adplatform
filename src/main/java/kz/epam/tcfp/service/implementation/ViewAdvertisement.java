package kz.epam.tcfp.service.implementation;

import kz.epam.tcfp.service.Service;
import kz.epam.tcfp.dao.AdvertisementDAO;
import kz.epam.tcfp.dao.UserDAO;
import kz.epam.tcfp.dao.exception.DAOException;
import kz.epam.tcfp.dao.factory.DAOFactory;
import kz.epam.tcfp.model.*;
import kz.epam.tcfp.service.util.PreviousPage;
import kz.epam.tcfp.service.util.ServiceConstants;

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
public class ViewAdvertisement extends PreviousPage implements Service {


    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        savePreviousPage(request);
        HttpSession session = request.getSession(true);
        String languageCode = (String) session.getAttribute(ServiceConstants.LOCAL_LANGUAGE);
        Integer userId = (Integer) session.getAttribute(ServiceConstants.SESSION_USER_ID);
        AdvertisementDAO advertisementDAO = DAOFactory.getAdvertisementDAO();
        UserDAO userDAO = DAOFactory.getUserDAO();
        Integer adId = null;
        if (request.getParameter(ServiceConstants.ADVERTISEMENT_ID) == null){
            adId = (Integer) session.getAttribute(ServiceConstants.ADVERTISEMENT_ID);
        } else {
            adId = Integer.parseInt(request.getParameter(ServiceConstants.ADVERTISEMENT_ID));
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

        request.setAttribute(ServiceConstants.IS_AD_BELONGS_TO_CURRENT_USER, false);
        if (advertisement.getUserId() == userId) {
            request.setAttribute(ServiceConstants.IS_AD_BELONGS_TO_CURRENT_USER, true);
        }
        request.setAttribute(ServiceConstants.ADVERTISEMENT, advertisement);
        request.setAttribute(ServiceConstants.COMMENT_LIST, comments);
        request.setAttribute(ServiceConstants.LOCATION, location);
        request.setAttribute(ServiceConstants.PHONE_NUMBER_LIST, phoneNumbers);
        RequestDispatcher dispatcher = request.getRequestDispatcher("/jsp/ViewAdvertisement.jsp");
        dispatcher.forward(request, response);
    }
}
