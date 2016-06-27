package edu.buu.childhood.login.service;

import java.util.Date;
import org.jboss.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import edu.buu.childhood.common.C;
import edu.buu.childhood.login.dao.RegisterDao;
import edu.buu.childhood.my.pojo.ChildInf;
import edu.buu.childhood.my.pojo.ParentInf;
import edu.buu.childhood.my.pojo.UserInf;
import edu.buu.childhood.my.pojo.UserRegInf;

public class RegisterServiceImpl implements RegisterService {
	
	private Logger logger=Logger.getLogger(this.getClass());
	
	@Autowired
	private RegisterDao registerDao;
	
	@Override
	@Transactional
	public String register(String userName, String userPwd, double regLatitude,
			double regLongitude, int liveCommunity, String userHeadImage,
			String userNickname, int belongingProvince, int belongingCity,
			int belongingDistrict, String detailLiveCommunity,
			String detailAddr, String fatherName, String matherName,
			String fatherTel, String matherTel, String email, String childName,
			char childSex, Date childBirthday, int educationCode,
			int gradeCode, int schoolCode) {
		if(registerDao.queryByUserName(userName)==null){
			try{
			UserRegInf userRegInf=new UserRegInf();
			userRegInf.setUserName(userName);
			userRegInf.setRegLatitude(regLatitude);
			userRegInf.setRegLongitude(regLongitude);
			userRegInf.setLiveCommunity(liveCommunity);
		
			ParentInf parentInf=new ParentInf();
			parentInf.setFatherName(fatherName);
			parentInf.setMatherName(matherName);
			parentInf.setFatherTel(fatherTel);
			parentInf.setMatherTel(matherTel);
			parentInf.setEmail(email);
		
			UserInf userInf=new UserInf();
			userInf.setUserName(userName);
			userInf.setUserPwd(userPwd);
			userInf.setUserNickname(userNickname);
			userInf.setUserHeadImage(userHeadImage);
			userInf.setBelongingProvince(belongingProvince);
			userInf.setBelongingCity(belongingCity);
			userInf.setBelongingDistrict(belongingDistrict);
			userInf.setLiveCommunity(detailLiveCommunity);
			userInf.setDetailAddr(detailAddr);
			userInf.setLoginStatus(C.status.offline);
			userInf.setParentInf(parentInf);
		
			ChildInf childInf=new ChildInf();
			childInf.setUserName(userName);
			childInf.setChildName(childName);
			childInf.setChildSex(childSex);
			childInf.setChildBirthday(childBirthday);
			childInf.setEducationCode(educationCode);
			childInf.setSchoolCode(schoolCode);
			childInf.setGradeCode(gradeCode);
		
			registerDao.insert(userRegInf, parentInf, userInf, childInf);
			logger.info("新用户：["+userName+"]注册成功");
			return C.reg.REG_SUCCESS;
			}catch(Exception e){
				logger.error(e.getMessage());
				return C.reg.REG_ERROR;
			}
		}else{
			return C.reg.USER_EXISTS;
		}
	}

}
