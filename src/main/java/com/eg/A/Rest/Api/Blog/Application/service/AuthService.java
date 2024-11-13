package com.eg.A.Rest.Api.Blog.Application.service;

import com.eg.A.Rest.Api.Blog.Application.DTO.LoginDto;
import com.eg.A.Rest.Api.Blog.Application.DTO.RegisterDto;

public interface AuthService {
	String login(LoginDto loginDto);

	String register(RegisterDto registerDto);
}
