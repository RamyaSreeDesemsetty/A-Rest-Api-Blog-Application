package com.eg.A.Rest.Api.Blog.Application.exception;

import org.springframework.http.HttpStatus;

public class BlogAPIException extends RuntimeException {

	private HttpStatus status;
	private String message;

	public BlogAPIException(HttpStatus status, String message) {
		super();
		this.status = status;
		this.message = message;
	}

	public HttpStatus getStatus() {
		return status;
	}

	public String getMessage() {
		return message;
	}

}
