package kz.epam.tcfp.service.implementation;

import kz.epam.tcfp.dao.AdvertisementDAO;
import kz.epam.tcfp.dao.exception.DAOException;
import kz.epam.tcfp.dao.factory.DAOFactory;
import kz.epam.tcfp.model.Advertisement;
import kz.epam.tcfp.model.Category;
import kz.epam.tcfp.model.Location;
import kz.epam.tcfp.service.PagePath;
import kz.epam.tcfp.service.Service;
import kz.epam.tcfp.service.util.NumberUtil;
import kz.epam.tcfp.service.util.PreviousPage;
import kz.epam.tcfp.service.util.ServiceConstants;
import org.apache.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 * @author Talgat Bekkaliyev
 * @project AdPlatform
 */
public class PostAdvertisementService extends PreviousPage implements Service {
    private static final Logger LOGGER = Logger.getLogger(PostAdvertisementService.class);
    private final AdvertisementDAO advertisementDAO = DAOFactory.getAdvertisementDAO();

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        savePreviousPage(request);
        HttpSession session = request.getSession(true);
        Long userId = NumberUtil.tryCastToLong(session.getAttribute(ServiceConstants.SESSION_USER_ID));
        if (userId == null) {
            response.sendRedirect(PagePath.LOGIN_SERVICE);
            return;
        }
        Long categoryId = NumberUtil.tryParseLong(request.getParameter(ServiceConstants.CATEGORY_PICK));
        Long locationId = NumberUtil.tryParseLong(request.getParameter(ServiceConstants.LOCATION_PICK));
        String advertisementTitle = request.getParameter(ServiceConstants.ADVERTISEMENT_TITLE);
        String advertisementDescription = request.getParameter(ServiceConstants.ADVERTISEMENT_DESCRIPTION);
        Integer advertisementPrice = NumberUtil.tryParseInteger(request.getParameter(ServiceConstants.ADVERTISEMENT_PRICE));

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
            LOGGER.warn("Error in DAO while posting an advertisement", e);
        }

        response.sendRedirect(HOME_SERVICE);
        }

}
