package kz.epam.tcfp.model;



import java.io.Serializable;
import java.util.Date;
import java.util.List;


/**
 * @author Talgat Bekkaliyev
 * @project AdPlatform
 */
public class Advertisement implements Serializable {
    private static final long serialVersionUID = 1234L;


    private Integer userId;
    private Integer adId;
    private String title;
    private String description;
    private Location location;
    private Integer price;
    private Date postedDate;
    private Category category;
    private List<Photo> photos;

    public Advertisement() {
    }

    public Advertisement(Integer adId, String title, String description, Integer locationId, Integer categoryId, List<Photo> photos) {
        this.adId = adId;
        this.title = title;
        this.description = description;
        this.location = new Location(locationId);
        this.category = new Category(categoryId);
        this.photos = photos;
    }

    public Integer getAdId() {
        return adId;
    }

    public void setAdId(Integer adId) {
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

    public List<Photo> getPhotos() {
        return photos;
    }

    public void setPhotos(List<Photo> photos) {
        this.photos = photos;
    }

    public Date getPostedDate() {
        return postedDate;
    }

    public void setPostedDate(Date postedDate) {
        this.postedDate = postedDate;
    }

    public Integer getPrice() {
        return price;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
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
                ", photos=" + photos +
                '}';
    }
}
