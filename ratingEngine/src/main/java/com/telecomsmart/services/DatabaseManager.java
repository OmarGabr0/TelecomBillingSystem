/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.telecomsmart.services;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 *
 * @author omar
 */
public class DatabaseManager {

    private static final String URL = "jdbc:postgresql://localhost:5432/your_db";
    private static final String USER = "postgres";
    private static final String PASS = "postgres";
  
    public static Connection getConnection() throws SQLException {

        return DriverManager.getConnection(URL, USER, PASS);
    }

    public void closeConn(Connection con ) {
        try {
            if (con != null) {
                con.close();

            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}
