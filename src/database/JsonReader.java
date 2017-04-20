package database;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONObject;

public class JsonReader {
	File targetFile;
	List<Map<String, String>> list;

	public JsonReader(File targetFile) {
		this.targetFile = targetFile;
	}

	public List<Map<String, String>> getList() {
		list = new ArrayList<Map<String,String>>();
		boolean isProvince = false;
		if (targetFile.getName().equals("provinces.json")) {
			isProvince = true;
		}
		StringBuffer jsonStringBuffer = new StringBuffer();
		try {
			BufferedReader reader = new BufferedReader(new FileReader(targetFile));
			String line = reader.readLine();
			while (line != null) {
				jsonStringBuffer.append(line);
				line = reader.readLine();
			}
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		JSONArray jsonArray = new JSONArray(jsonStringBuffer.toString());
		for (int i = 0; i < jsonArray.length(); i++) {
			JSONObject jsonObject = jsonArray.getJSONObject(i);
			Map<String, String> map = new HashMap<>();
			map.put("code", jsonObject.getString("code"));
			map.put("name", jsonObject.getString("name"));
			// provinces.json 没有parent_code key, 会抛出异常,无法添加 map 到 list
			if (!isProvince) {
				map.put("parent_code", jsonObject.getString("parent_code"));
			}
			list.add(map);
		}
		return list;
	}
}
