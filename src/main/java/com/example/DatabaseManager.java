package com.example;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Properties;

public class DatabaseManager {
    private Connection conn;

    public DatabaseManager() {
        Properties props = new Properties();
        try (FileInputStream input = new FileInputStream("config.properties")) {
            props.load(input);
            String url = props.getProperty("db.url");
            String user = props.getProperty("db.user");
            String password = props.getProperty("db.password");

            conn = DriverManager.getConnection(url, user, password);
            System.out.println("Connected to PostgreSQL successfully!");
        } catch (SQLException e) {
            System.err.println("Database connection failed: " + e.getMessage());
            conn = null;
        } catch (IOException e) {
            System.err.println("Failed to load config file: " + e.getMessage());
            conn = null;
        }
    }

    public boolean isConnected() {
        return conn != null;
    }

    public void saveOperation(String operation, double a, double b, double result) {
        if (!isConnected()) {
            System.err.println("No database connection available!");
            return;
        }

        try {
            String sql = "INSERT INTO operations (operation, a, b, result, timestamp) VALUES (?, ?, ?, ?, NOW())";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, operation);
            stmt.setDouble(2, a);
            stmt.setDouble(3, b);
            stmt.setDouble(4, result);
            stmt.executeUpdate();
            System.out.println("Operation saved: " + operation + " (" + a + ", " + b + ") = " + result);
        } catch (SQLException e) {
            System.err.println("Failed to save operation: " + e.getMessage());
        }
    }

    public void closeConnection() {
        try {
            if (conn != null) {
                conn.close();
                System.out.println("Connection closed.");
                conn = null;
            }
        } catch (SQLException e) {
            System.err.println("Failed to close connection: " + e.getMessage());
        }
    }
}