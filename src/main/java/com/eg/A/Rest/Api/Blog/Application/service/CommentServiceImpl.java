package com.eg.A.Rest.Api.Blog.Application.service;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.eg.A.Rest.Api.Blog.Application.DTO.CommentDto;
import com.eg.A.Rest.Api.Blog.Application.entity.Comment;
import com.eg.A.Rest.Api.Blog.Application.entity.Post;
import com.eg.A.Rest.Api.Blog.Application.exception.BlogAPIException;
import com.eg.A.Rest.Api.Blog.Application.exception.ResourceNotFoundException;
import com.eg.A.Rest.Api.Blog.Application.repository.CommentRepository;
import com.eg.A.Rest.Api.Blog.Application.repository.PostRepository;

@Service
public class CommentServiceImpl implements CommentService {
	private CommentRepository commentRepo;
	private PostRepository postRepo;
	private ModelMapper mapper;

	public CommentServiceImpl(CommentRepository commentRepo, PostRepository postRepo, ModelMapper mapper) {

		this.commentRepo = commentRepo;
		this.postRepo = postRepo;
		this.mapper = mapper;
	}

	@Override
	public CommentDto createComment(long postId, CommentDto commentDto) {
		Comment comment = mapToEntity(commentDto);

		// retirve postentity id
		Post post = postRepo.findById(postId).orElseThrow(() -> new ResourceNotFoundException("Post", "id", postId));
		// set post to comment entity
		comment.setPost(post);

//comment entity to DB
		Comment newComment = commentRepo.save(comment);
		return mapToDto(newComment);
	}

//	@Override
//	public List<CommentDto> getAllCommentsByPostId(long postId) {
//
//		List<Comment> comments = commentRepo.findById(postId);
//		return comments.stream().map(comment->mapToDto(comment)).collect(Collectors.toList());
//		}
	@Override
	public List<CommentDto> getAllCommentsByPostId(long postId) {
		// Retrieve post by its ID
		Post post = postRepo.findById(postId).orElseThrow(() -> new ResourceNotFoundException("Post", "id", postId));

		// Retrieve all comments associated with the post
		List<Comment> comments = commentRepo.findByPost(post);

		// Map comments to DTOs
		return comments.stream().map(this::mapToDto).collect(Collectors.toList());
	}

	// convert entity into dto
	private CommentDto mapToDto(Comment comment) {
		CommentDto commentDto = mapper.map(comment, CommentDto.class);
//		CommentDto commentDto = new CommentDto();
//		commentDto.setId(comment.getId());
//		commentDto.setName(comment.getName());
//		commentDto.setEmail(comment.getEmail());
//		commentDto.setBody(comment.getBody());
		return commentDto;
	}

	// Convert dto to entity
	private Comment mapToEntity(CommentDto comDto) {
		Comment com = mapper.map(comDto, Comment.class);
//		Comment com = new Comment();
//		com.setId(comDto.getId());
//		com.setName(comDto.getName());
//		com.setEmail(comDto.getEmail());
//		com.setBody(comDto.getBody());
		return com;
	}

	@Override
	public CommentDto getAllCommentsById(long postId, long commentId) {
		Post post = postRepo.findById(postId).orElseThrow(() -> new ResourceNotFoundException("Post", "id", postId));
		Comment comment = commentRepo.findById(commentId)
				.orElseThrow(() -> new ResourceNotFoundException("Comment", "id", commentId));
		if (!comment.getPost().getId().equals(post.getId())) {
			throw new BlogAPIException(HttpStatus.BAD_REQUEST, "Comment does not belongs to post");
		}
		return mapToDto(comment);
	}

	@Override
	public CommentDto updateCommentById(long postId, long commentId, CommentDto commentDto) {
		Post post = postRepo.findById(postId).orElseThrow(() -> new ResourceNotFoundException("Post", "id", postId));
		Comment comment = commentRepo.findById(commentId)
				.orElseThrow(() -> new ResourceNotFoundException("Comment", "id", commentId));
		if (!comment.getPost().getId().equals(post.getId())) {

			throw new BlogAPIException(HttpStatus.BAD_REQUEST, "Comment does not belongs to post");
		}
		comment.setName(commentDto.getName());
		comment.setEmail(commentDto.getEmail());
		comment.setBody(commentDto.getBody());
		Comment commentUpdate = commentRepo.save(comment);
		return mapToDto(commentUpdate);
	}

	@Override
	public void deleteCommentById(long postId, long commentId) {

		Post post = postRepo.findById(postId).orElseThrow(() -> new ResourceNotFoundException("Post", "id", postId));
		Comment comment = commentRepo.findById(commentId)
				.orElseThrow(() -> new ResourceNotFoundException("Comment", "id", commentId));
		if (!comment.getPost().getId().equals(post.getId())) {

			throw new BlogAPIException(HttpStatus.BAD_REQUEST, "Comment does not belongs to post");
		}
		commentRepo.deleteById(commentId);
	}

}