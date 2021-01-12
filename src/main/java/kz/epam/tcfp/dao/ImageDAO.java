package kz.epam.tcfp.dao;

import kz.epam.tcfp.dao.exception.DAOException;
import kz.epam.tcfp.model.Image;

/**
 * @author Talgat Bekkaliyev
 * @project AdPlatform
 */
public interface ImageDAO {

    boolean postImage(Image image) throws DAOException;
    Image getImage(Long adId) throws DAOException;

}
