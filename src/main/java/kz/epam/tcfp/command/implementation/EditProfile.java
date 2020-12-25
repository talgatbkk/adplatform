package kz.epam.tcfp.command.implementation;

import kz.epam.tcfp.command.Command;
import kz.epam.tcfp.dao.CustomerDAO;
import kz.epam.tcfp.dao.exception.DAOException;
import kz.epam.tcfp.dao.factory.DAOFactory;
import kz.epam.tcfp.model.Customer;
import kz.epam.tcfp.model.PhoneNumber;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;

/**
 * @author Talgat Bekkaliyev
 * @project AdPlatform
 */
public class EditProfile implements Command {
    private static final String SESSION_USER_ID = "userId";

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        Integer userId = (Integer) session.getAttribute(SESSION_USER_ID);
        CustomerDAO customerDAO = DAOFactory.getCustomerDAO();
        Customer customer = null;
        try {
            customer= customerDAO.getCustomerById(userId);
            List<PhoneNumber> phoneNumbers = customerDAO.getPhoneNumberByCustomerId(userId);
            customer.setPhoneNumbers(phoneNumbers);
            if (customer == null) {
                request.setAttribute("incorrect_auth", true);
                request.getRequestDispatcher("/signin").forward(request, response);
                return;
            }
        } catch (DAOException e) {
            e.printStackTrace();
        }

        request.setAttribute("incorrect_auth", false);
        request.setAttribute("customer", customer);
        session = request.getSession(true);
        request.getRequestDispatcher("/profile").forward(request, response);
    }
}
