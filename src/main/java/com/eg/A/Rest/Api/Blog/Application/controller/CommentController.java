package com.eg.A.Rest.Api.Blog.Application.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.eg.A.Rest.Api.Blog.Application.DTO.CommentDto;
import com.eg.A.Rest.Api.Blog.Application.service.CommentService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/v1")
public class CommentController {
	private CommentService commentService;

	public CommentController(CommentService commentService) {

		this.commentService = commentService;
	}

//comment to posts
	@PostMapping("/createcomment/{postId}")
	public ResponseEntity<CommentDto> createCommentByPostId(@PathVariable Long postId,
			@Valid @RequestBody CommentDto commentDto) {
		return new ResponseEntity<>(commentService.createComment(postId, commentDto), HttpStatus.CREATED);
	}

//get comments by postid
	@GetMapping("/all/{postId}")
	public ResponseEntity<List<CommentDto>> getCommentsByPostId(@PathVariable(value = "postId") long postID) {
		return ResponseEntity.ok(commentService.getAllCommentsByPostId(postID));
	}

	// get comments by cid
	@GetMapping("/all/{postId}/{id}")
	public ResponseEntity<CommentDto> getCommentsByCid(@PathVariable long postId, @PathVariable long id) {
		return ResponseEntity.ok(commentService.getAllCommentsById(postId, id));
	}

	// update
	@PutMapping("/update/posts/{postId}/{id}")
	public ResponseEntity<CommentDto> updateComment(@PathVariable long postId, @PathVariable long id,
			@Valid @RequestBody CommentDto commentDto) {
		return new ResponseEntity<>(commentService.updateCommentById(postId, id, commentDto), HttpStatus.OK);
	}

	@DeleteMapping("/delete/posts/{postId}/{id}")
	public ResponseEntity<String> deleteComment(@PathVariable long postId, @PathVariable long id) {
		commentService.deleteCommentById(postId, id);
		return new ResponseEntity<>("Deleted Successfully", HttpStatus.OK);
	}
}
