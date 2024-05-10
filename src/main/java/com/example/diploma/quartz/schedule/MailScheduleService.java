package com.example.diploma.quartz.schedule;

import com.example.diploma.dto.AddresseeDto;
import com.example.diploma.dto.ReportDto;
import com.example.diploma.dto.SQLAuthorisationDto;
import com.example.diploma.entity.SQLAuthorisation;
import com.example.diploma.quartz.job.EmailJob;
import com.example.diploma.quartz.triggers.SimpleTriggerListener;
import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.quartz.*;
import org.quartz.impl.matchers.GroupMatcher;
import org.springframework.stereotype.Service;

import java.sql.*;
import java.util.*;
import java.util.stream.Collectors;

import static org.quartz.CronScheduleBuilder.cronSchedule;

@Service
@RequiredArgsConstructor
@Slf4j
public class MailScheduleService {

    private final Scheduler scheduler;

    public void createSchedule(ReportDto reportDto) {
        try {
            JobDetail jobDetail = jobDetail(reportDto);
            Trigger trigger = jobTrigger(jobDetail, reportDto);

            scheduler.getContext().put("reportDto", reportDto);
            scheduler.scheduleJob(jobDetail, trigger);
        } catch (SchedulerException e) {
            log.error(e.getMessage());
        }
    }

    @PostConstruct
    public void init() {
        try {
            scheduler.start();
        } catch (SchedulerException e) {
            log.error(e.getMessage());
        }
    }

    @PreDestroy
    public void destroy() {
        try {
            scheduler.shutdown();
        } catch (SchedulerException e) {
            log.error(e.getMessage());
        }
    }

    private JobDetail jobDetail(ReportDto reportDto) {
        JobDataMap jobDataMap = new JobDataMap();

        jobDataMap.put("name", reportDto.getName());
        jobDataMap.put("addressesSize", String.valueOf(reportDto.getAddresses().size()));
        for (int i = 0; i < reportDto.getAddresses().size(); i++) {
            jobDataMap.put("addresses[" + i + "]", reportDto.getAddresses().get(i).getEmail());
        }
        jobDataMap.put("url", reportDto.getSqlAuthorisations().getFirst().getUrl());
        jobDataMap.put("login", reportDto.getSqlAuthorisations().getFirst().getLogin());
        jobDataMap.put("password", reportDto.getSqlAuthorisations().getFirst().getPassword());
        jobDataMap.put("query", reportDto.getSqlAuthorisations().getFirst().getSqlRequests().getFirst().getRequest());


        return JobBuilder.newJob(EmailJob.class)
                .withIdentity(UUID.randomUUID().toString(), reportDto.getName())
                .requestRecovery(true)
                .withDescription("Send Email Job")
                .usingJobData(jobDataMap)
                .storeDurably()
                .build();
    }

    private Trigger jobTrigger(JobDetail jobDetail, ReportDto reportDto) {
        return TriggerBuilder.newTrigger()
                .startNow()
                .forJob(jobDetail)
                .withIdentity(jobDetail.getKey().getName(), reportDto.getName())
                .withDescription("Send Email Trigger")
                .withSchedule(cronSchedule(reportDto.getCronExpression()))
                .build();
    }

    public String toHtmlTable(String url, String login, String password, String query) throws SQLException {
        Connection connection = DriverManager.getConnection(
                url,
                login,
                password);
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery(query);
        ResultSetMetaData resultSetMetaData = resultSet.getMetaData();

        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("<table style=\"border: 1px solid #000000; border-collapse: collapse;\">");
        while (resultSet.next()) {
            stringBuilder.append("<tr>");
            for (int i = 1; i <= resultSetMetaData.getColumnCount(); i++) {
                stringBuilder.append("<td style=\"border: 1px solid #000000; padding: 5px\">").append(resultSet.getString(i)).append("</td>");
            }
            stringBuilder.append("</tr>");
        }
        stringBuilder.append("</th>");
        return stringBuilder.toString();
    }

    public void updateReportDto(ReportDto reportDto) {
        try {
            JobDetail jobDetail = scheduler.getJobDetail(new JobKey(reportDto.getName()));
            if (jobDetail != null) {
                jobDetail.getJobDataMap().put(reportDto.getName(), reportDto);
                scheduler.addJob(jobDetail, true, true);
            } else {
                log.error("jobDetail is null");
            }
        } catch (SchedulerException e) {
            log.error(e.getMessage());
        }
    }

    public List<ReportDto> getReportDtos() {
        try {
            return scheduler.getJobKeys(GroupMatcher.anyGroup())
                    .stream().map(jobKey -> {
                        try {
                            JobDetail jobDetail = scheduler.getJobDetail(jobKey);
                            return (ReportDto) jobDetail.getJobDataMap().get(jobKey.getName());
                        } catch (SchedulerException e) {
                            log.error(e.getMessage());
                            return null;
                        }
                    })
                    .filter(Objects::nonNull)
                    .collect(Collectors.toList());
        } catch (SchedulerException e) {
            log.error(e.getMessage());
            return Collections.emptyList();
        }
    }

    public ReportDto getReportDto(String id) {
        try {
            JobDetail jobDetail = scheduler.getJobDetail(new JobKey(id));
            if (jobDetail != null) {
                return (ReportDto) jobDetail.getJobDataMap().get(id);
            } else {
                return null;
            }
        } catch (SchedulerException e) {
            log.error(e.getMessage());
            return null;
        }
    }
}
