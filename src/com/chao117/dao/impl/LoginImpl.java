package com.chao117.dao.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.chao117.dao.BaseDAO;
import com.chao117.model.ServerResult;
import com.chao117.model.User;
import com.chao117.service.QueryUserHelper;

public class LoginImpl implements BaseDAO<User>{
	private int errorCode;
	private String result;
	private ServerResult<User> serverResult;
	
	private String account;
	private String password;
	private User user;
	
	

	@Override
	public int getErrorCode() {
		return errorCode;
	}

	@Override
	public boolean init(User object) {
		account = object.getAccount();
		password = object.getPassword();
		result = RESULT_FAILURE;
		errorCode = ERROR_NON;
		return false;
	}

	@Override
	public boolean doOperate() {
		QueryUserHelper helper  = new QueryUserHelper();
		ResultSet set = helper.query(account);
		System.out.println("set是否为空:"+(set == null));
		try {
			while(set.next()){
				String targetPassword = set.getString(set.findColumn(U_PASSWORD));
				if (password.equals(targetPassword)) {
					System.out.println("密码匹配,允许登录");
					updateErrorCode(ERROR_NON);
					result = RESULT_SUCCESS;
					//返回用户的关键信息
					user = new User();
					user.setId(set.getInt(set.findColumn(ID)));
					user.setName(set.getString(set.findColumn(U_NAME)));
					return true;
				}else{
					updateErrorCode(ERROR_PASSWORD);
					return false;
				}
			}
			updateErrorCode(ERROR_USER_INEXIST);//用户不存在
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}

	@Override
	public void updateErrorCode(int errorCode) {
		this.errorCode = errorCode;
	}

	@Override
	public ServerResult<User> getServerResult() {
		// TODO Auto-generated method stub
		serverResult = new ServerResult<User>();
		serverResult.setErrorCode(errorCode);
		serverResult.setResult(result);
		List<User> users = new ArrayList<User>();
		users.add(user);
		serverResult.setObjects(users);
		return serverResult;
	}

}
