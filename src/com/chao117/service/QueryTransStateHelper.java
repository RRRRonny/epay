package com.chao117.service;

import com.chao117.base.constant.DatabaseField;
import com.chao117.model.TransState;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by chan on 24/04/2017.
 */
public class QueryTransStateHelper implements DatabaseField {
    private DBHelper helper;

    public TransState queryTransState(int id) {
        TransState transState = null;
        String sqlString = "select * from table_trans_state where id = ?";
        helper = new DBHelper(sqlString);
        try {
            helper.pst.setInt(1, id);
            ResultSet set = helper.pst.executeQuery();
            if (set.next()) {
                transState = new TransState();
                transState.setId(id);
                transState.setContent(set.getString(set.findColumn(TS_CONTENT)));
                transState.setRemark(set.getString(set.findColumn(TS_REMARK)));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            helper.close();
            return transState;
        }
    }
}
