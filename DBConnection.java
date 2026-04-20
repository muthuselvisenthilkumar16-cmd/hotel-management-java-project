package com.hotel.management;

import java.sql.*;

public class DBConnection {

    public static Connection getConnection() {
        Connection con = null;

        try {
            Class.forName("com.mysql.cj.jdbc.Driver"); // IMPORTANT

            con = DriverManager.getConnection(
                "jdbc:mysql://localhost:3306/hotel_db",
                "root",
                "siws" // your password
            );

            System.out.println("Database Connected Successfully");

        } catch (Exception e) {
            e.printStackTrace();
        }

        return con;
    }
}