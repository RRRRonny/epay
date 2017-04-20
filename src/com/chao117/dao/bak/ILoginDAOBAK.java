package com.chao117.dao.bak;


import org.json.JSONObject;


public interface ILoginDAOBAK {
	boolean init(JSONObject user);
	boolean isMath();
	boolean isOnline();
}
