package edu.buu.childhood.feedback.dao;

import edu.buu.childhood.common.Page;
import edu.buu.childhood.feedback.pojo.Feedback;

public interface FeedbackDao {
	public Page<Feedback> queryAll(int pageNum, int pageSize);

	public Page<Feedback> queryByModule(String moduleName, int pageNum,
			int pageSize);

	public Feedback queryUniqueById(int feedbackId);

	public boolean insert(Feedback feedback);

	public boolean update(Feedback feedback);

}
