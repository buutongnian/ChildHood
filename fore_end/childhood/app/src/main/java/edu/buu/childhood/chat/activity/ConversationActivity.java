package edu.buu.childhood.chat.activity;

import android.app.NotificationManager;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

import edu.buu.childhood.R;
import edu.buu.childhood.chat.adapter.ConversationAdapter;
import edu.buu.childhood.chat.bean.ChatRecordsList;
import edu.buu.childhood.chat.bean.EMConversation;
import edu.buu.childhood.chat.bean.User;
import edu.buu.childhood.common.GlobalVeriable;
import edu.buu.childhood.common.activity.BaseAvtivity;
import edu.buu.childhood.database.ChatRecodrsListDao;
import edu.buu.childhood.database.ChatRecordsDao;
import edu.buu.childhood.database.UserDao;
import edu.buu.childhood.util.CloseActivity;

/**
 * Created by Administrator on 2016/9/25.
 */
public class ConversationActivity extends BaseAvtivity {
    private List<EMConversation> dataList = new ArrayList<>();
    private ConversationAdapter conversationAdapter;
    private ListView listView;

    private NotificationManager mNotificationManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        CloseActivity.activityList.add(this);
        setContentView(R.layout.fragment_home);
        //SmackManager.getInstance().getRoster();

        mNotificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        mNotificationManager.cancelAll();

        listView = (ListView) findViewById(R.id.conversation_list);
        conversationAdapter = new ConversationAdapter(this, dataList);
        listView.setAdapter(conversationAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                EMConversation emConversation = dataList.get(position);
                String fromUser = emConversation.getFromUser();
                ((GlobalVeriable) getApplication()).setToChatingUser(emConversation.getFromUser());
                Intent intent = new Intent(ConversationActivity.this, ChatRoomActivity.class);
                intent.putExtra("userName", fromUser);
                intent.putExtra("userNick", emConversation.getUserNick());
                startActivity(intent);
            }
        });
    }

    @Override
    protected void onResume() {
        if (dataList.size() > 0) {
            dataList.clear();
        }
        getConversationDatalist();
        conversationAdapter.notifyDataSetChanged();
        super.onResume();
    }

    public void getConversationDatalist() {
        ChatRecodrsListDao chatRecodrsListDao = new ChatRecodrsListDao(this);
        List<ChatRecordsList> chatRecordsList = chatRecodrsListDao.getChatRecordsList(((GlobalVeriable) getApplication()).getUserName());
        if (chatRecordsList.size() > 0) {
            for (ChatRecordsList list : chatRecordsList) {
                String fromUser = list.getFromUser();
                if (fromUser != null) {
                    User user = getUserInfo(fromUser);
                    int count = getMessageCount(fromUser);
                    if (user != null) {
                        EMConversation emConversation = new EMConversation();
                        emConversation.setFromUser(fromUser);
                        emConversation.setUserNick(user.getUsernick());
                        emConversation.setMessage(list.getLastMessage());
                        emConversation.setFromDate(list.getLastTime());
                        emConversation.setImageUrl(user.getUserImage());
                        emConversation.setCountMessage(count);
                        dataList.add(emConversation);
                    }
                }
            }
        }
    }

    public User getUserInfo(String userName) {
        UserDao userDao = new UserDao(this);
        User user = userDao.getUserInfo(userName);
        return user;
    }

    public int getMessageCount(String fromUser) {
        int id = -1;
        ChatRecordsDao chatRecordsDao = new ChatRecordsDao(this);
        id = chatRecordsDao.getMessageCount(((GlobalVeriable) getApplication()).getUserName(), fromUser);
        return id;
    }


}
