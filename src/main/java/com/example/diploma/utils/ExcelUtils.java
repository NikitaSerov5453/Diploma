//package com.example.diploma.utils;
//
//import org.apache.poi.hssf.usermodel.HSSFWorkbook;
//import org.apache.poi.ss.usermodel.*;
//import org.apache.poi.xssf.usermodel.XSSFWorkbook;
//import org.springframework.stereotype.Component;
//
//import java.io.ByteArrayInputStream;
//import java.io.ByteArrayOutputStream;
//import java.io.IOException;
//import java.sql.ResultSet;
//import java.sql.ResultSetMetaData;
//import java.sql.SQLException;
//import java.util.ArrayList;
//import java.util.List;
//
//@Component
//public class ExcelUtils {
//
//    public ByteArrayOutputStream dataToExcel(ResultSet resultSet) throws IOException, SQLException {
//        List<String> header = new ArrayList<>();
//
//        ResultSetMetaData resultSetMetaData = resultSet.getMetaData();
//        for (int i = 1; i <= resultSetMetaData.getColumnCount(); i++) {
//            header.add(resultSetMetaData.getColumnLabel(i));
//        }
//
//        try (Workbook workbook = new XSSFWorkbook(); ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream()) {
//            Sheet sheet = workbook.createSheet();
//            Row row = sheet.createRow(0);
//
//            for (int i = 0; i < header.size(); i++) {
//                Cell cell = row.createCell(i);
//                cell.setCellValue(header.get(i));
//            }
//
//            int rowIndex = 1;
//            while (resultSet.next()) {
//                row = sheet.createRow(rowIndex);
//                rowIndex++;
//                for (int i = 0; i < header.size(); i++) {
//                    Cell cell = row.createCell(i);
//                    cell.setCellValue(resultSet.getString(i + 1));
//                }
//            }
//
//            workbook.write(byteArrayOutputStream);
//            return byteArrayOutputStream;
//        } catch (IOException e) {
//            throw new RuntimeException(e);
//        }
//    }
//}
