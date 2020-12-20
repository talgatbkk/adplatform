package kz.epam.tcfp.service.implementation;

import kz.epam.tcfp.dao.CustomerDAO;
import kz.epam.tcfp.dao.exception.DAOException;
import kz.epam.tcfp.dao.factory.DAOFactory;
import kz.epam.tcfp.model.Customer;
import kz.epam.tcfp.service.CustomerService;
import org.apache.log4j.Logger;

/**
 * @author Talgat Bekkaliyev
 * @project AdPlatform
 */
public class CustomerServiceImpl implements CustomerService {
//    private static final Logger logger = Logger.getLogger(CustomerServiceImpl.class);
    private final CustomerDAO customerDAO = DAOFactory.getCustomerDAO();
    @Override
    public Customer getCustomer(Integer customerId) {
        try {
            return customerDAO.getCustomerById(customerId);
        } catch (DAOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
