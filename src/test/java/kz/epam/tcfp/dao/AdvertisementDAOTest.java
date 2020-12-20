package kz.epam.tcfp.dao;

import kz.epam.tcfp.dao.exception.DAOException;
import kz.epam.tcfp.dao.factory.DAOFactory;
import kz.epam.tcfp.model.Advertisement;
import org.junit.Test;

import java.util.List;

/**
 * @author Talgat Bekkaliyev
 * @project AdPlatform
 */
public class AdvertisementDAOTest {

    private static final AdvertisementDAO advertisementDAO = DAOFactory.getAdvertisementDAO();

    @Test
    public void getAdByCustomerIdTest() throws DAOException {
        Integer customerId = 1;
        List<Advertisement> allAdvertisements = advertisementDAO.getAdvertisementByCustomerId(customerId);
        System.out.println(allAdvertisements.toString());

    }


    @Test
    public void getAdByIdTest() throws DAOException {
        Integer adId = 1;
        Advertisement advertisement = advertisementDAO.getAdvertisementById(adId);
        System.out.println(advertisement.toString());

    }
}
