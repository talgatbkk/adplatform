package kz.epam.tcfp.service.implementation;

import kz.epam.tcfp.service.Service;
import kz.epam.tcfp.dao.UserDAO;
import kz.epam.tcfp.dao.exception.DAOException;
import kz.epam.tcfp.dao.factory.DAOFactory;
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
public class DeleteUserAccount extends PreviousPage implements Service {

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        savePreviousPage(request);
        HttpSession session = request.getSession(true);
        Integer userId = (Integer) session.getAttribute(ServiceConstants.SESSION_USER_ID);

        Integer userIdToDeleted = null;
        String userIdInputToDeleted = request.getParameter(ServiceConstants.USER_ID_TO_BE_DELETED);
        if (userIdInputToDeleted != null && !userIdInputToDeleted.isEmpty()){
            userIdToDeleted = Integer.parseInt(userIdInputToDeleted);
        }
        UserDAO userDAO = DAOFactory.getUserDAO();

        if (userIdToDeleted == userId) {
            try {
                if (userDAO.deleteUserAccount(userId)) {
                    session.removeAttribute(ServiceConstants.SESSION_USER_ID);
                    response.sendRedirect("/home");
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
        response.sendRedirect("/home");
    }
}

