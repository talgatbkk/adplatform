package kz.epam.tcfp.model;

/**
 * @author Talgat Bekkaliyev
 * @project AdPlatform
 */
public class Category {
    private static final long serialVersionUID = 1234L;


    private Integer categoryId;
    private String categoryName;

    public Category() {
    }

    public Category(Integer categoryId) {
        this.categoryId = categoryId;

    }


    public Integer getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Integer categoryId) {
        this.categoryId = categoryId;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }
}
