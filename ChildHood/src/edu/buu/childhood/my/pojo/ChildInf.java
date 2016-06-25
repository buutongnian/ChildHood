package edu.buu.childhood.my.pojo;

import java.util.Date;
import edu.buu.childhood.common.C;

/**
 * 2016/6/17
 * @Author Joe
 * @note 映射T_SYS_CHILD_Inf表
 */
public class ChildInf {
	
	private int childInf;
	private String userName;
	private String childName;
	private char childSex=Character.MIN_VALUE;
	private Date childBirthday;
	private int educationCode=C.def.DEFAULT;
	private int gradeCode=C.def.DEFAULT;
	private int schoolCode=C.def.DEFAULT;
	
	public int getChildInf() {
		return childInf;
	}
	public void setChildInf(int childInf) {
		this.childInf = childInf;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
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
}
