package com.chao117.dao.impl;

import com.chao117.dao.BaseDAO;
import com.chao117.model.ServerResult;
import com.chao117.model.Transaction;
import com.chao117.service.InsertTransHelper;

/**
 * Created by chan on 24/04/2017.
 */
public class CancelTransImpl implements BaseDAO<Transaction> {
    private int errorCode;
    private String result;
    private ServerResult serverResult;
    private Transaction transaction;

    @Override
    public int getErrorCode() {
        return errorCode;
    }

    @Override
    public boolean init(Transaction object) {
        errorCode = ERROR_NON;
        this.transaction = object;
        result = RESULT_FAILURE;
        return false;
    }

    @Override
    public boolean doOperate() {
        InsertTransHelper insertTransHelper = new InsertTransHelper();
        int lines = insertTransHelper.updateTransStateToCancel(transaction);
        if (lines > 0) {
            result = RESULT_SUCCESS;
        } else {
            errorCode = ERROR_REFUSE;
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
        serverResult.setErrorCode(errorCode);
        serverResult.setResult(result);
        return serverResult;
    }
}
