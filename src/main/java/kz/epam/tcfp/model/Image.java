package kz.epam.tcfp.model;

/**
 * @author Talgat Bekkaliyev
 * @project AdPlatform
 */
public class Image {
    private static final long serialVersionUID = 1234L;

    private Long imageId;
    private Long advertisementId;
    private String path;

    public Image() {
    }

    public Image(Long imageId, String path) {
        this.imageId = imageId;
        this.path = path;
    }

    public Long getImageId() {
        return imageId;
    }

    public void setImageId(Long imageId) {
        this.imageId = imageId;
    }

    public Long getAdvertisementId() {
        return advertisementId;
    }

    public void setAdvertisementId(Long advertisementId) {
        this.advertisementId = advertisementId;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }
}

