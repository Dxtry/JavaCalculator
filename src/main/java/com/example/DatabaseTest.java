package com.example;

public class DatabaseTest {
    public static void main(String[] args) {
        DatabaseManager db = new DatabaseManager();
        if (db != null && db.isConnected()) {
            db.saveOperation("add", 21.0, 24.0, 45.0);
            db.closeConnection();
        } else {
            System.out.println("DatabaseManager is not properly initialized!");
        }
    }
}
