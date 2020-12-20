package kz.epam.tcfp.dao;

import kz.epam.tcfp.dao.exception.DAOException;
import kz.epam.tcfp.model.Customer;
import kz.epam.tcfp.model.PhoneNumber;

import java.util.List;

/**
 * @author Talgat Bekkaliyev
 * @project AdPlatform
 */
public interface CustomerDAO {


    Customer getCustomerById(Integer customerId) throws DAOException;
    List<PhoneNumber> getPhoneNumberByCustomerId(Integer customerId) throws DAOException;

}
