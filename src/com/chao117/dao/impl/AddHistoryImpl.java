package com.chao117.dao.impl;

import com.chao117.dao.BaseDAO;
import com.chao117.model.History;
import com.chao117.model.ServerResult;
import com.chao117.service.InsertHistoryHelper;

/**
 * Created by chan on 23/04/2017.
 */
public class AddHistoryImpl implements BaseDAO<History> {
    private int errorCode;
    private String result;
    private ServerResult serverResult;
    private History history;

    @Override
    public int getErrorCode() {
        return errorCode;
    }

    @Override
    public boolean init(History object) {
        result = RESULT_FAILURE;
        updateErrorCode(ERROR_NON);
        this.history = object;
        return false;
    }

    @Override
    public boolean doOperate() {
        int lines = new InsertHistoryHelper().insertHistory(history);
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
        serverResult.setResult(result);
        serverResult.setErrorCode(errorCode);
        return serverResult;
    }
}
