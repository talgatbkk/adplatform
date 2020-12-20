package kz.epam.tcfp.service;

import kz.epam.tcfp.model.Customer;
import kz.epam.tcfp.service.factory.ServiceFactory;
import org.junit.Test;

/**
 * @author Talgat Bekkaliyev
 * @project AdPlatform
 */
public class CustomerServiceTest{
    private static final CustomerService service = ServiceFactory.getCustomerService();


    @Test
    public void getCustomerTest() {
        Integer customerId = 1;
        Customer customer = service.getCustomer(customerId);
        System.out.println(customer.toString());

    }

}