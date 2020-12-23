package kz.epam.tcfp.dao;

import kz.epam.tcfp.dao.exception.DAOException;
import kz.epam.tcfp.dao.factory.DAOFactory;
import kz.epam.tcfp.model.Advertisement;
import kz.epam.tcfp.model.Customer;
import kz.epam.tcfp.model.PhoneNumber;
import kz.epam.tcfp.model.inputform.SignInInput;
import kz.epam.tcfp.model.inputform.SignUpInput;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

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

    @Test
    public void authenticateCustomerValidInputTest() throws DAOException {
        SignInInput input = new SignInInput();
        input.setLogin("takha");
        input.setPassword("passwordtest123");
        assertTrue(customerDAO.authenticateCustomer(input));

    }

    @Test
    public void authenticateCustomerInvalidLoginInputTest() throws DAOException {
        SignInInput input = new SignInInput();
        input.setLogin("invalidlogin");
        input.setPassword("passwordtest123");
        assertFalse(customerDAO.authenticateCustomer(input));

    }

    @Test
    public void authenticateCustomerInvalidPasswordInputTest() throws DAOException {
        SignInInput input = new SignInInput();
        input.setLogin("takha");
        input.setPassword("someWrongPassword");
        assertFalse(customerDAO.authenticateCustomer(input));

    }

    @Test
    public void authenticateCustomerInvalidLoginAndPasswordInputTest() throws DAOException {
        SignInInput input = new SignInInput();
        input.setLogin("wrongLogin");
        input.setPassword("someWrongPassword");
        assertFalse(customerDAO.authenticateCustomer(input));

    }

    @Test
    public void registerCustomerTest() throws DAOException {
        SignUpInput signInInput = new SignUpInput();
        signInInput.setLogin("testLogin");
        signInInput.setPassword("Testpass123");
        signInInput.setFirstName("Jim");
        signInInput.setLastName("Halpert");
        signInInput.setEmail("jim@office.com");
        signInInput.setPhoneInfo(new PhoneNumber("+77016667799"));
        assertTrue(customerDAO.registerCustomer(signInInput));

    }

    @Test
    public void isLoginTakenTestAlreadyExists() throws DAOException {
        SignUpInput signInInput = new SignUpInput();
        signInInput.setLogin("takha");
        assertTrue(customerDAO.isLoginTaken(signInInput));

    }

    @Test
    public void isLoginTakenTestNotPresent() throws DAOException {
        SignUpInput signInInput = new SignUpInput();
        signInInput.setLogin("nonexistinglogin");
        assertFalse(customerDAO.isLoginTaken(signInInput));

    }

    @Test
    public void isEmailTakenTestAlreadyExists() throws DAOException {
        SignUpInput signInInput = new SignUpInput();
        signInInput.setEmail("talgat@email.com");
        assertTrue(customerDAO.isEmailTaken(signInInput));

    }

    @Test
    public void isEmailTakenTestNotPresent() throws DAOException {
        SignUpInput signInInput = new SignUpInput();
        signInInput.setEmail("nonexistingEmail");
        assertFalse(customerDAO.isLoginTaken(signInInput));

    }

    @Test
    public void isPhoneNumberTakenTestAlreadyExists() throws DAOException {
        SignUpInput signInInput = new SignUpInput();
        signInInput.setPhoneInfo(new PhoneNumber("+77011820844"));
        assertTrue(customerDAO.isPhoneNumberTaken(signInInput));

    }

    @Test
    public void isPhoneNumberTakenTestNotPresent() throws DAOException {
        SignUpInput signInInput = new SignUpInput();
        signInInput.setPhoneInfo(new PhoneNumber("+77777777777"));
        assertFalse(customerDAO.isPhoneNumberTaken(signInInput));

    }

}