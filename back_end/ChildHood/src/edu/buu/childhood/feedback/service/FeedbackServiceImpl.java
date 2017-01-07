package edu.buu.childhood.feedback.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import edu.buu.childhood.achvmt.service.EventSupport;
import edu.buu.childhood.achvmt.service.TriggerService;
import edu.buu.childhood.common.C;
import edu.buu.childhood.common.Page;
import edu.buu.childhood.feedback.dao.FeedbackDao;
import edu.buu.childhood.feedback.pojo.Feedback;

public class FeedbackServiceImpl implements FeedbackService, EventSupport {

	@Override
	public String moduleName() {
		return "feedback";
	}

	@Autowired
	private FeedbackDao feedbackDao;

	private TriggerService triggerService;

	public FeedbackServiceImpl() {
		super();
	}

	public FeedbackServiceImpl(TriggerService triggerService) {
		super();
		this.triggerService = triggerService;
		triggerService.registerModule(this);
	}

	@Override
	@Transactional
	public Page<Feedback> getAllFeedback(int pageNum) {
		return feedbackDao.queryAll(pageNum, C.def.WEB_PAGE_SIZE);
	}

	@Override
	@Transactional
	public Page<Feedback> getFeedbackByModule(String moduleName, int pageNum) {
		if ("all".equals(moduleName)) {
			return getAllFeedback(pageNum);
		} else {
			return feedbackDao.queryByModule(moduleName, pageNum,
					C.def.WEB_PAGE_SIZE);
		}
	}

	@Override
	@Transactional
	public Feedback getFeedbackById(int feedbackId) {
		return feedbackDao.queryUniqueById(feedbackId);
	}

	@Override
	@Transactional
	public boolean addFeedback(String userName, String feedbackContent,
			String feedbackModule) {
		Feedback feedback = new Feedback();
		feedback.setUserName(userName);
		feedback.setFeedbackContent(feedbackContent);
		feedback.setFeedbackModule(feedbackModule);
		triggerService.triggerEvent(this, userName);
		return feedbackDao.insert(feedback);
	}

	@Override
	@Transactional
	public boolean dealFeedback(int feedbackId, String dealUser, String result) {
		Feedback feedback = new Feedback();
		feedback.setFeedbackId(feedbackId);
		feedback.setResultUser(dealUser);
		feedback.setResult(result);
		feedback.setProcessible(C.def.DISABLED);
		feedback.setFeedbackModule(null);
		return feedbackDao.update(feedback);
	}

	@Override
	@Transactional
	public boolean modifyModule(int feedbackId, String feedbackModule) {
		Feedback feedback = new Feedback();
		feedback.setFeedbackId(feedbackId);
		feedback.setFeedbackModule(feedbackModule);
		return feedbackDao.update(feedback);
	}

}
