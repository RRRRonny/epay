package com.chao117.service;

import com.chao117.model.User;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by chan on 21/04/2017.
 */
public class QueryShipAddressHelper {
    private DBHelper helper;

    public ResultSet queryShipAddress(User user) {
        ResultSet set = null;
        if (user == null) {
            return set;
        }
        int id = user.getId();
        if (id < 2) {
            return set;
        }
        String sqlString = "select * from table_ship_address where u_id = ?";
        helper = new DBHelper(sqlString);
        try {
            helper.pst.setInt(1, id);
            set = helper.pst.executeQuery();
        } catch (SQLException e) {
            e.printStackTrace();
            return set;
        } finally {
            return set;
        }
    }
}
