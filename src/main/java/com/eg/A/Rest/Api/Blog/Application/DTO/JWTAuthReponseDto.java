package com.eg.A.Rest.Api.Blog.Application.DTO;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class JWTAuthReponseDto {

	private String accessToken;
	private String tokenType = "Bearer";
}
