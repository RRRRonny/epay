package com.chao117.test;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class TestDBHelper {
	final String DRIVER ="com.mysql.jdbc.Driver";
	final String DATABASE_NAME = "eapy";
	final String USER_NAME = "root";
	final String PASSWORD = "imgundam00q#";
	final String URL = "jdbc:mysql://112.74.219.11:3306/epay"; 
	
	private Connection con;
	private PreparedStatement pst;
	private ResultSet resultSet;
	
	private String sqlString;
		
	public TestDBHelper(String sqlString){
		this.sqlString = sqlString;
		init();
	}
	
	public void setSqlString(String sqlString){
		this.sqlString = sqlString;
	}

	private void init() {
		try {
			Class.forName(DRIVER);
			con = DriverManager.getConnection(URL,USER_NAME,PASSWORD);
			pst = con.prepareStatement(sqlString);
			resultSet = pst.executeQuery(sqlString);
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public ResultSet getResultSet(){
		return resultSet;
	}
	
	public void close(){
		try {
			con.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
