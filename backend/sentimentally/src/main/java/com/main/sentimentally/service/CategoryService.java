package com.main.sentimentally.service;

import com.main.sentimentally.entity.Category;
import com.main.sentimentally.repository.CategoryRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@AllArgsConstructor
public class CategoryService {

    private final CategoryRepository categoryRepository;

    public List<Category> getAllCategories(){ return categoryRepository.findAll();}

    public Category saveCategory(String  category){
        Category newCategory = new Category();
        newCategory.setName(category);
        return categoryRepository.save(newCategory);
    }

    public List<Category> saveCategories(String[]  categories){
        List<Category> newCategories = new ArrayList<Category>();
        for(String category: categories){
            Category newCategory = new Category();
            newCategory.setName(category);
            newCategories.add(newCategory);
        }
        return categoryRepository.saveAll(newCategories);
    }
}
