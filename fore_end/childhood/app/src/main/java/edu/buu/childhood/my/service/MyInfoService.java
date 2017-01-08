package edu.buu.childhood.my.service;

import edu.buu.childhood.common.CallBackPage;
import edu.buu.childhood.my.pojo.User;

/**
 * Created by Administrator on 2016/9/13.
 */
public interface MyInfoService {
    public CallBackPage<User> getMyHeadInfo(String result);
    public CallBackPage<String> updatePhoneNumBer(String result);
}
