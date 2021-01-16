package kz.epam.tcfp.model;

import java.util.Objects;

/**
 * @author Talgat Bekkaliyev
 * @project AdPlatform
 */
public class Category {
    private static final long serialVersionUID = 1234L;


    private Long categoryId;
    private Long languageId;
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

    public Long getLanguageId() {
        return languageId;
    }

    public void setLanguageId(Long languageId) {
        this.languageId = languageId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Category)) return false;
        Category category = (Category) o;
        return Objects.equals(getCategoryId(), category.getCategoryId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getCategoryId());
    }
}
