package com.chao117.service;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.chao117.base.constant.DatabaseField;
import com.chao117.model.User;

public class QueryUserHelper implements DatabaseField {
    private DBHelperBAK helper;

    public ResultSet query(int id) {
        String sqlString = "select * from table_user where id = " + "'" + id + "'";
        System.out.println("查询语句:" + sqlString);
        helper = new DBHelperBAK(sqlString);
        ResultSet set = helper.getResultSet();
        System.out.println("queryUser内set是否为空:" + (set == null));
        return set;
    }

    public ResultSet query(String account) {
        String sqlString = "select * from table_user where u_account = " + "'" + account + "'";
        System.out.println("查询语句:" + sqlString);
        helper = new DBHelperBAK(sqlString);
        ResultSet set = helper.getResultSet();
        System.out.println("queryUser内set是否为空:" + (set == null));
        return set;
    }

    public User querySingleUser(int id) {
        User user = null;
        String sqlString = "select * from table_user where id = " + "'" + id + "'";
        helper = new DBHelperBAK(sqlString);
        ResultSet set = helper.getResultSet();
        try {
            if (set.next()) {
                user = new User();
                user.setId(id);
                user.setAccount(set.getString(set.findColumn(U_ACCOUNT)));
                user.setBirth(set.getDate(set.findColumn(U_BIRTH_)));
                user.setGender(set.getString(set.findColumn(U_GENDER)));
                user.setHeader(set.getString(set.findColumn(U_HEADER)));
                user.setName(set.getString(set.findColumn(U_NAME)));
                user.setPhone(set.getString(set.findColumn(U_PHONE)));
                user.setTimestamp(set.getLong(set.findColumn(U_TIMESTAMP)));
                user.setRemark(set.getString(set.findColumn(U_REMARK)));
                user.setPassword("null");
                user.setHeaderLocal("null");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            return user;
        }
    }

}
