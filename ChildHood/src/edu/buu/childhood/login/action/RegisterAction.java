package edu.buu.childhood.login.action;

import java.util.Date;
import com.opensymphony.xwork2.ActionSupport;
import edu.buu.childhood.common.C;
import edu.buu.childhood.login.service.RegisterService;

public class RegisterAction extends ActionSupport{
	

	private RegisterService registerService;
	
	public void setRegisterService(RegisterService registerService) {
		this.registerService = registerService;
	}
	
	private String webOrApp;//请求方式（Web端或App端），内容由C.java常量文件定义
	
	private String userName;
	private String userPwd;
	private double regLatitude;
	private double regLongitude;
	private int liveCommunity;
	private String userHeadImage;
	private String userNickname;
	private int belongingProvince;
	private int belongingCity;
	private int belongingDistrict;
	private String detailLiveCommunity;
	private String detailAddr;
	private String fatherName;
	private String matherName;
	private String fatherTel;
	private String matherTel;
	private String email;
	private String childName;
	private char childSex;
	private Date childBirthday;
	private int educationCode;
	private int gradeCode;
	private int schoolCode;
	
	public String getWebOrApp() {
		return webOrApp;
	}

	public void setWebOrApp(String webOrApp) {
		this.webOrApp = webOrApp;
	}
		
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

	public double getRegLatitude() {
		return regLatitude;
	}

	public void setRegLatitude(double regLatitude) {
		this.regLatitude = regLatitude;
	}

	public double getRegLongitude() {
		return regLongitude;
	}

	public void setRegLongitude(double regLongitude) {
		this.regLongitude = regLongitude;
	}

	public int getLiveCommunity() {
		return liveCommunity;
	}

	public void setLiveCommunity(int liveCommunity) {
		this.liveCommunity = liveCommunity;
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

	public String getDetailLiveCommunity() {
		return detailLiveCommunity;
	}

	public void setDetailLiveCommunity(String detailLiveCommunity) {
		this.detailLiveCommunity = detailLiveCommunity;
	}

	public String getDetailAddr() {
		return detailAddr;
	}

	public void setDetailAddr(String detailAddr) {
		this.detailAddr = detailAddr;
	}

	public String getFatherName() {
		return fatherName;
	}

	public void setFatherName(String fatherName) {
		this.fatherName = fatherName;
	}

	public String getMatherName() {
		return matherName;
	}

	public void setMatherName(String matherName) {
		this.matherName = matherName;
	}

	public String getFatherTel() {
		return fatherTel;
	}

	public void setFatherTel(String fatherTel) {
		this.fatherTel = fatherTel;
	}

	public String getMatherTel() {
		return matherTel;
	}

	public void setMatherTel(String matherTel) {
		this.matherTel = matherTel;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getChildName() {
		return childName;
	}

	public void setChildName(String childName) {
		this.childName = childName;
	}

	public char getChildSex() {
		return childSex;
	}

	public void setChildSex(char childSex) {
		this.childSex = childSex;
	}

	public Date getChildBirthday() {
		return childBirthday;
	}

	public void setChildBirthday(Date childBirthday) {
		this.childBirthday = childBirthday;
	}

	public int getEducationCode() {
		return educationCode;
	}

	public void setEducationCode(int educationCode) {
		this.educationCode = educationCode;
	}

	public int getGradeCode() {
		return gradeCode;
	}

	public void setGradeCode(int gradeCode) {
		this.gradeCode = gradeCode;
	}

	public int getSchoolCode() {
		return schoolCode;
	}

	public void setSchoolCode(int schoolCode) {
		this.schoolCode = schoolCode;
	}
	
	@Override
	public String execute() throws Exception {
		String result=registerService.register(userName, userPwd,
				regLatitude, regLongitude,
				liveCommunity, userHeadImage, userNickname,
				belongingProvince, belongingCity, belongingDistrict,
				detailLiveCommunity, detailAddr,
				fatherName, matherName, fatherTel, matherTel,email,
				childName, childSex, childBirthday,
				educationCode, gradeCode, schoolCode);
		if(C.reg.REG_SUCCESS.equals(result)){
			return SUCCESS;
		}else if(C.reg.USER_EXISTS.equals(result)){
			return "USER_EXISTS";
		}else{
			return ERROR;
		}
	}
}
