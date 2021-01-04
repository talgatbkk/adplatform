package kz.epam.tcfp.service;

import kz.epam.tcfp.model.Customer;
import kz.epam.tcfp.model.User;

/**
 * @author Talgat Bekkaliyev
 * @project AdPlatform
 */
public interface UserService {

    User getUser(Integer userId);
}
