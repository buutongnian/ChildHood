package edu.buu.childhood.common;

import java.util.List;

import edu.buu.childhood.achievement.pojo.UserHistoryGameList;
import edu.buu.childhood.achievement.pojo.UserKPI;
import edu.buu.childhood.achievement.pojo.UserMedalList;

public class UserPersonalPage {
	private List<UserHistoryGameList> userGameList;
	private List<UserMedalList> medalList;
	private UserKPI userKPI;

	public UserPersonalPage(List<UserHistoryGameList> userGameList,
			List<UserMedalList> medalList, UserKPI userKPI) {
		super();
		this.userGameList = userGameList;
		this.medalList = medalList;
		this.userKPI = userKPI;
	}

	public List<UserHistoryGameList> getUserGameList() {
		return userGameList;
	}

	public void setUserGameList(List<UserHistoryGameList> userGameList) {
		this.userGameList = userGameList;
	}

	public List<UserMedalList> getMedalList() {
		return medalList;
	}

	public void setMedalList(List<UserMedalList> medalList) {
		this.medalList = medalList;
	}

	public UserKPI getUserKPI() {
		return userKPI;
	}

	public void setUserKPI(UserKPI userKPI) {
		this.userKPI = userKPI;
	}

}
