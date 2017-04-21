package com.chao117.dao.impl;

import com.chao117.base.constant.ServerConstants;
import com.chao117.dao.BaseDAO;
import com.chao117.model.Goods;
import com.chao117.model.ServerResult;
import com.chao117.service.InsertGoodsHelper;

public class PublishImpl implements BaseDAO<Goods>, ServerConstants {
	private Goods goods;
	private int errorCode;
	private String result;
	private ServerResult<Goods> serverResult;

	@Override
	public int getErrorCode() {
		return errorCode;
	}

	@Override
	public boolean init(Goods object) {
		this.goods = object;
		result = RESULT_FAILURE;
		errorCode = ERROR_NON;
		return false;
	}

	@Override
	public boolean doOperate() {
		// TODO Auto-generated method stub
		InsertGoodsHelper helper = new InsertGoodsHelper();
		int lines = helper.insertGoods(goods);
		if (lines > 0) {
			result = RESULT_SUCCESS;
			updateErrorCode(ERROR_NON);
		} else {
			result = RESULT_FAILURE;
			updateErrorCode(ERROR_REFUSE);
		}
		return false;
	}

	@Override
	public void updateErrorCode(int errorCode) {
		this.errorCode = errorCode;
	}

	@Override
	public ServerResult<Goods> getServerResult() {
		serverResult = new ServerResult<Goods>();
		serverResult.setErrorCode(errorCode);
		serverResult.setResult(result);
		return serverResult;
	}

}
