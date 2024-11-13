package com.eg.A.Rest.Api.Blog.Application.service;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.eg.A.Rest.Api.Blog.Application.DTO.PostDto;
import com.eg.A.Rest.Api.Blog.Application.DTO.PostResponseDto;
import com.eg.A.Rest.Api.Blog.Application.entity.Category;
import com.eg.A.Rest.Api.Blog.Application.entity.Post;
import com.eg.A.Rest.Api.Blog.Application.exception.ResourceNotFoundException;
import com.eg.A.Rest.Api.Blog.Application.repository.CategoryRepository;
import com.eg.A.Rest.Api.Blog.Application.repository.PostRepository;

@Service
public class PostServiceImpl implements PostService {
	private PostRepository postRepo;
	private ModelMapper mapper;
	private CategoryRepository categoryRepository;

	public PostServiceImpl(PostRepository postRepo, ModelMapper mapper, CategoryRepository categoryRepository) {
		this.postRepo = postRepo;
		this.mapper = mapper;
		this.categoryRepository = categoryRepository;
	}

	/*
	 * @Override public PostDto createPost(PostDto postDto) { // convert Dto to
	 * entity Post post = new Post(); post.setTitle(postDto.getTitle());
	 * post.setDescription(postDto.getDescription());
	 * post.setContent(postDto.getContent()); Post newPost = postRepo.save(post); //
	 * convert entity to Dto PostDto postDtoResponse = new PostDto();
	 * postDtoResponse.setId(newPost.getId());
	 * postDtoResponse.setTitle(newPost.getTitle());
	 * postDtoResponse.setDescription(newPost.getDescription());
	 * postDtoResponse.setContent(newPost.getContent()); return postDtoResponse; }
	 */
	@Override
	public PostDto createPost(PostDto postDto) {

		Category category = categoryRepository.findById(postDto.getCategoryId())
				.orElseThrow(() -> new ResourceNotFoundException("Category", "id", postDto.getCategoryId()));
		// convert Dto to entity
		Post post = MapToEntity(postDto);
		post.setCategory(category);
		Post newPost = postRepo.save(post);
		// convert entity to Dto
		PostDto postDtoNewResponse = mapToDto(newPost);
		return postDtoNewResponse;
	}

	// Get blog posts
	@Override
	public List<PostDto> getAllPosts() {
		List<Post> postList = postRepo.findAll();
		return postList.stream().map(post -> mapToDto(post)).collect(Collectors.toList());

	}

	// get by page
	@Override
	public List<PostDto> getAllPostsPage(int pageNo, int pageSize) {

		Pageable pageable = PageRequest.of(pageNo, pageSize);
		Page<Post> postLists = postRepo.findAll(pageable);
//get content for page object
		List<Post> listPosts = postLists.getContent();
		return listPosts.stream().map(post -> mapToDto(post)).collect(Collectors.toList());
	}

	// get by page with details
	public PostResponseDto getAllPostsPageDetails(int pageNo, int pageSize) {

		Pageable pageable = PageRequest.of(pageNo, pageSize);
		Page<Post> postLists = postRepo.findAll(pageable);
//get content for page object
		List<Post> listPosts = postLists.getContent();

		List<PostDto> content = listPosts.stream().map(post -> mapToDto(post)).collect(Collectors.toList());

		PostResponseDto prd = new PostResponseDto();
		prd.setContent(content);
		prd.setPageNo(postLists.getNumber());
		prd.setPageSize(postLists.getSize());
		prd.setTotalElements(postLists.getTotalElements());
		prd.setTotalPages(postLists.getTotalPages());
		prd.setLast(postLists.isLast());
		return prd;
	}

	// with sort
	@Override
	public PostResponseDto getAllPostsPageDetailsSort(int pageNo, int pageSize, String sortBy) {

		Pageable pageable = PageRequest.of(pageNo, pageSize, Sort.by(sortBy));
		Page<Post> postLists = postRepo.findAll(pageable);
//get content for page object
		List<Post> listPosts = postLists.getContent();

		List<PostDto> content = listPosts.stream().map(post -> mapToDto(post)).collect(Collectors.toList());

		PostResponseDto prd = new PostResponseDto();
		prd.setContent(content);
		prd.setPageNo(postLists.getNumber());
		prd.setPageSize(postLists.getSize());
		prd.setTotalElements(postLists.getTotalElements());
		prd.setTotalPages(postLists.getTotalPages());
		prd.setLast(postLists.isLast());
		return prd;
	}

	@Override
	public PostResponseDto getAllPostsPageDetailsSortDir(int pageNo, int pageSize, String sortBy, String sortDir) {
		Sort sort = sortDir.equalsIgnoreCase(Sort.Direction.ASC.name()) ? Sort.by(sortBy).ascending()
				: Sort.by(sortBy).descending();
		Pageable pageable = PageRequest.of(pageNo, pageSize, sort);
		Page<Post> postLists = postRepo.findAll(pageable);
//get content for page object
		List<Post> listPosts = postLists.getContent();

		List<PostDto> content = listPosts.stream().map(post -> mapToDto(post)).collect(Collectors.toList());

		PostResponseDto prd = new PostResponseDto();
		prd.setContent(content);
		prd.setPageNo(postLists.getNumber());
		prd.setPageSize(postLists.getSize());
		prd.setTotalElements(postLists.getTotalElements());
		prd.setTotalPages(postLists.getTotalPages());
		prd.setLast(postLists.isLast());
		return prd;
	}
	// convert entity into dto
	private PostDto mapToDto(Post post) {
		PostDto postDtoResponse = mapper.map(post, PostDto.class);
//		PostDto postDtoResponse = new PostDto();
//		postDtoResponse.setId(post.getId());
//		postDtoResponse.setTitle(post.getTitle());
//		postDtoResponse.setDescription(post.getDescription());
//		postDtoResponse.setContent(post.getContent());
		return postDtoResponse;
	}

//convert dto into enity
	private Post MapToEntity(PostDto postDto) {
		Post post = mapper.map(postDto, Post.class);
//		Post post = new Post();
//		post.setTitle(postDto.getTitle());
//		post.setDescription(postDto.getDescription());
//		post.setContent(postDto.getContent());

		return post;
	}

	@Override
	public PostDto getPostsById(long id) {
		Post post = postRepo.findById(id).orElseThrow(() -> new ResourceNotFoundException("Post", "id", id));

		return mapToDto(post);
	}

	@Override
	public PostDto updatePost(PostDto postDto, long id) {
		Category category = categoryRepository.findById(postDto.getCategoryId())
				.orElseThrow(() -> new ResourceNotFoundException("Category", "id", postDto.getCategoryId()));

		Post post = postRepo.findById(id).orElseThrow(() -> new ResourceNotFoundException("Post", "id", id));
		post.setTitle(postDto.getTitle());
		post.setDescription(postDto.getDescription());
		post.setContent(postDto.getContent());
		post.setCategory(category);
		Post updatedPosts = postRepo.save(post);
		return mapToDto(updatedPosts);
	}

	@Override
	public void DeletePostById(long id) {
		Post post = postRepo.findById(id).orElseThrow(() -> new ResourceNotFoundException("Post", "id", id));
		postRepo.deleteById(id);

	}

	@Override
	public List<PostDto> getPostByCategory(Long categoryId) {
		Category category = categoryRepository.findById(categoryId)
				.orElseThrow(() -> new ResourceNotFoundException("category", "id", categoryId));
		List<Post> posts = postRepo.findByCategoryId(categoryId);

		return posts.stream().map((post) -> mapToDto(post)).collect(Collectors.toList());
	}


}
