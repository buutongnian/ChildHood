package edu.buu.childhood.service;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.Image;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Messenger;
import android.os.RemoteException;
import android.support.v4.app.NotificationCompat;
import android.util.Log;
import android.widget.ImageView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.jivesoftware.smack.ConnectionListener;
import org.jivesoftware.smack.MessageListener;
import org.jivesoftware.smack.PacketCollector;
import org.jivesoftware.smack.SmackException;
import org.jivesoftware.smack.StanzaListener;
import org.jivesoftware.smack.XMPPConnection;
import org.jivesoftware.smack.chat.Chat;
import org.jivesoftware.smack.chat.ChatManager;
import org.jivesoftware.smack.chat.ChatManagerListener;
import org.jivesoftware.smack.chat.ChatMessageListener;
import org.jivesoftware.smack.filter.AndFilter;
import org.jivesoftware.smack.filter.StanzaFilter;
import org.jivesoftware.smack.filter.StanzaTypeFilter;
import org.jivesoftware.smack.packet.Message;
import org.jivesoftware.smack.packet.Presence;
import org.jivesoftware.smack.packet.Stanza;
import org.jivesoftware.smack.roster.Roster;
import org.jivesoftware.smackx.muc.MultiUserChat;
import org.jivesoftware.smackx.vcardtemp.packet.VCard;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

import edu.buu.childhood.R;
import edu.buu.childhood.baidumap.pojo.GameCanJoin;
import edu.buu.childhood.baidumap.pojo.PushMessage;
import edu.buu.childhood.baidumap.pojo.ServiceMessage;
import edu.buu.childhood.chat.activity.ChatRoomActivity;
import edu.buu.childhood.chat.bean.ChatRecords;
import edu.buu.childhood.chat.bean.ChatRecordsList;
import edu.buu.childhood.chat.bean.InviteMessage;
import edu.buu.childhood.common.C;
import edu.buu.childhood.common.ChatMessage;
import edu.buu.childhood.common.GlobalVeriable;
import edu.buu.childhood.database.ChatRecodrsListDao;
import edu.buu.childhood.database.ChatRecordsDao;
import edu.buu.childhood.database.InviteMessgeDao;
import edu.buu.childhood.mainui.activity.Main_interface;
import edu.buu.childhood.my.pojo.UserInfo;
import edu.buu.childhood.util.AsyncImageLoader;
import edu.buu.childhood.util.DBOprate;
import edu.buu.childhood.util.HttpUtil;
import edu.buu.childhood.util.NewCallBack;
import edu.buu.childhood.util.NewNetAsyncTask;
import edu.buu.childhood.util.NotificationCallback;
import edu.buu.childhood.util.SmackManager;
import edu.buu.childhood.util.URLUtil;

/**
 * Created by tt on 2016/8/29.
 */
public class CoreService extends Service implements NewCallBack {
    //绑定标示位
    public static final int MESSAGE_TYPE_REGISTER = 1;
    public static final int MESSAGE_TYPE_TEXT = 2;
    public static final int MESSAGE_TYPE_CHATROOM_FOUNDER = 3;
    public static final int MESSAGE_TYPE_CHATROOM_OTHERS = 4;
    public static final int MESSAGE_TYPE_CHATROMM_SEND = 5;
    //activity中的Messager
    private Messenger mActivityMessage = null;
    // 获取消息线程
    private MessageThread messageThread = null;
    private NotificationCompat.Builder mBuilder;
    private NotificationManager mNotificationManager;
    //获取聊天室对象，通过activity传过来
    private MultiUserChat multiUserChat = null;
    private Boolean isFirstRunning = true;
    private DBOprate dbOprate;
    private Boolean isFirstReceive = true;
    private Timer exitTimer;

    private AsyncImageLoader asyncImageLoader;
    private String currentMessage;

    private Gson json = new Gson();


    public IBinder onBind(Intent intent) {
        Messenger messenger = new Messenger(handler);
        return messenger.getBinder();
    }

    Handler handler = new Handler() {
        @Override
        public void handleMessage(android.os.Message msg) {
            Bundle bundleData = msg.getData();
            switch (msg.what) {
                case MESSAGE_TYPE_TEXT:
                    if (bundleData != null) {

                    }
                    break;
                case MESSAGE_TYPE_REGISTER:
                    mActivityMessage = msg.replyTo;
                    break;
                case MESSAGE_TYPE_CHATROOM_FOUNDER:
                    if (bundleData != null) {
                        ArrayList<CharSequence> creatChatRoomData = bundleData.getCharSequenceArrayList("data");
                        String roomName = null, userName = null, password = null;
                        roomName = creatChatRoomData.get(0).toString();
                        userName = creatChatRoomData.get(1).toString();
                        if (roomName != null & userName != null) {
                            Log.i("sss", roomName + "" + userName);
                            ChatRoomMessageThread chatRoomMessageThread = new ChatRoomMessageThread(roomName, userName, true);
                            chatRoomMessageThread.start();
                        }
                    }
                    break;
                case MESSAGE_TYPE_CHATROOM_OTHERS:
                    if (bundleData != null) {
                        ArrayList<CharSequence> creatChatRoomData = bundleData.getCharSequenceArrayList("data");
                        String roomName = null, userName = null, password = null;
                        roomName = creatChatRoomData.get(0).toString();
                        userName = creatChatRoomData.get(1).toString();
                        if (roomName != null & roomName != null) {
                            //multiUserChat=SmackManager.getInstance().joinChatRoom(roomName,userName,password);
                            ChatRoomMessageThread chatRoomMessageThread = new ChatRoomMessageThread(roomName, userName, false);
                            chatRoomMessageThread.start();
                        }
                    }
                    break;
                case MESSAGE_TYPE_CHATROMM_SEND:
                    ArrayList<CharSequence> chatRoomMessage = bundleData.getCharSequenceArrayList("data");
                    String roomName = null, message = null;
                    roomName = chatRoomMessage.get(0).toString();
                    message = chatRoomMessage.get(1).toString();
                    Log.i("userName",roomName+message);
                    if (roomName != null && message != null) {
                        SmackManager.getInstance().sendChatRoomMessage(roomName, message);
                    }
                    break;
                default:
                    super.handleMessage(msg);
            }
        }
    };

    //向activity发送消息
    void sendToActivity(ServiceMessage serviceMessage) {
        if (mActivityMessage == null) {
        } else {
            Bundle data = new Bundle();
            data.putSerializable("serviceMessage", serviceMessage);
            android.os.Message msg = android.os.Message.obtain(null, MESSAGE_TYPE_TEXT);
            msg.setData(data);
            try {
                mActivityMessage.send(msg);
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void onCreate() {
        /** 初始化通知栏 */
        dbOprate = new DBOprate(this, "Msf.db");
        mNotificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        mBuilder = new NotificationCompat.Builder(this);
        // 开启线程
        asyncImageLoader = new AsyncImageLoader(this);
        messageThread = new MessageThread();
        messageThread.start();
        super.onCreate();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        //只有游戏进行中才监听聊天室信息
        if (((GlobalVeriable) getApplication()).getGameRunning()) {
            ChatRoomMessageThread chatRoomMessageThread = new ChatRoomMessageThread(String.valueOf(((GlobalVeriable) getApplication()).getGameId()), ((GlobalVeriable) getApplication()).getUserName(), false);
            chatRoomMessageThread.start();
            isFirstRunning = false;
        }
        return super.onStartCommand(intent, flags, startId);
    }

    //通过setContentIntent(PendingIntent intent)方法中的意图设置对应的flags
    public PendingIntent getDefalutIntent(String userName, String userNickname) {
        Intent intent = new Intent(CoreService.this, ChatRoomActivity.class);
        intent.putExtra("userName", userName);
        intent.putExtra("userNick", userNickname);
        ((GlobalVeriable) getApplication()).setToChatingUser(userName);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 1, intent, PendingIntent.FLAG_ONE_SHOT);
        return pendingIntent;
    }

    @Override
    public void getResult(String jsonStr) {
        if (jsonStr != null) {
            edu.buu.childhood.common.Message reciveMsg = json.fromJson(jsonStr, new TypeToken<edu.buu.childhood.common.Message<?>>() {
            }.getType());
            if (reciveMsg.getMessageCode() != null && reciveMsg.getMessageCode() instanceof String) {
                switch (reciveMsg.getMessageCode()) {
                    case C.convene.GET_USER_INFO_SUCCESS:
                        reciveMsg = json.fromJson(jsonStr, new TypeToken<edu.buu.childhood.common.Message<UserInfo>>() {
                        }.getType());
                        UserInfo userInfo = ((edu.buu.childhood.common.Message<UserInfo>) reciveMsg).getMessageContent();
                        mBuilder.setContentTitle(userInfo.getUserNickname())
                                .setContentText(currentMessage)
                                .setSmallIcon(R.drawable.logo_32px)
                                .setLargeIcon(asyncImageLoader.loadImage(userInfo.getUserHeadImage()))
                                .setAutoCancel(true)
                                .setContentIntent(getDefalutIntent(userInfo.getUserName(), userInfo.getUserNickname()))
                                .setTicker(userInfo.getUserNickname() + "\n" + currentMessage);//通知首次出现在通知栏，带上升动画效果的
                        mNotificationManager.notify(100, mBuilder.build());
                        break;
                    default:
                        break;
                }
            }
        }
    }

    @Override
    public String doInBackground(String... url) {
        HttpUtil httpUtil = new HttpUtil(url[0]);
        byte[] bytes = httpUtil.getHttpData();
        if (bytes != null) {
            return new String(bytes);
        }
        return null;
    }

    /**
     * 从服务器端获取消息
     */
    class MessageThread extends Thread {
        public void run() {
            try {
                //连接openfire并登陆
                if (((GlobalVeriable) getApplication()).getUserName() != null && ((GlobalVeriable) getApplication()).getPassword() != null) {
                    SmackManager.getInstance().login(((GlobalVeriable) getApplication()).getUserName(), ((GlobalVeriable) getApplication()).getPassword());
                }
                Roster roster = SmackManager.getInstance().getRoster();
                roster.setSubscriptionMode(Roster.SubscriptionMode.manual);
                //条件过滤
                StanzaFilter filter = new AndFilter(new StanzaTypeFilter(Presence.class));
                PacketCollector myCollector = SmackManager.getInstance().getConnection().createPacketCollector(filter);
                SmackManager.getInstance().getConnection().addAsyncStanzaListener(packetListener, filter);
                SmackConnectListener smackConnectListener = new SmackConnectListener();
                SmackManager.getInstance().getConnection().addConnectionListener(smackConnectListener);
                //设置聊天监听器，监听聊天消息
                ChatManager chatm = SmackManager.getInstance().getChatManager();
                if (chatm != null) {
                    chatm.addChatListener(new ChatManagerListener() {
                                              @Override
                                              public void chatCreated(Chat chat, boolean b) {
                                                  if (!b) {           //不是本地创建的会话
                                                      Log.d("CoreService", "CoreService step3");
                                                      chat.addMessageListener(new ChatMessageListener() {
                                                                                  @Override
                                                                                  public void processMessage(Chat chat, Message message) {
                                                                                      if (message.getBody() != null) {
                                                                                          String from = message.getFrom();
                                                                                          //是否是系统推送消息
                                                                                          if ("pushmaster".equals(from.substring(0, from.indexOf("@")))) {
                                                                                              ServiceMessage serviceMessage = new ServiceMessage(from.substring(0, from.indexOf("@")), message.getBody());
                                                                                              Gson json = new Gson();
                                                                                              edu.buu.childhood.common.Message messagePushmaster = json.fromJson(message.getBody(), new TypeToken<edu.buu.childhood.common.Message>() {
                                                                                              }.getType());
                                                                                              //是不是系统推送邀请消息
                                                                                              if (C.push.GAME_INVITE.equals(messagePushmaster.getMessageCode())) {
                                                                                                  //判断推送消息接收页面是否运行
                                                                                                  if (((GlobalVeriable) getApplication()).getRunningNewMessage()) {
                                                                                                      sendToActivity(serviceMessage);
                                                                                                  } else {
                                                                                                      edu.buu.childhood.common.Message<GameCanJoin> pushStatusMessage = json.fromJson(message.getBody(), new TypeToken<edu.buu.childhood.common.Message<GameCanJoin>>() {
                                                                                                      }.getType());
                                                                                                      GameCanJoin gameCanJoin = pushStatusMessage.getMessageContent();
                                                                                                      PushMessage pushMessage = new PushMessage();
                                                                                                      pushMessage.setUserName(((GlobalVeriable) getApplication()).getUserName());
                                                                                                      pushMessage.setGameId(gameCanJoin.getGameId());
                                                                                                      pushMessage.setGameTitle(gameCanJoin.getGameTitle());
                                                                                                      pushMessage.setGameFounder(gameCanJoin.getGameFounder());
                                                                                                      pushMessage.setFounderNickName(gameCanJoin.getFounderNickname());
                                                                                                      pushMessage.setGatherPlace(gameCanJoin.getGatherPlace());
                                                                                                      pushMessage.setCustomInf(gameCanJoin.getCustomInf());
                                                                                                      pushMessage.setCustomCount(gameCanJoin.getCustomCount());
                                                                                                      pushMessage.setIsRead(0);
                                                                                                      dbOprate.savePushMessage(pushMessage);
                                                                                                  }//推送消息界面if
                                                                                              } else {
                                                                                                  if (((GlobalVeriable) getApplication()).getRunnigMainBaidu()) {
                                                                                                      sendToActivity(serviceMessage);
                                                                                                  } else {
                                                                                                      ChatMessage chatMessage = new ChatMessage();
                                                                                                      chatMessage.setRoomId(((GlobalVeriable) getApplication()).getGameId());
                                                                                                      chatMessage.setUserName(((GlobalVeriable) getApplication()).getUserName());
                                                                                                      chatMessage.setFromName("pushmaster");
                                                                                                      chatMessage.setIsRead(0);
                                                                                                      chatMessage.setIsRoomMessage(1);
                                                                                                      edu.buu.childhood.common.Message<String> messageContent = json.fromJson(message.getBody(), new TypeToken<edu.buu.childhood.common.Message<String>>() {
                                                                                                      }.getType());
                                                                                                      switch (messagePushmaster.getMessageCode()) {
                                                                                                          case C.push.GAME_CANCEL:
                                                                                                              chatMessage.setMessageContent(messageContent.getMessageContent() + "取消游戏");
                                                                                                              ((GlobalVeriable) getApplication()).setGameRunning(false);
                                                                                                              break;
                                                                                                          case C.push.GAME_INFO_CHANGED:
                                                                                                              chatMessage.setMessageContent("创建者更改游戏信息");
                                                                                                              break;
                                                                                                          case C.push.GAME_START:
                                                                                                              chatMessage.setMessageContent("开始游戏");
                                                                                                              break;
                                                                                                          case C.push.GAME_FINISHED:
                                                                                                              ((GlobalVeriable) getApplication()).setGameRunning(false);
                                                                                                              chatMessage.setMessageContent("完成游戏");
                                                                                                              break;
                                                                                                          case C.push.NEW_COMMER:
                                                                                                              chatMessage.setMessageContent(messageContent.getMessageContent() + "加入游戏");
                                                                                                              break;
                                                                                                          case C.push.USER_EXIT:
                                                                                                              chatMessage.setMessageContent(messageContent.getMessageContent() + "退出游戏");
                                                                                                              break;
                                                                                                      }
                                                                                                      dbOprate.saveChatMessage(chatMessage);
                                                                                                  }//chatRoom是否运行if
                                                                                              }//系统推送消息if
                                                                                          } else {
                                                                                              //私人聊天消息
                                                                                              String fromUser = from.substring(0, from.indexOf("@"));
                                                                                              String messageBody = message.getBody();
                                                                                              //存储消息记录
                                                                                              int id = saveChatRecords(fromUser, messageBody);
                                                                                              //发送广播
                                                                                              Intent intent = new Intent("CHATMESSAGE");
                                                                                              intent.putExtra("fromUser", fromUser);
                                                                                              intent.putExtra("message", messageBody);
                                                                                              intent.putExtra("msgId", id);
                                                                                              sendBroadcast(intent);//传递过去
                                                                                              //存储到聊天记录列表信息
                                                                                              saveChatRecordsList(fromUser, messageBody);
                                                                                              ((GlobalVeriable) getApplication()).setHaveMessage(true);
                                                                                              currentMessage = message.getBody();
                                                                                              Map<String, String> args = new HashMap<String, String>();
                                                                                              args.put("userName", ((GlobalVeriable) getApplication()).getUserName());
                                                                                              args.put("targetUser", from.substring(0, from.indexOf("@")));
                                                                                              String userInfoUrl = URLUtil.getURL("getUserInfo", args);
                                                                                              new NewNetAsyncTask(CoreService.this).execute(userInfoUrl);
                                                                                              android.os.Message messgae = new android.os.Message();
                                                                                              Bundle bundle = new Bundle();
                                                                                              bundle.putString("handleEvent", "changeMessageTip");
                                                                                              messgae.setData(bundle);
                                                                                              Main_interface.getMainHandler().handleMessage(messgae);
                                                                                          }//私人聊天监听if
                                                                                      }
                                                                                  }
                                                                              }
                                                      );
                                                  }
                                              }
                                          }
                    );
                }
            } catch (Exception e) {
                Log.e("error", "CoreService", e);
                e.printStackTrace();
            }
        }
    }

    @Override
    public void onDestroy() {
        SmackManager.getInstance().logout();
        super.onDestroy();
    }

    //监听群组聊天的消息线程
    class ChatRoomMessageThread extends Thread {
        private String roomName;
        private String userName;
        private Boolean flag = false;

        public ChatRoomMessageThread(String roomName, String userName, Boolean flag) {
            this.roomName = roomName;
            this.userName = userName;
            this.flag = flag;
        }

        @Override
        public void run() {
            try {
                SmackManager.getInstance().login(((GlobalVeriable) getApplication()).getUserName(), ((GlobalVeriable) getApplication()).getPassword());
            } catch (Exception e) {
                e.printStackTrace();
            }
            if (flag) {
                multiUserChat = SmackManager.getInstance().createChatRoom(roomName, userName, null);
            } else {
                multiUserChat = SmackManager.getInstance().joinChatRoom(roomName, userName, null);
            }
            if (multiUserChat != null) {
                multiUserChat.addMessageListener(new MessageListener() {
                    @Override
                    public void processMessage(Message message) {
                        GlobalVeriable globalVeriable = (GlobalVeriable) getApplication();
                        //百度activty是否在启动中
                        if (globalVeriable.getRunnigMainBaidu()) {
                            String from = message.getFrom();
                            ServiceMessage serviceMessage = new ServiceMessage(from.substring(from.indexOf("/") + 1), message.getBody());
                            sendToActivity(serviceMessage);
                           /* mBuilder.setContentTitle(from.substring(0, from.indexOf("@")))
                                    .setContentText(message.getBody())
                                    // .setContentIntent(pendingIntent)
                                    .setTicker("测试通知来啦");//通知首次出现在通知栏，带上升动画效果的
                            mNotificationManager.notify(100, mBuilder.build());*/
                        } else {
                            String from = message.getFrom();
                            if (globalVeriable.getFirstReceiveChatRoomMessage()) {
                                dbOprate.deleteMuliChat(globalVeriable.getUserName(), Integer.parseInt(from.substring(0, from.indexOf("@"))));
                                globalVeriable.setFirstReceiveChatRoomMessage(false);
                            }
                            //Log.i("from",from.substring(0,from.indexOf("@")));
                            ChatMessage chatMessage = new ChatMessage();
                            chatMessage.setRoomId(Integer.parseInt(from.substring(0, from.indexOf("@"))));
                            chatMessage.setUserName(((GlobalVeriable) getApplication()).getUserName());
                            chatMessage.setFromName(from.substring(from.indexOf("/") + 1));
                            chatMessage.setMessageContent(message.getBody());
                            chatMessage.setIsRead(0);
                            chatMessage.setIsRoomMessage(1);
                            dbOprate.saveChatMessage(chatMessage);
                            if (!isFirstReceive) {
                            } else {
                                isFirstReceive = false;
                            }
                        }

                    }
                });
            }
            super.run();
        }
    }

    StanzaListener packetListener = new StanzaListener() {
        @Override
        public void processPacket(Stanza stanza) throws SmackException.NotConnectedException {
            if (stanza instanceof Presence) {
                Presence presence = (Presence) stanza;
                String fromUser = presence.getFrom();//发送方
                fromUser = fromUser.substring(0, fromUser.indexOf("@"));
                String userName = presence.getTo();//接收方
                userName = userName.substring(0, userName.indexOf("@"));
                InviteMessage inviteMessage = new InviteMessage();
                inviteMessage.setUserName(userName);
                inviteMessage.setFrom(fromUser);
                //inviteMessage.setStatus();
                InviteMessgeDao inviteMessgeDao = new InviteMessgeDao(CoreService.this);
                if (presence.getType().equals(Presence.Type.subscribe)) {
                    inviteMessage.setReason("对方请求加为好友");
                    inviteMessage.setStatus(InviteMessage.InviteMesageStatus.BEAPPLYED);
                    //添加好友请求
                    if (!inviteMessgeDao.haveInviteMessage(userName, fromUser)) {
                        //添加好友请求
                        inviteMessgeDao.saveMessage(inviteMessage);
                    }
                } else {
                    if (presence.getType().equals(Presence.Type.subscribed)) {
                        //对方同意加为好友
                        inviteMessage.setReason("同意加为好友");
                        inviteMessage.setStatus(InviteMessage.InviteMesageStatus.BEAGREED);
                        inviteMessgeDao.updateMessage(inviteMessage);
                    } else if (presence.getType().equals(Presence.Type.unsubscribe)) {
                        //对方拒绝加为好友
                        inviteMessage.setReason("拒绝加为好友");
                        inviteMessage.setStatus(InviteMessage.InviteMesageStatus.BEREFUSED);
                        inviteMessgeDao.updateMessage(inviteMessage);
                    }
                }
            }
        }
    };


    public Integer saveChatRecords(String fromUser, String message) {
        ChatRecords chatRecords = new ChatRecords();
        chatRecords.setUserName(((GlobalVeriable) getApplication()).getUserName());
        chatRecords.setFromUser(fromUser);
        chatRecords.setMessageContent(message);
        chatRecords.setIsRead(0);
        chatRecords.setIsSelf(0);
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        String date = dateFormat.format(new Date(System.currentTimeMillis()));
        chatRecords.setMessageTime(date);
        ChatRecordsDao chatRecordsDao = new ChatRecordsDao(this);
        int id = chatRecordsDao.saveChatMessage(chatRecords);
        String userName = ((GlobalVeriable) getApplication()).getUserName();
        ChatRecordsList chatRecordsList = new ChatRecordsList();
        chatRecordsList.setFromUser(fromUser);
        chatRecordsList.setUserName(userName);
        chatRecordsList.setLastMessage(message);
        chatRecordsList.setLastTime(date);
        ChatRecodrsListDao chatRecodrsListDao = new ChatRecodrsListDao(this);
        if (chatRecodrsListDao.isHaveRecord(userName, fromUser)) {
            chatRecodrsListDao.updateChatRecordsList(chatRecordsList);
        } else {
            chatRecodrsListDao.saveChatRecordsList(chatRecordsList);
        }
        return id;
    }

    public void saveChatRecordsList(String fromUser, String message) {
        GlobalVeriable globalVeriable = (GlobalVeriable) getApplication();
        ChatRecodrsListDao chatRecodrsListDao = new ChatRecodrsListDao(this);
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String date = dateFormat.format(new Date(System.currentTimeMillis()));
        if (!chatRecodrsListDao.isHaveRecord(globalVeriable.getUserName(), fromUser)) {
            ChatRecordsList chatRecordsList = new ChatRecordsList();
            chatRecordsList.setFromUser(fromUser);
            chatRecordsList.setUserName(globalVeriable.getUserName());
            chatRecordsList.setLastMessage(message);
            chatRecordsList.setLastTime(date);
            chatRecodrsListDao.saveChatRecordsList(chatRecordsList);
        } else {
            ChatRecordsList chatRecordsList = new ChatRecordsList();
            chatRecordsList.setFromUser(fromUser);
            chatRecordsList.setUserName(globalVeriable.getUserName());
            chatRecordsList.setLastMessage(message);
            chatRecordsList.setLastTime(date);
            chatRecodrsListDao.updateChatRecordsList(chatRecordsList);
        }
    }

    private class SmackConnectListener implements ConnectionListener {

        @Override
        public void connected(XMPPConnection xmppConnection) {

        }

        @Override
        public void authenticated(XMPPConnection xmppConnection, boolean b) {

        }

        @Override
        public void connectionClosed() {

        }

        @Override
        public void connectionClosedOnError(Exception e) {
            exitTimer = new Timer();
            exitTimer.schedule(new timetask(), 2000);
        }

        @Override
        public void reconnectionSuccessful() {

        }

        @Override
        public void reconnectingIn(int i) {

        }

        @Override
        public void reconnectionFailed(Exception e) {

        }
    }

    class timetask extends TimerTask {
        @Override
        public void run() {
            GlobalVeriable globalVeriable = (GlobalVeriable) getApplication();
            if (globalVeriable.getUserName() != null && globalVeriable.getPassword() != null) {
                // 连接服务器
                try {
                    if (SmackManager.getInstance().login(globalVeriable.getUserName(), globalVeriable.getPassword())) {
                    } else {
                        exitTimer.schedule(new timetask(), 2000);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
