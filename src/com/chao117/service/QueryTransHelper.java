package com.chao117.service;

import com.chao117.base.constant.DatabaseField;
import com.chao117.model.*;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by chan on 24/04/2017.
 */
public class QueryTransHelper implements DatabaseField {
    private DBHelper helper;

    public List<Transaction> queryTransByBuyerId(int id) {
        List<Transaction> transactionList = null;
        boolean mBool = false;
        String sqlString = "select from table_transaction where u_b_id = ?";
        return getTransactionList(id, transactionList, mBool, sqlString);
    }

    private List<Transaction> getTransactionList(int id, List<Transaction> transactionList, boolean mBool, String sqlString) {
        helper = new DBHelper(sqlString);
        try {
            helper.pst.setInt(1, id);
            ResultSet set = helper.pst.executeQuery();
            while (set.next()) {
                if (!mBool) {
                    transactionList = new ArrayList<>();
                    mBool = !mBool;
                }
                Transaction transaction = new Transaction();
                transaction.setId(set.getInt(set.findColumn(ID)));
                QueryUserHelper queryUserHelper = new QueryUserHelper();
                User buyer = queryUserHelper.querySingleUser(set.getInt(set.findColumn(U_B_ID)));
                User seller = queryUserHelper.querySingleUser(set.getInt(set.findColumn(U_S_ID)));
                transaction.setBuyer(buyer);
                transaction.setSaler(seller);
                transaction.setTimestamp(set.getLong(set.findColumn(T_TIMESTAMP)));
                QueryGoodsHelper queryGoodsHelper = new QueryGoodsHelper();
                Goods goods = queryGoodsHelper.querySingleGoods(set.getInt(set.findColumn(G_ID)));
                transaction.setGoods(goods);
                TransState transState = new QueryTransStateHelper().queryTransState(set.getInt(set.findColumn(TS_ID)));
                transaction.setTransState(transState);
                TransType transType = new QueryTransTypeHelper().queryTransType(set.getInt(set.findColumn(TT_ID)));
                transaction.setTransType(transType);
                transaction.setBuyerRemark(set.getString(set.findColumn(T_B_REMARK)));
                transaction.setSalerRemark(set.getString(set.findColumn(T_S_REMARK)));
                transactionList.add(transaction);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            helper.close();
            return transactionList;
        }
    }

    public List<Transaction> queryTransBySellerId(int id) {
        List<Transaction> transactionList = null;
        boolean mBool = false;
        String sqlString = "select from table_transaction where u_s_id = ?";
        return getTransactionList(id, transactionList, mBool, sqlString);
    }
}
