package kz.epam.tcfp.dao;

import kz.epam.tcfp.dao.exception.DAOException;
import kz.epam.tcfp.dao.factory.DAOFactory;
import kz.epam.tcfp.model.Advertisement;
import kz.epam.tcfp.model.Location;
import kz.epam.tcfp.service.util.ServiceConstants;
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
        Long customerId = 1L;
        List<Advertisement> allAdvertisements = advertisementDAO.getAdvertisementByUserId(customerId);
        System.out.println(allAdvertisements.toString());

    }


    @Test
    public void getAdByIdTest() throws DAOException {
        Long adId = 1L;
        Advertisement advertisement = advertisementDAO.getAdvertisementById(adId);
        System.out.println(advertisement.toString());

    }

    @Test
    public void getLocationNamesByIdTest() throws DAOException {
        Long locationId = 2L;
        Location location = advertisementDAO.getLocationNamesById(locationId, ServiceConstants.RUSSIAN_LANGUAGE);
        System.out.println(location.toString());

    }
}
