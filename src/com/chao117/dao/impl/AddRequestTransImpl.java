package com.chao117.dao.impl;

import com.chao117.dao.BaseDAO;
import com.chao117.model.RequestTrans;
import com.chao117.model.ServerResult;
import com.chao117.model.Transaction;
import com.chao117.service.InsertRequestTransHelper;

/**
 * Created by chan on 24/04/2017.
 */
public class AddRequestTransImpl implements BaseDAO<RequestTrans> {
    private int errorCode;
    private String result;
    private ServerResult serverResult;

    private RequestTrans requestTrans;

    @Override
    public int getErrorCode() {
        return errorCode;
    }

    @Override
    public boolean init(RequestTrans object) {
        result = RESULT_FAILURE;
        errorCode = ERROR_NON;
        this.requestTrans = object;
        return false;
    }

    @Override
    public boolean doOperate() {
        int lines = new InsertRequestTransHelper().insertRequestTrans(requestTrans);
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
