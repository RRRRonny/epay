package com.chao117.dao.impl;

import com.chao117.dao.BaseDAO;
import com.chao117.model.RequestTrans;
import com.chao117.model.ServerResult;
import com.chao117.model.User;
import com.chao117.service.QueryReqTransHelper;

import java.util.List;

/**
 * Created by chan on 24/04/2017.
 */
public class CheckReqTransImpl implements BaseDAO<User> {
    private int errorCode;
    private String result;
    private ServerResult serverResult;
    private User user;
    private List<RequestTrans> requestTransList;

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
        QueryReqTransHelper helper = new QueryReqTransHelper();
        requestTransList = helper.queryReqTransByBuyerId(user.getId());
        requestTransList.addAll(helper.queryReqTransBySellerId(user.getId()));
        if (requestTransList == null) {
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
        serverResult.setErrorCode(errorCode);
        serverResult.setObjects(requestTransList);
        serverResult.setResult(result);
        return serverResult;
    }
}
