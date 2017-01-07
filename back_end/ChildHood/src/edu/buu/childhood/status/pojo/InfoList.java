package edu.buu.childhood.status.pojo;

import java.util.List;

/**
 * 2016/7/1 用于返回包含当前可邀请人数、可加入游戏数量信息的可邀请人、可加入游戏列表
 * 
 * @author joe
 * @param count
 *            可邀请人数、可加入游戏数量 2016/9/8 可用于记录游戏房间中
 * @param <T>
 *            List中放入对象类型
 */
public class InfoList<T> {

	public int count;
	public List<T> dataList;

	/*
	 * 2016/9/8 增加单变量构造方法，使用list的长度作为count
	 */
	public InfoList(List<T> dataList) {
		if (dataList != null) {
			this.count = dataList.size();
			this.dataList = dataList;
		}
	}

	public InfoList(int count, List<T> dataList) {
		if (dataList != null) {
			this.count = count;
			this.dataList = dataList;
		}
	}

	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
	}

	public List<T> getDataList() {
		return dataList;
	}

	public void setDataList(List<T> dataList) {
		this.dataList = dataList;
	}
}
