package kz.epam.tcfp.service.implementation;

import kz.epam.tcfp.dao.UserDAO;
import kz.epam.tcfp.dao.exception.DAOException;
import kz.epam.tcfp.dao.factory.DAOFactory;
import kz.epam.tcfp.model.User;
import kz.epam.tcfp.service.UserService;

/**
 * @author Talgat Bekkaliyev
 * @project AdPlatform
 */
public class UserServiceImpl implements UserService {
//    private static final Logger logger = Logger.getLogger(CustomerServiceImpl.class);
    private final UserDAO userDAO = DAOFactory.getUserDAO();
    @Override
    public User getUser(Integer userId) {
        try {
            return userDAO.getUserById(userId);
        } catch (DAOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
