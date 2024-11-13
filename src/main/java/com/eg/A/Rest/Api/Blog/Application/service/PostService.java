package com.eg.A.Rest.Api.Blog.Application.service;

import java.util.List;

import com.eg.A.Rest.Api.Blog.Application.DTO.PostDto;
import com.eg.A.Rest.Api.Blog.Application.DTO.PostResponseDto;

public interface PostService {
	PostDto createPost(PostDto postDto);

	List<PostDto> getAllPosts();

	List<PostDto> getAllPostsPage(int pageNo, int pageSize);


	PostResponseDto getAllPostsPageDetails(int pageNo, int pageSize);

	PostDto getPostsById(long id);

	PostResponseDto getAllPostsPageDetailsSort(int pageNo, int pageSize, String sortBy);

	PostResponseDto getAllPostsPageDetailsSortDir(int pageNo, int pageSize, String sortBy, String sortDir);
	PostDto updatePost(PostDto postDto, long id);

	void DeletePostById(long id);

	List<PostDto> getPostByCategory(Long categoryId);
}
