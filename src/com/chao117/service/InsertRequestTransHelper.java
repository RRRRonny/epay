package com.chao117.service;

import com.chao117.model.RequestTrans;

import java.sql.SQLException;

/**
 * Created by chan on 24/04/2017.
 */
public class InsertRequestTransHelper {

    private DBHelper helper;

    public int insertRequestTrans(RequestTrans requestTrans) {
        int lines = -1;
        String sqlString = "insert into table_req_trans (u_b_id,u_s_id,g_id,rt_timestamp) values () ";
        helper = new DBHelper(sqlString);
        try {
            helper.pst.setInt(1, requestTrans.getBuyer().getId());
            helper.pst.setInt(2, requestTrans.getSeller().getId());
            helper.pst.setInt(3, requestTrans.getGoods().getId());
            helper.pst.setLong(4, requestTrans.getTimestamp());
            lines = helper.pst.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            return lines;
        }
    }

    public int updateState(RequestTrans requestTrans, int state) {
        int lines = -1;
        String sqlString = "update table_req_trans set rt_state = ? where id = ?";
        helper = new DBHelper(sqlString);
        try {
            helper.pst.setInt(1, state);
            helper.pst.setInt(2, requestTrans.getId());
            lines = helper.pst.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            helper.close();
            return lines;
        }
    }
}
