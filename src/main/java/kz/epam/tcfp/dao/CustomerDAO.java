package kz.epam.tcfp.dao;

import kz.epam.tcfp.command.implementation.SignIn;
import kz.epam.tcfp.dao.exception.DAOException;
import kz.epam.tcfp.model.Customer;
import kz.epam.tcfp.model.PhoneNumber;
import kz.epam.tcfp.model.inputform.SignInInput;
import kz.epam.tcfp.model.inputform.SignUpInput;

import java.util.List;

/**
 * @author Talgat Bekkaliyev
 * @project AdPlatform
 */
public interface CustomerDAO {


    Customer getCustomerById(Integer customerId) throws DAOException;
    List<PhoneNumber> getPhoneNumberByCustomerId(Integer customerId) throws DAOException;
    Customer getCustomerIdByLogin(String login) throws DAOException;
    Boolean authenticateCustomer(SignInInput input) throws DAOException;
    Boolean registerCustomer(SignUpInput input) throws DAOException;
    Boolean isLoginTaken(SignUpInput input) throws DAOException;
    Boolean isEmailTaken(SignUpInput input) throws DAOException;
    Boolean isPhoneNumberTaken(SignUpInput input) throws DAOException;

}
