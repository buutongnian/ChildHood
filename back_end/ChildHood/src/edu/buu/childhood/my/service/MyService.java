package edu.buu.childhood.my.service;

import java.util.Date;
import java.util.List;
import edu.buu.childhood.my.pojo.ChildInf;
import edu.buu.childhood.my.pojo.User;

public interface MyService {
	public User getUserInf(String userName);

	public void updateUserInf(String userName, String userNickname,
			int belongingProvince, int belongingCity, int belongingDistrict,
			String liveCommunity, String detailAddress, String email);

	public List<ChildInf> getChildInf(String userName);

	public void updateChildInf(int childInfId, String childName, String sex,
			Date childBirthday, int educationCode, int schoolCode,
			String customSchool);

	public String updateHeadImage(String userName, String root, byte[] imageData);

	public void setChildInf(String userName, String childName, char childSex,
			Date childBirthday, int educationCode, int gradeCode,
			int schoolCode, String customSchool);

}
