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

    private Integer adId;
    private String title;
    private String description;
    private Location location;
    private Integer price;
    private Date postedDate;
    private Integer category;
    private List<Photo> photos;

    public Advertisement() {
    }

    public Advertisement(Integer adId, String title, String description, Integer locationId, Integer category, List<Photo> photos) {
        this.adId = adId;
        this.title = title;
        this.description = description;
        this.location = new Location(locationId);
        this.category = category;
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

    public Integer getCategory() {
        return category;
    }

    public void setCategory(Integer category) {
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
