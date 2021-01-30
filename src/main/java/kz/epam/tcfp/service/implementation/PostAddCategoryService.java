package kz.epam.tcfp.service.implementation;

import kz.epam.tcfp.dao.AdvertisementDAO;
import kz.epam.tcfp.dao.CategoryDAO;
import kz.epam.tcfp.dao.exception.DAOException;
import kz.epam.tcfp.dao.factory.DAOFactory;
import kz.epam.tcfp.model.Category;
import kz.epam.tcfp.service.PagePath;
import kz.epam.tcfp.service.Service;
import kz.epam.tcfp.service.util.LanguageUtil;
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
public class PostAddCategoryService extends PreviousPage implements Service {
    private static final Logger LOGGER = Logger.getLogger(PostAddCategoryService.class);
    private final AdvertisementDAO advertisementDAO = DAOFactory.getAdvertisementDAO();
    private final CategoryDAO categoryDAO = DAOFactory.getCategoryDAO();

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        savePreviousPage(request);
        HttpSession session = request.getSession(true);
        String localLanguage = null;
        if (!LanguageUtil.validateLanguageCode(session.getAttribute(ServiceConstants.LOCAL_LANGUAGE))) {
            session.setAttribute(ServiceConstants.LOCAL_LANGUAGE, ServiceConstants.RUSSIAN_LANGUAGE);
            localLanguage = ServiceConstants.RUSSIAN_LANGUAGE;
        } else {
            localLanguage = (String) session.getAttribute(ServiceConstants.LOCAL_LANGUAGE);
        }

        Long userId = NumberUtil.tryCastToLong(session.getAttribute(ServiceConstants.SESSION_USER_ID));
        Long roleId = NumberUtil.tryCastToLong(session.getAttribute(ServiceConstants.USER_ROLE_ID));
        if (userId == null || !roleId.equals(ServiceConstants.ADMIN_ROLE_ID)) {
            request.getRequestDispatcher(PagePath.SIGN_IN_SERVICE).forward(request, response);
            return;
        }
        String categoryName = request.getParameter(ServiceConstants.NEW_CATEGORY_NAME);

        if (categoryName == null || categoryName.isEmpty()) {
            response.sendRedirect(PagePath.ERROR_JSP);
            return;
        }
        Category category = new Category();
        category.setCategoryName(categoryName);
        try {
            Long languageId = advertisementDAO.getLanguageIdByName(localLanguage);
            category.setLanguageId(languageId);
            if (!categoryDAO.postCategory(category)) {
                response.sendRedirect(PagePath.ERROR_JSP);
                return;
            }
        } catch (DAOException e) {
            LOGGER.warn("Error in DAO while posting an advertisement", e);
        }
        response.sendRedirect(PagePath.ADMIN_ADD_CATEGORY);

    }
}
