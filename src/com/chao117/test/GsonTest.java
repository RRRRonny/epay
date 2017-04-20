package com.chao117.test;

import com.chao117.model.User;
import com.google.gson.Gson;

public class GsonTest {
	private String json;
	User user;
	public GsonTest(String json){
		this.json = json;
	}
	
	public User getUser(){
		user = new Gson().fromJson(json, User.class);
		return user;
	}
}	
