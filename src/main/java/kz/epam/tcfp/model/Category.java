package kz.epam.tcfp.model;

/**
 * @author Talgat Bekkaliyev
 * @project AdPlatform
 */
public class Category {
    private static final long serialVersionUID = 1234L;


    private Integer categoryId;
    private String categoryName;

    public Category(Integer categoryId, String categoryName) {
        this.categoryId = categoryId;
        this.categoryName = categoryName;
    }
}
