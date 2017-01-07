package edu.buu.childhood.version.pojo;

import java.util.Date;

import edu.buu.childhood.common.C;

public class VersionBean {
	private int versionId;
	private String version;
	private Date modifyTime;
	private String packageUrl;
	private String note;
	private String system = C.def.ANDROID;
	private char enable = C.def.ENABLED;

	public int getVersionId() {
		return versionId;
	}

	public void setVersionId(int versionId) {
		this.versionId = versionId;
	}

	public String getVersion() {
		return version;
	}

	public void setVersion(String version) {
		this.version = version;
	}

	public Date getModifyTime() {
		return modifyTime;
	}

	public void setModifyTime(Date modifyTime) {
		this.modifyTime = modifyTime;
	}

	public String getPackageUrl() {
		return packageUrl;
	}

	public void setPackageUrl(String packageUrl) {
		this.packageUrl = packageUrl;
	}

	public String getNote() {
		return note;
	}

	public void setNote(String note) {
		this.note = note;
	}

	public String getSystem() {
		return system;
	}

	public void setSystem(String system) {
		this.system = system;
	}

	public char getEnable() {
		return enable;
	}

	public void setEnable(char enable) {
		this.enable = enable;
	}

	@Override
	public String toString() {
		return "VersionBean [versionId=" + versionId + ", version=" + version
				+ ", modifyTime=" + modifyTime + ", packageUrl=" + packageUrl
				+ ", note=" + note + ", system=" + system + ", enable="
				+ enable + "]";
	}
	
	
	
	

}
