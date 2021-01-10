package kz.epam.tcfp.dao;

import kz.epam.tcfp.dao.exception.DAOException;
import kz.epam.tcfp.model.Advertisement;
import kz.epam.tcfp.model.Category;
import kz.epam.tcfp.model.Comment;
import kz.epam.tcfp.model.Location;

import java.util.List;

/**
 * @author Talgat Bekkaliyev
 * @project AdPlatform
 */
public interface AdvertisementDAO {


    List<Advertisement> getAdvertisementByUserId(Long userId) throws DAOException;
    Advertisement getAdvertisementById(Long adId) throws DAOException;
    Integer getAdvertisementCountById(Long userId) throws DAOException;
    Location getLocationNamesById(Long locationId, String languageId) throws DAOException;
    List<Comment> getCommentsAByAdvertisementId(Long userId) throws DAOException;
    boolean postComment(Comment comment) throws DAOException;
    boolean postAdvertisement(Advertisement advertisement) throws DAOException;
    List<Category> getCategories(Long languageId) throws DAOException;
    List<Location> getLocations(Long languageId) throws DAOException;
    Long getLanguageIdByName(String languageName) throws DAOException;
    List<Advertisement> getAllAdvertisements() throws DAOException;
    List<Advertisement> searchAdvertisementsByCategory(Long categoryId) throws DAOException;
    List<Advertisement> searchAdvertisementsByCategoryAndLocation(Long categoryId, Long locationId) throws DAOException;
    List<Advertisement> searchAdvertisementsByLocation(Long locationId) throws DAOException;
    boolean deleteAdvertisementByUserIdAndAdId(Long adId) throws DAOException;
    List<Advertisement> searchAdvertisementsByDescriptionAndCategoryAndLocation(String descriptionInput, Long categoryId, Long locationId) throws DAOException;
    List<Advertisement> searchAdvertisementsByDescriptionAndCategory(String descriptionInput, Long categoryId) throws DAOException;
    List<Advertisement> searchAdvertisementsByDescriptionAndLocation(String descriptionInput, Long locationId) throws DAOException;
    List<Advertisement> searchAdvertisementsByDescription(String descriptionInput) throws DAOException;
    boolean postLocation(Location location) throws DAOException;

}
