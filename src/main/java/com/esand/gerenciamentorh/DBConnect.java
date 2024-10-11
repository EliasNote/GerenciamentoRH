package com.esand.gerenciamentorh;


import java.sql.Connection;
import java.sql.DriverManager;

public class DBConnect {

    private static String URL = "jdbc:h2:./data/";
    private static String DB = "rh";
    private static String USER = "root";
    private static String PASSWORD = "root";

    public static Connection connect() {
        Connection databaseLink;

        try {
            Class.forName("org.h2.Driver");
            databaseLink = DriverManager.getConnection(URL + DB, USER, PASSWORD);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        return databaseLink;
    }
}
