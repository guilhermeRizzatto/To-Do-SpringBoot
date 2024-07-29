package com.rizzatto.ToDo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class ToDoApplication {

	public static void main(String[] args) {
		System.out.println("hello world");
		SpringApplication.run(ToDoApplication.class, args);
	}

}
