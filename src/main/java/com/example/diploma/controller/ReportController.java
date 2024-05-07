package com.example.diploma.controller;

import com.example.diploma.dto.AddresseeDto;
import com.example.diploma.dto.ReportDto;
import com.example.diploma.job.EmailJob;
import com.example.diploma.service.ReportService;
import com.example.diploma.test.Test;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.quartz.*;
import org.springframework.web.bind.annotation.*;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.*;

import static org.quartz.CronScheduleBuilder.cronSchedule;

@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/reports")
public class ReportController {

    private final ReportService reportService;

    private final Scheduler scheduler;

    private final Test test;

    @PostMapping
    public ReportDto addReport(@Valid @RequestBody ReportDto reportDto) {
        try {
            for (AddresseeDto a : reportDto.getAddresses()) {
                JobDetail jobDetail = jobDetail(a.getEmail(), reportDto.getName());
                Trigger trigger = jobTrigger(jobDetail, reportDto.getCronExpression());
                scheduler.scheduleJob(jobDetail, trigger);
            }
            return reportService.createNewReport(reportDto);
        } catch (SchedulerException ex) {
            log.error(ex.getMessage());
            return reportService.createNewReport(reportDto);
        }
    }

    @GetMapping
    public List<ReportDto> getAllReports() {
        return reportService.getAllReports();
    }

    @GetMapping("/{id}")
    public Optional<ReportDto> getReportById(@PathVariable("id") UUID id) {
        return reportService.getReportByReportId(id);
    }

    @SneakyThrows
    private JobDetail jobDetail(String email, String subject) {
        JobDataMap jobDataMap = new JobDataMap();
//        Connection connection = test.dbConnection("jdbc:postgresql://localhost:5432/postgres", "postgres", "");
//        Statement statement = connection.createStatement();
//        ResultSet resultSet = test.getQueryResult("SELECT * FROM Addresses", statement);
        jobDataMap.put("email", email);
        jobDataMap.put("subject", subject);
        jobDataMap.put("body", test.a());

        return JobBuilder.newJob(EmailJob.class)
                .withIdentity(UUID.randomUUID().toString(), "email-jobs")
                .withDescription("Send Email Job")
                .usingJobData(jobDataMap)
                .storeDurably()
                .build();
    }

    private Trigger jobTrigger(JobDetail jobDetail, String crone) {
        return TriggerBuilder.newTrigger()
                .forJob(jobDetail)
                .withIdentity(jobDetail.getKey().getName(), "email-triggers")
                .withDescription("Send Email Trigger")
                .withSchedule(cronSchedule(crone))
                .build();
    }
}
