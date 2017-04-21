package com.chao117.base;

import java.text.SimpleDateFormat;
import java.util.Date;


import org.json.JSONObject;

import com.chao117.base.constant.APIHelper;
import com.google.gson.Gson;

public class Utils implements APIHelper {

	/**
	 * 用 gson 生成 json
	 *
	 * @param object
	 *            对象
	 * @param targetClass
	 *            对象的类型
	 * @return
	 */
	public static String gsonBuilder(Object object, Class targetClass) {

		Gson gson = new Gson();
		String json = gson.toJson(object, targetClass);
		return json;
	}

	/**
	 * 用 gson 生成 json
	 * 
	 * @param object
	 * @return
	 */
	public static String gsonBuilder(Object object) {
		Gson gson = new Gson();
		String json = gson.toJson(object, object.getClass());
		return json;
	}

	/**
	 * 用 gson 解析 json, 生成实体类
	 * 
	 * @param json
	 * @param targetClass
	 * @return
	 */
	public static Object objectBuilder(String json, Class targetClass) {
		Object object = new Gson().fromJson(json, targetClass);
		return object;
	}
	
	/**
	 * 获取时间戳
	 * @return
	 */
	public static long getTimestamp(){
		return System.currentTimeMillis();
	}
	
	/**
	 * 获取指定格式的时间戳
	 * @return
	 */
	public static String getTimestampString(){
		long time   = System.currentTimeMillis();
	    return new SimpleDateFormat("yyyy-MM-dd-HH:mm:ss").format(new Date(time));
	}

}
