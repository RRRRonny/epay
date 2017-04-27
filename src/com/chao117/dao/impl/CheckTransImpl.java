package com.chao117.dao.impl;

import com.chao117.dao.BaseDAO;
import com.chao117.model.ServerResult;
import com.chao117.model.Transaction;
import com.chao117.model.User;
import com.chao117.service.QueryTransHelper;

import java.util.List;

/**
 * Created by chan on 24/04/2017.
 */
public class CheckTransImpl implements BaseDAO<User> {
    private int errorCode;
    private String result;
    private User user;
    private ServerResult serverResult;
    private List<Transaction> transactionList;

    @Override
    public int getErrorCode() {
        return errorCode;
    }

    @Override
    public boolean init(User object) {
        errorCode = ERROR_NON;
        result = RESULT_FAILURE;
        this.user = object;
        return false;
    }

    @Override
    public boolean doOperate() {
        QueryTransHelper queryTransHelper = new QueryTransHelper();
        transactionList = queryTransHelper.queryTransByBuyerId(user.getId());
        transactionList.addAll(queryTransHelper.queryTransBySellerId(user.getId()));
        if (transactionList == null) {
            errorCode = ERROR_REFUSE;
        } else {
            result = RESULT_SUCCESS;
        }
        return false;
    }

    @Override
    public void updateErrorCode(int errorCode) {
        this.errorCode = errorCode;
    }

    @Override
    public ServerResult getServerResult() {
        serverResult = new ServerResult();
        serverResult.setResult(result);
        serverResult.setErrorCode(errorCode);
        serverResult.setObjects(transactionList);
        return serverResult;
    }
}
