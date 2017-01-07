package edu.buu.childhood.util;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

/**
 * 2016/6/14
 * @author Joe
 * @note 将Gson封装为抽象类，方法写为静态方便调用，避免各模块中实例化Gson
 * @param typeToken：指定转换类型，其中可包含List<obj>形式，可取得List中对象
 */
public abstract class JSONUtil{
	private static Gson json=new Gson();
	
	public static String toJson(Object obj,TypeToken<?> typeToken){
		return json.toJson(obj, typeToken.getType());
	}
	
	public static Object fromJson(String jsonStr,TypeToken<?> typeToken){
		return json.fromJson(jsonStr,typeToken.getType());
	}
	
}
