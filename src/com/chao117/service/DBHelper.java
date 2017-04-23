package com.chao117.service;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DBHelper {
    final String DRIVER = "com.mysql.jdbc.Driver";
    final String DATABASE_NAME = "eapy";
    final String USER_NAME = "root";
    final String PASSWORD = "imgundam00q#";
    final String URL = "jdbc:mysql://112.74.219.11:3306/epay";

    private Connection con;
//	private ResultSet resultSet;

    public PreparedStatement pst;

    private String sqlString;

    public DBHelper(String sqlString) {
        this.sqlString = sqlString;
        try {
            Class.forName(DRIVER);
            con = DriverManager.getConnection(URL, USER_NAME, PASSWORD);
            pst = con.prepareStatement(sqlString);
        } catch (ClassNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } finally {
        }
    }

    public void close() {
        try {
            con.close();
            pst.close();
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

    }

}
