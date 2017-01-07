package edu.buu.childhood.pub.service;

import java.util.List;

import edu.buu.childhood.pub.pojo.ADInfo;

public interface PubService {
	public List<ADInfo> getADList(String userName);
}
