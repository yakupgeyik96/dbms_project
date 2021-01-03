package dbmsproj.service;

import java.sql.Connection;
import java.sql.DriverManager;

public class PostgreSqlConnection {

    private final String DB_URL = "jdbc:postgresql://localhost/dbms_project";
    private final String DB_USERNAME = "postgres";
    private final String DB_PASSWORD = "*";

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
