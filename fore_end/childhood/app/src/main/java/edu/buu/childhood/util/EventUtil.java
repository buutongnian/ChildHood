package edu.buu.childhood.util;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.Map;
import java.util.Map.Entry;

public abstract class EventUtil {
	private static Gson json = new Gson();

	public static String replaceStr(String template, String params) {
		if (params != null) {
			String newStr = template;
			Map<String, String> paramsMap = json.fromJson(params,
					new TypeToken<Map<String, String>>() {
					}.getType());
			if (paramsMap.isEmpty()) {
				return template;
			}
			for (Entry<String, String> entry : paramsMap.entrySet()) {
				newStr = newStr.replace(entry.getKey(), entry.getValue());
			}
			return newStr;
		}
		return template;
	}
}
