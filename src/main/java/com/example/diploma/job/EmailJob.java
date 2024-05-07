package com.example.diploma.job;

import com.example.diploma.service.MailSenderService;
import com.example.diploma.test.Test;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.quartz.*;
import org.springframework.boot.autoconfigure.mail.MailProperties;
import org.springframework.stereotype.Component;

import java.sql.SQLException;


@Component
@Slf4j
@RequiredArgsConstructor
public class EmailJob implements Job {

    private final MailProperties mailProperties;

    private final MailSenderService mailSenderService;



    @Override
    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        log.info("Executing Job with key {}", jobExecutionContext.getJobDetail().getKey());

        JobDataMap jobDataMap = jobExecutionContext.getMergedJobDataMap();
        String subject = jobDataMap.getString("subject");
        String body = jobDataMap.getString("body");

        String email = jobDataMap.getString("email");

        mailSenderService.sendMail(mailProperties.getUsername(), email, subject, body);
    }
}
