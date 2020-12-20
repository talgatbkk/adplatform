package kz.epam.tcfp.dao;

import kz.epam.tcfp.dao.exception.DAOException;
import kz.epam.tcfp.dao.factory.DAOFactory;
import kz.epam.tcfp.model.Advertisement;
import kz.epam.tcfp.model.Customer;
import kz.epam.tcfp.model.PhoneNumber;
import org.junit.Test;

import java.util.List;

/**
 * @author Talgat Bekkaliyev
 * @project AdPlatform
 */
public class CustomerDAOTest {

    private static final CustomerDAO customerDAO = DAOFactory.getCustomerDAO();

    @Test
    public void getCustomerByIdTest() throws DAOException {
        Integer customerId = 1;
        Customer customer = customerDAO.getCustomerById(customerId);
        System.out.println(customer.toString());

    }



    @Test
    public void getPhoneNumberByCustomerIdTest() throws DAOException {
        Integer customerId = 1;
        List<PhoneNumber> phoneNumbers = customerDAO.getPhoneNumberByCustomerId(customerId);
        System.out.println(phoneNumbers.toString());

    }

}