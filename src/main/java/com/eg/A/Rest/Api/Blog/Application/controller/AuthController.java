package com.eg.A.Rest.Api.Blog.Application.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.eg.A.Rest.Api.Blog.Application.DTO.JWTAuthReponseDto;
import com.eg.A.Rest.Api.Blog.Application.DTO.LoginDto;
import com.eg.A.Rest.Api.Blog.Application.DTO.RegisterDto;
import com.eg.A.Rest.Api.Blog.Application.service.AuthService;

@RestController
@RequestMapping("/api/v1/auth")
public class AuthController {
	private AuthService authservice;

	public AuthController(AuthService authservice) {
		super();
		this.authservice = authservice;
	}

// login restapi
	// @PostMapping(value = {"/login","/signin"})
	@PostMapping("/login")
	public ResponseEntity<JWTAuthReponseDto> loginInto(@RequestBody LoginDto loginDto) {
		String token = authservice.login(loginDto);
		JWTAuthReponseDto jwtAuthResponse = new JWTAuthReponseDto();
		jwtAuthResponse.setAccessToken(token);
		return ResponseEntity.ok(jwtAuthResponse);
	}

	// register
	// @PostMapping(value = {"/register","/signup"})
	@PostMapping("/register")
	public ResponseEntity<String> registerInto(@RequestBody RegisterDto registerDto) {
		return new ResponseEntity<>(authservice.register(registerDto), HttpStatus.CREATED);
	}

}