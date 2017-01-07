package edu.buu.childhood.find.dao;

import java.util.List;

import edu.buu.childhood.common.Page;
import edu.buu.childhood.find.pojo.FindReg;
import edu.buu.childhood.find.pojo.FindResult;

public interface FindDao {
	public void computePartener(String userName, int disRange, int ageRange);

	public List<String> query();

	public FindReg query(String userName);

	public void insert(FindReg findReg);

	public void update(FindReg findReg);

	public void update(String userName, char enable);

	public Page<FindResult> queryResult(String userName, int pageNum,
			int pageSize);
}
