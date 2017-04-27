package com.chao117.service;

import com.chao117.base.constant.DatabaseField;
import com.chao117.model.*;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by chan on 23/04/2017.
 */
public class QueryGoodsHelper implements DatabaseField {
    private DBHelper helper;

    public Goods querySingleGoods(int id) {
        String sqlString = "select * from table_goods where id  = ?";
        helper = new DBHelper(sqlString);
        ResultSet set = null;
        Goods goods = null;
        try {
            helper.pst.setInt(1, id);
            set = helper.pst.executeQuery();
            if (set.next()) {
                goods = new Goods();
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
                goods.setPrice(set.getDouble(set.findColumn(G_PRICE)));
                //main picture
                Picture picture = new QueryPictureHelper().querySinglePicture(set.getInt(set.findColumn(P_MAIN_ID)));
                goods.setMainPicture(picture);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            helper.close();
        }
        return goods;
    }

    public Goods querySingleGoods(Goods goods) {
        return querySingleGoods(goods.getId());
    }

    public List<Goods> queryAllGoods() {
        List<Goods> goodsList = null;
        boolean mBool = false;
        String sqlString = "select * from table_goods";
        helper = new DBHelper(sqlString);
        ResultSet set = null;
        Goods goods = null;
        try {
            set = helper.pst.executeQuery();
            while (set.next()) {
                System.out.println("插入新的商品");
                if (!mBool) {
                    goodsList = new ArrayList<>();
                    mBool = !mBool;
                }
                goods = new Goods();
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
                goods.setPrice(set.getDouble(set.findColumn(G_PRICE)));
                //main picture
                Picture picture = new QueryPictureHelper().querySinglePicture(set.getInt(set.findColumn(P_MAIN_ID)));
                goods.setMainPicture(picture);
                goodsList.add(goods);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            helper.close();
            return goodsList;
        }
    }
}
