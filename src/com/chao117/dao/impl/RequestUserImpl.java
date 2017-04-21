package com.chao117.dao.impl;

import com.chao117.base.constant.ServerConstants;
import com.chao117.dao.BaseDAO;
import com.chao117.model.ServerResult;
import com.chao117.model.User;
import com.chao117.service.QueryUserHelper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class RequestUserImpl implements BaseDAO<User>, ServerConstants {
    private int errorCode;
    private String result;
    private ServerResult<User> serverResult;

    private User user;
    private int id;


    @Override
    public int getErrorCode() {
        return errorCode;
    }

    @Override
    public boolean init(User object) {
        errorCode = ERROR_NON;
        this.user = object;
        result = RESULT_FAILURE;
        id = user.getId();
        return false;
    }

    @Override
    public boolean doOperate() {
        QueryUserHelper helper = new QueryUserHelper();
        ResultSet set = helper.query(id);
        user = new User();
        try {
            if (set.next()) {
                user.setId(id);
                user.setAccount(set.getString(set.findColumn(U_ACCOUNT)));
                user.setBirth(set.getDate(set.findColumn(U_BIRTH_)));
                user.setGender(set.getString(set.findColumn(U_GENDER)));
                user.setHeader(set.getString(set.findColumn(U_HEADER)));
                user.setName(set.getString(set.findColumn(U_NAME)));
                user.setPhone(set.getString(set.findColumn(U_PHONE)));
                user.setTimestamp(set.getLong(set.findColumn(U_TIMESTAMP)));
                user.setRemark(set.getString(set.findColumn(U_REMARK)));
                user.setPassword("null");
                user.setHeaderLocal("null");
                result = RESULT_SUCCESS;
            } else {
                errorCode = ERROR_USER_INEXIST;
            }
        } catch (SQLException e) {
            e.printStackTrace();
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
        // TODO Auto-generated method stub
        serverResult = new ServerResult<User>();
        serverResult.setErrorCode(errorCode);
        serverResult.setResult(result);
        List<User> tList = new ArrayList<>();
        tList.add(user);
        serverResult.setObjects(tList);
        return serverResult;
    }

}
