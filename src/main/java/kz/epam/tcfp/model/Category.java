package kz.epam.tcfp.model;

/**
 * @author Talgat Bekkaliyev
 * @project AdPlatform
 */
public class Category {
    private static final long serialVersionUID = 1234L;


    private Long categoryId;
    private String categoryName;

    public Category() {
    }

    public Category(Long categoryId) {
        this.categoryId = categoryId;

    }


    public Long getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Long categoryId) {
        this.categoryId = categoryId;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }
}
