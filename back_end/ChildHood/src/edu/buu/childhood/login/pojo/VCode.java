package edu.buu.childhood.login.pojo;

import java.util.Date;

/**
 * 2016/9/13
 * 
 * @Author Joe
 * @note 映射T_SYS_VCODE表
 */
public class VCode {
	private int vcodeId;
	private String telnum;
	private Date generateTime;
	private String vcode;
	private String smsStatus;
	private String vcodeStatus;

	public VCode() {

	}

	public VCode(String telnum, String vcode, String smsStatus,
			String vcodeStatus) {
		this.telnum = telnum;
		this.vcode = vcode;
		this.smsStatus = smsStatus;
		this.vcodeStatus = vcodeStatus;
	}

	public int getVcodeId() {
		return vcodeId;
	}

	public void setVcodeId(int vcodeId) {
		this.vcodeId = vcodeId;
	}

	public String getTelnum() {
		return telnum;
	}

	public void setTelnum(String telnum) {
		this.telnum = telnum;
	}

	public Date getGenerateTime() {
		return generateTime;
	}

	public void setGenerateTime(Date generateTime) {
		this.generateTime = generateTime;
	}

	public String getVcode() {
		return vcode;
	}

	public void setVcode(String vcode) {
		this.vcode = vcode;
	}

	public String getSmsStatus() {
		return smsStatus;
	}

	public void setSmsStatus(String smsStatus) {
		this.smsStatus = smsStatus;
	}

	public String getVcodeStatus() {
		return vcodeStatus;
	}

	public void setVcodeStatus(String vcodeStatus) {
		this.vcodeStatus = vcodeStatus;
	}

}
