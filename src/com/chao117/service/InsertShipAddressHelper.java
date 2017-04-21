package com.chao117.service;

import com.chao117.base.Utils;
import com.chao117.model.Location;
import com.chao117.model.ShipAddress;
import com.chao117.model.User;

import java.sql.SQLException;

/**
 * Created by chan on 21/04/2017.
 */
public class InsertShipAddressHelper {
    private DBHelper helper;


    public int insertShipAddress(ShipAddress shipAddress) {
        int lines = -1;
        User user = shipAddress.getUser();
        Location location = shipAddress.getLocation();
        int userId = user.getId();
        int locationId = location.getId();
        if (user == null || location == null) {
            return lines;
        }
        if (userId < 2 || locationId < 1) {
            return lines;
        }
        String sqlString = "insert into table_ship_address (u_id,l_id,sa_detail,sa_remark,sa_timestamp) values (?,?,?,?,?)";
        helper = new DBHelper(sqlString);
        try {
            helper.pst.setInt(1, userId);
            helper.pst.setInt(2, locationId);
            helper.pst.setString(3, shipAddress.getDetail());
            helper.pst.setString(4, shipAddress.getRemark());
            helper.pst.setLong(5, Utils.getTimestamp());
            lines = helper.pst.executeUpdate();
            return lines;
        } catch (SQLException e) {
            e.printStackTrace();
            return lines;
        } finally {
            return lines;
        }
    }
}
