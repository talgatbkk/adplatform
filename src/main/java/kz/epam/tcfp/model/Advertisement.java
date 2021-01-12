package kz.epam.tcfp.model;



import java.io.Serializable;
import java.util.List;


/**
 * @author Talgat Bekkaliyev
 * @project AdPlatform
 */
public class Advertisement implements Serializable {
    private static final long serialVersionUID = 1234L;


    private Long userId;
    private Long adId;
    private String title;
    private String description;
    private Location location;
    private Integer price;
    private DateTimeInUTC postedDate;
    private Category category;
    private List<Image> images;

    public Advertisement() {
    }

    public Advertisement(Long adId, String title, String description, Long locationId, Long categoryId, List<Image> images) {
        this.adId = adId;
        this.title = title;
        this.description = description;
        this.location = new Location(locationId);
        this.category = new Category(categoryId);
        this.images = images;
    }

    public Long getAdId() {
        return adId;
    }

    public void setAdId(Long adId) {
        this.adId = adId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public List<Image> getPhotos() {
        return images;
    }

    public void setPhotos(List<Image> images) {
        this.images = images;
    }

    public DateTimeInUTC getPostedDate() {
        return postedDate;
    }

    public void setPostedDate(DateTimeInUTC postedDate) {
        this.postedDate = postedDate;
    }

    public Integer getPrice() {
        return price;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }



    @Override
    public String toString() {
        return "Ad{" +
                "adId=" + adId +
                ", title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", locationId=" + location +
                ", price=" + price +
                ", postedDate=" + postedDate +
                ", category=" + category +
                ", photos=" + images +
                '}';
    }
}
