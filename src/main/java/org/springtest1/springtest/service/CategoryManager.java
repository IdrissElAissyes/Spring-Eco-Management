package org.springtest1.springtest.service;

import org.springtest1.springtest.dao.entities.Category;
import org.springtest1.springtest.dao.entities.Product;

import java.util.List;
import java.util.Optional;

public interface CategoryManager {

    public Category getCategoryById(Long id) ;
    public List<Category> getAllCategories();
    public Category addCategory(Category category);
    void saveCategory(Long id,String name);

    void updateCategory(Long id, String updatedCategory);
    public void deleteCategoryAndProducts(Long categoryId);
    void deleteCategoryById(Long id);
}