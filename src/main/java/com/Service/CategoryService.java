package com.Service;

import com.Repository.CategoryRepository;
import com.model.Category;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CategoryService {
    @Autowired
    private CategoryRepository categoryRepository;

    public Category readCategory(String name){
        return categoryRepository.findByName(name);
    }
    public void createCategory(Category category){
        categoryRepository.save(category);
    }

    public List<Category> listCategory() {
        return  categoryRepository.findAll();
    }

    public Optional<Category> readId(Integer id){
        return categoryRepository.findById(id);
    }
    public void updateCategory(Integer categoryID, Category newCategory) {
        Category category = categoryRepository.findById(categoryID).orElseThrow(() -> new RuntimeException("Category not found"));
        category.setName(newCategory.getName());
        category.setDescription(newCategory.getDescription());
        category.setImageURL(newCategory.getImageURL());
        categoryRepository.save(category);
    }

    public void deleteCategory(Integer categoryId) {
        categoryRepository.deleteById(categoryId);
    }
}
