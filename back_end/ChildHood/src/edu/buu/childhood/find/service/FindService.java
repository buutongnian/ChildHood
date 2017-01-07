package edu.buu.childhood.find.service;

import edu.buu.childhood.common.Page;
import edu.buu.childhood.find.pojo.FindReg;
import edu.buu.childhood.find.pojo.FindResult;

public interface FindService {
	public FindReg getFindReg(String userName);

	public void insertFindReg(String userName, int age, char sex,
			double youngLatitude, double youngLongitude, String youngNickname,
			String youngRoad, String message);

	public void updateFindReg(String userName, int age, char sex,
			double youngLatitude, double youngLongitude, String youngNickname,
			String youngRoad, String message);

	public void deleteFindReg(String userName);

	public Page<FindResult> getFindResult(String userName, int pageNum);
}
