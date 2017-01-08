package edu.buu.childhood.my.service;

import edu.buu.childhood.common.CallBackPage;
import edu.buu.childhood.my.pojo.BackItem;

/**
 * Created by lcc on 2016/7/15.
 */
public interface Edit_nameService {
    public CallBackPage<BackItem> getmyContentHeadInf(String result);
    public String getmyContentContentInf();
}