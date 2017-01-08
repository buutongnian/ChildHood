package edu.buu.childhood.chat.activity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.jivesoftware.smack.roster.Roster;
import org.jivesoftware.smack.roster.RosterEntry;
import org.jivesoftware.smack.roster.packet.RosterPacket;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import edu.buu.childhood.R;
import edu.buu.childhood.chat.adapter.ContactAdapter;
import edu.buu.childhood.chat.bean.User;
import edu.buu.childhood.common.C;
import edu.buu.childhood.common.CallBackPage;
import edu.buu.childhood.common.GlobalVeriable;
import edu.buu.childhood.common.Message;
import edu.buu.childhood.common.activity.BaseAvtivity;
import edu.buu.childhood.common.activity.UserInfoActivity;
import edu.buu.childhood.database.ChatRecordsDao;
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
public class ChatContactActivity extends BaseAvtivity implements CallBack {
    private NewMessageBroadcastReceiver newMessageBroadcastReceiver;
    private IntentFilter intentFilter;
    private ListView listView;
    private TextView tv_newFriends;
    private TextView tv_newMessages;
    private TextView tv_total;
    private ContactAdapter adapter;
    private List<User> dataList = new ArrayList<>();
    private String getUserInfoUrl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        CloseActivity.activityList.add(this);
        setContentView(R.layout.fragment_contactlist);
        //getContactList();

        ((GlobalVeriable) getApplication()).setHaveMessage(false);
        listView = (ListView) findViewById(R.id.contact_list);
        View headView = LayoutInflater.from(this).inflate(R.layout.item_contact_list_header,
                null);

        listView.addHeaderView(headView);
        View footerView = LayoutInflater.from(this).inflate(R.layout.item_contact_list_footer,
                null);
        listView.addFooterView(footerView);
        tv_newFriends = (TextView) headView.findViewById(R.id.newfriends_unread);
        tv_newMessages = (TextView) findViewById(R.id.newmessage_unread);
        headView.findViewById(R.id.re_chatroom).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ChatContactActivity.this, ConversationActivity.class));
            }
        });
        headView.findViewById(R.id.re_newfriends).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ChatContactActivity.this, NewFriendsActivity.class));
            }
        });
        tv_total = (TextView) footerView.findViewById(R.id.tv_total);
        tv_total.setText(String.valueOf(dataList.size()) + "位联系人");
        adapter = new ContactAdapter(this, dataList);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (position != 0 && position != dataList.size() + 1) {
                    User user = dataList.get(position - 1);
                    String username = user.getUserName();
                    ((GlobalVeriable) getApplication()).setToChatingUser(username);
                    Intent intent = new Intent(ChatContactActivity.this, UserInfoActivity.class);
                    intent.putExtra("userName", username);
                    intent.putExtra("userNickName", user.getNick());
                    intent.putExtra("imageUrl", user.getUserImage());
                    startActivity(intent);
                   /* startActivity(new Intent(getActivity(), ChatUserInfoActivity.class)
                    .putExtra("hxid", username ).putExtra("nick", user.getNick() ).putExtra("avatar", user.getAvatar() ).putExtra("sex", user.getSex() ));*/
                }
            }
        });
        getUserListFromSmack();
        newMessageBroadcastReceiver = new NewMessageBroadcastReceiver();
        intentFilter = new IntentFilter();
        intentFilter.addAction("CHATMESSAGE");
        registerReceiver(newMessageBroadcastReceiver, intentFilter); //注册广播监听
        intentFilter.setPriority(3);
    }

    @Override
    protected void onResume() {
        updateTvMessage();
        super.onResume();
    }

    @Override
    protected void onDestroy() {
        try {
            unregisterReceiver(newMessageBroadcastReceiver); //取消监听}
            newMessageBroadcastReceiver = null;
        } catch (Exception e) {

        }
        super.onDestroy();
    }

    private void getContactList() {
        // dataList.clear();
        User user = new User();
        user.setSign("欧阳涛");
        dataList.add(user);

    }

    public void getUserListFromSmack() {
        Roster roster = SmackManager.getInstance().getRoster();
        if (roster != null) {
            Collection<RosterEntry> entries = roster.getEntries();
            if (entries != null) {
                for (RosterEntry entry : entries) {
                    String userName = entry.getUser();
                    RosterPacket.ItemType type = entry.getType();
                    //type为both才能为好友
                    if (!type.equals(RosterPacket.ItemType.none)) {
                        User user = getUserInfo(userName.substring(0, userName.indexOf("@")));
                        if (user != null) {
                            dataList.add(user);
                            adapter.notifyDataSetChanged();
                            tv_total.setText(String.valueOf(dataList.size()) + "位联系人");
                        }
                    }
                }
            }
        }
    }

    public User getUserInfo(String userName) {
        //首先去本地找是否有次用户的信息
        UserDao userDao = new UserDao(this);
        User user = userDao.getUserInfo(userName);
        Map map = new HashMap();
        map.put("userName", ((GlobalVeriable) getApplication()).getUserName());
        map.put("targetUser", userName);
        getUserInfoUrl = URLUtil.getURL("getUserInfo", map);
        new NetAsyncTask(this).execute(getUserInfoUrl);
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
            UserInfo userInfo = (UserInfo) result.getDatalist().get(0);
            updateContactList(userInfo);
        }
    }

    public void updateContactList(UserInfo userInfo) {
        User user = new User();
        user.setUserName(userInfo.getUserName());
        user.setNick(userInfo.getUserNickname());
        user.setUserImage(userInfo.getUserHeadImage());
        UserDao userDao = new UserDao(this);
        if (userDao.isUserInfo(user.getUserName())) {
            userDao.updateUserInfo(user);
        } else {
            dataList.add(user);
            adapter.notifyDataSetChanged();
            tv_total.setText(String.valueOf(dataList.size()) + "位联系人");
            saveUserInfo(user);
        }
    }

    public void saveUserInfo(User user) {
        UserDao userDao = new UserDao(this);
        userDao.saveContact(user);
    }

    public void updateTvMessage() {
        String userName = ((GlobalVeriable) getApplication()).getUserName();
        ChatRecordsDao chatRecordsDao = new ChatRecordsDao(this);
        int count = chatRecordsDao.getMessageCount(userName);
        if (count != -1) {
            tv_newMessages.setText(String.valueOf(count));
            tv_newMessages.setVisibility(View.VISIBLE);
        } else {
            tv_newMessages.setVisibility(View.GONE);
        }
    }


    private class NewMessageBroadcastReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            updateTvMessage();
        }
    }
}
