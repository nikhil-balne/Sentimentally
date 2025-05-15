package com.main.sentimentally.service;

import com.main.sentimentally.entity.Category;
import com.main.sentimentally.repository.CategoryRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class CategoryService {

	private final CategoryRepository categoryRepository;

	public List<Category> getAllCategories() {
		return categoryRepository.findAll();
	}

	public Category saveCategory(String category) {
		Category newCategory = new Category();
		newCategory.setName(category);
		return categoryRepository.save(newCategory);
	}

	public List<Category> saveCategories(List<Category> categories) {
		return categoryRepository.saveAll(categories);
	}

	public List<Category> getNewCategories(String[] categories, List<Category> categoryList) {
		// Extract existing category names into a Set for fast lookup
		Set<String> existingNames = categoryList.stream().map(Category::getName).collect(Collectors.toSet());

		// Filter and create new Category objects for names not already in the list
		return Arrays.stream(categories).filter(name -> !existingNames.contains(name)).map(name -> {
			Category newCategory = new Category();
			newCategory.setName(name);
			return newCategory;
		}).collect(Collectors.toList());
	}

	public List<Category> findExistingCategories(String[] categories, List<Category> categoryList) {
		// Convert input array to Set for efficient lookup
		Set<String> inputNames = new HashSet<>(Arrays.asList(categories));

		// Filter and return matching Category objects
		return categoryList.stream().filter(cat -> inputNames.contains(cat.getName())).collect(Collectors.toList());
	}

}
