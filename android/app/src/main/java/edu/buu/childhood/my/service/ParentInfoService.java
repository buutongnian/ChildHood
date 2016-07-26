package edu.buu.childhood.my.service;

import edu.buu.childhood.common.CallBackPage;
import edu.buu.childhood.my.pojo.ParentInfItem;
import edu.buu.childhood.my.pojo.myContentItem;

/**
 * Created by lcc on 2016/7/15.
 */
public interface ParentInfoService {
    public CallBackPage<ParentInfItem> getParentInfoHeadInf(String result);
    public String getmyContentContentInf();
}