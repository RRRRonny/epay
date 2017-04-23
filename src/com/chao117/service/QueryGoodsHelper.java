package com.chao117.service;

import com.chao117.base.constant.DatabaseField;
import com.chao117.model.*;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by chan on 23/04/2017.
 */
public class QueryGoodsHelper implements DatabaseField {
    private DBHelper helper;

    public Goods querySingleGoods(int id) {
        String sqlString = "select * from table_goods where id  = ?";
        helper = new DBHelper(sqlString);
        Goods goods = new Goods();
        ResultSet set = null;
        try {
            helper.pst.setInt(1, id);
            set = helper.pst.executeQuery();
            if (set.next()) {
                goods.setId(set.getInt(set.findColumn(ID)));
                //name
                goods.setName(set.getString(set.findColumn(G_NAME)));
                //联合查询
                //location
                Location location = new QueryLocationHelper().queryLocation(set.getInt(set.findColumn(L_ID)));
                goods.setLocation(location);
                //user/owner
                User user = new QueryUserHelper().querySingleUser(set.getInt(set.findColumn(U_ID)));
                goods.setUser(user);
                //category
                Category category = new QueryCategoryHelper().queryCategory(set.getInt(set.findColumn(C_ID)));
                goods.setCategory(category);
                //goods_state
                GoodsState goodsState = new QueryGoodsStateHelper().queryGoodsState(set.getInt(set.findColumn(GS_ID)));
                goods.setGoodsState(goodsState);
                //timestamp
                long timestamp = set.getLong(set.findColumn(G_TIMESTAMP));
                goods.setTimestamp(timestamp);
                //main picture
                Picture picture = new QueryPictureHelper().querySinglePicture(set.getInt(set.findColumn(P_MAIN_ID)));
                goods.setMainPicture(picture);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return goods;
    }

    public Goods querySingleGoods(Goods goods) {
        return querySingleGoods(goods.getId());
    }
}
