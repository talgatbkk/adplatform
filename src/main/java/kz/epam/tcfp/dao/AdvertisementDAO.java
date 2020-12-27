package kz.epam.tcfp.dao;

import kz.epam.tcfp.dao.exception.DAOException;
import kz.epam.tcfp.model.Advertisement;
import kz.epam.tcfp.model.Comment;
import kz.epam.tcfp.model.Location;

import java.util.List;

/**
 * @author Talgat Bekkaliyev
 * @project AdPlatform
 */
public interface AdvertisementDAO {


    List<Advertisement> getAdvertisementByCustomerId(Integer customerId) throws DAOException;
    Advertisement getAdvertisementById(Integer adId) throws DAOException;
    Integer getAdvertisementCountById(Integer customerId) throws DAOException;
    Location getLocationNamesById(Integer locationId, String languageId) throws DAOException;
    List<Comment> getCommentsAByAdvertisementId(Integer customerId) throws DAOException;
    boolean postComment(Comment comment) throws DAOException;



}
