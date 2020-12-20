package kz.epam.tcfp.model;

import java.util.List;

/**
 * @author Talgat Bekkaliyev
 * @project AdPlatform
 */
public class Customer extends User {
    private static final long serialVersionUID = 1234L;

    private Photo avatarPhoto;
    private Integer activeAds;
    private boolean isBanned;
    private List<PhoneNumber> phoneNumbers;

    public Customer() {
        super();
    }

    public Photo getAvatarPhoto() {
        return avatarPhoto;
    }

    public void setAvatarPhoto(Photo avatarPhoto) {
        this.avatarPhoto = avatarPhoto;
    }

    public Integer getActiveAds() {
        return activeAds;
    }

    public void setActiveAds(Integer activeAds) {
        this.activeAds = activeAds;
    }

    public boolean isBanned() {
        return isBanned;
    }

    public void setBanned(boolean banned) {
        isBanned = banned;
    }

    public List<PhoneNumber> getPhoneNumbers() {
        return phoneNumbers;
    }

    public void setPhoneNumbers(List<PhoneNumber> phoneNumbers) {
        this.phoneNumbers = phoneNumbers;
    }

    @Override
    public String toString() {
        return super.toString();
    }
}
