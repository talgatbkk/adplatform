package kz.epam.tcfp.dao;

import kz.epam.tcfp.dao.exception.DAOException;
import kz.epam.tcfp.dao.factory.DAOFactory;
import kz.epam.tcfp.model.PhoneNumber;
import kz.epam.tcfp.model.User;
import kz.epam.tcfp.model.inputform.SignInInput;
import kz.epam.tcfp.model.inputform.SignUpInput;
import kz.epam.tcfp.service.util.Encryption;
import org.junit.Test;


import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * @author Talgat Bekkaliyev
 * @project AdPlatform
 */
public class UserDAOTest {

    private static final UserDAO USER_DAO = DAOFactory.getUserDAO();

    @Test
    public void getCustomerByIdTest() throws DAOException {
        Long customerId = 1L;
        User user = USER_DAO.getUserById(customerId);
        assertTrue(user.getUserId().equals(customerId));
    }



    @Test
    public void getPhoneNumberByCustomerIdTest() throws DAOException {
        Long customerId = 1L;
        List<PhoneNumber> phoneNumbers = USER_DAO.getPhoneNumberByUserId(customerId);
        phoneNumbers.get(0).getPhoneNumber().equals("+77011820844");
        phoneNumbers.get(1).getPhoneNumber().equals("+77471820844");
    }

    @Test
    public void authenticateCustomerValidInputTest() throws DAOException {
        SignInInput input = new SignInInput();
        input.setLogin("user");
        input.setPassword(Encryption.encrypt("user1234"));
        assertTrue(USER_DAO.authenticateUser(input));
    }

    @Test
    public void authenticateCustomerInvalidLoginInputTest() throws DAOException {
        SignInInput input = new SignInInput();
        input.setLogin("invalidlogin");
        input.setPassword(Encryption.encrypt("admin"));
        assertFalse(USER_DAO.authenticateUser(input));

    }

    @Test
    public void authenticateCustomerInvalidPasswordInputTest() throws DAOException {
        SignInInput input = new SignInInput();
        input.setLogin("user");
        input.setPassword(Encryption.encrypt("someWrongPassword"));
        assertFalse(USER_DAO.authenticateUser(input));

    }

    @Test
    public void authenticateCustomerInvalidLoginAndPasswordInputTest() throws DAOException {
        SignInInput input = new SignInInput();
        input.setLogin("wrongLogin");
        input.setPassword(Encryption.encrypt("user1234"));
        assertFalse(USER_DAO.authenticateUser(input));

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
        assertTrue(USER_DAO.registerUser(signInInput));

    }

    @Test
    public void isLoginTakenTestAlreadyExists() throws DAOException {
        SignUpInput signInInput = new SignUpInput();
        signInInput.setLogin("user");
        assertTrue(USER_DAO.isLoginTaken(signInInput));

    }

    @Test
    public void isLoginTakenTestNotPresent() throws DAOException {
        SignUpInput signInInput = new SignUpInput();
        signInInput.setLogin("nonexistinglogin");
        assertFalse(USER_DAO.isLoginTaken(signInInput));

    }

    @Test
    public void isEmailTakenTestAlreadyExists() throws DAOException {
        SignUpInput signInInput = new SignUpInput();
        signInInput.setEmail("jim@email.com");
        assertTrue(USER_DAO.isEmailTaken(signInInput));

    }

    @Test
    public void isEmailTakenTestNotPresent() throws DAOException {
        SignUpInput signInInput = new SignUpInput();
        signInInput.setEmail("nonexistingEmail");
        assertFalse(USER_DAO.isLoginTaken(signInInput));

    }

    @Test
    public void isPhoneNumberTakenTestAlreadyExists() throws DAOException {
        SignUpInput signInInput = new SignUpInput();
        signInInput.setPhoneInfo(new PhoneNumber("+77011820844"));
        assertTrue(USER_DAO.isPhoneNumberTaken(signInInput));

    }

    @Test
    public void isPhoneNumberTakenTestNotPresent() throws DAOException {
        SignUpInput signInInput = new SignUpInput();
        signInInput.setPhoneInfo(new PhoneNumber("+77777777777"));
        assertFalse(USER_DAO.isPhoneNumberTaken(signInInput));

    }

}