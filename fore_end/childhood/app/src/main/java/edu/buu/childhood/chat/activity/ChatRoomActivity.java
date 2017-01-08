package edu.buu.childhood.chat.activity;

import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.jivesoftware.smack.SmackException;
import org.jivesoftware.smack.chat.Chat;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import edu.buu.childhood.R;
import edu.buu.childhood.baidumap.adapter.MutiLayoutAdapter;
import edu.buu.childhood.baidumap.pojo.App;
import edu.buu.childhood.baidumap.pojo.Book;
import edu.buu.childhood.chat.bean.ChatRecords;
import edu.buu.childhood.chat.bean.ChatRecordsList;
import edu.buu.childhood.chat.bean.User;
import edu.buu.childhood.common.C;
import edu.buu.childhood.common.GlobalVeriable;
import edu.buu.childhood.common.Message;
import edu.buu.childhood.common.activity.BaseAvtivity;
import edu.buu.childhood.database.ChatRecodrsListDao;
import edu.buu.childhood.database.ChatRecordsDao;
import edu.buu.childhood.database.UserDao;
import edu.buu.childhood.mainui.activity.Main_interface;
import edu.buu.childhood.my.pojo.UserInfo;
import edu.buu.childhood.util.CloseActivity;
import edu.buu.childhood.util.HttpUtil;
import edu.buu.childhood.util.MessageCallBack;
import edu.buu.childhood.util.MessageEnty;
import edu.buu.childhood.util.MessageLoaderAsyncTask;
import edu.buu.childhood.util.SmackManager;
import edu.buu.childhood.util.URLUtil;

/**
 * Created by Administrator on 2016/9/26.
 */
public class ChatRoomActivity extends BaseAvtivity implements MessageCallBack {
    private List<ChatRecords> chatRecordsesList;
    private ListView listView;
    private MutiLayoutAdapter adapter;
    private ArrayList<Object> mData = new ArrayList<>();
    private NewMessageBroadcastReceiver newMessageBroadcastReceiver;
    private IntentFilter intentFilter;
    private String getUserInfoUrl;
    private Boolean flagAdapter = true;
    private TextView fromUserText;
    private EditText messageEditText;
    private Button sendButton;
    private Chat chat;
    private String toChatUser;
    private String toChatUserNick;

    private NotificationManager mNotificationManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        CloseActivity.activityList.add(this);
        setContentView(R.layout.chat_chat_room);
        messageEditText = (EditText) findViewById(R.id.chat_input_text);
        fromUserText = (TextView) findViewById(R.id.chat_room_from_user);
        Intent intent = getIntent();
        if (intent != null) {
            toChatUser = intent.getStringExtra("userName");
            toChatUserNick = intent.getStringExtra("userNick");
        }
        fromUserText.setText(toChatUserNick);
        newMessageBroadcastReceiver = new NewMessageBroadcastReceiver();
        intentFilter = new IntentFilter();
        listView = (ListView) findViewById(R.id.chat_room_listView);
        adapter = new MutiLayoutAdapter(this, mData);
        if (mData != null && mData.size() > 0) {
            flagAdapter = false;
            listView.setAdapter(adapter);
            listView.setSelection(listView.getCount() - 1);
        }
        getChatRecords();
        intentFilter.addAction("CHATMESSAGE");
        registerReceiver(newMessageBroadcastReceiver, intentFilter); //注册广播监听
        intentFilter.setPriority(5);
        sendButton = (Button) findViewById(R.id.chat_chat_room_send);
        sendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendMessage();
            }
        });
        //监听输入框文字的改变，来改变sendBtn颜色
        messageEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                GradientDrawable sendShape = (GradientDrawable) sendButton.getBackground();
                if (!"".equals(messageEditText.getText().toString())) {
                    sendShape.setColor(getResources().getColor(R.color.send_blue));
                } else {
                    sendShape.setColor(getResources().getColor(R.color.send_before_gray));
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
                GradientDrawable sendShape = (GradientDrawable) sendButton.getBackground();
                if (!"".equals(messageEditText.getText().toString())) {
                    sendShape.setColor(getResources().getColor(R.color.send_blue));
                } else {
                    sendShape.setColor(getResources().getColor(R.color.send_before_gray));
                }
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        try {
            unregisterReceiver(newMessageBroadcastReceiver); //取消监听}
            newMessageBroadcastReceiver = null;
        } catch (Exception e) {

        }
        ((GlobalVeriable) getApplication()).setToChatingUser(null);
    }

    public void getChatRecords() {
        GlobalVeriable globalVeriable = (GlobalVeriable) getApplication();
        ChatRecordsDao chatRecordsDao = new ChatRecordsDao(this);
        List<ChatRecords> chatList = chatRecordsDao.getChatMessage(globalVeriable.getUserName(), toChatUser);
        if (chatList != null && chatList.size() > 0) {
            for (int i = 0; i < chatList.size(); i++) {
                ChatRecords chatRecords = chatList.get(i);
                if (chatRecords.getIsSelf() == 1) {
                    loadMessageRecords(chatRecords.getMessageContent(), chatRecords.getUserName(), true);
                } else {
                    loadMessageRecords(chatRecords.getMessageContent(), chatRecords.getFromUser(), false);
                    //消息记录更新已读
                    if (chatRecords.getIsRead() == 0) {
                        updateRecordsStatus(chatRecords.getMessageId());
                    }
                }
            }
            adapter.notifyDataSetChanged();
            listView.setSelection(listView.getCount() - 1);
        }
    }

    private class NewMessageBroadcastReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            GlobalVeriable globalVeriable = (GlobalVeriable) getApplication();
            String fromUser = intent.getStringExtra("fromUser");
            String message = intent.getStringExtra("message");

            int msgId = intent.getIntExtra("msgId", -1);
            //是否是当前会话
            if (fromUser.equals(((GlobalVeriable) getApplication()).getToChatingUser())) {
                receiveMessage(fromUser, message, msgId);
            }
        }
    }

    @Override
    public MessageEnty doInBackground(String url) {
        HttpUtil httpUtil = new HttpUtil(url);
        byte[] bytes = httpUtil.getHttpData();
        Gson json = new Gson();
        if (bytes != null) {
            Message message = json.fromJson(new String(bytes), new TypeToken<Message>() {
            }.getType());
            if (C.convene.GET_USER_INFO_SUCCESS.equals(message.getMessageCode())) {
                Message<UserInfo> message1 = json.fromJson(new String(bytes), new TypeToken<Message<UserInfo>>() {
                }.getType());
                MessageEnty imageEnty = new MessageEnty();
                List list = new ArrayList();
                UserInfo userInf = (UserInfo) message1.getMessageContent();
                list.add(userInf);
                imageEnty.setDatalist(list);
                return imageEnty;
            }
        }
        return null;
    }

    @Override
    public void getResult(MessageEnty messageEnty, String message) {
        if (messageEnty != null) {
            UserInfo userInf = (UserInfo) messageEnty.getDatalist().get(0);
            if (mData == null) {
                mData = new ArrayList<>();
            }
            mData.add(new App(userInf.getUserName(), R.drawable.touxiang, message, userInf.getUserHeadImage()));
            if (flagAdapter) {
                listView.setAdapter(adapter);
                flagAdapter = false;
            }
            adapter.notifyDataSetChanged();
            listView.setSelection(listView.getCount() - 1);
            //储存user信息
            User user = new User();
            user.setUserName(userInf.getUserName());
            user.setUsernick(userInf.getUserNickname());
            user.setUserImage(userInf.getUserHeadImage());
            UserDao userDao = new UserDao(this);
            userDao.saveContact(user);
        }
    }

    public void sendMessage() {
        if (!"".equals(messageEditText.getText().toString())) {
            ArrayList<CharSequence> message = new ArrayList<CharSequence>();
            if (chat == null) {
                GlobalVeriable globalVeriable = (GlobalVeriable) getApplication();
                chat = SmackManager.getInstance().createChat(globalVeriable.getToChatingUser() + "@" + SmackManager.getInstance().getServiceName());
            }
            try {
                chat.sendMessage(messageEditText.getText().toString());
            } catch (SmackException.NotConnectedException e) {
                e.printStackTrace();
            }
            sendToOthersMessage(((GlobalVeriable) getApplication()).getUserName(), messageEditText.getText().toString());
            messageEditText.setText("");
        }
    }

    //本人发送消息后，消息的显示
    public void updateMessage(String message, String userName, Boolean isSelf) {
        User user = getUserInfo(userName);
        if (user != null) {
            if (isSelf) {
                mData.add(new Book(userName, R.drawable.touxiang, message, user.getUserImage()));
                //saveSelfChatMessage(userName,message);
            } else {
                mData.add(new App(userName, R.drawable.touxiang, message, user.getUserImage()));
            }
            if (mData == null) {
                mData = new ArrayList<>();
            }
            if (flagAdapter) {
                adapter = new MutiLayoutAdapter(this, mData);
                listView.setAdapter(adapter);
                flagAdapter = false;
            }
            adapter.notifyDataSetChanged();
            listView.setSelection(listView.getCount() - 1);
        } else {
            Map map = new HashMap();
            map.put("userName", ((GlobalVeriable) getApplication()).getUserName());
            map.put("targetUser", userName);
            getUserInfoUrl = URLUtil.getURL("getUserInfo", map);
            new MessageLoaderAsyncTask(this, message).execute(getUserInfoUrl);
        }
    }

    public User getUserInfo(String userName) {
        //首先去本地找是否有次用户的信息
        UserDao userDao = new UserDao(this);
        User user = userDao.getUserInfo(userName);
        return user;
    }

    public void receiveMessage(String fromUser, String message, int msgId) {
        updateMessage(message, fromUser, false);
        //更新记录已读
        updateRecordsStatus(msgId);
    }

    public void sendToOthersMessage(String userName, String message) {
        updateMessage(message, userName, true);
        saveSelfChatMessage(userName, message);
    }

    public void saveSelfChatMessage(String userName, String message) {
        ChatRecordsDao chatRecordsDao = new ChatRecordsDao(this);
        ChatRecords chatRecords = new ChatRecords();
        chatRecords.setUserName(userName);
        chatRecords.setFromUser(((GlobalVeriable) getApplication()).getToChatingUser());
        chatRecords.setMessageContent(message);
        chatRecords.setIsSelf(1);
        chatRecords.setIsRead(1);
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String date = dateFormat.format(new Date(System.currentTimeMillis()));
        chatRecords.setMessageTime(date);
        chatRecordsDao.saveChatMessage(chatRecords);
        ChatRecodrsListDao chatRecodrsListDao = new ChatRecodrsListDao(this);
        ChatRecordsList chatRecordsList = new ChatRecordsList();
        chatRecordsList.setLastMessage(message);
        chatRecordsList.setLastTime(date);
        chatRecordsList.setUserName(userName);
        chatRecordsList.setFromUser(((GlobalVeriable) getApplication()).getToChatingUser());
        if (chatRecodrsListDao.isHaveRecord(userName, ((GlobalVeriable) getApplication()).getToChatingUser())) {
            chatRecodrsListDao.updateChatRecordsList(chatRecordsList);
        } else {
            chatRecodrsListDao.saveChatRecordsList(chatRecordsList);
        }
    }

    public void updateRecordsStatus(int msgId) {
        if (msgId != -1) {
            ChatRecordsDao chatRecordsDao = new ChatRecordsDao(ChatRoomActivity.this);
            chatRecordsDao.updateMessageStatus(msgId);
        }
    }

    public void loadMessageRecords(String message, String userName, Boolean isSelf) {
        User user = getUserInfo(userName);
        if (user != null) {
            if (isSelf) {
                mData.add(new Book(userName, R.drawable.touxiang, message, user.getUserImage()));
                //saveSelfChatMessage(userName,message);
            } else {
                mData.add(new App(userName, R.drawable.touxiang, message, user.getUserImage()));
            }
            if (mData == null) {
                mData = new ArrayList<>();
            }
            if (flagAdapter) {
                adapter = new MutiLayoutAdapter(this, mData);
                listView.setAdapter(adapter);
                flagAdapter = false;
            }
        } else {
            Map map = new HashMap();
            map.put("userName", ((GlobalVeriable) getApplication()).getUserName());
            map.put("targetUser", userName);
            getUserInfoUrl = URLUtil.getURL("getUserInfo", map);
            new MessageLoaderAsyncTask(this, message).execute(getUserInfoUrl);
        }
    }

    @Override
    protected void onResume() {
        ((GlobalVeriable) getApplication()).setHaveMessage(false);
        android.os.Message messgae = new android.os.Message();
        Bundle bundle = new Bundle();
        bundle.putString("handleEvent", "changeMessageTip");
        messgae.setData(bundle);
        Main_interface.getMainHandler().handleMessage(messgae);
        mNotificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        mNotificationManager.cancelAll();
        super.onResume();
    }
}
