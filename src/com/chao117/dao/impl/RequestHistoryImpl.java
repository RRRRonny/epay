package com.chao117.dao.impl;

import com.chao117.dao.BaseDAO;
import com.chao117.model.History;
import com.chao117.model.ServerResult;
import com.chao117.model.User;
import com.chao117.service.QueryHistoryHelper;

import java.util.List;

/**
 * Created by chan on 23/04/2017.
 */
public class RequestHistoryImpl implements BaseDAO<User> {
    private int errorCode;
    private String result;
    private ServerResult serverResult;

    private User user;
    private List<History> historyList;

    @Override
    public int getErrorCode() {
        return errorCode;
    }

    @Override
    public boolean init(User object) {
        updateErrorCode(ERROR_NON);
        result = RESULT_FAILURE;
        this.user = object;
        return false;
    }

    @Override
    public boolean doOperate() {
        historyList = new QueryHistoryHelper().queryHistory(user);
        if (historyList != null) {
            result = RESULT_SUCCESS;
        } else {
            updateErrorCode(ERROR_REFUSE);
        }
        //todo 重要,更新 result
        return false;
    }

    @Override
    public void updateErrorCode(int errorCode) {
        this.errorCode = errorCode;
    }

    @Override
    public ServerResult getServerResult() {
        serverResult.setErrorCode(errorCode);
        serverResult.setResult(result);
        serverResult.setObjects(historyList);
        return serverResult;
    }
}
