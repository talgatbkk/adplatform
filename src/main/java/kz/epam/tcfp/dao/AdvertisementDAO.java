package kz.epam.tcfp.dao;

import kz.epam.tcfp.dao.exception.DAOException;
import kz.epam.tcfp.model.*;

import java.util.List;

/**
 * @author Talgat Bekkaliyev
 * @project AdPlatform
 */
public interface AdvertisementDAO {


    List<Advertisement> getAdvertisementByUserId(Long userId) throws DAOException;
    Advertisement getAdvertisementById(Long adId) throws DAOException;
    Integer getAdvertisementCountById(Long userId) throws DAOException;
    boolean postAdvertisement(Advertisement advertisement) throws DAOException;
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
    boolean postImage(Image image) throws DAOException;
    Image getImage(Long adId) throws DAOException;

}
