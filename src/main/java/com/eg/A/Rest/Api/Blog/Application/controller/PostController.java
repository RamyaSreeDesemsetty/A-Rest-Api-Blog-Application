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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.eg.A.Rest.Api.Blog.Application.DTO.PostDto;
import com.eg.A.Rest.Api.Blog.Application.DTO.PostResponseDto;
import com.eg.A.Rest.Api.Blog.Application.service.PostService;
import com.eg.A.Rest.Api.Blog.Application.utils.AppConstants;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/v1/posts")
@Tag(name = "CRUD REST APIs For Post Resource")
public class PostController {
	private PostService postService;

	public PostController(PostService postService) {
		this.postService = postService;
	}
@Operation(summary ="Creates post REST API",
description ="create post rest api is used to save post into database")
@ApiResponse(responseCode ="201",
		description = "http status 201 created")
	@SecurityRequirement(name = "Bearer Authentication")
//create blog post
	@PreAuthorize("hasRole('ADMIN')")
	@PostMapping
	public ResponseEntity<PostDto> createPost(@Valid @RequestBody PostDto postDto) {
		return new ResponseEntity<>(postService.createPost(postDto), HttpStatus.CREATED);
	}

	// Get All blog post
	@GetMapping("/all")
	public ResponseEntity<List<PostDto>> getAllPosts() {
		// return new ResponseEntity<>(postService.getAllPosts(), HttpStatus.OK);
		return ResponseEntity.ok(postService.getAllPosts());
	}

	// Get All blog post with pageno & pazesize
	@GetMapping("/all/withpage")
	public ResponseEntity<List<PostDto>> getAllPostsByPage(

			@RequestParam(value = "pageNo", defaultValue = "0", required = false) int pageNo,
			@RequestParam(value = "pageSize", defaultValue = "10", required = false) int pageSize) {
		return ResponseEntity.ok(postService.getAllPostsPage(pageNo, pageSize));

	}

	// get posts by details
	@GetMapping("/all/withpagedetails")
	public ResponseEntity<PostResponseDto> getAllPostsByPageDetails(

			@RequestParam(value = "pageNo", defaultValue = "0", required = false) int pageNo,
			@RequestParam(value = "pageSize", defaultValue = "10", required = false) int pageSize) {
		return ResponseEntity.ok(postService.getAllPostsPageDetails(pageNo, pageSize));

	}

	// get posts by details sort
	@GetMapping("/all/withpagedetailssort")
	public ResponseEntity<PostResponseDto> getAllPostsSortByPageDetails(

			@RequestParam(value = "pageNo", defaultValue = "0", required = false) int pageNo,
			@RequestParam(value = "pageSize", defaultValue = "10", required = false) int pageSize,
			@RequestParam(value = "sortBy", defaultValue = "id", required = false) String sortBy) {
		return ResponseEntity.ok(postService.getAllPostsPageDetailsSort(pageNo, pageSize, sortBy));

	}

	// get posts by details sort
	@GetMapping("/all/withpagedetailssortdir")
	public ResponseEntity<PostResponseDto> getAllPostsSorDirtByPageDetails(

			@RequestParam(value = "pageNo", defaultValue = AppConstants.DEFAULT_PAGE_NUMBER, required = false) int pageNo,
			@RequestParam(value = "pageSize", defaultValue = AppConstants.DEFAULT_PAGE_SIZE, required = false) int pageSize,
			@RequestParam(value = "sortBy", defaultValue = AppConstants.DEFAULT_SORT_BY, required = false) String sortBy,
			@RequestParam(value = "sortDir", defaultValue = AppConstants.DEFAULT_SORT_DIRECTION, required = false) String sortDir)
	/*
	 * @RequestParam(value = "pageNo", defaultValue = "0", required = false) int
	 * pageNo,
	 * 
	 * @RequestParam(value = "pageSize", defaultValue = "10", required = false) int
	 * pageSize,
	 * 
	 * @RequestParam(value = "sortBy", defaultValue = "id", required = false) String
	 * sortBy,
	 * 
	 * @RequestParam(value = "sortDir", defaultValue = "asc", required = false)
	 * String sortDir)
	 */
	{
		return ResponseEntity.ok(postService.getAllPostsPageDetailsSortDir(pageNo, pageSize, sortBy, sortDir));

	}
	// Get posts by id
	@GetMapping("/{id}")
	public ResponseEntity<PostDto> getPostsById(@PathVariable long id) {
		return ResponseEntity.ok(postService.getPostsById(id));
	}

	// update postby id
	@SecurityRequirement(name = "Bearer Authentication")
	@PreAuthorize("hasRole('ADMIN')")
	@PutMapping("/update/{id}")
	public ResponseEntity<PostDto> updatePostById(@Valid @RequestBody PostDto postDto, @PathVariable long id) {
		// PostDto postResponse = postService.updatePost(postDto, id);
		return new ResponseEntity<>(postService.updatePost(postDto, id), HttpStatus.OK);
	}

	// delete by id
	@SecurityRequirement(name = "Bearer Authentication")
	@PreAuthorize("hasRole('ADMIN')")
	@DeleteMapping("/delete/{id}")
	public ResponseEntity<String> deletePostById(@PathVariable long id) {
		postService.DeletePostById(id);
		return new ResponseEntity<>("Deleted successfully", HttpStatus.OK);
	}

	// get posts by category
	@GetMapping("/category/{id}")
	public ResponseEntity<List<PostDto>> getPostsByCategory(@PathVariable("id") Long categoryId) {
		List<PostDto> postDtoResponse = postService.getPostByCategory(categoryId);
		return ResponseEntity.ok(postDtoResponse);
	}
}
