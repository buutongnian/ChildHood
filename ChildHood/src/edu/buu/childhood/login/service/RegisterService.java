package edu.buu.childhood.login.service;

import java.util.Date;

public interface RegisterService {
	
	public String register(String userName,String userPwd
			,double regLatitude,double regLongitude,int liveCommunity
			,String userHeadImage,String userNickname
			,int belongingProvince,int belongingCity,int belongingDistrict
			,String detailLiveCommunity,String detailAddr
			,String fatherName,String matherName
			,String fatherTel,String matherTel,String email
			,String childName,char childSex,Date childBirthday
			,int educationCode,int gradeCode,int schoolCode);
	
}


//private String userName;
//private Date regDate;
//private double regLatitude;
//private double regLongitude;
//private int belongingArea;
//private int liveCommunity;

//private String userName;
//private String userPwd;
//private String userHeadImage;
//private String userNickname;
//private double lastLatitude;
//private double lastLongitude;
//private int belongingProvince;
//private int belongingCity;
//private int belongingDistrict;
//private String liveCommunity;
//private String detailAddr;
//private String loginStatus=C.status.offline;

//private String fatherName;
//private String matherName;
//private String fatherTel;
//private String matherTel;
//private String email;

//private String userName;
//private String childName;
//private static char childSex=Character.MIN_VALUE;
//private Date childBirthday;
//private int educationCode=C.def.DEFAULT;
//private int gradeCode=C.def.DEFAULT;
//private int schoolCode=C.def.DEFAULT;