package kz.epam.tcfp.dao;

import kz.epam.tcfp.dao.exception.DAOException;
import kz.epam.tcfp.model.Category;

import java.util.List;

/**
 * @author Talgat Bekkaliyev
 * @project AdPlatform
 */
public interface CategoryDAO {

    boolean postCategory(Category category) throws DAOException;
    List<Category> getCategories(Long languageId) throws DAOException;
}
