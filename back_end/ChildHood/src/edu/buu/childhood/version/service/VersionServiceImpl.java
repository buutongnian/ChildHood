package edu.buu.childhood.version.service;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import edu.buu.childhood.common.C;
import edu.buu.childhood.version.dao.VersionDao;
import edu.buu.childhood.version.pojo.VersionBean;

public class VersionServiceImpl implements VersionService {

	@Autowired
	private VersionDao versionDao;

	@Override
	public VersionBean getLeastVersion(String system) {
		return versionDao.getLeastVersion(system, C.def.ENABLED);
	}

	@Override
	public List<VersionBean> getAllVersions() {
		return versionDao.query();
	}

	@Override
	public VersionBean getInfoByVersionId(int versionId) {
		return versionDao.query(versionId);
	}

	@Override
	public void addVersionInfo(String version, String packageUrl,String note, String system) {
		VersionBean versionBean = new VersionBean();
		versionBean.setVersion(version);
		versionBean.setPackageUrl(packageUrl);
		versionBean.setNote(note);
		versionBean.setSystem(system);
		versionDao.insert(versionBean);
	}

	@Override
	public void updateVersionInfo(int versionId, String version,
			String packageUrl, String system) {
		VersionBean versionBean = new VersionBean();
		versionBean.setVersionId(versionId);
		versionBean.setVersion(version);
		versionBean.setPackageUrl(packageUrl);
		versionBean.setSystem(system);
		versionDao.update(versionBean);
	}

	@Override
	public void delete(int versionId) {
		versionDao.delete(versionId);
	}

}
