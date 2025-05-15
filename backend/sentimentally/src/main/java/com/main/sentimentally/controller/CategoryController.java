package com.main.sentimentally.controller;

import com.main.sentimentally.entity.Category;
import com.main.sentimentally.service.CategoryService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@RestController
@RequestMapping("/categories")
@AllArgsConstructor
public class CategoryController {

    private final CategoryService categoryService;

    @GetMapping("/")
    public List<Category> getAllCategories() {
        return categoryService.getAllCategories();
    }
}
