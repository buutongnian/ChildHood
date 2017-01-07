package edu.buu.childhood.feedback.service;

import edu.buu.childhood.common.Page;
import edu.buu.childhood.feedback.pojo.Feedback;

public interface FeedbackService {
	public Page<Feedback> getAllFeedback(int pageNum);

	public Page<Feedback> getFeedbackByModule(String moduleName, int pageNum);
	
	public Feedback getFeedbackById(int feedbackId);

	public boolean addFeedback(String userName, String feedbackContent,
			String feedbackModule);

	public boolean dealFeedback(int feedbackId, String dealUser, String result);

	public boolean modifyModule(int feedbackId, String feedbackModule);
}
