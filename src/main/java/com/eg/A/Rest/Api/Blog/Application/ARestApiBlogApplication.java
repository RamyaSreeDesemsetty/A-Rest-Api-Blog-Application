package com.eg.A.Rest.Api.Blog.Application;

import org.modelmapper.ModelMapper;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import io.swagger.v3.oas.annotations.ExternalDocumentation;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.info.License;

@SpringBootApplication
@OpenAPIDefinition(
		info = @Info(
				title = "Spring Boot Blog App Rest APIs",
		        description ="Spring Boot Blog App Rest APIs Documentation",
		         version ="v1.0",
		         contact =@Contact(
				name="Ramya",
				email="ramya@gmail.com",
				url="https://www.javaguides"
				),
		license =@License(
				name="Apache 2.0",
				url ="https://www.javaguides/lisense"
		)
		),

externalDocs = @ExternalDocumentation(
		description ="Spring Boot Blog App Documentation",
				url = "https://github.com//ramyasreedesemsetty")
)

public class ARestApiBlogApplication {
	@Bean
	public ModelMapper msodelmapper() {
		return new ModelMapper();
	}
	public static void main(String[] args) {
		SpringApplication.run(ARestApiBlogApplication.class, args);
	}

}
