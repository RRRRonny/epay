package com.chao117.dao.impl;

import com.chao117.dao.BaseDAO;
import com.chao117.model.Goods;
import com.chao117.model.ServerResult;
import com.chao117.service.QueryGoodsHelper;

import java.util.List;

/**
 * Created by chan on 24/04/2017.
 */
public class SimpleBrowseImpl implements BaseDAO<Goods> {
    private int errorCde;
    private String result;
    private ServerResult serverResult;
    private List<Goods> goodsList;

    @Override
    public int getErrorCode() {
        return errorCde;
    }

    @Override
    public boolean init(Goods object) {
        result = RESULT_FAILURE;
        errorCde = ERROR_NON;
        return false;
    }

    @Override
    public boolean doOperate() {
        goodsList = new QueryGoodsHelper().queryAllGoods();
        if (goodsList == null) {
            errorCde = ERROR_REFUSE;
        } else {
            result = RESULT_SUCCESS;
        }
        return false;
    }

    @Override
    public void updateErrorCode(int errorCode) {
        this.errorCde = errorCode;
    }

    @Override
    public ServerResult getServerResult() {
        serverResult = new ServerResult();
        serverResult.setErrorCode(errorCde);
        serverResult.setResult(result);
        serverResult.setObjects(goodsList);
        return serverResult;
    }
}
