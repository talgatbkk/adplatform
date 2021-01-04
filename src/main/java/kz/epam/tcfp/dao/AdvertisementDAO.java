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


    List<Advertisement> getAdvertisementByUserId(Integer userId) throws DAOException;
    Advertisement getAdvertisementById(Integer adId) throws DAOException;
    Integer getAdvertisementCountById(Integer userId) throws DAOException;
    Location getLocationNamesById(Integer locationId, String languageId) throws DAOException;
    List<Comment> getCommentsAByAdvertisementId(Integer userId) throws DAOException;
    boolean postComment(Comment comment) throws DAOException;
    boolean postAdvertisement(Advertisement advertisement) throws DAOException;
    List<Category> getCategories(Integer languageId) throws DAOException;
    List<Location> getLocations(Integer languageId) throws DAOException;
    Integer getLanguageIdByName(String languageName) throws DAOException;
    List<Advertisement> getAllAdvertisements() throws DAOException;
    List<Advertisement> searchAdvertisementsByCategory(Integer categoryId) throws DAOException;
    List<Advertisement> searchAdvertisementsByCategoryWithLocation(Integer categoryId, Integer locationId) throws DAOException;
    List<Advertisement> searchAdvertisementsByLocation(Integer locationId) throws DAOException;
    boolean deleteAdvertisementByUserIdAndAdId(Integer userId, Integer adId) throws DAOException;

}
