package com.example.diploma.quartz.schedule.job;

import com.example.diploma.quartz.schedule.MailScheduleService;
import com.example.diploma.service.MailSenderService;
import com.example.diploma.utils.ExcelUtils;
import jakarta.mail.MessagingException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.quartz.*;
import org.springframework.boot.autoconfigure.mail.MailProperties;
import org.springframework.stereotype.Component;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
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

    private final ExcelUtils excelUtils;

    /*
    Работа выполняющайся при срабатывании триггера

    Серриализуются только параметры не требующие обновления при каждом запуске метода
     */
    @Override
    public void execute(JobExecutionContext jobExecutionContext) {
        log.info("Executing Job with key {}", jobExecutionContext.getJobDetail().getKey());

        JobDataMap jobDataMap = jobExecutionContext.getMergedJobDataMap();
//        Object reportDto = jobDataMap.get("reportDto");
//        System.out.println(reportDto);
//        System.out.println(reportDto.getClass().getName());

        int addressesSize = Integer.parseInt(jobDataMap.getString("addressesSize"));
        int authorisationsSize = Integer.parseInt(jobDataMap.getString("authorisationsSize"));

        String[] addresses = new String[addressesSize];
        List<Statement> statements = new ArrayList<>();
        List<Connection> connections = new ArrayList<>();
        List<List<String>> queries = new ArrayList<>(authorisationsSize);
        List<Integer> queriesSize = new ArrayList<>();
        ByteArrayOutputStream byteArrayOutputStream = null;
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
            Connection connection = mailScheduleService.connection(
                    jobDataMap.getString("url[" + i + "]"),
                    jobDataMap.getString("login[" + i + "]"),
                    jobDataMap.getString("password[" + i + "]"));
            connections.add(connection);
            try {
                statements.add(connection.createStatement());
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
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
                    byteArrayOutputStream = excelUtils.dataToExcel(statements.get(i).executeQuery(queries.get(i).get(j)));
                } catch (SQLException | IOException e) {
                    throw new RuntimeException(e);
                }
            }
            try {
                assert byteArrayOutputStream != null;
                mailSenderService.sendMail(mailProperties.getUsername(), addresses, name, htmlTable.toString(), byteArrayOutputStream);
            } catch (MessagingException e) {
                throw new RuntimeException(e);
            }
        }

        for (Connection connection : connections) {
            try {
                connection.close();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
