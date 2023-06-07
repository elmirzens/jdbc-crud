package org.example.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DbConnection {

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(
                //like this << jdbc:postgresql://localhost:8080/postgres >>
                "jdbc:postgresql://localhost: your_port / your_database",
                "your_database_username",
                "and password" );
    }
}