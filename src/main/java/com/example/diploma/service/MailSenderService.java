package com.example.diploma.service;

import jakarta.activation.DataSource;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import jakarta.mail.util.ByteArrayDataSource;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FileUtils;
import org.apache.poi.util.IOUtils;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.InputStreamSource;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.util.Objects;

@Component
@RequiredArgsConstructor
@Slf4j
public class MailSenderService {

    private final JavaMailSender mailSender;

    public void sendMail(String fromEmail, String[] toEmail, String subject, String text, ByteArrayOutputStream byteArrayOutputStream) throws MessagingException {
        try {
            log.info("Sending Email to {}", fromEmail);
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");

            helper.setSubject(subject);
            if (text.length() < 6000) {
                message.setContent(text, "text/html;charset=UTF-8");
            } else {
                InputStream inputStream = new ByteArrayInputStream(byteArrayOutputStream.toByteArray());
                DataSource ds = new ByteArrayDataSource(inputStream, "application/octet-stream");
                helper.setText("Отчет во вложении");
                helper.addAttachment("report.xlsx", ds);
            }
            helper.setFrom(fromEmail);
            helper.setTo(toEmail);



//            FileSystemResource file = new FileSystemResource("C:/kitty.png");
//
//            helper.addAttachment(Objects.requireNonNull(file.getFilename()), file);

            mailSender.send(message);
        } catch (MessagingException | IOException e) {
            log.error(e.getMessage());
        }
    }
}
