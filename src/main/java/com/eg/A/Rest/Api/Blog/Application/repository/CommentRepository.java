package com.eg.A.Rest.Api.Blog.Application.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.eg.A.Rest.Api.Blog.Application.entity.Comment;
import com.eg.A.Rest.Api.Blog.Application.entity.Post;

public interface CommentRepository extends JpaRepository<Comment, Long> {

	List<Comment> findByPost(Post post);

}
