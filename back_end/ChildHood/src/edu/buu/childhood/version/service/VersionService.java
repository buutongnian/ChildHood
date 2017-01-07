package edu.buu.childhood.version.service;

import java.util.List;
import edu.buu.childhood.version.pojo.VersionBean;

public interface VersionService {
	public VersionBean getLeastVersion(String system);

	public List<VersionBean> getAllVersions();

	public VersionBean getInfoByVersionId(int versionId);

	public void addVersionInfo(String version,String packageUrl,String note,String system);

	public void updateVersionInfo(int versionId,String version,String packageUrl,String system);
	
	public void delete(int versionId);
}
