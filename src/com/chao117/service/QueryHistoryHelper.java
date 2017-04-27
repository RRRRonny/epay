package com.chao117.service;

import com.chao117.base.constant.DatabaseField;
import com.chao117.model.Goods;
import com.chao117.model.History;
import com.chao117.model.User;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by chan on 23/04/2017.
 */
public class QueryHistoryHelper implements DatabaseField {
    private DBHelper helper;

    public List<History> queryHistory(int id) {
        String sqlString = "select * from table_history where u_id = ?";
        helper = new DBHelper(sqlString);
        List<History> historyList = null;
        try {
            helper.pst.setInt(1, id);
            ResultSet set = helper.pst.executeQuery();
            boolean mBool = false;
            while (set.next()) {
                if (!mBool) {
                    mBool = !mBool;
                    historyList = new ArrayList<>();
                }
                History history = new History();
                history.setId(set.getInt(set.findColumn(ID)));
                Goods goods = new QueryGoodsHelper().querySingleGoods(set.getInt(set.findColumn(G_ID)));
                history.setGoods(goods);
                history.setRemark(set.getString(set.findColumn(H_REMARK)));
                history.setTimestamp(set.getLong(set.findColumn(H_TIMESTAMP)));
                historyList.add(history);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            helper.close();
            return historyList;
        }
    }

    public List<History> queryHistory(User user) {
        return queryHistory(user.getId());
    }

}
