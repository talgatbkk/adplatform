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
    public static final String HOME_SERVICE = "/home";
    public static final Character QUESTION_MARK = '?';
    public static final Character EQUAL_SIGN = '=';

    protected void savePreviousPage(HttpServletRequest request) {
        HttpSession session = request.getSession(true);
        StringBuilder requestURL = new StringBuilder(request.getRequestURL().toString());
        String queryString = request.getQueryString();

        if (queryString == null) {
            session.setAttribute(ServiceConstants.PREVIOUS_PAGE, requestURL.toString());
        } else {
            session.setAttribute(ServiceConstants.PREVIOUS_PAGE, requestURL.append(QUESTION_MARK)
                                                                .append(queryString)
                                                                .toString());
        }

    }

    protected void reloadPreviousPage(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession(true);
        String previousPageUrl = (String) session.getAttribute(ServiceConstants.PREVIOUS_PAGE);
        if (previousPageUrl == null || previousPageUrl.isEmpty()){
            response.sendRedirect(HOME_SERVICE);
        } else {
            response.sendRedirect(previousPageUrl);
        }
    }


}
