package com.chao117.service;

import com.chao117.base.constant.DatabaseField;
import com.chao117.model.Location;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by chan on 21/04/2017.
 */
public class QueryLocationHelper implements DatabaseField {

    private DBHelper helper;

    public Location queryLocation(int id) {
        Location location = null;
        if (id < 1) {
            return location;
        }
        String sqlString = "select * from table_location where id = ?";
        helper = new DBHelper(sqlString);
        try {
            helper.pst.setInt(1, id);
            ResultSet set = helper.pst.executeQuery();
            if (set.next()) {
                location = new Location();
                location.setId(set.getInt(set.findColumn(ID)));
                location.setName(set.getString(set.findColumn(L_NAME)));
                location.setParentCode(set.getString(set.findColumn(L_PARENT_CODE)));
                location.setCode(set.getString(set.findColumn(L_CODE)));
                location.setRemark(set.getString(set.findColumn(L_REMARK)));
                location.setTimestamp(set.getLong(set.findColumn(L_TIMESTAMP)));
                return location;
            } else {
                return location;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return location;
        } finally {
            helper.close();
            return location;
        }
    }
}
