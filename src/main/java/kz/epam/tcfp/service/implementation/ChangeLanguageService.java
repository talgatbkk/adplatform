package kz.epam.tcfp.service.implementation;

import kz.epam.tcfp.service.PagePath;
import kz.epam.tcfp.service.Service;
import kz.epam.tcfp.service.util.PreviousPage;
import kz.epam.tcfp.service.util.ServiceConstants;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 * @author Talgat Bekkaliyev
 * @project AdPlatform
 */
public class ChangeLanguageService extends PreviousPage implements Service {

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession(true);
        String pickedLanguage = request.getParameter(ServiceConstants.LANGUAGE_CODE);
        if (pickedLanguage == null) {
            response.sendRedirect(PagePath.ERROR_JSP);
            return;
        }
        session.setAttribute(ServiceConstants.LOCAL_LANGUAGE, pickedLanguage);
        reloadPreviousPage(request, response);
    }
}
