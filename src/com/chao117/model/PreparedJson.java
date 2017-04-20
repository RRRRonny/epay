package com.chao117.model;

import org.json.JSONException;
import org.json.JSONObject;

public class PreparedJson {
	private JSONObject jsonObject;
	private String what;

	public PreparedJson() {

	}

	public PreparedJson(String json,String what) {
		try {
			this.jsonObject  = new JSONObject(json);
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			System.out.println("解析异常");
			e.printStackTrace();
		}
		this.what = what;
	}

	public JSONObject getJsonObject() {
		return jsonObject;
	}

	public void setJsonObject(JSONObject jsonObject) {
		this.jsonObject = jsonObject;
	}

	public String getWhat() {
		return what;
	}

	public void setWhat(String what) {
		this.what = what;
	}

	@Override
	public String toString() {
		return "PreparedJson [jsonObject=" + jsonObject + ", what=" + what + "]";
	}

	
}
