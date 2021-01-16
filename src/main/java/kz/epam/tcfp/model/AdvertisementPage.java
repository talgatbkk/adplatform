package kz.epam.tcfp.model;

import java.util.List;

/**
 * @author Talgat Bekkaliyev
 * @project AdPlatform
 */
public class AdvertisementPage {

    List<Advertisement> currentPageAdvertisements;
    Integer totalNumberOfAdvertisements;

    public AdvertisementPage() {
    }

    public List<Advertisement> getCurrentPageAdvertisements() {
        return currentPageAdvertisements;
    }

    public void setCurrentPageAdvertisements(List<Advertisement> currentPageAdvertisements) {
        this.currentPageAdvertisements = currentPageAdvertisements;
    }

    public Integer getTotalNumberOfAdvertisements() {
        return totalNumberOfAdvertisements;
    }

    public void setTotalNumberOfAdvertisements(Integer totalNumberOfAdvertisements) {
        this.totalNumberOfAdvertisements = totalNumberOfAdvertisements;
    }
}
