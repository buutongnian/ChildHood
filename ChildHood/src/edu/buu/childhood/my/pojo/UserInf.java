package edu.buu.childhood.my.pojo;

import java.util.List;

import edu.buu.childhood.common.C;

/*
 * 2016/6/17
 * @Author Joe
 * 映射T_SYS_USER_Inf表
 */
public class UserInf {
	
	private String userName;
	private String userPwd;
	private String userHeadImage;
	private String userNickname;
	private double lastLatitude;
	private double lastLongitude;
	private int belongingProvince;
	private int belongingCity;
	private int belongingDistrict;
	private String liveCommunity;
	private String detailAddr;
	private int parentInf;
	private String loginStatus=C.def.offline;
	
	private UserRegInf userRegInf;
	private ParentInf partenInf;
	private List<ChildInf> childInfList;
	
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getUserPwd() {
		return userPwd;
	}
	public void setUserPwd(String userPwd) {
		this.userPwd = userPwd;
	}
	public String getUserHeadImage() {
		return userHeadImage;
	}
	public void setUserHeadImage(String userHeadImage) {
		this.userHeadImage = userHeadImage;
	}
	public String getUserNickname() {
		return userNickname;
	}
	public void setUserNickname(String userNickname) {
		this.userNickname = userNickname;
	}
	public double getLastLatitude() {
		return lastLatitude;
	}
	public void setLastLatitude(double lastLatitude) {
		this.lastLatitude = lastLatitude;
	}
	public double getLastLongitude() {
		return lastLongitude;
	}
	public void setLastLongitude(double lastLongitude) {
		this.lastLongitude = lastLongitude;
	}
	public int getBelongingProvince() {
		return belongingProvince;
	}
	public void setBelongingProvince(int belongingProvince) {
		this.belongingProvince = belongingProvince;
	}
	public int getBelongingCity() {
		return belongingCity;
	}
	public void setBelongingCity(int belongingCity) {
		this.belongingCity = belongingCity;
	}
	public int getBelongingDistrict() {
		return belongingDistrict;
	}
	public void setBelongingDistrict(int belongingDistrict) {
		this.belongingDistrict = belongingDistrict;
	}
	public String getLiveCommunity() {
		return liveCommunity;
	}
	public void setLiveCommunity(String liveCommunity) {
		this.liveCommunity = liveCommunity;
	}
	public String getDetailAddr() {
		return detailAddr;
	}
	public void setDetailAddr(String detailAddr) {
		this.detailAddr = detailAddr;
	}
	public int getParentInf() {
		return parentInf;
	}
	public void setParentInf(int parentInf) {
		this.parentInf = parentInf;
	}
	public String getLoginStatus() {
		return loginStatus;
	}
	public void setLoginStatus(String loginStatus) {
		this.loginStatus = loginStatus;
	}
	public UserRegInf getUserRegInf() {
		return userRegInf;
	}
	public void setUserRegInf(UserRegInf userRegInf) {
		this.userRegInf = userRegInf;
	}
	public ParentInf getPartenInf() {
		return partenInf;
	}
	public void setPartenInf(ParentInf partenInf) {
		this.partenInf = partenInf;
	}
	public List<ChildInf> getChildInfList() {
		return childInfList;
	}
	public void setChildInfList(List<ChildInf> childInfList) {
		this.childInfList = childInfList;
	}
}
