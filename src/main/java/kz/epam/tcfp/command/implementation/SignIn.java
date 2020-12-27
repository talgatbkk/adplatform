package kz.epam.tcfp.command.implementation;

import kz.epam.tcfp.command.Command;
import kz.epam.tcfp.dao.CustomerDAO;
import kz.epam.tcfp.dao.exception.DAOException;
import kz.epam.tcfp.dao.factory.DAOFactory;
import kz.epam.tcfp.model.Customer;
import kz.epam.tcfp.model.inputform.SignInInput;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 * @author Talgat Bekkaliyev
 * @project AdPlatform
 */
public class SignIn implements Command {

    private static final String REDIRECT_TO_HOME_PAGE = "/home?forward_page=customer_profile";
    private static final String SESSION_USER_ID = "userId";
    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        session.setAttribute("local", "ru");
        SignInInput signInInput = new SignInInput();
        signInInput.setLogin(request.getParameter("login"));
        signInInput.setPassword(request.getParameter("password"));
        CustomerDAO customerDAO = DAOFactory.getCustomerDAO();
        Customer customer = null;
        try {
            if (!customerDAO.authenticateCustomer(signInInput)){
                if (signInInput.getLogin() != null) {
                    request.setAttribute("incorrect_auth", true);
                }
                request.getRequestDispatcher("/signin").forward(request,response);
                return;
            } else {
                    customer = customerDAO.getCustomerIdByLogin(signInInput.getLogin());
            }
        } catch (DAOException e) {
            e.printStackTrace();
        }

        request.setAttribute("incorrect_auth", false);
        session.setAttribute(SESSION_USER_ID, customer.getUserId());
        response.sendRedirect(REDIRECT_TO_HOME_PAGE);
    }
}
