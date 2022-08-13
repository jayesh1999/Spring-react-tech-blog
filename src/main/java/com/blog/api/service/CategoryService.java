package com.blog.api.service;

import java.util.List;

import com.blog.api.entities.Category;
import com.blog.api.payloads.CategoryDto;

public interface CategoryService {

	// create
	CategoryDto createCategory(CategoryDto categoryDto);

	// update
	CategoryDto updateCategory(CategoryDto categoryDto, Integer categoryId);

	// delete
	public void deleteCategory(Integer categoryId);

	// get
	public CategoryDto getCategory(Integer categoryId);

	// get all
	List<CategoryDto> getCategories();

}
