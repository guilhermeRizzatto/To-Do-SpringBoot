package com.rizzatto.ToDo.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;



@Configuration
@EnableWebSecurity
public class SecurityConfig {
	
	@Autowired
	private CustomUserDetailsService service;
	
	@Autowired
	SecurityFilter securityFilter;
		
	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception{
		
		http
			.authorizeHttpRequests(authorize -> authorize
					.requestMatchers(HttpMethod.POST, "/login/create").permitAll()
					.requestMatchers(HttpMethod.GET, "/login/enter").permitAll()
					.requestMatchers(HttpMethod.POST, "/tokens/refresh").permitAll()
					.requestMatchers(HttpMethod.DELETE, "/cookies/deleteLoginCookie").permitAll()
					.anyRequest().authenticated()
			)
			.cors(Customizer.withDefaults())
			.csrf(crsf -> crsf.disable())
			.sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
			.addFilterBefore(securityFilter, UsernamePasswordAuthenticationFilter.class);
					
		return http.build();
	}
	
	@Bean
	public PasswordEncoder passwordEncoder() { 
		return new BCryptPasswordEncoder(); 
	}
	
	@Bean
	public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception { 
		return authenticationConfiguration.getAuthenticationManager();
	}

}
