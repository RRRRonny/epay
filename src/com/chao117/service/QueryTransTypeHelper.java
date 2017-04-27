package com.chao117.service;

import com.chao117.base.constant.DatabaseField;
import com.chao117.model.TransType;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by chan on 24/04/2017.
 */
public class QueryTransTypeHelper implements DatabaseField {
    private DBHelper helper;

    public TransType queryTransType(int id) {
        TransType transType = null;
        String sqlString = "select * from table_trans_type where id = ?";
        helper = new DBHelper(sqlString);
        try {
            helper.pst.setInt(1, id);
            ResultSet set = helper.pst.executeQuery();
            if (set.next()) {
                transType = new TransType();
                transType.setId(id);
                transType.setContent(set.getString(set.findColumn(TT_CONTENT)));
                transType.setRemark(set.getString(set.findColumn(TT_REMARK)));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            helper.close();
            return transType;
        }
    }
}
