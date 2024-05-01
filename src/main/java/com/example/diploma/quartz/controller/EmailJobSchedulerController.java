package com.example.diploma.quartz.controller;

import com.example.diploma.dto.ReportDto;
import com.example.diploma.quartz.ScheduleEmailRequest;
import com.example.diploma.quartz.job.EmailJob;
import com.example.diploma.quartz.ScheduleEmailResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.quartz.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.UUID;

import static org.quartz.CronScheduleBuilder.cronSchedule;

@Controller
@Slf4j
@RequiredArgsConstructor
public class EmailJobSchedulerController {

    private final Scheduler scheduler;

    @PostMapping("/scheduleemail")
    public ResponseEntity<ReportDto> scheduleEmail(@Valid @RequestBody ScheduleEmailRequest scheduleEmailRequest) {
        try {
            String croneExpression = "0 * * * * ?";
            JobDetail jobDetail = buildJobDetail(scheduleEmailRequest);
            Trigger trigger = buildJobTrigger(jobDetail, croneExpression);
            scheduler.scheduleJob(jobDetail, trigger);

            return ResponseEntity.ok().build();
        } catch (SchedulerException ex) {
            log.error("Error scheduling email", ex);

            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    private JobDetail buildJobDetail(ScheduleEmailRequest scheduleEmailRequest) {
        JobDataMap jobDataMap = new JobDataMap();

        jobDataMap.put("email", scheduleEmailRequest.getEmail());
        jobDataMap.put("subject", scheduleEmailRequest.getSubject());
        jobDataMap.put("body", scheduleEmailRequest.getBody());

        return JobBuilder.newJob(EmailJob.class)
                .withIdentity(UUID.randomUUID().toString(), "email-jobs")
                .withDescription("Send Email Job")
                .usingJobData(jobDataMap)
                .storeDurably()
                .build();
    }

    private Trigger buildJobTrigger(JobDetail jobDetail, String crone) {
        return TriggerBuilder.newTrigger()
                .forJob(jobDetail)
                .withIdentity(jobDetail.getKey().getName(), "email-triggers")
                .withDescription("Send Email Trigger")
                .withSchedule(cronSchedule(crone))
                .build();
    }
}
