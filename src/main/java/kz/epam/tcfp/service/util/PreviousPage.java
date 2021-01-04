package kz.epam.tcfp.service.util;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 * @author Talgat Bekkaliyev
 * @project AdPlatform
 */
public class PreviousPage {

    protected void savePreviousPage(HttpServletRequest request) {
        HttpSession session = request.getSession(true);
        StringBuilder requestURL = new StringBuilder(request.getRequestURL().toString());
        String queryString = request.getQueryString();

        if (queryString == null) {
            session.setAttribute("prev_page", requestURL.toString());
        } else {
            session.setAttribute("prev_page", requestURL.append('?').append(queryString).toString());
        }

    }

    protected void reloadPreviousPage(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession(true);
        String previousPageUrl = (String) session.getAttribute("prev_page");
        if (previousPageUrl == null || previousPageUrl.isEmpty()){
            response.sendRedirect("/home");
        } else {
            response.sendRedirect(previousPageUrl);
        }
    }


}
