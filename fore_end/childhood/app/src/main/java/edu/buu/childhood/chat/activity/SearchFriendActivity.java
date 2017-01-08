package edu.buu.childhood.chat.activity;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.ProgressBar;
import android.widget.SearchView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.jivesoftware.smack.roster.Roster;
import org.jivesoftware.smack.roster.RosterEntry;
import org.jivesoftware.smack.roster.packet.RosterPacket;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

import edu.buu.childhood.R;
import edu.buu.childhood.common.C;
import edu.buu.childhood.common.CallBackPage;
import edu.buu.childhood.common.GlobalVeriable;
import edu.buu.childhood.common.Message;
import edu.buu.childhood.common.activity.BaseAvtivity;
import edu.buu.childhood.common.activity.UserInfoActivity;
import edu.buu.childhood.my.pojo.UserInfo;
import edu.buu.childhood.util.CallBack;
import edu.buu.childhood.util.CloseActivity;
import edu.buu.childhood.util.HttpUtil;
import edu.buu.childhood.util.NetAsyncTask;
import edu.buu.childhood.util.SmackManager;
import edu.buu.childhood.util.URLUtil;
import edu.buu.childhood.view.LoadDialog;

public class SearchFriendActivity extends BaseAvtivity implements CallBack {
    private ProgressBar lodingProBar;
    private SearchView searchView;
    private TextView promptText;
    private Dialog loadDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        CloseActivity.activityList.add(this);
        setContentView(R.layout.activity_search_friend);
        promptText = (TextView) findViewById(R.id.search_friend_message);
        lodingProBar = (ProgressBar) findViewById(R.id.seach_friend_loding);
        searchView = (SearchView) findViewById(R.id.search_friend_search);
        loadDialog = showDialog(this);

        int id = searchView.getContext().getResources().getIdentifier("android:id/search_src_text", null, null);
        //获取到TextView的控件
        TextView searchText = (TextView) searchView.findViewById(id);
        //设置字体颜色
        searchText.setTextColor(Color.parseColor("#ffffff"));
        //设置提示文字颜色
        searchText.setHintTextColor(Color.parseColor("#ffffff"));

        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE | WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE);

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                loadDialog.show();
                Map map = new HashMap();
                map.put("userName", ((GlobalVeriable) getApplication()).getUserName());
                map.put("targetUser", query);
                new NetAsyncTask(SearchFriendActivity.this).execute(URLUtil.getURL("getUserInfo", map));
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });
    }

    public Dialog showDialog(Context context) {
        Dialog dialog;
        dialog = new LoadDialog(context, R.style.Dialog);
        dialog.setCanceledOnTouchOutside(false);
        return dialog;
    }

    @Override
    public void getResult(CallBackPage result) {
        if (result != null) {
            UserInfo userInf = (UserInfo) result.getDatalist().get(0);
            Intent intent = new Intent(SearchFriendActivity.this, UserInfoActivity.class);
            intent.putExtra("userName", userInf.getUserName());
            intent.putExtra("userNickName", userInf.getUserNickname());
            intent.putExtra("imageUrl", userInf.getUserHeadImage());
            startActivity(intent);
            loadDialog.dismiss();
        } else {
            loadDialog.dismiss();
            promptText.setVisibility(View.VISIBLE);
        }
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

    public Boolean getIsFriend(String userName) {
        Roster roster = SmackManager.getInstance().getRoster();
        RosterEntry rosterEntry = roster.getEntry(userName + "@" + SmackManager.getInstance().getServiceName());
        if (rosterEntry != null) {
            RosterPacket.ItemType type = rosterEntry.getType();
            if (RosterPacket.ItemType.both.equals(type)) {
                return true;
            }
        }
        return false;
    }

    @Override
    protected void onResume() {
        lodingProBar.setVisibility(View.GONE);
        promptText.setVisibility(View.GONE);
        super.onResume();
    }
}
