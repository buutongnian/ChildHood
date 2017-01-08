package edu.buu.childhood.baidumap.activity;

/**
 * Created by lcc on 2016/7/2.
 */

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.Messenger;
import android.os.RemoteException;
import android.view.View;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import edu.buu.childhood.R;
import edu.buu.childhood.baidumap.fragment.ChatRoom;
import edu.buu.childhood.baidumap.fragment.NewMessage;
import edu.buu.childhood.baidumap.fragment.baiduMap;
import edu.buu.childhood.baidumap.pojo.GameCanJoin;
import edu.buu.childhood.baidumap.pojo.NewMessage_ItemBean;
import edu.buu.childhood.baidumap.pojo.PushMessage;
import edu.buu.childhood.baidumap.pojo.ServiceMessage;
import edu.buu.childhood.common.C;
import edu.buu.childhood.common.GlobalVeriable;
import edu.buu.childhood.common.activity.BaseAvtivity;
import edu.buu.childhood.service.CoreService;
import edu.buu.childhood.util.CloseActivity;
import edu.buu.childhood.util.DBOprate;

public class MainBaidu extends BaseAvtivity {
    private RadioGroup rg;
    private RadioButton baidu;
    //Fragment Object
    private baiduMap fg2;
    private static ChatRoom fg1;
    private NewMessage fg3;
    private FragmentManager fManager;
    Messenger messenger = null;
    boolean mServiceConnected = false;
    private Messenger mServiceMessager = null;
    private String userName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        CloseActivity.activityList.add(this);
        setContentView(R.layout.main_baidu);
        fg1 = new ChatRoom();
        fg2 = new baiduMap();
        fg3 = new NewMessage();
        //设置activty启动标志位
        final GlobalVeriable globalVeriable = (GlobalVeriable) getApplication();
        fManager = getFragmentManager();
        Map mapUrl = new HashMap();
        if (globalVeriable.getLogin()) {
            if (globalVeriable.getGameRunning()) {
                addChatRoomFragement();
            } else {
                //fg3.initData(((GlobalVeriable) getApplication()).getInstance());
                fManager.beginTransaction().add(R.id.main_baidu_fl, fg3).commit();
                fManager.beginTransaction().add(R.id.main_baidu_fl, fg2).commit();
            }
            Intent intent = new Intent();
            intent.setAction("CoreService");
            intent.setPackage(getPackageName());
            bindService(intent, connection, Context.BIND_AUTO_CREATE);
        } else {
            fManager.beginTransaction().add(R.id.main_baidu_fl, fg3).commit();
            fManager.beginTransaction().add(R.id.main_baidu_fl, fg2).commit();

        }
        rg = (RadioGroup) findViewById(R.id.main_baidu_rg);
        baidu = (RadioButton) findViewById(R.id.main_baidu_rg_map);
        baidu.setChecked(true);
        rg.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
                                          @Override
                                          public void onCheckedChanged(RadioGroup group, int checkedId) {
                                              FragmentTransaction fTransaction = fManager.beginTransaction();
                                              hideAllFragment(fTransaction);
                                              switch (checkedId) {
                                                  case R.id.main_baidu_rg_chat:
                                                      if (fg1 == null) {
                                                          fg1 = new ChatRoom();
                                                          fTransaction.add(R.id.main_baidu_fl, fg1);
                                                      } else {
                                                          fTransaction.show(fg1);
                                                      }
                                                      break;
                                                  case R.id.main_baidu_rg_map:
                                                      if (fg2 == null) {
                                                          fg2 = new baiduMap();
                                                          fTransaction.add(R.id.main_baidu_fl, fg2);
                                                      } else {
                                                          fTransaction.show(fg2);
                                                      }
                                                      break;
                                                  case R.id.main_baidu_rg_info:
                                                      if (fg3 == null) {
                                                          fg3 = new NewMessage();
                                                          fTransaction.add(R.id.main_baidu_fl, fg3);
                                                      } else {
                                                          fTransaction.show(fg3);
                                                      }
                                                      break;

                                              }
                                              fTransaction.commit();
                                          }
                                      }
        );
    }

    //隐藏所有Fragment
    private void hideAllFragment(FragmentTransaction fragmentTransaction) {
        if (fg1 != null) fragmentTransaction.hide(fg1);
        if (fg2 != null) fragmentTransaction.hide(fg2);
        if (fg3 != null) fragmentTransaction.hide(fg3);
    }

    //服务绑定
    private ServiceConnection connection = new ServiceConnection() {
        @Override
        public void onServiceDisconnected(ComponentName name) {
            messenger = null;
            mServiceConnected = false;
        }

        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            mServiceMessager = new Messenger(service);
            mServiceConnected = true;
            messenger = new Messenger(mhandler);
            Message msg = Message.obtain(null, CoreService.MESSAGE_TYPE_REGISTER);
            msg.replyTo = messenger;
            try {
                mServiceMessager.send(msg);
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }
    };
    Handler mhandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            if (msg.what == CoreService.MESSAGE_TYPE_TEXT) {
                Gson json = new Gson();
                Bundle b = msg.getData();
                ServiceMessage serviceMessage = null;
                if (b != null) {
                    serviceMessage = (ServiceMessage) b.getSerializable("serviceMessage");
                    //判断属于系统推送还是聊天消息
                    if (serviceMessage.getUserName().equals("pushmaster")) {
                        edu.buu.childhood.common.Message message = json.fromJson(serviceMessage.getBody(), new TypeToken<edu.buu.childhood.common.Message>() {
                        }.getType());
                        if (C.push.GAME_INVITE.equals(message.getMessageCode())) {
                            edu.buu.childhood.common.Message<GameCanJoin> pushMessage = json.fromJson(serviceMessage.getBody(), new TypeToken<edu.buu.childhood.common.Message<GameCanJoin>>() {
                            }.getType());
                            if (pushMessage.getMessageContent() != null) {
                                GameCanJoin gameCanJoin = (GameCanJoin) pushMessage.getMessageContent();
                                fg3.updateNewMesageAdapter(new NewMessage_ItemBean(gameCanJoin.getGameId(), gameCanJoin.getGameIcon(), gameCanJoin.getGameTitle(), gameCanJoin.getFounderNickname(), gameCanJoin.getGatherPlace()));
                                //存储到数据库中以便查看
                                PushMessage pushMessageStore = new PushMessage();
                                pushMessageStore.setUserName(((GlobalVeriable) getApplication()).getUserName());
                                pushMessageStore.setGameId(gameCanJoin.getGameId());
                                pushMessageStore.setGameTitle(gameCanJoin.getGameTitle());
                                pushMessageStore.setGameFounder(gameCanJoin.getGameFounder());
                                pushMessageStore.setFounderNickName(gameCanJoin.getFounderNickname());
                                pushMessageStore.setGatherPlace(gameCanJoin.getGatherPlace());
                                pushMessageStore.setCustomInf(gameCanJoin.getCustomInf());
                                pushMessageStore.setCustomCount(gameCanJoin.getCustomCount());
                                pushMessageStore.setIsRead(0);
                                DBOprate dbOprate = new DBOprate(getApplication(), "Msf.db");
                                dbOprate.savePushMessage(pushMessageStore);
                            }
                        } else {
                            edu.buu.childhood.common.Message messageContent = json.fromJson(serviceMessage.getBody(), new TypeToken<edu.buu.childhood.common.Message>() {
                            }.getType());
                            switch (message.getMessageCode()) {
                                case C.push.GAME_CANCEL:
                                    fg1.updateData(messageContent.getMessageContent().toString() + "取消游戏", null, 2, ((GlobalVeriable) getApplication()).getInstance());
                                    ((GlobalVeriable) getApplication()).setGameRunning(false);
                                    refresh();
                                    break;
                                case C.push.GAME_INFO_CHANGED:
                                    fg1.updateData("游戏内容更新", null, 2, ((GlobalVeriable) getApplication()).getInstance());
                                    break;
                                case C.push.GAME_START:
                                    fg1.updateData("游戏开始", null, 2, ((GlobalVeriable) getApplication()).getInstance());
                                    break;
                                case C.push.GAME_FINISHED:
                                    fg1.updateData("游戏结束", null, 2, ((GlobalVeriable) getApplication()).getInstance());
                                    ((GlobalVeriable) getApplication()).setGameRunning(false);
                                    refresh();
                                    break;
                                case C.push.NEW_COMMER:
                                    if (fg1 != null) {
                                        if (((GlobalVeriable) getApplication()).getMySelf()) {
                                            ((GlobalVeriable) getApplication()).setMySelf(false);
                                        } else {
                                            fg1.updateData(messageContent.getMessageContent().toString() + "加入游戏", null, 2, ((GlobalVeriable) getApplication()).getInstance());
                                        }
                                    }
                                    break;
                                case C.push.USER_EXIT:
                                    fg1.updateData(messageContent.getMessageContent().toString() + "退出游戏", null, 2, ((GlobalVeriable) getApplication()).getInstance());
                                    break;
                            }
                        }
                    } else {
                        fg1.updateData(serviceMessage.getBody(), serviceMessage.getUserName(), 1, ((GlobalVeriable) getApplication()).getInstance());
                    }
                } else {
                    serviceMessage = null;
                }
               /* final TextView responseFromService = (TextView) findViewById(R.id.responseFromService);
                responseFromService.setText(text);*/
            } else {
                super.handleMessage(msg);
            }
        }
    };

    public void sendToService(ArrayList<CharSequence> message, int messageType) {
        if (mServiceConnected) {
            Bundle b = new Bundle();
            Message msg = null;
            switch (messageType) {
                case CoreService.MESSAGE_TYPE_TEXT:
                    msg = Message.obtain(null,
                            CoreService.MESSAGE_TYPE_TEXT);
                    break;
                case CoreService.MESSAGE_TYPE_CHATROOM_FOUNDER:
                    msg = Message.obtain(null,
                            CoreService.MESSAGE_TYPE_CHATROOM_FOUNDER);

                    break;
                case CoreService.MESSAGE_TYPE_CHATROOM_OTHERS:
                    msg = Message.obtain(null,
                            CoreService.MESSAGE_TYPE_CHATROOM_OTHERS);
                    break;
                case CoreService.MESSAGE_TYPE_CHATROMM_SEND:
                    msg = Message.obtain(null, CoreService.MESSAGE_TYPE_CHATROMM_SEND);
            }
            //发送消息
            b.putCharSequenceArrayList("data", message);
            msg.setData(b);
            try {
                mServiceMessager.send(msg);
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        } else {
        }
    }

    @Override
    protected void onDestroy() {
        if (mServiceConnected) {
            unbindService(connection);
        }
        super.onDestroy();
    }

    public void addChatRoomFragement() {
        RadioButton radioButton = (RadioButton) findViewById(R.id.main_baidu_rg_chat);
        radioButton.setVisibility(View.VISIBLE);
        //fg3.initData(((GlobalVeriable) getApplication()).getInstance());
        fManager.beginTransaction().add(R.id.main_baidu_fl, fg3).commit();
        fManager.beginTransaction().add(R.id.main_baidu_fl, fg1).commit();
        fManager.beginTransaction().add(R.id.main_baidu_fl, fg2).commit();
        // fg1.initData(((GlobalVeriable) getApplication()).getInstance());
        GlobalVeriable globalVeriable = (GlobalVeriable) getApplication();
        globalVeriable.setGameRunning(true);
    }

    public void refresh() {
        finish();
        Intent intent = new Intent(this, MainBaidu.class);
        startActivity(intent);
    }

    @Override
    protected void onStart() {
        Intent intent = getIntent();
        String flag = intent.getStringExtra("flag");
        if ("flag".equals(flag)) {
            refresh();
        }
        super.onStart();
    }

    @Override
    protected void onNewIntent(Intent intent) {
        String flag = intent.getStringExtra("flag");
        if ("flag".equals(flag)) {
            refresh();
        }
        super.onNewIntent(intent);
    }

    @Override
    protected void onResume() {
        if (!((GlobalVeriable) getApplication()).getGameRunning()) {
            fManager.beginTransaction().remove(fg1);
            fManager.beginTransaction().addToBackStack(null);
            fManager.beginTransaction().commit();
        }
        super.onResume();
    }
}