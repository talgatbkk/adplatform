package kz.epam.tcfp.service;

import kz.epam.tcfp.model.Customer;

/**
 * @author Talgat Bekkaliyev
 * @project AdPlatform
 */
public interface CustomerService {

    Customer getCustomer(Integer customerId);
}
