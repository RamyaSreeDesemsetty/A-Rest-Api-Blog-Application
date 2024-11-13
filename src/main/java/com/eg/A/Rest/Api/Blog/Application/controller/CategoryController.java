package com.eg.A.Rest.Api.Blog.Application.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.eg.A.Rest.Api.Blog.Application.DTO.CategoryDto;
import com.eg.A.Rest.Api.Blog.Application.service.CategoryService;

@RestController
@RequestMapping("/api/v1/categories")
public class CategoryController {

	private CategoryService categoryService;

	public CategoryController(CategoryService categoryService) {
		super();
		this.categoryService = categoryService;
	}

	// Add category

	@PostMapping("/create")
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<CategoryDto> createCategory(@RequestBody CategoryDto categoryDto) {
		return new ResponseEntity<>(categoryService.addCategory(categoryDto), HttpStatus.CREATED);
	}

	@GetMapping("/id/{id}")
	public ResponseEntity<CategoryDto> getCategoryById(@PathVariable("id") Long categoryId) {
		CategoryDto categoryDto = categoryService.getCategory(categoryId);
		return ResponseEntity.ok(categoryDto);
	}

	@GetMapping("/all")
	public ResponseEntity<List<CategoryDto>> getCategories() {
		return ResponseEntity.ok(categoryService.getAllCategories());
	}

	@PutMapping("/update/{categoryId}")
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<CategoryDto> updateCategory(@RequestBody CategoryDto categoryDto,
			@PathVariable Long categoryId) {

		return ResponseEntity.ok(categoryService.updateCategoryById(categoryDto, categoryId));
	}

	@PreAuthorize("hasRole('ADMIN')")
	@DeleteMapping("/delete/{id}")
	public ResponseEntity<String> deleteCategory(@PathVariable("id") Long categoryId) {
		categoryService.deleteCategoryById(categoryId);
		return ResponseEntity.ok(" Category Deleted Successfully");
	}
}
