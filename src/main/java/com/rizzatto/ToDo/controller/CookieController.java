package com.rizzatto.ToDo.controller;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@RestController
@RequestMapping("/cookies")
public class CookieController {
	
	@DeleteMapping("/deleteLoginCookie")
	public void deleteCookie(HttpServletResponse response) {
		
		Cookie tokenCookie = new Cookie("token", "");
		Cookie refreshTokenCookie = new Cookie("refreshToken", "");
		
		tokenCookie.setHttpOnly(true);
		tokenCookie.setSecure(true);
		tokenCookie.setMaxAge(4);
		tokenCookie.setPath("/");
		tokenCookie.setDomain("");
		tokenCookie.setAttribute("SameSite", "none");
		response.addCookie(tokenCookie);
		
		refreshTokenCookie.setHttpOnly(true);
		refreshTokenCookie.setSecure(true);
		refreshTokenCookie.setMaxAge(4);
		refreshTokenCookie.setPath("/");
		refreshTokenCookie.setDomain("");
		refreshTokenCookie.setAttribute("SameSite", "none");
		response.addCookie(refreshTokenCookie);
	}
	
	protected void sendCookies(HttpServletResponse response, String token, String refreshToken) {
		
		if(token != null) {
			Cookie tokenCookie = new Cookie("token", token);
			
			tokenCookie.setHttpOnly(true);
			tokenCookie.setSecure(true);
			tokenCookie.setMaxAge(10800);
			tokenCookie.setPath("/");
			tokenCookie.setDomain("");
			tokenCookie.setAttribute("SameSite", "none");
			response.addCookie(tokenCookie);
		}
		
		if(refreshToken != null) {
			Cookie tokenCookie = new Cookie("refreshToken", refreshToken);
			
			tokenCookie.setHttpOnly(true);
			tokenCookie.setSecure(true);
			tokenCookie.setMaxAge(1296000);
			tokenCookie.setPath("/");
			tokenCookie.setDomain("");
			tokenCookie.setAttribute("SameSite", "none");
			response.addCookie(tokenCookie);
		}
	}
	
	
	public String getRefreshTokenCookie(HttpServletRequest request) {
		Cookie[] cookies = request.getCookies();
		
		String token = null;
		
		if(cookies != null) {
			for(Cookie cookie : cookies) {
				if("refreshToken".equals(cookie.getName())) {
					token = cookie.getValue();
					return token.replace("refreshToken=", "");		
				}
			}
		}
		return null;
	}
	
	public String recoverToken(HttpServletRequest request) {
		Cookie[] cookies = request.getCookies();
		String token = null;
		
		if(cookies != null) {
			for(Cookie cookie : cookies) {
				if("token".equals(cookie.getName())) {
					token = cookie.getValue();
					return token.replace("token=", "");		
				}
			}
		}
		return null;
	}

}
