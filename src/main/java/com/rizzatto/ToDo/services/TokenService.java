package com.rizzatto.ToDo.services;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.rizzatto.ToDo.entity.User;

@Service
public class TokenService {
	
	@Value("${jwt.private.key}")
	private String privateKey;
	
	
	public String generateToken(String email) {
		try {
			
			Algorithm algorithm = Algorithm.HMAC256(privateKey);
			
			String token = JWT.create()
					.withIssuer("todo-backend")
					.withSubject(email)
					.withExpiresAt(generateExpirationDate())
					.sign(algorithm);
			
			return token;
			
		}catch (JWTCreationException e) {
			throw new RuntimeException("Error while authenticating");
		}
	}
	
	public String generateRefreshToken(String email) {
		try {
			Algorithm algorithm = Algorithm.HMAC256(privateKey);
			
			String refreshToken = JWT.create()
					.withIssuer("todo-backend")
					.withAudience("refresh")
					.withSubject(email)
					.withExpiresAt(generateRefreshExpirationDate())
					.sign(algorithm);
			
			return refreshToken;
			
		}catch (JWTCreationException e) {
			throw new RuntimeException("Error while authenticating");
		}
	}
	
	public String validateToken(String token) {
		try {
			Algorithm algorithm = Algorithm.HMAC256(privateKey);
			
			return JWT.require(algorithm)
					.withIssuer("todo-backend")
					.build()
					.verify(token)
					.getSubject();
			
		}catch (JWTVerificationException e) {
			return null;
		}
	}
	
	public String validateRefreshToken(String refreshToken) {
		try {
			Algorithm algorithm = Algorithm.HMAC256(privateKey);
			
			return JWT.require(algorithm)
				.withIssuer("todo-backend")
				.withAudience("refresh")
				.build()
				.verify(refreshToken)
				.getSubject();
			
			
		}catch (JWTVerificationException e) {
			System.out.println(e);
			return null;
		}
	}
	
	private Instant generateExpirationDate() {
		return LocalDateTime.now().plusMinutes(1).toInstant(ZoneOffset.of("-03:00"));
	}
	
	private Instant generateRefreshExpirationDate() {
		return LocalDateTime.now().plusDays(15).toInstant(ZoneOffset.of("-03:00"));
	}

}
