package kz.epam.tcfp.service;

import junit.framework.TestCase;
import kz.epam.tcfp.model.Advertisement;
import kz.epam.tcfp.service.factory.ServiceFactory;
import org.junit.Test;

import java.util.List;

/**
 * @author Talgat Bekkaliyev
 * @project AdPlatform
 */
public class AdvertisementServiceTest {
    private static final CustomerService service = ServiceFactory.getCustomerService();


    @Test
    public void getAdvertisementTest() {
        AdvertisementService service = ServiceFactory.getAdvertisementService();
        List<Advertisement> advertisements = service.getAdvertisement(1);
//        advertisements.forEach(advertisement -> System.out.println());
        for (Advertisement ad:advertisements){
            System.out.println(ad);
        }
    }

}