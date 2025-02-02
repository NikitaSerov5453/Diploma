package com.example.diploma.quartz.schedule;

import com.example.diploma.dto.ReportDto;
import com.example.diploma.quartz.schedule.job.EmailJob;
import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;

import lombok.RequiredArgsConstructor;
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

    /*
    Создание задачи которую необходимо запускать по определенному рассписанию
     */
    public void createSchedule(ReportDto reportDto) {
        try {
            JobDetail jobDetail = jobDetail(reportDto);
            Trigger trigger = jobTrigger(jobDetail, reportDto);

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

    /*
    Нужен для передачи данных необходимых для работы, а так же передает их в БД для хранения
     */

    private JobDetail jobDetail(ReportDto reportDto) {
        JobDataMap jobDataMap = new JobDataMap();
//        jobDataMap.put("reportDto", reportDto);

        jobDataMap.put("name", reportDto.getName());
        jobDataMap.put("addressesSize", String.valueOf(reportDto.getAddresses().size()));
        for (int i = 0; i < reportDto.getAddresses().size(); i++) {
            jobDataMap.put("addresses[" + i + "]", reportDto.getAddresses().get(i).getEmail());
        }
        jobDataMap.put("authorisationsSize", String.valueOf(reportDto.getSqlAuthorisations().size()));
        for (int i = 0; i < reportDto.getSqlAuthorisations().size(); i++) {
            jobDataMap.put("url[" + i + "]", reportDto.getSqlAuthorisations().get(i).getUrl());
            jobDataMap.put("login[" + i + "]", reportDto.getSqlAuthorisations().get(i).getLogin());
            jobDataMap.put("password[" + i + "]", reportDto.getSqlAuthorisations().get(i).getPassword());

            jobDataMap.put("querySize[" + i + "]", String.valueOf(reportDto.getSqlAuthorisations().get(i).getSqlRequests().size()));
            for (int j = 0; j < reportDto.getSqlAuthorisations().get(i).getSqlRequests().size(); j++) {
                jobDataMap.put("query[" + i + "][" + j + "]", reportDto.getSqlAuthorisations().get(i).getSqlRequests().get(j).getRequest());
            }
        }
        return JobBuilder.newJob(EmailJob.class)
                .withIdentity(reportDto.getAutomatedReporting().toString(), reportDto.getName())
                .requestRecovery(true)
                .withDescription("Send Email Job")
                .usingJobData(jobDataMap)
                .storeDurably()
                .build();
    }

    /*
    Триггер задачи.

    Нужен что бы запускать задачу по рассписанию

    Использует Крон выражения
     */

    private Trigger jobTrigger(JobDetail jobDetail, ReportDto reportDto) {
        return TriggerBuilder.newTrigger()
                .startNow()
                .forJob(jobDetail)
                .withIdentity(reportDto.getAutomatedReporting().toString(), reportDto.getName())
                .withDescription("Send Email Trigger")
                .withSchedule(cronSchedule(reportDto.getCronExpression()))
                .build();
    }

    /*
    Подключение к сторонней БД
     */
    public Connection connection(String url, String login, String password) {
        try {
            return DriverManager.getConnection(
                    url,
                    login,
                    password);
        } catch (SQLException e) {
            log.error(e.getMessage());
            return null;
        }
    }

    /*
    Преобразование таблицы из бд в единую String с HTML разметкой
     */
    public StringBuilder toHtmlTable(ResultSet resultSet) throws SQLException {
        ResultSetMetaData resultSetMetaData = resultSet.getMetaData();
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("<table style=\"border: 1px solid #000000; border-collapse: collapse; margin-bottom: 20px;\">");
        stringBuilder.append("<thead>");
        stringBuilder.append("<tr>");
        for (int j = 1; j <= resultSetMetaData.getColumnCount(); j++) {
            stringBuilder.append("<th style=\"border: 1px solid #000000; padding: 5px; background-color: #bfbfbf;\">").append(resultSetMetaData.getColumnLabel(j)).append("</th>");
        }
        stringBuilder.append("</tr>");
        stringBuilder.append("</thead>");
        while (resultSet.next()) {
            stringBuilder.append("<tbody>");
            stringBuilder.append("<tr>");
            for (int i = 1; i <= resultSetMetaData.getColumnCount(); i++) {
                stringBuilder.append("<td style=\"border: 1px solid #000000; padding: 5px;\">").append(resultSet.getString(i)).append("</td>");
            }
            stringBuilder.append("</tr>");
            stringBuilder.append("</tbody>");
        }

        stringBuilder.append("</table>");

        return stringBuilder;
    }

    public void stopSchedule(UUID scheduleId, String groupName) {
        try {
            scheduler.unscheduleJob(new TriggerKey(scheduleId.toString(), groupName));
        } catch (SchedulerException e) {
            log.error("{} ошибка отключения планировщика", e.getMessage());
        }
    }

    public void deleteSchedule(UUID scheduleId, String groupName) {
        try {
            scheduler.deleteJob(new JobKey(scheduleId.toString(), groupName));
        } catch (SchedulerException e) {
            log.error("{} ошибка удаления планировщика", e.getMessage());
        }
    }
}
