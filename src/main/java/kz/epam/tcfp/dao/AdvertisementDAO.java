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
    List<Advertisement> getAllAdvertisements(Integer page) throws DAOException;
    Integer getCountAllAdvertisements() throws DAOException;
    boolean deleteAdvertisementByUserIdAndAdId(Long adId) throws DAOException;
    List<Advertisement> searchAdvertisementsByCategory(Long categoryId, Integer page) throws DAOException;
    List<Advertisement> searchAdvertisementsByCategoryAndLocation(Long categoryId, Long locationId, Integer page) throws DAOException;
    List<Advertisement> searchAdvertisementsByLocation(Long locationId, Integer page) throws DAOException;
    List<Advertisement> searchAdvertisementsByDescriptionAndCategoryAndLocation(String descriptionInput, Long categoryId, Long locationId, Integer page) throws DAOException;
    List<Advertisement> searchAdvertisementsByDescriptionAndCategory(String descriptionInput, Long categoryId, Integer page) throws DAOException;
    List<Advertisement> searchAdvertisementsByDescriptionAndLocation(String descriptionInput, Long locationId, Integer page) throws DAOException;
    List<Advertisement> searchAdvertisementsByDescription(String descriptionInput, Integer page) throws DAOException;
    Integer countSearchAdvertisementsByCategory(Long categoryId) throws DAOException;
    Integer countSearchAdvertisementsByCategoryAndLocation(Long categoryId, Long locationId) throws DAOException;
    Integer countSearchAdvertisementsByLocation(Long locationId) throws DAOException;
    Integer countSearchAdvertisementsByDescriptionAndCategoryAndLocation(String descriptionInput, Long categoryId, Long locationId) throws DAOException;
    Integer countSearchAdvertisementsByDescriptionAndCategory(String descriptionInput, Long categoryId) throws DAOException;
    Integer countSearchAdvertisementsByDescriptionAndLocation(String descriptionInput, Long locationId) throws DAOException;
    Integer countSearchAdvertisementsByDescription(String descriptionInput) throws DAOException;

}
