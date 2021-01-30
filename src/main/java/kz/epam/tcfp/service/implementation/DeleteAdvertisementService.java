package kz.epam.tcfp.service.implementation;

import kz.epam.tcfp.model.Advertisement;
import kz.epam.tcfp.service.PagePath;
import kz.epam.tcfp.service.Service;
import kz.epam.tcfp.dao.AdvertisementDAO;
import kz.epam.tcfp.dao.exception.DAOException;
import kz.epam.tcfp.dao.factory.DAOFactory;
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
public class DeleteAdvertisementService extends PreviousPage implements Service {
    private static final Logger LOGGER = Logger.getLogger(DeleteAdvertisementService.class);
    private final AdvertisementDAO advertisementDAO = DAOFactory.getAdvertisementDAO();

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        savePreviousPage(request);
        HttpSession session = request.getSession(true);
        Long userId = NumberUtil.tryCastToLong(session.getAttribute(ServiceConstants.SESSION_USER_ID));
        Long roleId = NumberUtil.tryCastToLong(session.getAttribute(ServiceConstants.USER_ROLE_ID));
        Long adId = NumberUtil.tryParseLong(request.getParameter(ServiceConstants.ADVERTISEMENT_ID));
        if (userId == null) {
            response.sendRedirect(PagePath.SIGN_IN_SERVICE);
            return;
        }
        if (adId == null) {
            response.sendRedirect(PagePath.ERROR_JSP);
            return;
        }
        Advertisement advertisement = null;
        try {
            advertisement = advertisementDAO.getAdvertisementById(adId);
            if (advertisement.getUserId().equals(userId) || roleId.equals(ServiceConstants.ADMIN_ROLE_ID)) {
                advertisementDAO.deleteAdvertisementByUserIdAndAdId(adId);
            } else {
                LOGGER.warn("Failed to delete advertisement");
                response.sendRedirect(PagePath.ERROR_JSP);
            }
        } catch (DAOException e) {
            LOGGER.warn("Error in DAO while deleting user advertisement", e);
        }
        request.getRequestDispatcher(HOME_SERVICE).forward(request, response);


    }
}
