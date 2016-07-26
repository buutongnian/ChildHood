package edu.buu.childhood.baidumap.service;

import edu.buu.childhood.baidumap.poj.MarkItem;
import edu.buu.childhood.common.CallBackPage;

/**
 * Created by lcc on 2016/7/15.
 */
public interface UnPersonInfoService {
    public CallBackPage<MarkItem> getUnPersonInfoHeadInf(String result);
    public String getmyUnPersonInfoContentInf();
}