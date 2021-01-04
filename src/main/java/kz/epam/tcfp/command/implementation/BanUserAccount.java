package kz.epam.tcfp.command.implementation;

import kz.epam.tcfp.command.Command;
import kz.epam.tcfp.dao.UserDAO;
import kz.epam.tcfp.dao.exception.DAOException;
import kz.epam.tcfp.dao.factory.DAOFactory;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 * @author Talgat Bekkaliyev
 * @project AdPlatform
 */
public class BanUserAccount implements Command {
    private static final String SESSION_USER_ID = "userId";
    private static final Integer ADMIN_ROLE_ID = 1;
    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
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
                    response.sendRedirect("home?forward_page=get_ads");
                    return;
                } else {
                    System.out.println("Error");
                }
            } catch (DAOException e) {
                e.printStackTrace();
            }

        } else {

            System.out.println("Error");
        }



        response.sendRedirect("home?forward_page=get_ads");
    }
}
