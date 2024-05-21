package org.springtest1.springtest.service;

import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springtest1.springtest.dao.entities.Category;
import org.springtest1.springtest.dao.entities.Product;
import org.springtest1.springtest.dao.repositories.CategoryRepository;

import java.util.List;
import java.util.Optional;

@Service
public class CategoryService implements CategoryManager{

    @Autowired
    private CategoryRepository categoryRepository;
@Autowired
ProductService productService;
    public List<Category> getAllCategories() {
        return categoryRepository.findAll();
    }


    @Override
    public Category addCategory(Category category) {
        return categoryRepository.save(category);
    }
    @Override
    public void saveCategory(Long id,String name) {
        Category category=new Category();
        category.setName(name);
        categoryRepository.save(category);
    }
    @Override
    public Category getCategoryById(Long id) {
        return categoryRepository.findById(id).orElse(null);
    }
    @Override
    public void updateCategory(Long id, String updatedCategory) {
        Category category= categoryRepository.findById(id).orElse(null);
        category.setName(updatedCategory);
        categoryRepository.save(category);
    }
    @Override
    public void deleteCategoryById(Long id) {
        categoryRepository.deleteById(id);
    }
@Override
@Transactional
public void deleteCategoryAndProducts(Long categoryId) {

    productService.deleteProductsByCategoryId(categoryId);


    categoryRepository.deleteById(categoryId);
}

}
