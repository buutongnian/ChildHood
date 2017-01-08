package edu.buu.childhood.my.service;

import edu.buu.childhood.common.CallBackPage;
import edu.buu.childhood.my.pojo.BackItem;

/**
 * Created by lcc on 2016/7/15.
 */
public interface LiveCommunityService {
    public CallBackPage<BackItem> getLiveCommunityChangeHeadInf(String result);
    public String getLiveCommunityChangeContentInf();
}