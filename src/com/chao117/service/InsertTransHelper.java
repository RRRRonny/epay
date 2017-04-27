package com.chao117.service;

import com.chao117.model.Goods;
import com.chao117.model.Transaction;

import java.sql.SQLException;

/**
 * Created by chan on 24/04/2017.
 */
public class InsertTransHelper {
    private DBHelper helper;

    public int insertTrans(Transaction transaction) {
        int lines = -1;
        String sqlString = "insert into table_transaction (u_b_id,u_s_id,g_id,ts_id,tt_id,t_b_remark,t_s_remark,t_timestamp) values (?,?,?,?,?,?,?,?)";
        helper = new DBHelper(sqlString);
        try {
            helper.pst.setInt(1, transaction.getBuyer().getId());
            helper.pst.setInt(2, transaction.getSaler().getId());
            helper.pst.setInt(3, transaction.getGoods().getId());
            helper.pst.setInt(4, transaction.getTransState().getId());
            helper.pst.setInt(5, transaction.getTransType().getId());
            helper.pst.setString(6, transaction.getBuyerRemark());
            helper.pst.setString(7, transaction.getSalerRemark());
            helper.pst.setLong(8, transaction.getTimestamp());
            lines = helper.pst.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            helper.close();
            return lines;
        }
    }

    public int updateTransStateToCancel(Transaction transaction) {
        int lines = -1;
        String sqlString = "update table_transaction set ts_id = ? where id = ?";
        helper = new DBHelper(sqlString);
        try {
            helper.pst.setInt(1, 2);
            helper.pst.setInt(2, transaction.getId());
            lines = helper.pst.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            helper.close();
            return lines;
        }
    }

    public int updateTrans(Transaction transaction) {
        int lines = -1;
        String sqlString = "update table_transaction set u_b_id = ?,u_s_id = ?,g_id = ?,ts_id = ?,tt_id = ?,t_b_remark = ?,t_s_remark = ?";
        helper = new DBHelper(sqlString);
        try {
            helper.pst.setInt(1, transaction.getBuyer().getId());
            helper.pst.setInt(2, transaction.getSaler().getId());
            helper.pst.setInt(3, transaction.getGoods().getId());
            helper.pst.setInt(4, transaction.getTransState().getId());
            helper.pst.setInt(5, transaction.getTransType().getId());
            helper.pst.setString(6, transaction.getBuyerRemark());
            helper.pst.setString(7, transaction.getSalerRemark());
            lines = helper.pst.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            helper.close();
            return lines;
        }
    }
}
