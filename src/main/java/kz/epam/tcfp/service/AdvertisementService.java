package kz.epam.tcfp.service;

import kz.epam.tcfp.model.Advertisement;

import java.util.List;

/**
 * @author Talgat Bekkaliyev
 * @project AdPlatform
 */
public interface AdvertisementService {

    List<Advertisement> getAdvertisement(Integer userId);
}
