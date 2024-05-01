package com.example.diploma;

//import com.example.diploma.service.EmailSenderService;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;

import java.util.UUID;

@SpringBootApplication
@RequiredArgsConstructor
public class DiplomaApplication {
//
//	private final EmailSenderService emailSenderService;

	public static void main(String[] args) {
		SpringApplication.run(DiplomaApplication.class, args);
	}

//	@EventListener(ApplicationReadyEvent.class)
//	public void sendEmail() {
////		emailSenderService.sendEmail("dante5453@gmail.com", "This is a test", "This is a body");
//	}

}

