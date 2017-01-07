package edu.buu.childhood.pub.dao;

import java.util.List;

import edu.buu.childhood.pub.pojo.ADInfo;

public interface PubDao {
	public void insert(ADInfo adInfo);

	public List<ADInfo> queryAll();

	public List<ADInfo> queryAllByEnabled(char enabled);

	public List<ADInfo> queryTop5ByPriority();

	public List<ADInfo> queryByPriority(int priority);

	public boolean update(ADInfo adInfo);
}
