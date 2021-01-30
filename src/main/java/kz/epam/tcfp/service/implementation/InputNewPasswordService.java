package kz.epam.tcfp.service.implementation;

import kz.epam.tcfp.service.PagePath;
import kz.epam.tcfp.service.Service;
import kz.epam.tcfp.service.util.NumberUtil;
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
public class InputNewPasswordService extends PreviousPage implements Service {

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        savePreviousPage(request);
        HttpSession session = request.getSession();
        Long userId = NumberUtil.tryCastToLong(session.getAttribute(ServiceConstants.SESSION_USER_ID));
        if (userId == null) {
            request.setAttribute(ServiceConstants.INCORRECT_AUTHORIZATION, true);
            request.getRequestDispatcher(PagePath.SIGN_IN).forward(request, response);
            return;
        }
        request.setAttribute(ServiceConstants.INCORRECT_AUTHORIZATION, false);
        request.getRequestDispatcher(PagePath.INPUT_NEW_PASSWORD_JSP).forward(request, response);
    }
}
