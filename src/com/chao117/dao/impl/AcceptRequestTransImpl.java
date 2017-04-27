package com.chao117.dao.impl;

import com.chao117.base.Utils;
import com.chao117.dao.BaseDAO;
import com.chao117.model.*;
import com.chao117.service.InsertRequestTransHelper;
import com.chao117.service.InsertTransHelper;
import com.chao117.service.QueryGoodsHelper;
import com.chao117.service.QueryReqTransHelper;

import java.util.List;

/**
 * Created by chan on 24/04/2017.
 * 接受交易,接受该交易
 */
public class AcceptRequestTransImpl implements BaseDAO<RequestTrans> {
    private int errorCode;
    private String result;
    private ServerResult serverResult;
    private RequestTrans requestTrans;
    private Transaction transaction;

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
        int goodsId = requestTrans.getGoods().getId();
        QueryReqTransHelper queryReqTransHelper = new QueryReqTransHelper();
        List<RequestTrans> requestTransList = queryReqTransHelper.queryReqTransByBuyerId(goodsId);
        if (requestTransList == null) {
            result = RESULT_FAILURE;
            errorCode = ERROR_REFUSE;
            return false;
        }
        InsertRequestTransHelper insertRequestTransHelper = new InsertRequestTransHelper();
        for (int i = 0; i < requestTransList.size(); i++) {
            insertRequestTransHelper.updateState(requestTransList.get(i), 2);
        }

        Transaction transaction = new Transaction();
        transaction.setBuyer(requestTrans.getBuyer());
        transaction.setSaler(requestTrans.getSeller());
        transaction.setGoods(requestTrans.getGoods());
        transaction.setTimestamp(Utils.getTimestamp());

        TransState transState = new TransState();
        transState.setId(1);
        transaction.setTransState(transState);
        TransType transType = new TransType();
        transType.setId(1);
        transaction.setTransType(transType);
        System.out.println("生成交易单:" + transaction.toString());
        InsertTransHelper insertTransHelper = new InsertTransHelper();
        int lines = insertTransHelper.insertTrans(transaction);
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
        serverResult.setErrorCode(errorCode);
        serverResult.setResult(result);
        return serverResult;
    }
}
