package edu.buu.childhood.my.service;

import edu.buu.childhood.common.CallBackPage;
import edu.buu.childhood.my.pojo.VersionInfo;

/**
 * Created by lcc on 2016/7/15.
 */
public interface VersionService {
    public CallBackPage<VersionInfo> getVersionHeadInf(String result);
    public String getVersionContentInf();
}