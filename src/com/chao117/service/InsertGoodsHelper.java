package com.chao117.service;

import java.sql.SQLException;

import com.chao117.base.Utils;
import com.chao117.model.Goods;

public class InsertGoodsHelper {
	private DBHelper helper;

	public int insertGoods(Goods goods) {
		int lines = -1;
		String sqlString = "insert into table_goods (g_name,c_id,u_id,g_price,gs_id,l_id,p_main_id,g_timestamp,g_remark) values (?,?,?,?,?,?,?,?,?)";
		helper = new DBHelper(sqlString);
		try {
			helper.pst.setString(1, goods.getName());
			helper.pst.setInt(2, goods.getCategory() != null ? goods.getCategory().getId() : 1);
			helper.pst.setInt(3, goods.getUser() != null ? goods.getUser().getId() : 1);
//			helper.pst.setString(4, goods.getName());
			helper.pst.setDouble(4, goods.getPrice());
			helper.pst.setInt(5, goods.getGoodsState() != null ? goods.getGoodsState().getId() : 1);
			helper.pst.setInt(6, goods.getLocation() != null ? goods.getLocation().getId() : 1);
			helper.pst.setInt(7, goods.getMainPicture() != null ? goods.getMainPicture().getId() : 1);
			helper.pst.setLong(8, Utils.getTimestamp());
			helper.pst.setString(9, goods.getRemark());
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
