package edu.buu.childhood.algorithms;

import java.util.Vector;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

/**
 * 
 * User Characteristic Vector Evolution Algorithm 用户特征向量进化算法，用户查看、发起、加入或完成游戏后，
 * 通过所查看/进行游戏的特征向量与进化向量迭代学习用户特征向量 进化模型暂时采用1-arctan(x)/(PI/2) 2016/10/23
 * 
 * @author joe
 *
 */
public final class UCVE {
	private float rate = 0.1f;
	private Gson gson = new Gson();
	private static final Vector<Float> avgX = new Vector<Float>() {
		private static final long serialVersionUID = 9070757309005579398L;

		{
			// [0.4154,-0.2345,0.4462,0.5615,0.6455,0.5462,0.5462]
			addElement(0.4154f);
			addElement(-0.2345f);
			addElement(0.4462f);
			addElement(0.5615f);
			addElement(0.6455f);
			addElement(0.5462f);
			addElement(0.5462f);
		}
	};

	public float getRate() {
		return rate;
	}

	public void setRate(float rate) {
		this.rate = rate;
	}

	/**
	 * 通过游戏特征向量和用户特征向量获得下一代用户特征向量
	 * 
	 * @param GCV
	 *            游戏特征向量(Game Characteristic Vector)
	 * @param UCV
	 *            用户特征向量(User Characteristic Vector)
	 * @return 下一代用户特征向量
	 * @throws VectorCanNotMultiplyException
	 *             throw向量不可乘异常
	 */
	public Vector<Float> getNextGeneration(Vector<Float> GCV, Vector<Float> UCV) throws VectorCanNotMultiplyException {
		Vector<Float> ECV = getEvolutionVector(GCV);
		Vector<Float> newUCV = new Vector<Float>(UCV.size());
		if (VectorUtil.isMultipliable(ECV, UCV)) {
			for (int i = 0; i < UCV.size(); i++) {
				float ele = UCV.elementAt(i) * ECV.elementAt(i);
				newUCV.addElement(ele);
			}
			return newUCV;
		} else {
			throw new VectorCanNotMultiplyException("The size of GCV and UCV is not equal or one of them is null.");
		}
	}

	/**
	 * 通过游戏特征向量json字符串和用户特征向量json字符串获得下一代用户特征向量
	 * 
	 * @param GCVjson
	 *            游戏特征向量(Game Characteristic Vector)json字符串
	 * @param UCVjson
	 *            用户特征向量(User Characteristic Vector)json字符串
	 * @return 下一代用户特征向量
	 * @throws VectorCanNotMultiplyException
	 *             throw向量不可乘异常
	 */
	public Vector<Float> getNextGeneration(String GCVjson, String UCVjson) throws VectorCanNotMultiplyException {
		Vector<Float> GCV = VectorUtil.parseJsonToVector(GCVjson);
		Vector<Float> UCV = VectorUtil.parseJsonToVector(UCVjson);
		return getNextGeneration(GCV, UCV);
	}

	/**
	 * 通过游戏特征向量和用户特征向量获取下一代向量的json字符串
	 * 
	 * @param GCV
	 *            游戏特征向量(Game Characteristic Vector)
	 * @param UCV
	 *            用户特征向量(User Characteristic Vector)
	 * @return 下一代用户特征向量的json字符串
	 * @throws VectorCanNotMultiplyException
	 *             throw向量不可乘异常
	 */
	public String getNextGenerationJson(Vector<Float> GCV, Vector<Float> UCV) throws VectorCanNotMultiplyException {
		return gson.toJson(getNextGeneration(GCV, UCV), new TypeToken<Vector<Float>>() {
		}.getType());
	}

	/**
	 * 通过游戏特征向量json字符串和用户特征向量json字符产获取下一代向量的json字符串
	 * 
	 * @param GCVjson
	 *            游戏特征向量(Game Characteristic Vector)json字符串
	 * @param UCVjson
	 *            用户特征向量(User Characteristic Vector)json字符串
	 * @return 下一代用户特征向量的json字符串
	 * @throws VectorCanNotMultiplyException
	 *             throw向量不可乘异常
	 */
	public String getNextGenerationJson(String GCVjson, String UCVjson) throws VectorCanNotMultiplyException {
		Vector<Float> UCV = VectorUtil.parseJsonToVector(UCVjson);
		Vector<Float> GCV = VectorUtil.parseJsonToVector(GCVjson);
		return gson.toJson(getNextGeneration(GCV, UCV), new TypeToken<Vector<Float>>() {
		}.getType());
	}

	/**
	 * 获得进化向量
	 * 
	 * @param GCV
	 *            游戏特征向量(Game Characteristic Vector)
	 * @return 进化向量
	 */
	public Vector<Float> getEvolutionVector(Vector<Float> GCV) {
		Vector<Float> ECV = new Vector<Float>(GCV.size());
		for (int i = 0; i < GCV.size(); i++) {
			float x = GCV.elementAt(i);
			float xAvg = avgX.elementAt(i);
			float diff = x - xAvg;
			ECV.addElement((float) (Math.atan(rate * diff) / Math.PI / 2 + 1));
		}
		return ECV;
	}
}
