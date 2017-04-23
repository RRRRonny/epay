package com.chao117.dao.impl;

import com.chao117.dao.BaseDAO;
import com.chao117.model.Goods;
import com.chao117.model.ServerResult;
import com.chao117.service.QueryGoodsHelper;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by chan on 23/04/2017.
 */
public class RequestGoodsDetailImpl implements BaseDAO<Goods> {
    private int errorCode;
    private String result;
    private ServerResult<Goods> serverResult;
    private Goods goods;

    @Override
    public int getErrorCode() {
        return errorCode;
    }

    @Override
    public boolean init(Goods object) {
        updateErrorCode(ERROR_NON);
        result = RESULT_FAILURE;
        this.goods = object;
        return false;
    }

    @Override
    public boolean doOperate() {
        QueryGoodsHelper helper = new QueryGoodsHelper();
        goods = helper.querySingleGoods(goods);
        if (goods == null) {
            updateErrorCode(ERROR_REFUSE);
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
        serverResult = new ServerResult<>();
        serverResult.setErrorCode(errorCode);
        serverResult.setResult(result);
        List<Goods> tList = new ArrayList<>();
        tList.add(goods);
        serverResult.setObjects(tList);
        return serverResult;
    }
}
