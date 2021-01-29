package kz.epam.tcfp.service.implementation;

import kz.epam.tcfp.dao.*;
import kz.epam.tcfp.service.PagePath;
import kz.epam.tcfp.service.Service;
import kz.epam.tcfp.dao.exception.DAOException;
import kz.epam.tcfp.dao.factory.DAOFactory;
import kz.epam.tcfp.model.*;
import kz.epam.tcfp.service.util.NumberUtil;
import kz.epam.tcfp.service.util.PreviousPage;
import kz.epam.tcfp.service.util.ServiceConstants;
import org.apache.log4j.Logger;

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
public class ViewAdvertisementService extends PreviousPage implements Service {

    private static final Logger LOGGER = Logger.getLogger(ViewAdvertisementService.class);
    public static final String IMAGE_LOCATION_PARAMETER = "imageLocation";
    public static final char SLASH = '/';
    AdvertisementDAO advertisementDAO = DAOFactory.getAdvertisementDAO();
    private final ImageDAO imageDAO = DAOFactory.getImageDAO();
    private final UserDAO userDAO = DAOFactory.getUserDAO();
    private final CommentDAO commentDAO = DAOFactory.getCommentDAO();
    private final LocationDAO locationDAO = DAOFactory.getLocationDAO();

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        savePreviousPage(request);
        HttpSession session = request.getSession(true);
        String languageCode = (String) session.getAttribute(ServiceConstants.LOCAL_LANGUAGE);
        Long userId = NumberUtil.tryCastToLong(session.getAttribute(ServiceConstants.SESSION_USER_ID));
        Long adId = NumberUtil.tryParseLong(request.getParameter(ServiceConstants.ADVERTISEMENT_ID));
        if (adId == null) {
            response.sendRedirect(PagePath.ERROR_JSP);
            return;
        }
        Advertisement advertisement = null;
        Location location = null;
        List<PhoneNumber> phoneNumbers = null;
        List<Comment> comments = null;
        Image image = null;
        try {
            advertisement = advertisementDAO.getAdvertisementById(adId);
            image = imageDAO.getImage(adId);
            comments = commentDAO.getCommentsAByAdvertisementId(adId);
            location = locationDAO.getLocationNamesById(advertisement.getLocation().getId(), languageCode);
            phoneNumbers = userDAO.getPhoneNumberByUserId(advertisement.getUserId());
        } catch (DAOException e) {
            LOGGER.warn("Error in DAO while getting advertisement data", e);
        }
        if (advertisement == null) {
            response.sendRedirect(PagePath.ERROR_JSP);
            return;
        }
        request.setAttribute(ServiceConstants.IS_AD_BELONGS_TO_CURRENT_USER, false);
        if (advertisement.getUserId().equals(userId)) {
            request.setAttribute(ServiceConstants.IS_AD_BELONGS_TO_CURRENT_USER, true);
        }
        String imagePath = null;
        if (image != null) {
            imagePath = image.getPath();
        }
        request.setAttribute(ServiceConstants.ADVERTISEMENT, advertisement);
        request.setAttribute(ServiceConstants.IMAGE_PATH, imagePath);
        request.setAttribute(ServiceConstants.COMMENT_LIST, comments);
        request.setAttribute(ServiceConstants.LOCATION, location);
        request.setAttribute(ServiceConstants.PHONE_NUMBER_LIST, phoneNumbers);
        RequestDispatcher dispatcher = request.getRequestDispatcher(PagePath.VIEW_ADVERTISEMENT_JSP);
        dispatcher.forward(request, response);
    }
}
