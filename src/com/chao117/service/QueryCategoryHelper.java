package com.chao117.service;

import com.chao117.base.constant.DatabaseField;
import com.chao117.model.Category;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by chan on 21/04/2017.
 */
public class QueryCategoryHelper implements DatabaseField {
    private DBHelper helper;

    public Category queryCategory(int id) {
        Category category = null;
        String sqlString = "select * from table_category where id = ?";
        helper = new DBHelper(sqlString);
        try {
            helper.pst.setInt(1, id);
            ResultSet set = helper.pst.executeQuery();
            if (set.next()) {
                category = new Category();
                category.setId(set.getInt(set.findColumn(ID)));
                category.setName(set.getString(set.findColumn(C_NAME)));
                category.setRemark(set.getString(set.findColumn(C_REMARK)));
                category.setParentCode(set.getString(set.findColumn(C_PARENT_CODE)));
                category.setCode(set.getString(set.findColumn(C_CODE)));
                category.setTimestamp(set.getLong(set.findColumn(C_TIMESTAMP)));
                return category;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            helper.close();
            return category;
        }
    }
}
