package edu.buu.childhood.my.service;

import edu.buu.childhood.common.CallBackPage;
import edu.buu.childhood.my.pojo.myContentItem;

/**
 * Created by lcc on 2016/7/15.
 */
public interface MyContentService {
    public CallBackPage<myContentItem> getmyContentHeadInf(String result);
    public String getmyContentContentInf();
}