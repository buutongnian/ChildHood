package edu.buu.childhood.chat.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.jivesoftware.smack.SmackException;
import org.jivesoftware.smack.packet.Presence;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import edu.buu.childhood.Interface.BtnListener;
import edu.buu.childhood.R;
import edu.buu.childhood.chat.adapter.NewFriendsAdapter;
import edu.buu.childhood.chat.bean.InviteMessage;
import edu.buu.childhood.chat.bean.User;
import edu.buu.childhood.common.C;
import edu.buu.childhood.common.CallBackPage;
import edu.buu.childhood.common.GlobalVeriable;
import edu.buu.childhood.common.Message;
import edu.buu.childhood.common.activity.BaseAvtivity;
import edu.buu.childhood.database.InviteMessgeDao;
import edu.buu.childhood.database.UserDao;
import edu.buu.childhood.my.pojo.UserInfo;
import edu.buu.childhood.util.CallBack;
import edu.buu.childhood.util.CloseActivity;
import edu.buu.childhood.util.HttpUtil;
import edu.buu.childhood.util.NetAsyncTask;
import edu.buu.childhood.util.SmackManager;
import edu.buu.childhood.util.URLUtil;

/**
 * Created by Administrator on 2016/9/25.
 */
public class NewFriendsActivity extends BaseAvtivity implements CallBack, BtnListener {
    private ListView listView;
    private List<InviteMessage> dataList = new ArrayList<>();
    private NewFriendsAdapter newFriendsAdapter;
    private String getUserInfoUrl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        CloseActivity.activityList.add(this);
        setContentView(R.layout.activity_newfriendsmsg);
        listView = (ListView) findViewById(R.id.listview);
        newFriendsAdapter = new NewFriendsAdapter(this, dataList, this);
        listView.setAdapter(newFriendsAdapter);
        getInviteMessageDataList();
        findViewById(R.id.activity_new_friwndsmsg_search).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(NewFriendsActivity.this, SearchFriendActivity.class));
            }
        });
    }

    public void getInviteMessageDataList() {
        InviteMessgeDao inviteMessgeDao = new InviteMessgeDao(this);
        List<InviteMessage> inviteMessagesList = inviteMessgeDao.getMessagesList(((GlobalVeriable) getApplication()).getUserName());
        if (inviteMessagesList.size() > 0) {
            for (InviteMessage inviteMessage : inviteMessagesList) {
                String fromUser = inviteMessage.getFrom();
                User user = getUserInfo(fromUser);
                if (user != null) {
                    addDataList(inviteMessage, user);
                }
            }
            newFriendsAdapter.notifyDataSetChanged();
        }
    }

    public User getUserInfo(String userName) {
        //首先去本地找是否有次用户的信息
        UserDao userDao = new UserDao(this);
        User user = userDao.getUserInfo(userName);
        if (user == null) {
            Map map = new HashMap();
            map.put("userName", ((GlobalVeriable) getApplication()).getUserName());
            map.put("targetUser", userName);
            getUserInfoUrl = URLUtil.getURL("getUserInfo", map);
            new NetAsyncTask(this).execute(getUserInfoUrl);
        }
        return user;

    }

    @Override
    public CallBackPage doInBackground(String url) {
        HttpUtil httpUtil = new HttpUtil(url);
        byte[] bytes = httpUtil.getHttpData();
        if (bytes != null) {
            Gson json = new Gson();
            Message message = json.fromJson(new String(bytes), new TypeToken<Message>() {
            }.getType());
            if (C.convene.GET_USER_INFO_SUCCESS.equals(message.getMessageCode())) {
                Message<UserInfo> message1 = json.fromJson(new String(bytes), new TypeToken<Message<UserInfo>>() {
                }.getType());
                CallBackPage<UserInfo> callBackPage = new CallBackPage<>();
                List list = new ArrayList();
                UserInfo userInf = (UserInfo) message1.getMessageContent();
                list.add(userInf);
                callBackPage.setDatalist(list);
                return callBackPage;
            }
        }
        return null;
    }

    @Override
    public void getResult(CallBackPage result) {
        if (result != null) {
            UserInfo userInf = (UserInfo) result.getDatalist().get(0);
            firstGetInviteMessage(userInf);
        }
    }

    public void addDataList(InviteMessage inviteMessage, User user) {
        InviteMessage inviteMessageData = new InviteMessage();
        inviteMessageData.setUserName(inviteMessage.getUserName());
        inviteMessageData.setFrom(inviteMessage.getFrom());
        inviteMessageData.setId(inviteMessage.getId());
        inviteMessageData.setImageUrl(user.getUserImage());
        inviteMessageData.setFromUserNick(user.getNick());
        inviteMessageData.setReason(inviteMessage.getReason());
        inviteMessageData.setStatus(inviteMessage.getStatus());
        dataList.add(inviteMessageData);
    }


    //首次获得邀请消息，查询用户信息后的返回处理内容
    public void firstGetInviteMessage(UserInfo userInf) {
        User user = new User();
        user.setUserName(userInf.getUserName());
        user.setNick(userInf.getUserNickname());
        user.setUserImage(userInf.getUserHeadImage());
        user.setTel(userInf.getUserName());
        InviteMessgeDao inviteMessgeDao = new InviteMessgeDao(this);
        InviteMessage inviteMessage = inviteMessgeDao.getInviteMessage(((GlobalVeriable) getApplication()).getUserName(), userInf.getUserName());
        inviteMessage.setImageUrl(userInf.getUserHeadImage());
        inviteMessage.setFromUserNick(userInf.getUserNickname());
        dataList.add(inviteMessage);
        newFriendsAdapter.notifyDataSetChanged();
        UserDao userDao = new UserDao(this);
        userDao.saveContact(user);
    }

    @Override
    public void onClick(View v, int positation) {
        InviteMessage inviteMessage = dataList.get(0);
        Presence presenceRes = new Presence(Presence.Type.subscribed);
        String serviceName = SmackManager.getInstance().getServiceName();
        presenceRes.setTo(inviteMessage.getFrom() + "@" + serviceName);
        Presence presenceRes1 = new Presence(Presence.Type.subscribe);
        presenceRes1.setTo(inviteMessage.getFrom() + "@" + serviceName);
        try {
            SmackManager.getInstance().getConnection().sendStanza(presenceRes);
            InviteMessgeDao inviteMessgeDao = new InviteMessgeDao(this);
            inviteMessage.setStatus(InviteMessage.InviteMesageStatus.AGREED);
            inviteMessgeDao.updateMessage(inviteMessage);
            dataList.get(0).setStatus(InviteMessage.InviteMesageStatus.AGREED);
            newFriendsAdapter.notifyDataSetChanged();
        } catch (SmackException.NotConnectedException e) {
            e.printStackTrace();
        }
    }
}
