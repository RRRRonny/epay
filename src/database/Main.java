package database;

import java.io.File;
import java.io.FileReader;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import javax.swing.text.DefaultEditorKit.InsertBreakAction;

import org.omg.IOP.Codec;

import com.chao117.service.DBHelperBAK;
import com.chao117.service.DBHelper;

public class Main {

	public static void main(String[] args) {
		int times = 0;
		List<Map<String, String>> provinceList;
		List<Map<String, String>> citiesList;
		List<Map<String, String>> areaList;

		File rootFile = new File("/Users/mymac/Downloads/region");
		String provinceString = "provinces.json";
		String citiesString = "cities.json";
		String areasStrignString = "areas.json";

		File provinceFile = new File(rootFile, provinceString);
		File citiesFile = new File(rootFile, citiesString);
		File areasFile = new File(rootFile, areasStrignString);
		boolean shouldContinue = provinceFile.exists() && citiesFile.exists() && areasFile.exists();
		if (shouldContinue) {
			provinceList = new JsonReader(provinceFile).getList();
			citiesList = new JsonReader(citiesFile).getList();
			areaList = new JsonReader(areasFile).getList();
		} else {
			return;
		}
		// System.out.println("pro:" + provinceList.toString());
		// System.out.println("city:"+citiesList.toString());
		// System.out.println("area:"+areaList.toString());

		// 插入省
		String sqlString = "insert into table_location (l_name,l_code) values (?,?)";
		for (int i = 0; i < provinceList.size(); i++) {
			System.out.println("插入第" + (++times) + "条");
			System.out.println("准备插入数据:" + provinceList.get(i).toString());
			// 插入数据
			DBHelper helper = new DBHelper(sqlString);
			try {
				helper.pst.setString(1, provinceList.get(i).get("name"));
				helper.pst.setString(2, provinceList.get(i).get("code"));
				int result = helper.pst.executeUpdate();
				System.out.println("插入数据,影响行数:" + result);
				helper.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		// 插入市
		String citySqlString = "insert into table_location (l_name,l_code,l_parent_code) values (?,?,?)";
		for (int i = 0; i < citiesList.size(); i++) {
			System.out.println("插入第" + (++times) + "条");
			System.out.println("准备插入数据:" + citiesList.get(i).toString());
			// String parentName = new GetParentName(provinceList,
			// citiesList.get(i).get("parent_code")).getParentName();
			// System.out.println("插入详细数据:" + "l_one:" + parentName + "l_two:" +
			// citiesList.get(i).get("name"));
			// 插入数据
			DBHelper helper = new DBHelper(citySqlString);
			try {
				helper.pst.setString(1, citiesList.get(i).get("name"));
				helper.pst.setString(2, citiesList.get(i).get("code"));
				helper.pst.setString(3, citiesList.get(i).get("parent_code"));
				int result = helper.pst.executeUpdate();
				System.out.println("插入数据,影响行数:" + result);
				helper.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		// 插入区
		String areaSqlString = "insert into table_location (l_name,l_code,l_parent_code) values (?,?,?)";
		for (int i = 0; i < areaList.size(); i++) {
			System.out.println("插入第" + (++times) + "条");
			System.out.println("准备插入数据:" + areaList.get(i).toString());
			// String grandparentName = new GetParentName(citiesList,
			// areaList.get(i).get("parent_code")).getParentName();
			// String parentName = new GetParentName(citiesList,
			// areaList.get(i).get("parent_code")).getParentName();

			// System.out.println("插入详细数据:" + "l_one:" + grandparentName +
			// "l_two:" + parentName + "l_three"
			// + citiesList.get(i).get("name"));

			// 插入数据
			DBHelper helper = new DBHelper(areaSqlString);
			try {
				helper.pst.setString(1, areaList.get(i).get("name"));
				helper.pst.setString(2, areaList.get(i).get("code"));
				helper.pst.setString(3, areaList.get(i).get("parent_code"));
				int result = helper.pst.executeUpdate();
				System.out.println("插入数据,影响行数:" + result);
				helper.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

}
