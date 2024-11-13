package com.eg.A.Rest.Api.Blog.Application.security;

import java.security.Key;
import java.util.Date;

import javax.crypto.SecretKey;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import com.eg.A.Rest.Api.Blog.Application.exception.BlogAPIException;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.UnsupportedJwtException;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;

@Component
public class JwtTokenProvider {
	@Value("${app.jwt-secret}")
	private String jwtSecret;
	@Value("${app-jwt-expiration-milliseconds}")
	private long jwtExpirationDate;

//generate jwt token
	public String generateToken(Authentication authentication) {
		String userName = authentication.getName();

		Date CurrentDate = new Date();

		Date expireDate = new Date(CurrentDate.getTime() + jwtExpirationDate);

		String token = Jwts.builder().subject(userName).issuedAt(new Date()).expiration(expireDate).signWith(key())
				.compact();
		return token;
	}

	private Key key() {
		return Keys.hmacShaKeyFor(Decoders.BASE64.decode(jwtSecret));
	}

// get username from jwttoken
	public String getUserName(String token) {
	
		return Jwts.parser()
			.verifyWith((SecretKey) key())
	
				.build().parseSignedClaims(token).getPayload().getSubject();
}

//validate jwt token
public boolean validateToken(String token) {
	try {
		Jwts.parser().verifyWith((SecretKey) key()).build().parse(token);
		return true;
	}

	catch (MalformedJwtException malformedJwtException) {
		throw new BlogAPIException(HttpStatus.BAD_REQUEST, "Invalid JWT Token");

	} catch (ExpiredJwtException expiredJwtException) {
		throw new BlogAPIException(HttpStatus.BAD_REQUEST, "Expired JWT Token");

	} catch (UnsupportedJwtException unsupportedJwtException) {
		throw new BlogAPIException(HttpStatus.BAD_REQUEST, "UnSupported JWT Token");

	} catch (IllegalArgumentException illegalArgumentException) {
		throw new BlogAPIException(HttpStatus.BAD_REQUEST, " JWT claims string is null or empty ");
	}

}

}
