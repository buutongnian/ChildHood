package edu.buu.childhood.algorithms;

import java.util.Vector;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

/**
 * 向量操作工具类，包含从json字符串解析成Vector对象、普安段向量是否可乘、点乘向量三个方法 2016/10/25
 * 
 * @author joe
 *
 */
public class VectorUtil {
	private static Gson gson = new Gson();

	/**
	 * 将json格式的向量字符串解析成Vector对象
	 * 
	 * @param json
	 *            传入json字符串
	 * @return 解析成功返回特征向量
	 * @throws NullPointerException
	 *             解析不成功或传入为空，throw异常
	 */
	public static Vector<Float> parseJsonToVector(String json)
			throws NullPointerException {
		Vector<Float> CV = gson.fromJson(json, new TypeToken<Vector<Float>>() {
		}.getType());
		if (CV != null) {
			return CV;
		}
		throw new NullPointerException(
				"The Game Characteristic Vector(GCV) or User Characteristic Vector(UCV) is null!");
	}

	/**
	 * 将Vector对象装换成json字符串
	 * 
	 * @param vec
	 *            传入向量
	 * @return 向量的json字符串
	 */
	public static String parseVectorToJson(Vector<Float> vec) {
		return gson.toJson(vec, new TypeToken<Vector<Float>>() {
		}.getType());
	}

	/**
	 * 判断两个向量是否可乘
	 * 
	 * @param vec1
	 *            向量1
	 * @param vec2
	 *            向量2
	 * @return 布尔型是否可乘结果
	 */
	public static boolean isMultipliable(Vector<Float> vec1, Vector<Float> vec2) {
		if (vec1 != null && vec2 != null && vec1.size() == vec2.size()) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * 点乘两个向量
	 * 
	 * @param vec1
	 *            向量1
	 * @param vec2
	 *            向量2
	 * @return 点乘结果，如果向量不可点乘返回0
	 */
	public static float docProduct(Vector<Float> vec1, Vector<Float> vec2) {
		if (isMultipliable(vec1, vec2)) {
			float result = 0f;
			for (int i = 0; i < vec1.size(); i++) {
				result += vec1.elementAt(i) * vec2.elementAt(i);
			}
			return result;
		} else {
			return 0f;
		}
	}
}
