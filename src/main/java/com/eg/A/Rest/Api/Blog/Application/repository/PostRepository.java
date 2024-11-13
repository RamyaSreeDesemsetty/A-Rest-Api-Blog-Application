package com.eg.A.Rest.Api.Blog.Application.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.eg.A.Rest.Api.Blog.Application.entity.Post;

public interface PostRepository extends JpaRepository<Post, Long> {

	List<Post> findByCategoryId(Long categoryId);
}
