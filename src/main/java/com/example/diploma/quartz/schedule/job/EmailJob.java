package com.example.diploma.quartz.schedule.job;

import com.example.diploma.quartz.schedule.MailScheduleService;
import com.example.diploma.service.MailSenderService;
import jakarta.mail.MessagingException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.quartz.*;
import org.springframework.boot.autoconfigure.mail.MailProperties;
import org.springframework.stereotype.Component;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.sql.*;
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
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        String name = jobDataMap.getString("name");
        StringBuilder htmlTable = new StringBuilder();
        Workbook workbook = new XSSFWorkbook();


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

                    List<String> header = new ArrayList<>();
                    ResultSet resultSet = statements.get(i).executeQuery(queries.get(i).get(j));
                    ResultSetMetaData resultSetMetaData = resultSet.getMetaData();
                    for (int k = 1; k <= resultSetMetaData.getColumnCount(); k++) {
                        header.add(resultSetMetaData.getColumnLabel(k));
                    }

                    Sheet sheet = workbook.createSheet("Лист " + (j + 1));
                    Row row = sheet.createRow(0);

                    for (int k = 0; k < header.size(); k++) {
                        Cell cell = row.createCell(k);
                        cell.setCellValue(header.get(k));
                    }

                    int rowIndex = 1;
                    while (resultSet.next()) {
                        row = sheet.createRow(rowIndex);
                        rowIndex++;
                        for (int k = 0; k < header.size(); k++) {
                            Cell cell = row.createCell(k);
                            cell.setCellValue(resultSet.getString(k + 1));
                        }
                    }
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
            }
            try {
                workbook.write(byteArrayOutputStream);
                mailSenderService.sendMail(mailProperties.getUsername(), addresses, name, htmlTable.toString(), byteArrayOutputStream);
            } catch (MessagingException | IOException e) {
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