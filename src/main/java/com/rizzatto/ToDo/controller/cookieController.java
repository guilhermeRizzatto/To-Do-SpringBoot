package com.rizzatto.ToDo.controller;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.rizzatto.ToDo.entity.User;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@RestController
@RequestMapping("/cookies")
public class cookieController {
	
	@DeleteMapping("/deleteLoginCookie")
	public void deleteCookie(HttpServletResponse response) {
		
		Cookie tokenCookie = new Cookie("token", "");
		Cookie refreshTokenCookie = new Cookie("refreshToken", "");
		
		tokenCookie.setHttpOnly(true);
		tokenCookie.setSecure(true);
		tokenCookie.setPath("/");
		tokenCookie.setDomain("");
		response.addCookie(tokenCookie);
		
		refreshTokenCookie.setHttpOnly(true);
		refreshTokenCookie.setSecure(true);
		refreshTokenCookie.setPath("/");
		refreshTokenCookie.setDomain("");
		response.addCookie(refreshTokenCookie);
	}
	
	protected void sendCookies(HttpServletResponse response, String token, String refreshToken) {
		
		if(token != null) {
			Cookie tokenCookie = new Cookie("token", token);
			
			tokenCookie.setHttpOnly(true);
			tokenCookie.setSecure(false);
			tokenCookie.setPath("/");
			tokenCookie.setDomain("");
			tokenCookie.setAttribute("SameSite", "none");
			response.addCookie(tokenCookie);
		}
		
		if(refreshToken != null) {
			Cookie tokenCookie = new Cookie("refreshToken", refreshToken);
			
			tokenCookie.setHttpOnly(true);
			tokenCookie.setSecure(true);
			tokenCookie.setPath("/");
			tokenCookie.setDomain("");
			tokenCookie.setAttribute("SameSite", "none");
			response.addCookie(tokenCookie);
		}
	}
	
	
	protected String getRefreshTokenCookie(HttpServletRequest request) {
		Cookie[] cookies = request.getCookies();
		
		String token = null;
		
		if(cookies != null) {
			for(Cookie cookie : cookies) {
				if("refreshToken".equals(cookie.getName())) {
					System.out.println(cookie.getValue());
					token = cookie.getValue();
					return token.replace("refreshToken=", "");		
				}
			}
		}
		return null;
	}

}
