package com.example.diploma.test;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.sql.*;

@RequiredArgsConstructor
@Component
public class Test {

    public Connection dbConnection(String url, String username, String password) throws SQLException {
        return DriverManager.getConnection(url, username, password);
    }

    public Statement statement(Connection connection) throws SQLException {
        return connection.createStatement();
    }

    public ResultSet getQueryResult(String query, Statement statement) throws SQLException {
        return statement.executeQuery(query);
    }

    public String a() throws SQLException {
        Connection connection = dbConnection("jdbc:postgresql://localhost:5432/postgres", "postgres", "");
        Statement statement = connection.createStatement();
        ResultSet resultSet = getQueryResult("SELECT * FROM Addresses", statement);
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
}
