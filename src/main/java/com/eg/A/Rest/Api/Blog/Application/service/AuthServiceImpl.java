package com.eg.A.Rest.Api.Blog.Application.service;


import java.util.HashSet;
import java.util.Set;

import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.eg.A.Rest.Api.Blog.Application.DTO.LoginDto;
import com.eg.A.Rest.Api.Blog.Application.DTO.RegisterDto;
import com.eg.A.Rest.Api.Blog.Application.entity.Role;
import com.eg.A.Rest.Api.Blog.Application.entity.User;
import com.eg.A.Rest.Api.Blog.Application.exception.BlogAPIException;
import com.eg.A.Rest.Api.Blog.Application.repository.RoleRepository;
import com.eg.A.Rest.Api.Blog.Application.repository.UserRepository;
import com.eg.A.Rest.Api.Blog.Application.security.JwtTokenProvider;

@Service
public class AuthServiceImpl implements AuthService {
	private AuthenticationManager authenticationManager;
	private UserRepository userRepo;
	private RoleRepository roleRepo;
	private PasswordEncoder passwordEncoder;
	private JwtTokenProvider jwtTokenProvider;

	public AuthServiceImpl(AuthenticationManager authenticationManager, UserRepository userRepo,
			RoleRepository roleRepo, PasswordEncoder passwordEncoder, JwtTokenProvider jwtTokenProvider) {
		super();
		this.authenticationManager = authenticationManager;
		this.userRepo = userRepo;
		this.roleRepo = roleRepo;
		this.passwordEncoder = passwordEncoder;
		this.jwtTokenProvider = jwtTokenProvider;
	}

	public String login(LoginDto loginDto) {
		Authentication authentication = authenticationManager.authenticate(
				new UsernamePasswordAuthenticationToken(loginDto.getUsernameOrEmail(), loginDto.getPassword()));

		SecurityContextHolder.getContext().setAuthentication(authentication);

		String token = jwtTokenProvider.generateToken(authentication);

		return token;
	}

	@Override
	public String register(RegisterDto registerDto) {
		// exists
		if (userRepo.existsByUserName(registerDto.getUserName())) {
			throw new BlogAPIException(HttpStatus.BAD_REQUEST, "Username is already exists");
		}
		// user email exists
		if (userRepo.existsByEmail(registerDto.getEmail())) {
			throw new BlogAPIException(HttpStatus.BAD_REQUEST, "Email is already exists");
		}
		User user = new User();
		user.setName(registerDto.getName());
		user.setUserName(registerDto.getUserName());
		user.setEmail(registerDto.getEmail());
		user.setPassword(passwordEncoder.encode(registerDto.getPassword()));
		Set<Role> roles = new HashSet<>();
		Role userRole = roleRepo.findByName("Role_User").get();
		roles.add(userRole);
		user.setRoles(roles);
		userRepo.save(user);

		return "Registered Successfully";
	}

}
