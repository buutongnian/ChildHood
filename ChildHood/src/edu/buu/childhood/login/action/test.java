package edu.buu.childhood.login.action;

import com.google.gson.Gson;

import edu.buu.childhood.login.pojo.LoginMessage;

public class test {
public static void main(String[] args) {
	Gson json=new Gson();
	System.out.println(json.toJson(new LoginMessage("中文输出","中文输出")));
}
}
