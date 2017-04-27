package com.chao117.service;

import java.sql.SQLException;

import com.chao117.base.constant.DatabaseField;
import com.chao117.model.User;

public class InsertUserHelper implements DatabaseField {
    private DBHelper helper;

    public int insertAccPsw(User user) {
        int result = -1;
        String sqlString = "insert into table_user (u_account,u_password) values (?,?)";
        helper = new DBHelper(sqlString);
        System.out.println("即将写入数据的用户注册信息,account:" + user.getAccount() + ",password:" + user.getPassword());
        if ("".equals(user.getAccount()) || "".equals(user.getPassword())) {
            System.out.println("数据不合法,拒绝写入");
            return result;
        }
        try {
            helper.pst.setString(1, user.getAccount());
            helper.pst.setString(2, user.getPassword());
            result = helper.pst.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            return result;
        }finally {
            helper.close();
        }
        return result;
    }


    public int updateUserPassword(User user) {
        System.out.println("需要修改的用户 id" + user.getId() + "  新密码:" + user.getPassword());
        int lines = -1;
        int id = user.getId();
        if (id < 1) {
            return lines;
        }
        String sqlString = "update table_user set u_password = ? where id = ?";
        helper = new DBHelper(sqlString);
        try {
            helper.pst.setString(1, user.getPassword() != null ? user.getPassword() : "admin");
            helper.pst.setInt(2, id);
            lines = helper.pst.executeUpdate();
            return lines;
        } catch (SQLException e) {
            e.printStackTrace();
            return lines;
        } finally {
            helper.close();
            return lines;
        }

    }
}
