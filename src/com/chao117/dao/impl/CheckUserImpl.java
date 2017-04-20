package com.chao117.dao.impl;

import com.chao117.base.constant.ServerConstants;
import com.chao117.dao.BaseDAO;
import com.chao117.model.ServerResult;
import com.chao117.model.User;

public class CheckUserImpl implements BaseDAO<User>,ServerConstants{

	@Override
	public int getErrorCode() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public boolean init(User object) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean doOperate() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void updateErrodrCode(int errorCode) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public ServerResult getserServerResult() {
		// TODO Auto-generated method stub
		return null;
	}

}
