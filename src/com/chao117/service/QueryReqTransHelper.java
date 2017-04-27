package com.chao117.service;

import com.chao117.base.constant.DatabaseField;
import com.chao117.model.Goods;
import com.chao117.model.RequestTrans;
import com.chao117.model.User;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by chan on 24/04/2017.
 */
public class QueryReqTransHelper implements DatabaseField {
    private DBHelper helper;

    public List<RequestTrans> queryReqTransBySellerId(int sellerId) {
        List<RequestTrans> requestTransList = null;
        boolean mBool = false;
        String sqlString = "select * from table_req_trans where u_s_id = ?";
        return getRequestTrans(sellerId, requestTransList, mBool, sqlString);
    }

    private List<RequestTrans> getRequestTrans(int userId, List<RequestTrans> requestTransList, boolean mBool, String sqlString) {
        helper = new DBHelper(sqlString);
        try {
            helper.pst.setInt(1, userId);
            ResultSet set = helper.pst.executeQuery();
            while (set.next()) {
                if (!mBool) {
                    requestTransList = new ArrayList<>();
                    mBool = !mBool;
                }
                RequestTrans requestTrans
                        = new RequestTrans();
                requestTrans.setId(set.getInt(set.findColumn(ID)));
                User buyer = new QueryUserHelper().querySingleUser(set.getInt(set.findColumn(U_B_ID)));
                requestTrans.setBuyer(buyer);
                User seller = new QueryUserHelper().querySingleUser(set.getInt(set.findColumn(U_S_ID)));
                requestTrans.setSeller(seller);
                Goods goods = new QueryGoodsHelper().querySingleGoods(set.getInt(set.findColumn(G_ID)));
                requestTrans.setGoods(goods);
                requestTrans.setState(set.getInt(set.findColumn(RT_STATE)));
                requestTrans.setTimestamp(set.getLong(set.findColumn(RT_TIMESTAMP)));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            return requestTransList;
        }
    }

    public List<RequestTrans> queryReqTransBySellerId(User user) {
        return queryReqTransBySellerId(user.getId());
    }

    public List<RequestTrans> queryReqTransByBuyerId(int buyerId) {
        List<RequestTrans> requestTransList = null;
        boolean mBool = false;
        String sqlString = "select * from table_req_trans where u_b_id = ?";
        return getRequestTrans(buyerId, requestTransList, mBool, sqlString);
    }

    public List<RequestTrans> queryReqTransByBuyerId(User user) {
        return queryReqTransByBuyerId(user.getId());
    }

    public List<RequestTrans> queryReqTransByGoodsId(int goodsId) {
        List<RequestTrans> requestTransList = null;
        boolean mBool = false;
        String sqlString = "select * from table_req_trans where g_id = ?";
        helper = new DBHelper(sqlString);
        try {
            helper.pst.setInt(1, goodsId);
            ResultSet set = helper.pst.executeQuery();
            while (set.next()) {
                if (!mBool) {
                    mBool = !mBool;
                    requestTransList = new ArrayList<>();
                }
                RequestTrans requestTrans
                        = new RequestTrans();
                requestTrans.setId(set.getInt(set.findColumn(ID)));
                User buyer = new QueryUserHelper().querySingleUser(set.getInt(set.findColumn(U_B_ID)));
                requestTrans.setBuyer(buyer);
                User seller = new QueryUserHelper().querySingleUser(set.getInt(set.findColumn(U_S_ID)));
                requestTrans.setSeller(seller);
                Goods goods = new QueryGoodsHelper().querySingleGoods(set.getInt(set.findColumn(G_ID)));
                requestTrans.setGoods(goods);
                requestTrans.setState(set.getInt(set.findColumn(RT_STATE)));
                requestTrans.setTimestamp(set.getLong(set.findColumn(RT_TIMESTAMP)));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            return requestTransList;
        }
    }

    public List<RequestTrans> queryReqTransByGoodsId(Goods goods) {
        return queryReqTransByGoodsId(goods.getId());
    }

}
