package kz.epam.tcfp.dao;

import kz.epam.tcfp.dao.exception.DAOException;
import kz.epam.tcfp.model.Location;

import java.util.List;

/**
 * @author Talgat Bekkaliyev
 * @project AdPlatform
 */
public interface LocationDAO {

    Location getLocationNamesById(Long locationId, String languageId) throws DAOException;
    List<Location> getLocations(Long languageId) throws DAOException;
    boolean postLocation(Location location) throws DAOException;
}
