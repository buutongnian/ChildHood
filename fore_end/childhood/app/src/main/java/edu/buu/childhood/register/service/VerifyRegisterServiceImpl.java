package edu.buu.childhood.register.service;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.List;

import edu.buu.childhood.common.C;
import edu.buu.childhood.common.CallBackPage;
import edu.buu.childhood.common.Message;
import edu.buu.childhood.my.pojo.BackItem;

/**
 * Created by lcc on 2016/7/15.
 */
public class VerifyRegisterServiceImpl implements RegisterService {
    Gson json = new Gson();

    public CallBackPage<BackItem> getmyContentHeadInf(String result) {
        Message message = json.fromJson(result, new TypeToken<Message>() {
        }.getType());
        //检查手机号是否已注册
        if (C.reg.USER_EXISTS.equals(message.getMessageCode())) {

            CallBackPage<BackItem> callBackPage = new CallBackPage<BackItem>();
            List<BackItem> list = new ArrayList<BackItem>();
            BackItem info = new BackItem();
            info.setIsright(C.reg.USER_EXISTS);
            list.add(info);
            callBackPage.setDatalist(list);
            return callBackPage;
        }
        if (C.reg.USER_NOT_EXISTS.equals(message.getMessageCode())) {

            CallBackPage<BackItem> callBackPage = new CallBackPage<BackItem>();
            List<BackItem> list = new ArrayList<BackItem>();
            BackItem info = new BackItem();
            info.setIsright(C.reg.USER_NOT_EXISTS);
            list.add(info);
            callBackPage.setDatalist(list);
            return callBackPage;
        }
        //使用手机号注册
        if (C.reg.REG_SUCCESS.equals(message.getMessageCode())) {
            CallBackPage<BackItem> callBackPage = new CallBackPage<BackItem>();
            List<BackItem> list = new ArrayList<BackItem>();
            BackItem info = new BackItem();
            info.setIsright(C.reg.REG_SUCCESS);
            list.add(info);
            callBackPage.setDatalist(list);
            return callBackPage;
        }
        if (C.reg.REG_UNSUCCESS.equals(message.getMessageCode())) {

            CallBackPage<BackItem> callBackPage = new CallBackPage<BackItem>();
            List<BackItem> list = new ArrayList<BackItem>();
            BackItem info = new BackItem();
            info.setIsright(C.reg.REG_UNSUCCESS);
            list.add(info);
            callBackPage.setDatalist(list);
            return callBackPage;
        }
        //SendSMS - 发送短信成功
        if (C.vcode.SMS_SEND_SUCCESS.equals(message.getMessageCode())) {
            CallBackPage<BackItem> callBackPage = new CallBackPage<BackItem>();
            List<BackItem> list = new ArrayList<BackItem>();
            BackItem info = new BackItem();
            info.setIsright(C.vcode.SMS_SEND_SUCCESS);
            list.add(info);
            callBackPage.setDatalist(list);
            return callBackPage;
        }
        if (C.vcode.SMS_SEND_ERROR.equals(message.getMessageCode())) {

            CallBackPage<BackItem> callBackPage = new CallBackPage<BackItem>();
            List<BackItem> list = new ArrayList<BackItem>();
            BackItem info = new BackItem();
            info.setIsright(C.vcode.SMS_SEND_ERROR);
            list.add(info);
            callBackPage.setDatalist(list);
            return callBackPage;
        }
        //注册成功
        if(C.reg.REG_SUCCESS.equals(message.getMessageCode())){
            CallBackPage<BackItem> callBackPage = new CallBackPage<BackItem>();
            List<BackItem> list = new ArrayList<BackItem>();
            BackItem info = new BackItem();
            info.setIsright(C.reg.REG_SUCCESS);
            list.add(info);
            callBackPage.setDatalist(list);
            return callBackPage;
        }
        //验证码错误
        if(C.vcode.VERIFIED_FAILURE.equals(message.getMessageCode())){
            CallBackPage<BackItem> callBackPage = new CallBackPage<BackItem>();
            List<BackItem> list = new ArrayList<BackItem>();
            BackItem info = new BackItem();
            info.setIsright(C.vcode.VERIFIED_FAILURE);
            list.add(info);
            callBackPage.setDatalist(list);
            return callBackPage;
        }
        //checkVcode - 验证码校验接口
       /* if (C.vcode.VERIFIED_SUCCESS.equals(message.getMessageCode())) {

            CallBackPage<BackItem> callBackPage = new CallBackPage<BackItem>();
            List<BackItem> list = new ArrayList<BackItem>();
            BackItem info = new BackItem();
            info.setIsright(C.vcode.VERIFIED_SUCCESS);
            list.add(info);
            callBackPage.setDatalist(list);
            return callBackPage;
        }*/
        /*if (C.vcode.VERIFIED_FAILURE.equals(message.getMessageCode())) {

            CallBackPage<BackItem> callBackPage = new CallBackPage<BackItem>();
            List<BackItem> list = new ArrayList<BackItem>();
            BackItem info = new BackItem();
            info.setIsright(C.vcode.VERIFIED_FAILURE);
            list.add(info);
            callBackPage.setDatalist(list);
            return callBackPage;
        }*/
        //完善个人信息
        if (C.reg.PERFECT_INFO_SUCCESS.equals(message.getMessageCode())) {
            CallBackPage<BackItem> callBackPage = new CallBackPage<BackItem>();
            List<BackItem> list = new ArrayList<BackItem>();
            BackItem info = new BackItem();
            info.setIsright(C.reg.PERFECT_INFO_SUCCESS);
            list.add(info);
            callBackPage.setDatalist(list);
            return callBackPage;
        }
        if (C.reg.PERFECT_INFO_UNSUCCESS.equals(message.getMessageCode())) {
            CallBackPage<BackItem> callBackPage = new CallBackPage<BackItem>();
            List<BackItem> list = new ArrayList<BackItem>();
            BackItem info = new BackItem();
            info.setIsright(C.reg.PERFECT_INFO_UNSUCCESS);
            list.add(info);
            callBackPage.setDatalist(list);
            return callBackPage;
        }

        return null;
    }

    public String getmyContentContentInf() {
        return null;
    }

}