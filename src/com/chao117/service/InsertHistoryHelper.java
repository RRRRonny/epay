package com.chao117.service;

import com.chao117.base.Utils;
import com.chao117.model.History;
import com.sun.org.apache.bcel.internal.generic.FNEG;

import java.sql.SQLException;

/**
 * Created by chan on 23/04/2017.
 */
public class InsertHistoryHelper {
    private DBHelper helper;

    public int insertHistory(History history) {
        String sqlString = "insert into table_history (u_id,g_id,h_remark,h_timestamp) values (?,?,?,?)";
        helper = new DBHelper(sqlString);
        int lines = -1;
        try {
            helper.pst.setInt(1, history.getUser().getId());
            helper.pst.setInt(2, history.getGoods().getId());
            helper.pst.setString(3, history.getRemark());
            helper.pst.setLong(4, Utils.getTimestamp());
            lines = helper.pst.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            return lines;
        }
    }
}
