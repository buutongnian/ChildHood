package edu.buu.childhood.util;

import android.util.Log;

import org.jivesoftware.smack.SmackException;
import org.jivesoftware.smack.XMPPException;
import org.jivesoftware.smack.chat.Chat;
import org.jivesoftware.smack.chat.ChatManager;
import org.jivesoftware.smack.packet.Presence;
import org.jivesoftware.smack.roster.Roster;
import org.jivesoftware.smack.tcp.XMPPTCPConnection;
import org.jivesoftware.smack.tcp.XMPPTCPConnectionConfiguration;
import org.jivesoftware.smackx.muc.MultiUserChat;
import org.jivesoftware.smackx.muc.MultiUserChatManager;
import org.jivesoftware.smackx.xdata.Form;
import org.jivesoftware.smackx.xdata.FormField;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by tt on 2016/8/29.
 */
public class SmackManager {
    private static final String TAG = "Smack";
    /**
     * Xmpp服务器地址
     */
    public static final String SERVER_IP = "123.57.52.135";
    /**
     * Xmpp 服务器端口
     */
    private static final int PORT = 5222;
    /**
     * 服务器名称
     */
    public static final String SERVER_NAME = "host";
    /**
     *
     */
    public static final String XMPP_CLIENT = "Smack";

    private static SmackManager xmppManager;
    /**
     * 连接
     */
    private XMPPTCPConnection connection;

    private SmackManager() {
        this.connection = connect();
        // Roster roster=getRoster();
        //roster.setSubscriptionMode(Roster.SubscriptionMode.manual);
    }

    /**
     * 获取操作实例
     *
     * @return
     */
    public static SmackManager getInstance() {
        if (xmppManager == null) {
            synchronized (SmackManager.class) {
                if (xmppManager == null) {
                    xmppManager = new SmackManager();
                }
            }
        }
        return xmppManager;
    }

    public XMPPTCPConnection getConnection() {
        return connection;
    }

    /**
     * 连接服务器
     *
     * @return
     */
    private XMPPTCPConnection connect() {
        try {
            XMPPTCPConnectionConfiguration config = XMPPTCPConnectionConfiguration.builder()
                    .setHost(SERVER_IP)//服务器IP地址
                    //服务器端口
                    .setPort(PORT)
                    //服务器名称
                    .setServiceName(SERVER_NAME)
                    //是否开启安全模式
                    .setSecurityMode(XMPPTCPConnectionConfiguration.SecurityMode.disabled)
                    //是否开启压缩
                    .setCompressionEnabled(false)
                    //开启调试模式
                    .setDebuggerEnabled(true).build();

            XMPPTCPConnection connection = new XMPPTCPConnection(config);
            connection.connect();
            return connection;
        } catch (Exception e) {
            return null;
        }
    }


    /**
     * 登陆
     *
     * @param user     用户账号
     * @param password 用户密码
     * @return
     * @throws Exception
     */
    public boolean login(String user, String password) throws Exception {
        if (!isConnected()) {
            return false;
        }
        try {
            connection.login(user, password);
            return true;
        } catch (Exception e) {
            throw e;
        }
    }

    /**
     * 注销
     *
     * @return
     */
    public boolean logout() {
        if (!isConnected()) {
            return false;
        }
        try {
            //AccountManager.getInstance(connection).deleteAccount();
            Presence presence = new Presence(Presence.Type.unavailable);
            connection.sendPacket(presence);
            connection.disconnect();
            connection.instantShutdown();
            xmppManager = null;
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 是否连接成功
     *
     * @return
     */
    private boolean isConnected() {
        if (connection == null) {
            return false;
        }
        if (!connection.isConnected()) {
            try {
                connection.connect();
                return true;
            } catch (SmackException | IOException | XMPPException e) {
                return false;
            }
        }
        return true;
    }

    /**
     * 断开连接，注销
     *
     * @return
     */
    public boolean disconnect() {
        if (!isConnected()) {
            return false;
        }
        connection.disconnect();
        return true;
    }

    /**
     * 获取服务器名称
     *
     * @return
     */
    public String getServiceName() {
        if (isConnected()) {
            return connection.getServiceName();
        }
        return null;
    }

    /**
     * 创建聊天窗口
     *
     * @param jid 好友的JID
     * @return
     */
    public Chat createChat(String jid) {
        if (isConnected()) {
            ChatManager chatManager = ChatManager.getInstanceFor(connection);
            return chatManager.createChat(jid);
        }
        throw new NullPointerException("服务器连接失败，请先连接服务器");
    }

    /**
     * 获取聊天对象管理器
     *
     * @return
     */
    public ChatManager getChatManager() {
        if (isConnected()) {
            ChatManager chatManager = ChatManager.getInstanceFor(connection);
            return chatManager;
        } else {
            return null;
        }
        //throw new NullPointerException("服务器连接失败，请先连接服务器");
    }

    /**
     * 获取群聊聊天对象
     *
     * @return
     */
    public MultiUserChatManager getMultiUserChatManager() {
        if (isConnected()) {
            MultiUserChatManager multiUserChatManager = MultiUserChatManager.getInstanceFor(connection);
            return multiUserChatManager;
        }
        throw new NullPointerException("服务器连接失败，请先连接服务器");
    }

    /**
     * 添加好友
     *
     * @param user      用户账号
     * @param nickName  用户昵称
     * @param groupName 所属组名
     * @return
     */
    public boolean addFriend(String user, String nickName, String groupName) {
        if (isConnected()) {
            try {
                Roster.getInstanceFor(connection).createEntry(user, nickName, new String[]{groupName});
                return true;
            } catch (SmackException.NotLoggedInException | SmackException.NoResponseException
                    | XMPPException.XMPPErrorException | SmackException.NotConnectedException e) {
                return false;
            }
        }
        throw new NullPointerException("服务器连接失败，请先连接服务器");
    }

    /**
     * 创建群聊聊天室
     *
     * @param roomName 聊天室名字
     * @param nickName 创建者在聊天室中的昵称
     * @param password 聊天室密码
     * @return
     */
    public MultiUserChat createChatRoom(String roomName, String nickName, String password) {
        if (!isConnected()) {
            throw new NullPointerException("服务器连接失败，请先连接服务器");
        }
        MultiUserChat muc = null;
        try {
            // 创建一个MultiUserChat
            muc = MultiUserChatManager.getInstanceFor(connection).getMultiUserChat(roomName + "@conference." + connection.getServiceName());
            // 创建聊天室
            boolean isCreated = muc.createOrJoin(nickName);
            if (isCreated) {
                // 获得聊天室的配置表单
                Form form = muc.getConfigurationForm();
                // 根据原始表单创建一个要提交的新表单。
                Form submitForm = form.createAnswerForm();
                // 向要提交的表单添加默认答复
                List<FormField> fields = form.getFields();
                for (int i = 0; fields != null && i < fields.size(); i++) {
                    if (FormField.Type.hidden != fields.get(i).getType() && fields.get(i).getVariable() != null) {
                        // 设置默认值作为答复
                        submitForm.setDefaultAnswer(fields.get(i).getVariable());
                    }
                }
                // 设置聊天室的新拥有者
                List<String> owners = new ArrayList<String>();
                owners.add(connection.getUser());// 用户JID
                submitForm.setAnswer("muc#roomconfig_roomowners", owners);
                // 设置聊天室是持久聊天室，即将要被保存下来
                submitForm.setAnswer("muc#roomconfig_persistentroom", true);
                // 房间仅对成员开放
                submitForm.setAnswer("muc#roomconfig_membersonly", false);
                // 允许占有者邀请其他人
                submitForm.setAnswer("muc#roomconfig_allowinvites", true);
                if (password != null && password.length() != 0) {
                    // 进入是否需要密码
                    submitForm.setAnswer("muc#roomconfig_passwordprotectedroom", true);
                    // 设置进入密码
                    submitForm.setAnswer("muc#roomconfig_roomsecret", password);
                }
                // 能够发现占有者真实 JID 的角色
                // submitForm.setAnswer("muc#roomconfig_whois", "anyone");
                // 登录房间对话
                submitForm.setAnswer("muc#roomconfig_enablelogging", true);
                // 仅允许注册的昵称登录
                submitForm.setAnswer("x-muc#roomconfig_reservednick", true);
                // 允许使用者修改昵称
                submitForm.setAnswer("x-muc#roomconfig_canchangenick", false);
                // 允许用户注册房间
                submitForm.setAnswer("x-muc#roomconfig_registration", false);
                // 发送已完成的表单（有默认值）到服务器来配置聊天室
                muc.sendConfigurationForm(submitForm);
            }
        } catch (XMPPException | SmackException e) {
            e.printStackTrace();
            return null;
        }
        return muc;
    }

    /**
     * 加入一个群聊聊天室
     *
     * @param roomName 聊天室名字
     * @param nickName 用户在聊天室中的昵称
     * @param password 聊天室密码
     * @return
     */
    public MultiUserChat joinChatRoom(String roomName, String nickName, String password) {
        if (!isConnected()) {
            throw new NullPointerException("服务器连接失败，请先连接服务器");
        }
        try {
            // 使用XMPPConnection创建一个MultiUserChat窗口
            MultiUserChat muc = MultiUserChatManager.getInstanceFor(connection).getMultiUserChat(roomName + "@conference." + connection.getServiceName());
            // 聊天室服务将会决定要接受的历史记录数量
          /*  DiscussionHistory history = new DiscussionHistory();
            history.setMaxChars(1);*/
            // history.setSince(new Date());
            // 用户加入聊天室
            muc.join(nickName, password);
            return muc;
        } catch (XMPPException | SmackException e) {
            e.printStackTrace();
            return null;
        }
    }

    public void sendChatRoomMessage(String roomName, String message) {
        if (!isConnected()) {
            throw new NullPointerException("服务器连接失败，请先连接服务器");
        }
        MultiUserChat muc = MultiUserChatManager.getInstanceFor(connection).getMultiUserChat(roomName + "@conference." + connection.getServiceName());
        if (muc != null) {
            try {
                muc.sendMessage(message);
            } catch (SmackException.NotConnectedException e) {
                e.printStackTrace();
            }
        }
    }

    public Roster getRoster() {
        if (isConnected()) {
            Roster roster = Roster.getInstanceFor(connection);
            return roster;
           /* Collection<RosterEntry> entries = roster.getEntries();
            for (RosterEntry entry : entries) {
                Log.i("entry",entry.getName()+" "+entry.getUser()+" "+entry.getGroups());*/
        }
        return null;
    }
}
