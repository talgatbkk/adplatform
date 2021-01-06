package kz.epam.tcfp.service.implementation;

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
public class LogOut extends PreviousPage implements Service {
    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        savePreviousPage(request);
        HttpSession session = request.getSession(true);
        session.removeAttribute(ServiceConstants.SESSION_USER_ID);
        request.getRequestDispatcher(HOME_SERVICE).forward(request, response);

    }
}
