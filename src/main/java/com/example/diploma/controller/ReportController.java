package com.example.diploma.controller;

import com.example.diploma.dto.AddresseeDto;
import com.example.diploma.dto.ReportDto;
import com.example.diploma.quartz.job.EmailJob;
import com.example.diploma.service.ReportService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.quartz.*;
import org.springframework.web.bind.annotation.*;

import javax.print.attribute.standard.JobMediaSheets;
import java.util.*;

import static org.quartz.CronScheduleBuilder.cronSchedule;

@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/reports")
public class ReportController {

    private final ReportService reportService;

    private final Scheduler scheduler;

    @PostMapping
    public ReportDto addReport(@Valid @RequestBody ReportDto reportDto) {
        try {
            for (AddresseeDto a : reportDto.getAddresses()) {
                JobDetail jobDetail = jobDetail(a.getEmail());
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

    private JobDetail jobDetail(String email) {
        JobDataMap jobDataMap = new JobDataMap();

        jobDataMap.put("body", "reportDto.getCronExpression()");
        jobDataMap.put("email", email);
        jobDataMap.put("subject", "them");

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
