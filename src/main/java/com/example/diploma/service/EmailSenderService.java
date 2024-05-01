//package com.example.diploma.service;
//
//import com.example.diploma.dto.AddresseeDto;
//import lombok.RequiredArgsConstructor;
//import org.springframework.mail.SimpleMailMessage;
//import org.springframework.mail.javamail.JavaMailSender;
//import org.springframework.scheduling.annotation.Scheduled;
//import org.springframework.stereotype.Service;
//
//import java.util.List;
//
//@Service
//@RequiredArgsConstructor
//public class EmailSenderService {
//
//    private final JavaMailSender mailSender;
//
////    @Scheduled(cron = "0 * * * * ?", zone = "Europe/Moscow")
//    public void sendEmail() {
//        SimpleMailMessage message = new SimpleMailMessage();
//
//        message.setTo("dante5453@gmail.com");
//        message.setText("test");
//        message.setSubject("test");
//
//        mailSender.send(message);
//    }

//    @Scheduled(cron = "0 * * * * ?", zone = "Europe/Moscow")
//    public void sendEmailList(List<AddresseeDto> addresseeDtoList) {
//        for (AddresseeDto addresseeDto : addresseeDtoList) {
//            sendEmail(addresseeDto.getEmail(), "test", "test");
//        }
//    }
//}
