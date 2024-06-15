package com.example.diploma;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;



import org.springframework.web.servlet.config.annotation.EnableWebMvc;

@SpringBootApplication
@EnableWebMvc
public class DiplomaApplication {
	public static void main(String[] args) {
		SpringApplication.run(DiplomaApplication.class, args);
	}
}