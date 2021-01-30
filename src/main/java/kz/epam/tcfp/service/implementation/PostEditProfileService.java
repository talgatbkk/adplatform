package kz.epam.tcfp.service.implementation;

import kz.epam.tcfp.dao.UserDAO;
import kz.epam.tcfp.dao.exception.DAOException;
import kz.epam.tcfp.dao.factory.DAOFactory;
import kz.epam.tcfp.model.PhoneNumber;
import kz.epam.tcfp.model.User;
import kz.epam.tcfp.model.inputform.SignUpInput;
import kz.epam.tcfp.service.PagePath;
import kz.epam.tcfp.service.Service;
import kz.epam.tcfp.service.util.NumberUtil;
import kz.epam.tcfp.service.util.PreviousPage;
import kz.epam.tcfp.service.util.ServiceConstants;
import org.apache.log4j.Logger;

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
public class PostEditProfileService extends PreviousPage implements Service {
    private static final Logger LOGGER = Logger.getLogger(PostEditProfileService.class);
    public static final String EMAIL_TAKEN = "email_taken";
    private final UserDAO userDAO = DAOFactory.getUserDAO();
    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        savePreviousPage(request);
        HttpSession session = request.getSession();
        Long userId = NumberUtil.tryCastToLong(session.getAttribute(ServiceConstants.SESSION_USER_ID));
        if (userId == null) {
            request.getRequestDispatcher(PagePath.SIGN_IN_SERVICE).forward(request, response);
            return;
        }
        SignUpInput editedUser = buildEditedUser(request);
        editedUser.setUserId(userId);
        User user = null;
        try {
            if (editedUser.getFirstName() != null && !editedUser.getFirstName().isEmpty()) {
                userDAO.updateUserFirstName(editedUser); }
            if (editedUser.getLastName() != null && !editedUser.getLastName().isEmpty()) {
                userDAO.updateUserLastName(editedUser); }
            if (editedUser.getEmail() != null && !editedUser.getEmail().isEmpty()) {
                if (Boolean.TRUE.equals(userDAO.isEmailTaken(editedUser))) {
                    user= userDAO.getUserById(userId);
                    List<PhoneNumber> phoneNumbers = userDAO.getPhoneNumberByUserId(userId);
                    user.setPhoneNumbers(phoneNumbers);
                    request.setAttribute(ServiceConstants.USER, user);
                    request.setAttribute(EMAIL_TAKEN, true);
                    request.getRequestDispatcher(PagePath.USER_PROFILE_EDIT_JSP).forward(request, response);
                    return;
                }
                userDAO.updateUserEmail(editedUser);
            }
        } catch (DAOException e) {
            LOGGER.warn("Error in DAO while signing up a user", e);
        }
        response.sendRedirect(PagePath.USER_PROFILE_EDIT_SERVICE);
    }

    private SignUpInput buildEditedUser(HttpServletRequest request) {
        SignUpInput editedUser = new SignUpInput();
        editedUser.setFirstName(request.getParameter(ServiceConstants.NEW_USER_FIRST_NAME));
        editedUser.setLastName(request.getParameter(ServiceConstants.NEW_USER_LAST_NAME));
        editedUser.setEmail(request.getParameter(ServiceConstants.NEW_USER_EMAIL));
        return editedUser;
    }

}

