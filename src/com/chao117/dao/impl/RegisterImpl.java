package com.chao117.dao.impl;

import java.sql.ResultSet;
import java.sql.SQLException;

import com.chao117.dao.BaseDAO;
import com.chao117.model.ServerResult;
import com.chao117.model.User;
import com.chao117.service.InsertUserHelper;
import com.chao117.service.QueryUserHelper;

public class RegisterImpl implements BaseDAO<User>{
	private int errorCode;
	private String result;
	private ServerResult<User> serverResult;
	
	private User user;

	@Override
	public int getErrorCode() {
		return errorCode;
	}

	@Override
	public boolean init(User object) {
		this.user = object;
		result = RESULT_FAILURE;
		return false;
	}

	@Override
	public boolean doOperate() {
		QueryUserHelper queryUserHelper = new QueryUserHelper();
		ResultSet set = queryUserHelper.query(user.getAccount());
		try {
			if(set.next()){
				updateErrodrCode(ERROR_USER_EXIST);
			}else{
				InsertUserHelper insertUserHelper  = new InsertUserHelper();
				int line  = insertUserHelper.insertAccPsw(user);
				if (line>0) {
					updateErrodrCode(ERROR_NON);
					result = RESULT_SUCCESS;
				}else{
					updateErrodrCode(ERROR_REFUSE);
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}

	@Override
	public void updateErrodrCode(int errorCode) {
		this.errorCode = errorCode;
	}

	@Override
	public ServerResult<User> getserServerResult() {
		serverResult = new ServerResult<User>();
		serverResult.setErrorCode(errorCode);
		serverResult.setResult(result);
		return serverResult;
	}

}
