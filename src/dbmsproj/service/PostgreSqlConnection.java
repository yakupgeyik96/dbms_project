package dbmsproj.service;

import java.sql.Connection;
import java.sql.DriverAction;
import java.sql.DriverManager;
import java.sql.Statement;

public class PostgreSqlConnection {

    private final String DB_URL = "jdbc:postgresql://localhost/company_db";
    private final String DB_USERNAME = "postgres";
    private final String DB_PASSWORD = "Lx0ex7ox5Tbpyu8h1!9";

    private static PostgreSqlConnection instance = null;

    private PostgreSqlConnection() {}

    public static PostgreSqlConnection getInstance() {

        if (instance == null) {
            instance = new PostgreSqlConnection();
        }

        return instance;
    }

    public Connection getConnection() throws Exception {
        return DriverManager.getConnection(DB_URL, DB_USERNAME, DB_PASSWORD);
    }
}
