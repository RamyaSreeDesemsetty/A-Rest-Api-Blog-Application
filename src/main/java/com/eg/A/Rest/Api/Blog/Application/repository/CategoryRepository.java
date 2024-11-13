package com.eg.A.Rest.Api.Blog.Application.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.eg.A.Rest.Api.Blog.Application.entity.Category;

public interface CategoryRepository extends JpaRepository<Category, Long> {

}
