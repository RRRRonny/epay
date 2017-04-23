package com.chao117.service;

import com.chao117.base.constant.DatabaseField;
import com.chao117.model.Goods;
import com.chao117.model.Picture;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by chan on 23/04/2017.
 */
public class QueryPictureHelper implements DatabaseField {
    private DBHelper helper;

    public Picture querySinglePicture(int id) {
        String sqlString = "select * from table_pics where id = ?";
        Picture picture = null;
        helper = new DBHelper(sqlString);
        try {
            helper.pst.setInt(1, id);
            ResultSet set = helper.pst.executeQuery();
            if (set.next()) {
                picture = new Picture();
                picture.setId(id);
                picture.setUrl(set.getString(set.findColumn(P_URL)));
                picture.setTimestamp(set.getLong(set.findColumn(P_TIMESTAMP)));
                Goods goods = new Goods();
                goods.setId(set.getInt(set.findColumn(G_ID)));
                picture.setGoods(goods);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            return picture;
        }

    }

    public Picture querySinglePicture(Picture picture) {
        return querySinglePicture(picture.getId());
    }

    public List<Picture> queryPicturesByGoodsId(int id) {
        List<Picture> pictureList = null;
        boolean mBool = false;
        String sqlString = "select * from table_pics where g_id = ?";
        helper = new DBHelper(sqlString);
        try {
            helper.pst.setInt(1, id);
            ResultSet set = helper.pst.executeQuery();
            while (set.next()) {
                if (!mBool) {
                    pictureList = new ArrayList<>();
                    mBool = !mBool;
                }
                Picture picture = new Picture();
                picture.setId(set.getInt(set.findColumn(ID)));
                picture.setUrl(set.getString(set.findColumn(P_URL)));
                Goods goods = new Goods();
                goods.setId(id);
                picture.setGoods(goods);
                picture.setTimestamp(set.getLong(set.findColumn(P_TIMESTAMP)));
                pictureList.add(picture);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            return pictureList;
        }
    }

    public List<Picture> queryPictureByGoodsId(Goods goods) {
        return queryPicturesByGoodsId(goods.getId());
    }

}
