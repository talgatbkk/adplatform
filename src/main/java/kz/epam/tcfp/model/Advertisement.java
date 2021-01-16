package kz.epam.tcfp.model;



import java.io.Serializable;
import java.util.Objects;


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
    private Image image;

    public Advertisement() {
    }

    public Advertisement(Long adId, String title, String description, Long locationId, Long categoryId, Image image) {
        this.adId = adId;
        this.title = title;
        this.description = description;
        this.location = new Location(locationId);
        this.category = new Category(categoryId);
        this.image = image;
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

    public Image getImage() {
        return image;
    }

    public void setImage(Image image) {
        this.image = image;
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
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Advertisement)) return false;
        Advertisement that = (Advertisement) o;
        return Objects.equals(getUserId(), that.getUserId()) &&
                Objects.equals(getTitle(), that.getTitle()) &&
                Objects.equals(getDescription(), that.getDescription()) &&
                Objects.equals(getLocation(), that.getLocation()) &&
                Objects.equals(getPrice(), that.getPrice()) &&
                Objects.equals(getCategory(), that.getCategory());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getUserId(), getTitle(), getDescription(), getLocation(), getPrice(), getCategory());
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
                ", photos=" + image +
                '}';
    }
}
