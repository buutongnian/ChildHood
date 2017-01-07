package edu.buu.childhood.algorithms;

import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

import edu.buu.childhood.game.pojo.GCVEntry;

/**
 * Recommend List Spanning Algorithm 推荐列表生成算法 2016/10/25
 * 
 * @author joe
 *
 */
public final class RLS {
	private GameInfo[] gameMatrix;

	/**
	 * 
	 * 游戏特征矩阵中行向量单元，同时也是计算单元和排序单元
	 * 
	 * @author joe
	 *
	 */
	final class GameInfo {
		private int gameCode;
		private Vector<Float> GCV;
		private float listScore = 0;

		public int getGameCode() {
			return gameCode;
		}

		public void setGameCode(int gameCode) {
			this.gameCode = gameCode;
		}

		public Vector<Float> getGCV() {
			return GCV;
		}

		public void setGCV(Vector<Float> GCV) {
			this.GCV = GCV;
		}

		public void setGCV(String GCVjson) {
			this.GCV = VectorUtil.parseJsonToVector(GCVjson);
		}

		public float getListScore() {
			return listScore;
		}

		public void setListScore(float listScore) {
			this.listScore = listScore;
		}

	}

	/**
	 * 传入从数据库中取出的游戏特征向量列表用于构造游戏特征矩阵，仅支持这一种构造方法
	 * 
	 * @param gameList
	 */
	public RLS(List<GCVEntry> gameList) {
		super();
		gameMatrix = new GameInfo[gameList.size()];
		for (int i = 0; i < gameList.size(); i++) {
			GameInfo gameInfo = new GameInfo();
			gameInfo.setGameCode(gameList.get(i).getGameCode());
			gameInfo.setGCV(gameList.get(i).getGCVjson());
			gameMatrix[i] = gameInfo;
		}
	}

	/**
	 * 通过游戏特征向量与用户特征向量得到游戏排行评分
	 * 
	 * @param GCV
	 *            游戏特征向量
	 * @param UCV
	 *            用户特征向量
	 * @return 游戏排行评分
	 * @throws VectorCanNotMultiplyException
	 *             throw向量不可乘异常
	 */
	public float getGameScore(Vector<Float> GCV, Vector<Float> UCV)
			throws VectorCanNotMultiplyException {
		if (VectorUtil.isMultipliable(GCV, UCV)) {
			float score = 0f;
			score = VectorUtil.docProduct(GCV, UCV);
			return score;
		} else {
			throw new VectorCanNotMultiplyException(
					"The size of GCV and UCV is not equal or one of them is null.");
		}
	}

	/**
	 * 通过用户特征向量获得推荐游戏排行列表
	 * 
	 * @param UCV
	 *            用户特征向量
	 * @return 倒序排序过的游戏编号列表
	 */
	public List<Integer> getGameList(Vector<Float> UCV)
			throws VectorCanNotMultiplyException {
		for (int i = 0; i < gameMatrix.length; i++) {
			gameMatrix[i]
					.setListScore(getGameScore(gameMatrix[i].getGCV(), UCV));
		}
		GameInfo temp = null;
		for (int i = 0; i < gameMatrix.length - 1; i++) {
			for (int j = 0; j < gameMatrix.length - i - 1; j++) {
				if (gameMatrix[j].getListScore() < gameMatrix[j + 1]
						.getListScore()) {
					temp = gameMatrix[j];
					gameMatrix[j] = gameMatrix[j + 1];
					gameMatrix[j + 1] = temp;
				}
			}
		}
		List<Integer> gameListDesc = new ArrayList<Integer>();
		for (GameInfo gameInfo : gameMatrix) {
			gameListDesc.add(gameInfo.getGameCode());
		}
		return gameListDesc;
	}

	/**
	 * 通过用户特征向量json字符串获得推荐游戏排行列表
	 * 
	 * @param UCVjson
	 *            用户特征向量json字符串
	 * @return 倒序排序过的游戏编号列表
	 */
	public List<Integer> getGameList(String UCVjson)
			throws VectorCanNotMultiplyException {
		Vector<Float> UCV = VectorUtil.parseJsonToVector(UCVjson);
		return getGameList(UCV);
	}
}
