package com.chao117.dao.bak;


import java.sql.ResultSet;
import java.sql.SQLException;

import org.json.JSONException;
import org.json.JSONObject;

import com.chao117.base.constant.DatabaseField;
import com.chao117.base.constant.ErrorCode;
import com.chao117.service.QueryUserHelper;

public class LoginDAOImplBAK implements ILoginDAOBAK,DatabaseField,ErrorCode {
	private String account;
	private String password;
	private boolean isOnline;
	private int errorCode;
	
	public LoginDAOImplBAK(){
		errorCode = 0;
	}

	@Override
	public boolean init(JSONObject user) {
		try {
			account = user.getString(U_ACCOUNT);
			password = user.getString(U_PASSWORD);
		} catch (JSONException e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}

	@Override
	public boolean isMath() {
		// TODO Auto-generated method stub
		QueryUserHelper queryUser = new QueryUserHelper();
		System.out.println("查找account:"+account+"的信息");
		ResultSet set = queryUser.query(account);
		System.out.println("set是否为空:"+(set == null));
		try {
			while(set.next()){
				String targetPassword = set.getString(set.findColumn(U_PASSWORD));
				if (password.equals(targetPassword)) {
					return true;
				}else{
					return false;
				}
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return false;
	}

	@Override
	public boolean isOnline() {
		// TODO Auto-generated method stub
		return false;
	}

}
