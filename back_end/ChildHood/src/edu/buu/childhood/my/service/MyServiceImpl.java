package edu.buu.childhood.my.service;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import edu.buu.childhood.common.C;
import edu.buu.childhood.my.dao.MyDao;
import edu.buu.childhood.my.pojo.ChildInf;
import edu.buu.childhood.my.pojo.User;
import edu.buu.childhood.rank.dao.RankDao;

public class MyServiceImpl implements MyService {

	private Logger logger = Logger.getLogger(this.getClass());

	@Autowired
	private MyDao myDao;
	@Autowired
	private RankDao rankDao;

	@Override
	@Transactional
	public User getUserInf(String userName) {
		User user = myDao.getUserInf(userName);
		user.setLikeCount(rankDao.uniqueUserRank(userName).getLikeCount());
		return user;
	}

	@Override
	@Transactional
	public void updateUserInf(String userName, String userNickname,
			int belongingProvince, int belongingCity, int belongingDistrict,
			String community, String detailAddress, String email) {
		StringBuffer set = new StringBuffer("");
		if (userNickname != null) {
			set.append(",userNickname='" + userNickname + "'");
		}
		if (belongingProvince != 0) {
			set.append(",belongingProvince='" + belongingProvince + "'");
		}
		if (belongingCity != 0) {
			set.append(",belongingCity='" + belongingCity + "'");
		}
		if (belongingDistrict != 0) {
			set.append(",belongingDistrict='" + belongingDistrict + "'");
		}
		if (community != null) {
			set.append(",community='" + community + "'");
		}
		if (detailAddress != null) {
			set.append(",detailAddr='" + detailAddress + "'");
		}
		if (email != null) {
			set.append(",email='" + email + "'");
		}
		myDao.updateUserInfo(userName, set.toString());
	}

	@Override
	@Transactional
	public List<ChildInf> getChildInf(String userName) {
		return myDao.getChildInf(userName);
	}

	@Override
	@Transactional
	public void updateChildInf(int childInfId, String childName, String sex,
			Date childBirthday, int educationCode, int schoolCode,
			String customSchool) {
		StringBuffer set = new StringBuffer("");
		if (childName != null) {
			set.append(",childName='" + childName + "'");
		}
		if (sex != null) {
			set.append(",childSex='" + sex + "'");
		}
		if (childBirthday != null) {
			DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
			set.append(",childBirthday='" + format.format(childBirthday) + "'");
		}
		if (educationCode != 0) {
			set.append(",educationCode='" + educationCode + "'");
		}
		if (schoolCode != 0) {
			set.append(",schoolCode='" + schoolCode + "'");
		}
		if (customSchool != null) {
			set.append(",customSchool='" + customSchool + "'");
		}
		myDao.updateChildInfo(childInfId, set.toString());
	}

	@Override
	public String updateHeadImage(String userName, String root, byte[] imageData) {
		try {
			InputStream inputStream = new ByteArrayInputStream(imageData);

			String fileName = userName + "_" + new Date().getTime() + ".png";
			OutputStream outputStream = new FileOutputStream(new File(root,
					fileName));
			byte[] buffer = new byte[1024];
			int count, i;
			while (-1 != (count = inputStream.read(buffer, 0, buffer.length))) {
				for (i = 0; i < count; i++) {
					outputStream.write(buffer[i]);
				}
			}
			outputStream.close();
			inputStream.close();

			String imageUrl = C.def.DOMAIN + "avatar/" + fileName;

			myDao.updateHeadImage(userName, imageUrl);
			return imageUrl;
		} catch (Exception e) {
			logger.error(e, e);
			return "";
		}
	}

	@Override
	public void setChildInf(String userName, String childName, char childSex,
			Date childBirthday, int educationCode, int gradeCode,
			int schoolCode, String customSchool) {
		ChildInf childInf = new ChildInf();
		childInf.setUserName(userName);
		childInf.setChildName(childName);
		childInf.setChildSex(childSex);
		childInf.setChildBirthday(childBirthday);
		childInf.setEducationCode(educationCode);
		childInf.setGradeCode(gradeCode);
		childInf.setSchoolCode(schoolCode);
		childInf.setCustomSchool(customSchool);
		myDao.insert(childInf);
	}

}
