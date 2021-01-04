package kz.epam.tcfp.dao;

import kz.epam.tcfp.command.implementation.SignIn;
import kz.epam.tcfp.dao.exception.DAOException;
import kz.epam.tcfp.model.Customer;
import kz.epam.tcfp.model.PhoneNumber;
import kz.epam.tcfp.model.User;
import kz.epam.tcfp.model.inputform.SignInInput;
import kz.epam.tcfp.model.inputform.SignUpInput;

import java.util.List;

/**
 * @author Talgat Bekkaliyev
 * @project AdPlatform
 */
public interface UserDAO {


    User getUserById(Integer userId) throws DAOException;
    List<PhoneNumber> getPhoneNumberByUserId(Integer customerId) throws DAOException;
    User getUserIdByLogin(String login) throws DAOException;
    Boolean authenticateUser(SignInInput input) throws DAOException;
    Boolean registerUser(SignUpInput input) throws DAOException;
    Boolean isLoginTaken(SignUpInput input) throws DAOException;
    Boolean isEmailTaken(SignUpInput input) throws DAOException;
    Boolean isPhoneNumberTaken(SignUpInput input) throws DAOException;
    Boolean deleteUserAccount(Integer userId) throws DAOException;
    Boolean isUserBanned(Integer userId) throws DAOException;
    Boolean banUserAccount(Integer userId) throws DAOException;

}
