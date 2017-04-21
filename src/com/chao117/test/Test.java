package com.chao117.test;

import java.sql.ResultSet;
import java.sql.SQLException;

public class Test {
	
	public void test(){
		String sqlString  = "select * from table_user";
		TestDBHelper helper = new TestDBHelper(sqlString);
		try {
			ResultSet resultSet = helper.getResultSet();
			int accountIndex = resultSet.findColumn("u_account");
			int passwordIndex = resultSet.findColumn("u_password");
			System.out.println("test");
			while(resultSet.next()){
				System.out.println(resultSet.getString(accountIndex));
				System.out.println(resultSet.getString(passwordIndex));
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
