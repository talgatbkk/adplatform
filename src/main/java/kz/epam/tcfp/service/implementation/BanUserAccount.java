package kz.epam.tcfp.service.implementation;

import kz.epam.tcfp.service.Service;
import kz.epam.tcfp.dao.UserDAO;
import kz.epam.tcfp.dao.exception.DAOException;
import kz.epam.tcfp.dao.factory.DAOFactory;
import kz.epam.tcfp.service.util.PreviousPage;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 * @author Talgat Bekkaliyev
 * @project AdPlatform
 */
public class BanUserAccount extends PreviousPage implements Service {
    private static final String SESSION_USER_ID = "userId";
    private static final Integer ADMIN_ROLE_ID = 1;
    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        savePreviousPage(request);
        HttpSession session = request.getSession(true);
        Integer userId = (Integer) session.getAttribute(SESSION_USER_ID);
        Integer userRoleId = (Integer) session.getAttribute("role_id");

        Integer userIdToBan = null;
        String userIdInputToBan = request.getParameter("ban_user_id");
        if (userIdInputToBan != null && !userIdInputToBan.isEmpty()){
            userIdToBan = Integer.parseInt(userIdInputToBan);
        }
        UserDAO userDAO = DAOFactory.getUserDAO();

        if (userRoleId == ADMIN_ROLE_ID) {
            try {
                if (userDAO.banUserAccount(userIdToBan)) {
                    request.getRequestDispatcher("/home").forward(request, response);
                    return;
                } else {
                    System.out.println("Error");
                }
            } catch (DAOException e) {
                e.printStackTrace();
            }

        } else {

            request.getRequestDispatcher("/error").forward(request, response);

        }
    }
}
