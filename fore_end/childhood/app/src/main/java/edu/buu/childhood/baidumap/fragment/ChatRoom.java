package edu.buu.childhood.baidumap.fragment;

import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import edu.buu.childhood.R;
import edu.buu.childhood.baidumap.activity.MainBaidu;
import edu.buu.childhood.baidumap.activity.chatTeamCreate;
import edu.buu.childhood.baidumap.adapter.MutiLayoutAdapter;
import edu.buu.childhood.baidumap.pojo.App;
import edu.buu.childhood.baidumap.pojo.Book;
import edu.buu.childhood.baidumap.pojo.Middle;
import edu.buu.childhood.common.C;
import edu.buu.childhood.common.ChatMessage;
import edu.buu.childhood.common.GlobalVeriable;
import edu.buu.childhood.common.Message;
import edu.buu.childhood.my.pojo.UserInfo;
import edu.buu.childhood.service.CoreService;
import edu.buu.childhood.util.DBOprate;
import edu.buu.childhood.util.HttpUtil;
import edu.buu.childhood.util.MessageCallBack;
import edu.buu.childhood.util.MessageEnty;
import edu.buu.childhood.util.MessageLoaderAsyncTask;
import edu.buu.childhood.util.URLUtil;

/**
 * Created by lcc on 2016/7/2.
 */
public class ChatRoom extends Fragment implements MessageCallBack {
    private static final int TYPE_BOOK = 0;
    private static final int TYPE_APP = 1;
    private static final int NOTICE = 2;
    private ListView list_content;
    private ArrayList<Object> mData = new ArrayList<>();
    private MutiLayoutAdapter myAdapter = null;
    private ImageView nextTeam;
    private Boolean flagAdapter = true;
    private String leftUrl;
    private String rightUrl;
    private Context context;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.chat_room, container, false);
        nextTeam = (ImageView) view.findViewById(R.id.chat_team_chat_team);
        final GlobalVeriable globalVeriable = (GlobalVeriable) getActivity().getApplication();
        nextTeam.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), chatTeamCreate.class));
            }
        });
        globalVeriable.setRunnigMainBaidu(true);
       /*else {
            nextTeam.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    startActivity(new Intent(getActivity(), chatTeamJoin.class));
                }

            });
        }*/
        //数据准备：

        initData(((GlobalVeriable) getActivity().getApplication()).getInstance());
        list_content = (ListView) view.findViewById(R.id.chat_room_listView);
        myAdapter = new MutiLayoutAdapter(getActivity(), mData);
        //Log.i("info", "到这儿了" + mData.size());
        if (mData.size() > 0) {
            flagAdapter = false;
            list_content.setAdapter(myAdapter);
            list_content.setSelection(list_content.getCount() - 1);
        }
        final EditText editText = (EditText) view.findViewById(R.id.input_text);
        final Button sendButton = (Button) view.findViewById(R.id.chat_room_send);
        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                GradientDrawable sendShape = (GradientDrawable) sendButton.getBackground();
                if (!"".equals(editText.getText().toString())) {

                    sendShape.setColor(getResources().getColor(R.color.send_blue));
                } else {
                    sendShape.setColor(getResources().getColor(R.color.send_before_gray));
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
                Log.i("after", "after");
                if (!"".equals(editText.getText().toString())) {
                    GradientDrawable sendShape = (GradientDrawable) sendButton.getBackground();
                    sendShape.setColor(getResources().getColor(R.color.send_blue));
                }

            }
        });

        sendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                GlobalVeriable globalVeriable1 = (GlobalVeriable) getActivity().getApplication();
                Log.i("edixt", editText.getText().toString());
                if (!"".equals(editText.getText().toString())) {
                    if (globalVeriable.getGameRunning()) {
                        ArrayList<CharSequence> message = new ArrayList<CharSequence>();
                        message.add(String.valueOf(globalVeriable.getGameId()));
                        message.add(editText.getText().toString());
                        ((MainBaidu) getActivity()).sendToService(message, CoreService.MESSAGE_TYPE_CHATROMM_SEND);
                        updateData(editText.getText().toString(), globalVeriable.getUserName(), 0, null);
                        saveChatRoomMessage(editText.getText().toString());
                        editText.setText("");
                    }
                } else {
                    Toast.makeText(getActivity(), "不能输入为空", Toast.LENGTH_SHORT).show();
                }
            }
        });
        return view;
    }


    //初始化adapter的数据
    public void initData(Context context) {
        if (mData == null) {
            mData = new ArrayList<Object>();
        }
        Log.i("Context", context + "ddd");
        DBOprate dbOprate = new DBOprate(context, "Msf.db");
        String querySql = "select * from chatRoomMessage where roomid=" + ((GlobalVeriable) context).getGameId() + " " + "and isroommessage=1 order by id desc limit 20";
        List<ChatMessage> list = dbOprate.getChatMessage(querySql);
        if (list.size() > 0) {
            for (int i = list.size() - 1; i >= 0; i--) {
                ChatMessage chatMessage = list.get(i);
                if (chatMessage.getUserName() != null && !"".equals(chatMessage.getUserName())) {
                    if (chatMessage.getFromName().trim().equals(((GlobalVeriable) context).getUserName())) {
                        rightUrl = getImageUrl(chatMessage.getFromName());
                        new MessageLoaderAsyncTask(this, chatMessage.getMessageContent()).execute(rightUrl);
                    } else {
                        leftUrl = getImageUrl(chatMessage.getFromName());
                        new MessageLoaderAsyncTask(this, chatMessage.getMessageContent()).execute(leftUrl);
                    }
                }
            }
        }
        /*for (int i = 0; i < 20; i++) {
            switch ((int) (Math.random() * 2)) {
                case TYPE_BOOK:
                    mData.add(new Book(R.mipmap.ic_launcher, "百度"));
                    break;
                case TYPE_APP:
                    mData.add(new App(R.mipmap.ic_launcher, "百度"));
                    break;
            }
        }*/
    }

    //更新adapter的数据
    public void updateData(String message, String userName, int flag, Context context) {
        this.context = context;
        if (mData == null) {
            mData = new ArrayList<Object>();
        }
        switch (flag) {
            case TYPE_BOOK:
                rightUrl = getImageUrl(userName);
                new MessageLoaderAsyncTask(ChatRoom.this, message).execute(rightUrl);
                break;
            case TYPE_APP:
                if (!((GlobalVeriable) context).getUserName().equals(userName)) {
                    leftUrl = getImageUrl(userName);
                    new MessageLoaderAsyncTask(ChatRoom.this, message).execute(leftUrl);
                }
                break;
            case NOTICE:
                mData.add(new Middle(message));
                if (flagAdapter) {
                    list_content.setAdapter(myAdapter);
                    flagAdapter = false;
                }
                myAdapter.notifyDataSetChanged();
                list_content.setSelection(list_content.getCount() - 1);
                break;

        }
        //  list_content.setAdapter(myAdapter);
    }

    @Override
    public void onResume() {

        Log.d("status", "fragement onResume");
        super.onResume();
    }

    @Override
    public void onStop() {
        Log.d("status", "fragement onStop");
        super.onStop();
    }

    @Override
    public void onPause() {
        super.onPause();
    }

    @Override
    public void onDestroy() {
        GlobalVeriable globalVeriable = (GlobalVeriable) getActivity().getApplication();
        globalVeriable.setRunnigMainBaidu(false);
        Log.d("status", "activity onDestroy");
        super.onDestroy();
    }


    //组装获取聊天室用户图像的地址
    public String getImageUrl(String userName) {
        Map map = new HashMap();
        map.put("userName", ((GlobalVeriable) getActivity().getApplication()).getUserName());
        map.put("targetUser", userName);
        return URLUtil.getURL("getUserInfo", map);
    }

    @Override
    public MessageEnty doInBackground(String url) {
        HttpUtil httpUtil = new HttpUtil(url);
        byte[] bytes = httpUtil.getHttpData();
        Gson json = new Gson();
        if (bytes != null) {
            if (leftUrl != null) {
                if (leftUrl.equals(url)) {
                    Log.i("leftUrl", leftUrl);
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
                            imageEnty.setFlagUrl("left");
                            return imageEnty;
                        }
                    }
                }
            }
            if (rightUrl != null) {
                if (rightUrl.equals(url)) {
                    if (bytes != null) {
                        Message<UserInfo> message = json.fromJson(new String(bytes), new TypeToken<Message<UserInfo>>() {
                        }.getType());
                        if (C.convene.GET_USER_INFO_SUCCESS.equals(message.getMessageCode())) {
                            MessageEnty imageEnty = new MessageEnty();
                            List list = new ArrayList();
                            UserInfo userInf = (UserInfo) message.getMessageContent();
                            list.add(userInf);
                            imageEnty.setDatalist(list);
                            imageEnty.setFlagUrl("right");
                            return imageEnty;
                        }
                    }
                }
            }
        }

        return null;
    }

    @Override
    public void getResult(MessageEnty imageEnty, String message) {
        if (imageEnty != null) {
            if ("left".equals(imageEnty.getFlagUrl())) {
                UserInfo userInf = (UserInfo) imageEnty.getDatalist().get(0);
                mData.add(new App(userInf.getUserName(), R.mipmap.ic_launcher, message, userInf.getUserHeadImage()));
            } else {
                UserInfo userInf = (UserInfo) imageEnty.getDatalist().get(0);
                mData.add(new Book(userInf.getUserName(), R.mipmap.ic_launcher, message, userInf.getUserHeadImage()));
            }
            if (flagAdapter) {
                if (myAdapter == null) {
                    myAdapter = new MutiLayoutAdapter(context, mData);
                }
                list_content.setAdapter(myAdapter);
                flagAdapter = false;
            }
            myAdapter.notifyDataSetChanged();
            list_content.setSelection(list_content.getCount() - 1);
        }
    }

    public void saveChatRoomMessage(String message) {
        DBOprate dbOprate = new DBOprate(getActivity(), "Msf.db");
        GlobalVeriable globalVeriable = (GlobalVeriable) getActivity().getApplication();
        ChatMessage chatMessage = new ChatMessage();
        chatMessage.setRoomId(globalVeriable.getGameId());
        chatMessage.setUserName(globalVeriable.getUserName());
        chatMessage.setFromName(globalVeriable.getUserName());
        chatMessage.setMessageContent(message);
        chatMessage.setIsRead(1);
        chatMessage.setIsRoomMessage(1);
        dbOprate.saveChatMessage(chatMessage);
    }
}
