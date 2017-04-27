package com.chao117.service;

import com.chao117.base.constant.DatabaseField;
import com.chao117.model.GoodsState;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by chan on 21/04/2017.
 */
public class QueryGoodsStateHelper implements DatabaseField {
    private DBHelper helper;

    public GoodsState queryGoodsState(int id) {
        GoodsState goodsState = null;
        if (id < 1) {
            return goodsState;
        }
        String sqlString = "select * from table_goods_state where id = ?";
        helper = new DBHelper(sqlString);
        try {
            helper.pst.setInt(1, id);
            ResultSet set = helper.pst.executeQuery();
            if (set.next()) {
                goodsState = new GoodsState();
                goodsState.setId(id);
                goodsState.setContent(set.getString(set.findColumn(GS_CONTENT)));
                goodsState.setRemark(set.getString(set.findColumn(GS_REMARK)));
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return goodsState;
        } finally {
            helper.close();
            return goodsState;
        }
    }
}
