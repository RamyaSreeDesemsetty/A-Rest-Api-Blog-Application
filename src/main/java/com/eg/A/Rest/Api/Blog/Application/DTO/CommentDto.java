package com.eg.A.Rest.Api.Blog.Application.DTO;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class CommentDto {
	private Long id;
	@NotEmpty
	@Size(min = 5, message = "Atleast should have 5 characters")
	private String name; // Adding the name field
	@NotEmpty(message = "should not be empty")
	@Size(min = 10, message = "Atleast should have 10 characters")
	@Email
	private String email;
	@NotEmpty(message = "Should not be empty")
	@Size(min = 10, message = "Atleast should have 10 characters")
	private String body;
}
