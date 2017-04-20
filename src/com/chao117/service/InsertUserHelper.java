package com.chao117.service;

import java.sql.SQLException;

import com.chao117.base.constant.DatabaseField;
import com.chao117.model.User;

public class InsertUserHelper implements DatabaseField {
	private DBHelper helper;

	public int insertAccPsw(User user) {
		int result = -1;
		String sqlString = "insert into table_user (u_account,u_password) values (?,?)";
		helper = new DBHelper(sqlString);
		System.out.println("即将写入数据的用户注册信息,account:" + user.getAccount() + ",password:" + user.getPassword());
		if ("".equals(user.getAccount()) || "".equals(user.getPassword())) {
			System.out.println("数据不合法,拒绝写入");
			return result;
		}
		try {
			helper.pst.setString(1, user.getAccount());
			helper.pst.setString(2, user.getPassword());
			result = helper.pst.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
			return result;
		}
		return result;
	}
}
