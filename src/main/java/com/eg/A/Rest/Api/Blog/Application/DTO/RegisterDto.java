package com.eg.A.Rest.Api.Blog.Application.DTO;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class RegisterDto {
	private String name;
	private String userName;
	private String email;
	private String password;
}
