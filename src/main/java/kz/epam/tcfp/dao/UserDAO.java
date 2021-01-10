package kz.epam.tcfp.dao;

import kz.epam.tcfp.dao.exception.DAOException;
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


    User getUserById(Long userId) throws DAOException;
    List<PhoneNumber> getPhoneNumberByUserId(Long customerId) throws DAOException;
    User getUserIdByLogin(String login) throws DAOException;
    Boolean authenticateUser(SignInInput input) throws DAOException;
    Boolean authenticateUserById(SignUpInput input) throws DAOException;
    Boolean registerUser(SignUpInput input) throws DAOException;
    Boolean isLoginTaken(SignUpInput input) throws DAOException;
    Boolean isEmailTaken(SignUpInput input) throws DAOException;
    Boolean isPhoneNumberTaken(SignUpInput input) throws DAOException;
    Boolean deleteUserAccount(Long userId) throws DAOException;
    Boolean isUserBanned(Long userId) throws DAOException;
    Boolean banUserAccount(Long userId) throws DAOException;
    Boolean unbanUserAccount(Long userId) throws DAOException;
    Boolean updateUserFirstName(SignUpInput editedUser) throws DAOException;
    Boolean updateUserLastName(SignUpInput editedUser) throws DAOException;
    Boolean updateUserEmail(SignUpInput editUser) throws DAOException;
    Boolean updateUserPassword(SignUpInput userWithNewPassword) throws DAOException;

}
