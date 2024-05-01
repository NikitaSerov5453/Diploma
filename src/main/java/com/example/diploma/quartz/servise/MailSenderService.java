package com.example.diploma.quartz.servise;

import com.mchange.net.MailSender;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;

@Component
@RequiredArgsConstructor
@Slf4j
public class MailSenderService {

    private final JavaMailSender mailSender;

    public void sendMail(String fromEmail, String to, String subject, String text) {
        try {
            log.info("Sending Email to {}", to);
            MimeMessage message = mailSender.createMimeMessage();

            MimeMessageHelper helper = new MimeMessageHelper(message, StandardCharsets.UTF_8.toString());
            helper.setSubject(subject);
            helper.setText(text, true);
            helper.setFrom(fromEmail);
            helper.setTo(to);

            mailSender.send(message);
        } catch (MessagingException e) {
            log.error(e.getMessage());
        }
    }


}
