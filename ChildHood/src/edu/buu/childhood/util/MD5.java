package edu.buu.childhood.util;

import java.security.MessageDigest;
/**
 * 2016/6/19
 * @author joe
 * MD5加密类
 */
public abstract class MD5 {
	final static char[] hexDigits={'0','1','2','3','4','5','6','7','8','9','a','b','c','d','e','f'};
	/**
	 * MD5加密方法
	 * @param password 待加密字符串
	 * @return 加密后的小写32位MD5字符串
	 */
	public final static String enc32Bit(String password){
		try{
			byte[] btInput=password.getBytes();
			MessageDigest messageDigest=MessageDigest.getInstance("MD5");
			messageDigest.update(btInput);
			byte[] message=messageDigest.digest();
			int length=message.length;
			char[] str=new char[length*2];
			int anc=0;
			for(int i=0;i<length;i++){
				byte byteTemp=message[i];
				str[anc++]=hexDigits[byteTemp >>> 4 & 0xf];
				str[anc++]=hexDigits[byteTemp & 0xf];
			}
			return new String(str);
		}catch(Exception e){
			e.printStackTrace();
			return null;
		}
	}
	
	public static void main(String[] args) {
		System.out.println(enc32Bit(String.valueOf(123456)));
	}
}
