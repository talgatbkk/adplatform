package kz.epam.tcfp.model;

import java.util.Objects;

/**
 * @author Talgat Bekkaliyev
 * @project AdPlatform
 */
public class Category {

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

}
