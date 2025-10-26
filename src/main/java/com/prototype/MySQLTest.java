package com.prototype;

import java.sql.*;
import java.time.Duration;
import java.time.Instant;

public class MySQLTest {
    public static void main(String[] args) {
        String url = "jdbc:mysql://localhost:3306/agile_re";
        String user = "root";
        String password = "Keeru@2408";

        int numStories = 1000;

        try (Connection conn = DriverManager.getConnection(url, user, password)) {
            System.out.println("Connected to MySQL!");

            // Clear existing
            try (Statement stmt = conn.createStatement()) {
                stmt.executeUpdate("DELETE FROM user_story");
            }

            // Insert Benchmark
            String insertSql = "INSERT INTO user_story (title, description, priority) VALUES (?, ?, ?)";
            Instant startInsert = Instant.now();
            try (PreparedStatement stmt = conn.prepareStatement(insertSql)) {
                for (int i = 0; i < numStories; i++) {
                    stmt.setString(1, "Story " + i);
                    stmt.setString(2, "Description for story " + i);
                    stmt.setString(3, (i % 2 == 0) ? "High" : "Low");
                    stmt.executeUpdate();
                }
            }
            Instant endInsert = Instant.now();

            // Fetch Benchmark
            Instant startFetch = Instant.now();
            String querySql = "SELECT * FROM user_story";
            try (Statement stmt = conn.createStatement();
                 ResultSet rs = stmt.executeQuery(querySql)) {
                int count = 0;
                while (rs.next()) {
                    count++;
                }
                System.out.println("Total stories fetched: " + count);
            }
            Instant endFetch = Instant.now();

            // Print timings
            System.out.println("\n⏱ MySQL Insert Time: " +
                    Duration.between(startInsert, endInsert).toMillis() + " ms");
            System.out.println("⏱ MySQL Fetch Time: " +
                    Duration.between(startFetch, endFetch).toMillis() + " ms");

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
