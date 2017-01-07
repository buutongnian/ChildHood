package edu.buu.childhood.version.dao;

import java.util.List;

import edu.buu.childhood.version.pojo.VersionBean;

public interface VersionDao {
	public VersionBean getLeastVersion(String system,char enable);
	
	public List<VersionBean> query();
	public VersionBean query(int versionId);
	public void insert(VersionBean versionBean);
	public void update(VersionBean versionBean);
	public void delete(int versionId);
}
