package kz.epam.tcfp.dao;

import kz.epam.tcfp.dao.exception.DAOException;
import kz.epam.tcfp.model.Advertisement;

import java.util.List;

/**
 * @author Talgat Bekkaliyev
 * @project AdPlatform
 */
public interface AdvertisementDAO {


    List<Advertisement> getAdvertisementByCustomerId(Integer customerId) throws DAOException;
    Advertisement getAdvertisementById(Integer adId) throws DAOException;
    Integer getAdvertisementCountById(Integer customerId) throws DAOException;


}
