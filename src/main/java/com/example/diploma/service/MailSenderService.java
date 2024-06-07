package com.example.diploma.service;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class MailSenderService {

    private final JavaMailSender mailSender;

    public void sendMail(String fromEmail, String[] toEmail, String subject, String text) {
        try {
            log.info("Sending Email to {}", fromEmail);
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");

            helper.setSubject(subject);
            message.setContent(text, "text/html;charset=UTF-8");
            helper.setFrom(fromEmail);
            helper.setTo(toEmail);

//            FileSystemResource file = new FileSystemResource("C:/kitty.png");
//            helper.addAttachment(Objects.requireNonNull(file.getFilename()), file);

            mailSender.send(message);
        } catch (MessagingException e) {
            log.error(e.getMessage());
        }
    }
}
