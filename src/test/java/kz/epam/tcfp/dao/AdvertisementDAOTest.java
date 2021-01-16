package kz.epam.tcfp.dao;

import kz.epam.tcfp.dao.exception.DAOException;
import kz.epam.tcfp.dao.factory.DAOFactory;
import kz.epam.tcfp.model.Advertisement;
import kz.epam.tcfp.model.Category;
import kz.epam.tcfp.model.Location;
import kz.epam.tcfp.service.util.ServiceConstants;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.*;

/**
 * @author Talgat Bekkaliyev
 * @project AdPlatform
 */
public class AdvertisementDAOTest {

    private static final AdvertisementDAO advertisementDAO = DAOFactory.getAdvertisementDAO();

    @Test
    public void postAdvertisementTest() throws DAOException {
        Advertisement advertisementToBePosted = new Advertisement();
        Long userId = 1L;
        Integer pageNumber = 1;
        advertisementToBePosted.setUserId(userId);
        advertisementToBePosted.setLocation(new Location(1L));
        advertisementToBePosted.setCategory(new Category(1L));
        advertisementToBePosted.setTitle("Test title");
        advertisementToBePosted.setDescription("Test desc");
        advertisementToBePosted.setPrice(120);
        assertTrue(advertisementDAO.postAdvertisement(advertisementToBePosted));
        List<Advertisement> recentAdvertisementsOfTheUser = advertisementDAO.getAdvertisementByUserId(userId, pageNumber);
        Advertisement theMostRecentAdvertisement = recentAdvertisementsOfTheUser.get(0);
        assertTrue(theMostRecentAdvertisement.equals(advertisementToBePosted));

    }

    @Test
    public void getAdvertisementByUserIdTest() throws DAOException {
        Long customerId = 1L;
        List<Advertisement> allAdvertisementsOfTheUser = advertisementDAO.getAdvertisementByUserId(customerId, 1);
        for (Advertisement advertisement:allAdvertisementsOfTheUser) {
            assertTrue(advertisement.getUserId().equals(customerId));
        }

    }


    @Test
    public void getAdByIdTest() throws DAOException {
        Long adId = 1L;
        Advertisement advertisement = advertisementDAO.getAdvertisementById(adId);
        assertTrue(advertisement.getAdId().equals(adId));
        assertFalse(advertisement.getDescription().isEmpty());
        assertFalse(advertisement.getTitle().isEmpty());
        assertNotNull(advertisement.getUserId());
        assertNotNull(advertisement.getCategory());
        assertNotNull(advertisement.getLocation());
        assertNotNull(advertisement.getPostedDate());
    }

    @Test
    public void searchAdvertisementsByCategoryTest() throws DAOException {
        Long categoryId = 1L;
        Integer pageNumber = 1;
        List<Advertisement> advertisementsWithGivenCategory = advertisementDAO.searchAdvertisementsByCategory(categoryId, pageNumber);
        for (Advertisement advertisement:advertisementsWithGivenCategory) {
            assertTrue(advertisement.getCategory().getCategoryId().equals(categoryId));
        }
    }


    @Test
    public void searchAdvertisementsByCategoryAndLocationTest() throws DAOException {
        Long categoryId = 1L;
        Long locationId = 2L;
        Integer pageNumber = 1;
        List<Advertisement> advertisementsWithGivenCategoryAndLocation = advertisementDAO.searchAdvertisementsByCategoryAndLocation(categoryId, locationId, pageNumber);
        for (Advertisement advertisement:advertisementsWithGivenCategoryAndLocation) {
            assertTrue(advertisement.getCategory().getCategoryId().equals(categoryId));
            assertTrue(advertisement.getLocation().getId().equals(locationId));
        }
    }

    @Test
    public void searchAdvertisementsByLocationTest() throws DAOException {
        Long locationId = 2L;
        Integer pageNumber = 1;
        List<Advertisement> advertisementsWithGivenLocation = advertisementDAO.searchAdvertisementsByLocation(locationId, pageNumber);
        for (Advertisement advertisement:advertisementsWithGivenLocation) {
            assertTrue(advertisement.getLocation().getId().equals(locationId));
        }
    }

    @Test
    public void searchAdvertisementsByDescriptionAndCategoryAndLocationTest() throws DAOException {
        Long categoryId = 1L;
        Long locationId = 2L;
        String searchInput = "продавец";
        Integer pageNumber = 1;
        List<Advertisement> advertisementsWithGivenCategoryAndLocationAndSearchInput = advertisementDAO.searchAdvertisementsByDescriptionAndCategoryAndLocation(searchInput, categoryId, locationId, pageNumber);
        for (Advertisement advertisement:advertisementsWithGivenCategoryAndLocationAndSearchInput) {
            assertTrue(advertisement.getCategory().getCategoryId().equals(categoryId));
            assertTrue(advertisement.getLocation().getId().equals(locationId));
            assertTrue(advertisement.getTitle().toLowerCase().contains(searchInput.toLowerCase())
                    || advertisement.getDescription().toLowerCase().contains(searchInput.toLowerCase()));
        }
    }

    @Test
    public void searchAdvertisementsByDescriptionAndCategoryTest() throws DAOException {
        Long categoryId = 1L;
        String searchInput = "продавец";
        Integer pageNumber = 1;
        List<Advertisement> advertisementsWithGivenCategoryAndLocationAndSearchInput = advertisementDAO.searchAdvertisementsByDescriptionAndCategory(searchInput, categoryId, pageNumber);
        for (Advertisement advertisement:advertisementsWithGivenCategoryAndLocationAndSearchInput) {
            assertTrue(advertisement.getCategory().getCategoryId().equals(categoryId));
            assertTrue(advertisement.getTitle().toLowerCase().contains(searchInput.toLowerCase())
                    || advertisement.getDescription().toLowerCase().contains(searchInput.toLowerCase()));
        }
    }

    @Test
    public void searchAdvertisementsByDescriptionAndLocationTest() throws DAOException {
        Long locationId = 2L;
        String searchInput = "продавец";
        Integer pageNumber = 1;
        List<Advertisement> advertisementsWithGivenCategoryAndLocationAndSearchInput = advertisementDAO.searchAdvertisementsByDescriptionAndLocation(searchInput, locationId, pageNumber);
        for (Advertisement advertisement:advertisementsWithGivenCategoryAndLocationAndSearchInput) {
            assertTrue(advertisement.getLocation().getId().equals(locationId));
            assertTrue(advertisement.getTitle().toLowerCase().contains(searchInput.toLowerCase())
                    || advertisement.getDescription().toLowerCase().contains(searchInput.toLowerCase()));
        }
    }

    @Test
    public void searchAdvertisementsByDescriptionTest() throws DAOException {
        String searchInput = "продавец";
        Integer pageNumber = 1;
        List<Advertisement> advertisementsWithGivenCategoryAndLocationAndSearchInput = advertisementDAO.searchAdvertisementsByDescription(searchInput, pageNumber);
        for (Advertisement advertisement:advertisementsWithGivenCategoryAndLocationAndSearchInput) {
            assertTrue(advertisement.getTitle().toLowerCase().contains(searchInput.toLowerCase())
                    || advertisement.getDescription().toLowerCase().contains(searchInput.toLowerCase()));
        }
    }

}
