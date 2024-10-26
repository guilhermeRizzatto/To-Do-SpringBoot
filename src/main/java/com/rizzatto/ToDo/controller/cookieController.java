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
		
		Cookie loginCookie = new Cookie("loginCookie", "");
		
		loginCookie.setHttpOnly(true);
		loginCookie.setSecure(true);
		loginCookie.setPath("/");
		loginCookie.setDomain("");
		response.addCookie(loginCookie);
	}
	
	protected void sendLoginCookies(HttpServletResponse response, User user) {
		String emailAndPassword = user.getEmail() + "|" + user.getPassword();	
		Cookie loginCookie = new Cookie("loginCookie", emailAndPassword);
		
		loginCookie.setHttpOnly(true);
		loginCookie.setSecure(false);
		loginCookie.setPath("/");
		loginCookie.setDomain("");
		response.addCookie(loginCookie);
	}
	
	protected String[] getLoginCookies(HttpServletRequest request) {
		Cookie[] cookies = request.getCookies();
		String loginCookie = null;
		
		if(cookies != null) {
			for(Cookie cookie : cookies) {
				if("loginCookie".equals(cookie.getName())) {
					loginCookie = cookie.getValue();
					return loginCookie.replace("loginCookie=", "").split("\\|");			
				}
			}
		}
		return null;
	}

}
