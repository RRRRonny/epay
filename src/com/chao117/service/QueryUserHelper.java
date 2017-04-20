package com.chao117.service;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.chao117.base.constant.DatabaseField;

public class QueryUserHelper implements DatabaseField{
	private DBHelperBAK helper;
	
	public ResultSet query(int id ){
		String sqlString = "select * from table_user where id = "+"'"+  "id" +"'";
		 helper = new DBHelperBAK(sqlString);
		 return helper.getResultSet();
	}
	
	public ResultSet query(String account){
		String sqlString = "select * from table_user where u_account = "+"'"+account+"'";
		System.out.println("查询语句:"+sqlString);
		helper = new DBHelperBAK(sqlString);
		ResultSet set = helper.getResultSet();
		System.out.println("queryUser内set是否为空:"+(set == null));
		return set;
	}
}
