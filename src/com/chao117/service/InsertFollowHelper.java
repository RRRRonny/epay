package com.chao117.service;

import com.chao117.base.Utils;
import com.chao117.model.Follow;
import com.chao117.model.Goods;
import com.chao117.model.User;

import java.sql.SQLException;

/**
 * Created by chan on 21/04/2017.
 */
public class InsertFollowHelper {
    private DBHelper helper;

    public int insertFollow(Follow follow) {
        String sqlString = "insert into table_follow (u_b_id , u_s_id , g_id , f_remark ,f_timestamp) values (?,?,?,?,?)";
        helper = new DBHelper(sqlString);
        User buyer = follow.getBuyer();
        User seller = follow.getSaler();
        Goods goods = follow.getGoods();
        int lines = -1;
        if (buyer == null || seller == null || goods == null) {
            return lines;
        }
        try {
            helper.pst.setInt(1, follow.getBuyer().getId());
            helper.pst.setInt(2, follow.getSaler().getId());
            helper.pst.setInt(3, follow.getGoods().getId());
            helper.pst.setString(4, follow.getRemark());
            helper.pst.setLong(5, Utils.getTimestamp());
            lines = helper.pst.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            return lines;
        } finally {
            helper.close();
            return lines;
        }
    }
}
