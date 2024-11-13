package com.eg.A.Rest.Api.Blog.Application.service;

import java.util.List;

import com.eg.A.Rest.Api.Blog.Application.DTO.CategoryDto;

public interface CategoryService {

	CategoryDto addCategory(CategoryDto categoryDtoy);

	CategoryDto getCategory(Long categoryId);

	List<CategoryDto> getAllCategories();

	CategoryDto updateCategoryById(CategoryDto categoryDto, Long categoryId);

	void deleteCategoryById(Long categoryId);

}
