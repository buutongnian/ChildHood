package edu.buu.childhood.login.action;

import com.google.gson.Gson;

import edu.buu.childhood.common.Message;

public class test {
public static void main(String[] args) {
	Gson json=new Gson();
	System.out.println(json.toJson(new Message("中文输出","中文输出")));
}
}
