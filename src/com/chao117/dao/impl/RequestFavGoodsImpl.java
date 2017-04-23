package com.chao117.dao.impl;

import com.chao117.dao.BaseDAO;
import com.chao117.model.*;
import com.chao117.service.*;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by chan on 21/04/2017.
 */
public class RequestFavGoodsImpl implements BaseDAO<User> {
    private int errorCode;
    private String result;
    private User user;

    private ServerResult<Goods> serverResult;
    private List<Goods> goodsList;

    @Override
    public int getErrorCode() {
        return errorCode;
    }

    @Override
    public boolean init(User object) {
        this.user = object;
        updateErrorCode(ERROR_NON);
        result = RESULT_FAILURE;
        return false;
    }

    @Override
    public boolean doOperate() {
        QueryFollowHelper helper = new QueryFollowHelper();
        ResultSet set = helper.queryFollow(user);
        if (set == null) {
            updateErrorCode(ERROR_REFUSE);
        }
        List<Integer> goodsIds = null;
        boolean mBool = false;
        try {
            while (set.next()) {
                if (!mBool) {
                    goodsIds = new ArrayList<>();
                    goodsList = new ArrayList<>();
                    mBool = !mBool;
                }
                goodsIds.add(set.getInt(set.findColumn(G_ID)));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        QueryGoodsHelper queryGoodsHelper = new QueryGoodsHelper();
        for (int i = 0; i < goodsIds.size(); i++) {
            goodsList.add(queryGoodsHelper.querySingleGoods(goodsIds.get(i)));
        }
        return false;
    }

    @Override
    public void updateErrorCode(int errorCode) {
        this.errorCode = errorCode;
    }

    @Override
    public ServerResult getServerResult() {
        serverResult = new ServerResult<Goods>();
        serverResult.setObjects(goodsList);
        serverResult.setErrorCode(errorCode);
        serverResult.setResult(result);
        return serverResult;
    }
}
