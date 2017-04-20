package com.chao117.dao;

import com.chao117.base.constant.ServerConstants;
import com.chao117.model.ServerResult;

/**
 * @author chan
 *
 * @param <O>该操作对象所操作的类
 */
public interface BaseDAO<O> extends ServerConstants{
	int getErrorCode();

	boolean init(O object);

	boolean doOperate();

	void updateErrodrCode(int errorCode);
	
	ServerResult getserServerResult();
}
