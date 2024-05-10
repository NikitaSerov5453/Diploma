package com.example.diploma.quartz.job;

import com.example.diploma.dto.ReportDto;
import com.example.diploma.dto.SQLAuthorisationDto;
import com.example.diploma.dto.SQLRequestDto;
import com.example.diploma.entity.Report;
import com.example.diploma.quartz.schedule.MailScheduleService;
import com.example.diploma.service.AddresseeService;
import com.example.diploma.service.MailSenderService;
import com.example.diploma.service.ReportService;
import com.example.diploma.test.Test;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.quartz.*;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.mail.MailProperties;
import org.springframework.stereotype.Component;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;


@Component
@Slf4j
@RequiredArgsConstructor
@PersistJobDataAfterExecution
@DisallowConcurrentExecution
public class EmailJob implements Job {

    private final MailProperties mailProperties;

    private final MailSenderService mailSenderService;

    private final AddresseeService addresseeService;

    private final MailScheduleService mailScheduleService;

    private final ReportService reportService;

    @Override
    public void execute(JobExecutionContext jobExecutionContext) {
        log.info("Executing Job with key {}", jobExecutionContext.getJobDetail().getKey());

        JobDataMap jobDataMap = jobExecutionContext.getMergedJobDataMap();

        List<String> addresses = new ArrayList<>();

        String name = jobDataMap.getString("name");
        int size = Integer.parseInt(jobDataMap.getString("addressesSize"));
        for (int i = 0; i < size; i++) {
            addresses.add(jobDataMap.getString("addresses[" + i + "]"));
        }
        String url = jobDataMap.getString("url");
        String login = jobDataMap.getString("login");
        String password = jobDataMap.getString("password");
        String query = jobDataMap.getString("query");

        String htmlTable = "hello";
        try {
            htmlTable = mailScheduleService.toHtmlTable(url, login, password, query);
        } catch (SQLException e) {
            log.error(e.getMessage());
        }

        for (String address : addresses) {
            mailSenderService.sendMail(mailProperties.getUsername(), address, name, htmlTable);
        }
    }
}
