package kz.epam.tcfp.service.implementation;

import kz.epam.tcfp.dao.AdvertisementDAO;
import kz.epam.tcfp.dao.exception.DAOException;
import kz.epam.tcfp.dao.factory.DAOFactory;
import kz.epam.tcfp.model.Advertisement;
import kz.epam.tcfp.service.AdvertisementService;

import java.util.List;

/**
 * @author Talgat Bekkaliyev
 * @project AdPlatform
 */
public class AdvertisementServiceImpl implements AdvertisementService {
//    private static final Logger logger = Logger.getLogger(AdvertisementServiceImpl.class);


    private final AdvertisementDAO advertisementDAO = DAOFactory.getAdvertisementDAO();

    @Override
    public List<Advertisement> getAdvertisement(Integer userId) {
        try {
            return advertisementDAO.getAdvertisementByUserId(userId);
        } catch (DAOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
