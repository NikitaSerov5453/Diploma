package com.example.diploma.quartz.schedule.job;

import com.example.diploma.quartz.schedule.MailScheduleService;
import com.example.diploma.service.MailSenderService;
import jakarta.persistence.Table;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.quartz.*;
import org.springframework.boot.autoconfigure.mail.MailProperties;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


@Component
@Slf4j
@RequiredArgsConstructor
@PersistJobDataAfterExecution
@DisallowConcurrentExecution
public class EmailJob implements Job {

    private final MailProperties mailProperties;

    private final MailSenderService mailSenderService;

    private final MailScheduleService mailScheduleService;

    /*
    Работа выполняющайся при срабатывании триггера

    Серриализуются только параметры не требующие обновления при каждом запуске метода
     */
    @Override
    public void execute(JobExecutionContext jobExecutionContext) {
        log.info("Executing Job with key {}", jobExecutionContext.getJobDetail().getKey());

        JobDataMap jobDataMap = jobExecutionContext.getMergedJobDataMap();

        int addressesSize = Integer.parseInt(jobDataMap.getString("addressesSize"));
        int authorisationsSize = Integer.parseInt(jobDataMap.getString("authorisationsSize"));

        String[] addresses = new String[addressesSize];
        List<Statement> statements = new ArrayList<>();
        List<List<String>> queries = new ArrayList<>(authorisationsSize);
        List<Integer> queriesSize = new ArrayList<>();

        String name = jobDataMap.getString("name");
        StringBuilder htmlTable = new StringBuilder();

        for (int i = 0; i < authorisationsSize; i++) {
            queries.add(new ArrayList<>());
        }

        for (int i = 0; i < addressesSize; i++) {
            addresses[i] = jobDataMap.getString("addresses[" + i + "]");
        }

        for (int i = 0; i < authorisationsSize; i++) {
            queriesSize.add(Integer.parseInt(jobDataMap.getString("querySize[" + i + "]")));
        }

        for (int i = 0; i < authorisationsSize; i++) {
            statements.add(mailScheduleService.statement(
                    jobDataMap.getString("url[" + i + "]"),
                    jobDataMap.getString("login[" + i + "]"),
                    jobDataMap.getString("password[" + i + "]"))
            );
        }

        for (int i = 0; i < authorisationsSize; i++) {
            for (int j = 0; j < queriesSize.get(i); j++) {
                queries.get(i).add(jobDataMap.getString("query[" + i + "][" + j + "]"));
            }
        }

        for (int i = 0; i < authorisationsSize; i++) {
            for (int j = 0; j < queriesSize.get(i); j++) {
                try {
                     htmlTable.append(mailScheduleService.toHtmlTable(statements.get(i).executeQuery(queries.get(i).get(j))));
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
            }
            mailSenderService.sendMail(mailProperties.getUsername(), addresses, name, htmlTable.toString());
        }
    }
}
