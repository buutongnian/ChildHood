package edu.buu.childhood.login.service;

import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.List;

import edu.buu.childhood.baidumap.pojo.GameStatus;
import edu.buu.childhood.common.C;
import edu.buu.childhood.common.CallBackPage;
import edu.buu.childhood.common.Message;
import edu.buu.childhood.my.pojo.BackItem;
import edu.buu.childhood.my.pojo.User;
import edu.buu.childhood.my.pojo.UserInfo;

/**
 * Created by lcc on 2016/7/15.
 */
public class LoginServiceImpl implements LoginService {
    Gson json = new Gson();

    public CallBackPage<BackItem> getLoginHeadInf(String result) {
        Message message = json.fromJson(result, new TypeToken<Message>() {
        }.getType());
        if (message != null) {
            if (C.LoginStatus.LOGIN_SUCCESS.equals(message.getMessageCode())) {
                Message<User> messageContent = json.fromJson(result, new TypeToken<Message<User>>() {
                }.getType());
                CallBackPage<BackItem> callBackPage = new CallBackPage<BackItem>();
                List<BackItem> list = new ArrayList<BackItem>();
                BackItem info = new BackItem();
                info.setIsright(C.LoginStatus.LOGIN_SUCCESS);
                info.setInGame(false);
                info.setUser(messageContent.getMessageContent());
                list.add(info);
                callBackPage.setDatalist(list);
                return callBackPage;
            }
            if (C.LoginStatus.USER_NOT_EXIST.equals(message.getMessageCode())) {
                CallBackPage<BackItem> callBackPage = new CallBackPage<BackItem>();
                List<BackItem> list = new ArrayList<BackItem>();
                BackItem info = new BackItem();
                info.setIsright(C.LoginStatus.USER_NOT_EXIST);
                info.setInGame(false);
                list.add(info);
                callBackPage.setDatalist(list);
                return callBackPage;
            }
            if (C.LoginStatus.PASSWORD_INCORRECT.equals(message.getMessageCode())) {
                CallBackPage<BackItem> callBackPage = new CallBackPage<BackItem>();
                List<BackItem> list = new ArrayList<BackItem>();
                BackItem info = new BackItem();
                info.setIsright("INCORRECT");
                info.setInGame(false);
                list.add(info);
                callBackPage.setDatalist(list);
                return callBackPage;
            }
            if (C.LoginStatus.LOGIN_UNSUCCESS.equals(message.getMessageCode())) {
                CallBackPage<BackItem> callBackPage = new CallBackPage<BackItem>();
                List<BackItem> list = new ArrayList<BackItem>();
                BackItem info = new BackItem();
                info.setIsright("UNSUCCESS");
                info.setInGame(false);
                list.add(info);
                callBackPage.setDatalist(list);
                return callBackPage;
            }
            if (C.def.USER_IN_GAME.equals(message.getMessageCode())) {
                Message<GameStatus> messageGame = json.fromJson(result, new TypeToken<Message<GameStatus>>() {
                }.getType());
                CallBackPage<BackItem> callBackPage = new CallBackPage<BackItem>();
                List<BackItem> list = new ArrayList<BackItem>();
                BackItem info = new BackItem();
                info.setInGame(true);
                info.setGameId(((GameStatus) messageGame.getMessageContent()).getGameId());
                //info.setGameFounder(((GameStatus)messageGame.getMessageContent()).);
                info.setIsright(C.def.USER_IN_GAME);
                list.add(info);
                callBackPage.setDatalist(list);
                return callBackPage;
            }
            if (C.def.USER_NOT_IN_GAME.equals(message.getMessageCode())) {
                CallBackPage<BackItem> callBackPage = new CallBackPage<BackItem>();
                List<BackItem> list = new ArrayList<BackItem>();
                BackItem info = new BackItem();
                info.setInGame(true);
                info.setIsright("UNSUCCESS");
                list.add(info);
                callBackPage.setDatalist(list);
                return callBackPage;
            }
            //修改密码
            if (C.vcode.RESET_PWD_SUCCESS.equals(message.getMessageCode())) {
                CallBackPage<BackItem> callBackPage = new CallBackPage<BackItem>();
                List<BackItem> list = new ArrayList<BackItem>();
                BackItem info = new BackItem();
                info.setIsright("alterSuccess");
                info.setInGame(false);
                list.add(info);
                callBackPage.setDatalist(list);
                return callBackPage;
            }
            if (C.vcode.RESET_PWD_FAILURE.equals(message.getMessageCode())) {

                CallBackPage<BackItem> callBackPage = new CallBackPage<BackItem>();
                List<BackItem> list = new ArrayList<BackItem>();
                BackItem info = new BackItem();
                info.setInGame(false);
                info.setIsright("alterError");
                list.add(info);
                callBackPage.setDatalist(list);
                return callBackPage;
            }
            //验证码登录
            if (C.LoginStatus.LOGIN_SUCCESS.equals(message.getMessageCode())) {
                CallBackPage<BackItem> callBackPage = new CallBackPage<BackItem>();
                List<BackItem> list = new ArrayList<BackItem>();
                BackItem info = new BackItem();
                info.setIsright(C.LoginStatus.LOGIN_SUCCESS);
                info.setInGame(false);
                list.add(info);
                callBackPage.setDatalist(list);
                return callBackPage;
            }
            if (C.vcode.VERIFIED_FAILURE.equals(message.getMessageCode())) {
                CallBackPage<BackItem> callBackPage = new CallBackPage<BackItem>();
                List<BackItem> list = new ArrayList<BackItem>();
                BackItem info = new BackItem();
                info.setIsright(C.vcode.VERIFIED_FAILURE);
                info.setInGame(false);
                list.add(info);
                callBackPage.setDatalist(list);
                return callBackPage;
            }
            if (C.reg.USER_EXISTS.equals(message.getMessageCode())) {
                CallBackPage<BackItem> callBackPage = new CallBackPage<BackItem>();
                List<BackItem> list = new ArrayList<BackItem>();
                BackItem info = new BackItem();
                info.setIsright(C.reg.USER_EXISTS);
                info.setInGame(false);
                list.add(info);
                callBackPage.setDatalist(list);
                return callBackPage;
            }

            if (C.reg.USER_NOT_EXISTS.equals(message.getMessageCode())) {
                CallBackPage<BackItem> callBackPage = new CallBackPage<BackItem>();
                List<BackItem> list = new ArrayList<BackItem>();
                BackItem info = new BackItem();
                info.setIsright(C.reg.USER_NOT_EXISTS);
                info.setInGame(false);
                list.add(info);
                callBackPage.setDatalist(list);
                return callBackPage;
            }
            if (C.vcode.SMS_SEND_ERROR.equals(message.getMessageCode())) {
                CallBackPage<BackItem> callBackPage = new CallBackPage<BackItem>();
                List<BackItem> list = new ArrayList<BackItem>();
                BackItem info = new BackItem();
                info.setIsright(C.vcode.SMS_SEND_ERROR);
                info.setInGame(false);
                list.add(info);
                callBackPage.setDatalist(list);
                return callBackPage;
            }
        }
        return null;
    }

    public String getLoginContentInf() {
        return null;
    }

    @Override
    public CallBackPage<UserInfo> getUserInfo(String result) {
        Message message = json.fromJson(result, new TypeToken<Message>() {
        }.getType());
        if (C.convene.GET_USER_INFO_SUCCESS.equals(message.getMessageCode())) {
            Message<UserInfo> message1 = json.fromJson(result, new TypeToken<Message<UserInfo>>() {
            }.getType());
            CallBackPage<UserInfo> callBackPage = new CallBackPage();
            List list = new ArrayList();
            UserInfo userInf = (UserInfo) message1.getMessageContent();
            list.add(userInf);
            callBackPage.setFalg("userInfo");
            callBackPage.setDatalist(list);
            return callBackPage;
        }
        return null;
    }
}