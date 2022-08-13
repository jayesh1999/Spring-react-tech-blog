package com.blog.api.service.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.blog.api.entities.Category;
import com.blog.api.exceptions.ResourceNotFoundException;
import com.blog.api.payloads.CategoryDto;
import com.blog.api.repositories.CategoryRepo;
import com.blog.api.service.CategoryService;

@Service
public class CategoryServiceImpl implements CategoryService {
	
	
	@Autowired
	private CategoryRepo categoryRepo;
	
	@Autowired
	private ModelMapper modelMapper;

	@Override
	public CategoryDto createCategory(CategoryDto categoryDto) {
		
		Category cat  = this.modelMapper.map(categoryDto, Category.class);
		Category addedCat = this.categoryRepo.save(cat);
		return this.modelMapper.map(addedCat, CategoryDto.class);
	}

	@Override
	public CategoryDto updateCategory(CategoryDto categoryDto, Integer categoryId) {

		Category cat = this.categoryRepo.findById(categoryId).orElseThrow(()-> new ResourceNotFoundException("category", "categoryId",categoryId));	
		cat.setCategoryDescription(categoryDto.getCategoryDescription());
		cat.setCategoryTitle(categoryDto.getCategoryTitle());
		
		this.categoryRepo.save(cat);
		
		return this.modelMapper.map(cat, CategoryDto.class);
	}

	@Override
	public void deleteCategory(Integer categoryId) {
		
		Category cat = this.categoryRepo.findById(categoryId).orElseThrow(()-> new ResourceNotFoundException("category", "categoryId",categoryId));	
		this.categoryRepo.delete(cat);
	}

	@Override
	public CategoryDto getCategory(Integer categoryId) {
		
		Category cat = this.categoryRepo.findById(categoryId).orElseThrow(()-> new ResourceNotFoundException("category", "categoryId",categoryId));	
		return this.modelMapper.map(cat,CategoryDto.class);
	}

	@Override
	public List<CategoryDto> getCategories() {
		
		List<Category> catagories  =  this.categoryRepo.findAll();
		List<CategoryDto> catDtos = catagories.stream().map((cat)->this.modelMapper.map(cat, CategoryDto.class)).collect(Collectors.toList());
	
		return catDtos;
	}
	
	

}
