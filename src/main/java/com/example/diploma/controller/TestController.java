//package com.example.diploma.controller;
//
//import com.example.diploma.entity.Report;
//import com.example.diploma.repository.ReportRepository;
//import com.example.diploma.utils.ExcelUtils;
//import jakarta.servlet.http.HttpServletResponse;
//import lombok.RequiredArgsConstructor;
//import org.springframework.core.io.InputStreamResource;
//import org.springframework.http.HttpHeaders;
//import org.springframework.http.MediaType;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RestController;
//
//import java.io.ByteArrayInputStream;
//import java.io.IOException;
//import java.io.InputStream;
//import java.sql.DriverManager;
//import java.sql.ResultSet;
//import java.sql.SQLException;
//
//@RestController
//@RequiredArgsConstructor
//@RequestMapping("/test")
//public class TestController {
//
//    private final ExcelUtils excelUtils;
//
//    @GetMapping
//    public ResponseEntity<InputStreamResource> test() throws IOException, SQLException {
//
//
//        ResultSet resultSet = DriverManager.getConnection(
//                "jdbc:postgresql://localhost:5432/postgres",
//                "postgres",
//                "").createStatement().executeQuery("SELECT * FROM Reports");
//
//
//        ByteArrayInputStream byteArrayInputStream = excelUtils.dataToExcel(resultSet);
//
//        InputStreamResource inputStreamResource = new InputStreamResource(byteArrayInputStream);
//
//        return ResponseEntity.ok()
//                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment;filename" + "file.xlsx")
//                .contentType(MediaType.parseMediaType("application/vnd.ms-excel"))
//                .body(inputStreamResource);
//    }
//}
