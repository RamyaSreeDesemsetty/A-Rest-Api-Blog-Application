package com.eg.A.Rest.Api.Blog.Application.DTO;

import java.util.Set;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
@Schema(description = "PostDto Model Information")
public class PostDto {
	private Long id;
	// title should not be null or empty
	// title should have atleast 2 characters
	@Schema(description = "Blog Post Title")
	@NotEmpty
	@Size(min = 5, message = "Atleast should have 5 characters")
	private String title;

	@NotEmpty
	@Size(min = 10, message = "Atleast should have 10 characters")
	private String description;

	@NotEmpty
	private String content;
	private Set<CommentDto> comments;

	private Long categoryId;
}
