package com.eg.A.Rest.Api.Blog.Application.utils;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

public class PasswordGeneratorEncoder {

	public static void main(String[] args) {
		PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
		System.out.println(passwordEncoder.encode("Admin@12"));
		System.out.println(passwordEncoder.encode("Ramya@12"));
		System.out.print(passwordEncoder.encode("Sree@12"));

	}

}
