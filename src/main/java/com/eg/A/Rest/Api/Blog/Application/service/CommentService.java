package com.eg.A.Rest.Api.Blog.Application.service;

import java.util.List;

import com.eg.A.Rest.Api.Blog.Application.DTO.CommentDto;

public interface CommentService {
	CommentDto createComment(long postId, CommentDto commentDto);

	List<CommentDto> getAllCommentsByPostId(long postId);

	CommentDto getAllCommentsById(long postId, long commentId);

	CommentDto updateCommentById(long postId, long commentId, CommentDto commentDto);

	void deleteCommentById(long postId, long commentId);
}
