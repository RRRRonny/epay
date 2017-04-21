package com.chao117.dao.impl;

import com.chao117.dao.BaseDAO;
import com.chao117.model.ServerResult;
import com.chao117.model.User;
import com.chao117.service.InsertUserHelper;

/**
 * Created by chan on 21/04/2017.
 */
public class ModifyPasswordImpl implements BaseDAO<User> {
    private int errorCode;
    private String result;
    private String password;
    private User user;

    private ServerResult serverResult;

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
        InsertUserHelper helper = new InsertUserHelper();
        int lines = helper.updateUserPassword(user);
        if (lines > 0) {
            result = RESULT_SUCCESS;
        } else {
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
        serverResult.setErrorCode(errorCode);
        serverResult.setResult(result);
        return serverResult;
    }
}
