package kz.epam.tcfp.model;

/**
 * @author Talgat Bekkaliyev
 * @project AdPlatform
 */
public class Photo {
    private static final long serialVersionUID = 1234L;

    private Long photoId;
    private String path;

    public Photo(Long photoId, String path) {
        this.photoId = photoId;
        this.path = path;
    }
}
