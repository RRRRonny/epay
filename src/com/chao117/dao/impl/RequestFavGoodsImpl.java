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
        goodsList = new ArrayList<>();
        try {
            while (set.next()) {
                Goods goods = new Goods();
                goods.setId(set.getInt(set.findColumn(ID)));
                //联合查询
                //location
                Location location = new QueryLocationHelper().queryLocation(set.getInt(set.findColumn(L_ID)));
                goods.setLocation(location);
                //buyer
                User buyer = new QueryUserHelper().querySingleUser(set.getInt(set.findColumn(U_B_ID)));
                //todo 重要,逻辑混乱
                //seller
                User seller = new QueryUserHelper().querySingleUser(set.getInt(set.findColumn(U_S_ID)));
                //category
                Category category = new QueryCategoryHelper().queryCategory(set.getInt(set.findColumn(C_ID)));
                //goods_state
                GoodsState goodsState = new QueryGoodsStateHelper().queryGoodsState(set.getInt(set.findColumn(GS_ID)));

            }
        } catch (SQLException e) {
            e.printStackTrace();
            updateErrorCode(ERROR_REFUSE);
        } finally {
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
        serverResult = new ServerResult<Goods>();
        serverResult.setObjects(goodsList);
        serverResult.setErrorCode(errorCode);
        serverResult.setResult(result);
        return serverResult;
    }
}
