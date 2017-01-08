package edu.buu.childhood.my.pojo;

/*
 * 2016/9/8
 * @author Joe
 * 存储队伍中成员基本信息
 * 
 * 2016/10/04
 * @modify 增加是否可点赞字段 couldLike
 */
public class MemberInfo {
	private String userName;
	private String userHeadImage;
	private String usernNickname;
	private boolean couldLike;

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getUserHeadImage() {
		return userHeadImage;
	}

	public void setUserHeadImage(String userHeadImage) {
		this.userHeadImage = userHeadImage;
	}

	public String getUsernNickname() {
		return usernNickname;
	}

	public void setUsernNickname(String usernNickname) {
		this.usernNickname = usernNickname;
	}

	public boolean isCouldLike() {
		return couldLike;
	}

	public void setCouldLike(boolean couldLike) {
		this.couldLike = couldLike;
	}

}
