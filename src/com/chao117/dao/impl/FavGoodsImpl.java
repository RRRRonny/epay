package com.chao117.dao.impl;

import com.chao117.dao.BaseDAO;
import com.chao117.model.Follow;
import com.chao117.model.ServerResult;
import com.chao117.service.InsertFollowHelper;

/**
 * Created by chan on 21/04/2017.
 */
public class FavGoodsImpl implements BaseDAO<Follow> {
    private int errorCode;
    private String result;
    private ServerResult serverResult;

    private Follow follow;

    @Override
    public int getErrorCode() {
        return errorCode;
    }

    @Override
    public boolean init(Follow object) {
        this.follow = object;
        errorCode = ERROR_NON;
        result = RESULT_FAILURE;
        return false;
    }

    @Override
    public boolean doOperate() {
        InsertFollowHelper helper = new InsertFollowHelper();
        int lines = helper.insertFollow(follow);
        if (lines > 0) {
            result = RESULT_SUCCESS;
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
    public ServerResult getServerResult() {
        serverResult = new ServerResult();
        serverResult.setResult(result);
        serverResult.setErrorCode(errorCode);
        return serverResult;
    }
}
