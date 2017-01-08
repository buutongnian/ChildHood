package edu.buu.childhood.my.service;

import edu.buu.childhood.common.CallBackPage;
import edu.buu.childhood.my.pojo.BackItem;
import edu.buu.childhood.my.pojo.ParentInfItem;

/**
 * Created by lcc on 2016/7/15.
 */
public interface ChangeParentInfoService {
    public CallBackPage<ParentInfItem> getmyContentHeadInf(String result);
    public String getmyContentContentInf();
}