package edu.buu.childhood.common.activity;


import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import org.jivesoftware.smack.roster.Roster;
import org.jivesoftware.smack.roster.RosterEntry;
import org.jivesoftware.smack.roster.packet.RosterPacket;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import edu.buu.childhood.R;
import edu.buu.childhood.achievement.pojo.UserHistoryGameList;
import edu.buu.childhood.achievement.pojo.UserKPI;
import edu.buu.childhood.achievement.pojo.UserMedalList;
import edu.buu.childhood.chat.activity.AddFriendsFinalActivity;
import edu.buu.childhood.chat.activity.ChatRoomActivity;
import edu.buu.childhood.chat.bean.User;
import edu.buu.childhood.common.C;
import edu.buu.childhood.common.CallBackPage;
import edu.buu.childhood.common.GlobalVeriable;
import edu.buu.childhood.common.UserPersonalPage;
import edu.buu.childhood.common.service.UserInfoISericeImpl;
import edu.buu.childhood.common.service.UserInfoService;
import edu.buu.childhood.database.UserDao;
import edu.buu.childhood.util.AsyncImageLoader;
import edu.buu.childhood.util.CallBack;
import edu.buu.childhood.util.CloseActivity;
import edu.buu.childhood.util.DBOprate;
import edu.buu.childhood.util.HttpUtil;
import edu.buu.childhood.util.NetAsyncTask;
import edu.buu.childhood.util.NetWorkStatusUtil;
import edu.buu.childhood.util.SmackManager;
import edu.buu.childhood.util.URLUtil;

/**
 * Created by lcc on 2016/9/11.
 */
public class UserInfoActivity extends BaseAvtivity implements CallBack {
    //定义基本信息
    private TextView tvNickname;
    private TextView tvProvince;
    private TextView tvGameLevelName;
    private TextView tvLikeCount;
    private Button btSendMessage;
    private ImageView ivHeadImage;
    private LinearLayout llGameRecordimg;
    private TextView tvGameLevel;
    private TextView tvCurrentPoint;
    private TextView tvNextPoint;
    private ProgressBar progressBar;
    private String userName;
    private String userNickName;
    private String imageUrl;
    private AsyncImageLoader imageLoader;
    private String getuserInfoUrl;
    private String getUserPageUrl;
    private DBOprate dbOprate;
    private LinearLayout llMedalimages;
    private int[] medalResource = {R.drawable.userinfo_medal1, R.drawable.userinfo_medal2, R.drawable.userinfo_medal3, R.drawable.userinfo_medal4, R.drawable.userinfo_medal5
            , R.drawable.userinfo_medal6, R.drawable.userinfo_medal7, R.drawable.userinfo_medal8, R.drawable.userinfo_medal9, R.drawable.userinfo_medal10};


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        CloseActivity.activityList.add(this);
        imageLoader = new AsyncImageLoader(this);
        dbOprate = new DBOprate(this);
        setContentView(R.layout.activity_user_info);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        tvNickname = (TextView) findViewById(R.id.partnerInfoNi);
        tvProvince = (TextView) findViewById(R.id.partnerInfoProvince);
        //tvCity = (TextView) findViewById(R.id.partnerInfoCity);
        tvLikeCount = (TextView) findViewById(R.id.userinfo_likecount);
        tvGameLevelName = (TextView) findViewById(R.id.gamelevel_name);
        ivHeadImage = (ImageView) findViewById(R.id.head_image);
        llGameRecordimg = (LinearLayout) findViewById(R.id.userinfo_img_layout);
        tvGameLevel = (TextView) findViewById(R.id.gamelevel);
        tvCurrentPoint = (TextView) findViewById(R.id.currentLevelPoin);
        tvNextPoint = (TextView) findViewById(R.id.nextLevelPoint);
        llMedalimages = (LinearLayout) findViewById(R.id.medal_imageview);
        progressBar = (ProgressBar) findViewById(R.id.progressBar);
        btSendMessage = (Button) findViewById(R.id.btn_sendmsg);
        btSendMessage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //是否是好友
                if (isFriend(userName) || userName.equals(((GlobalVeriable) getApplication()).getUserName())) {
                    Intent intent = new Intent(UserInfoActivity.this, ChatRoomActivity.class);
                    intent.putExtra("userName", userName);
                    intent.putExtra("userNick", userNickName);
                    startActivity(intent);
                } else {
                    Intent intent = new Intent(UserInfoActivity.this, AddFriendsFinalActivity.class);
                    intent.putExtra("fromUser", userName);
                    startActivity(intent);
                }
            }
        });

        Intent intent = getIntent();
        if (intent != null) {
            userName = intent.getStringExtra("userName");
            userNickName = intent.getStringExtra("userNickName");
            imageUrl = intent.getStringExtra("imageUrl");
            Log.i("sssg", userName);
            loadImage(imageUrl);
            tvNickname.setText(userNickName);

            if (isFriend(userName) || userName.equals(((GlobalVeriable) getApplication()).getUserName())) {
                btSendMessage.setText("发送消息");
            } else {
                btSendMessage.setText("添加到通讯录");
            }
            getUserInfo(userName);
            requsetNetWork(userName);
        }
    }

    public void loadImage(String imageUrl) {
        if (!TextUtils.isEmpty(imageUrl)) {
            ivHeadImage.setTag(imageUrl);
            Bitmap bitmap = imageLoader.loadImage(ivHeadImage, imageUrl);
            if (bitmap != null) {
                ivHeadImage.setImageBitmap(bitmap);
            }
        }
    }

    public void getUserInfo(String userName) {
        UserDao userDao = new UserDao(this);
        User user = userDao.getUserInfo(userName);
        if (user != null) {
            initView(user);
        }
    }

    public void requsetNetWork(String targetUserName) {
        if (NetWorkStatusUtil.checkNetWorkStatus(this)) {
            Map map = new HashMap<>();
            map.put("userName", targetUserName);
            String url = URLUtil.getURL("getUserInfo", map);
            new NetAsyncTask(this).execute(url);
            Map userPageMap = new HashMap<>();
            userPageMap.put("userName", ((GlobalVeriable) getApplication()).getUserName());
            userPageMap.put("targetUser", targetUserName);
            getUserPageUrl = URLUtil.getURL("getUserPersonalPage", userPageMap);
            Log.i("getUserpage", getUserPageUrl);
            new NetAsyncTask(this).execute(getUserPageUrl);
        } else {

        }
    }

    public Boolean isFriend(String userName) {
        Log.i("userName", userName);
        Roster roster = SmackManager.getInstance().getRoster();
        String serviceName = SmackManager.getInstance().getServiceName();
        RosterEntry entry = roster.getEntry(userName + "@" + serviceName);
        if (entry != null) {
            RosterPacket.ItemType type = entry.getType();
            if (!type.equals(RosterPacket.ItemType.none)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public CallBackPage doInBackground(String url) {
        HttpUtil httpUtil = new HttpUtil(url);
        byte[] bytes = httpUtil.getHttpData();
        if (bytes != null) {
            UserInfoService userInfoService = new UserInfoISericeImpl();
            if (url.equals(getuserInfoUrl)) {
                return userInfoService.getUserInfo(new String(bytes));
            } else if (url.equals(getUserPageUrl)) {
                return userInfoService.getUserPersionPage(new String(bytes));
            }
        }
        return null;
    }

    @Override
    public void getResult(CallBackPage result) {
        if (result != null) {
            if (result.getFalg().equals(C.achvmt.GET_USER_PERSONAL_PAGE_SUCCESS)) {
                handlePersonalInfo((UserPersonalPage) result.getDatalist().get(0));
                saveUserInfo((UserPersonalPage) result.getDatalist().get(0));
            }
            // edu.buu.childhood.my.pojo.UserInfo userInfo = (edu.buu.childhood.my.pojo.UserInfo) result.getDatalist().get(0);
        }

    }

    public void handlePersonalInfo(UserPersonalPage userPersonalPage) {
        String districtName = null;
        List<UserHistoryGameList> userGameList = userPersonalPage.getUserGameList();
        List<UserMedalList> medalList = userPersonalPage.getMedalList();
        UserKPI userKPI = userPersonalPage.getUserKPI();
        Log.i("ssss", userPersonalPage.getUserKPI().toString());
        // Log.i("sss",userKPI.getTotalLikeCount()+"ss");
        tvLikeCount.setText(String.valueOf(userKPI.getTotalLikeCount()));
        tvGameLevelName.setText(String.valueOf(userKPI.getLevelName()));
        tvGameLevel.setText(String.valueOf(userKPI.getUserLevel() + 1));
        tvCurrentPoint.setText(String.valueOf(userKPI.getAchievementPoint()));
        tvNextPoint.setText(String.valueOf(userKPI.getNextLevelPoint()));
        progressBar.setProgress(userKPI.getAchievementPoint() - userKPI.getCurrentLevelPoint());
        progressBar.setMax(userKPI.getNextLevelPoint() - userKPI.getCurrentLevelPoint());
        if (userKPI.getBelongingProvince() > 0) {
            districtName = dbOprate.getProvinceName(userKPI.getBelongingProvince());
            districtName += " ";
        }
        if (userKPI.getBelongingCity() > 0) {
            districtName += dbOprate.getCityName(userKPI.getBelongingCity());
        }
        if (districtName != null) {
            tvProvince.setText(districtName);
        } else {
            tvProvince.setVisibility(View.GONE);
        }
        for (int i = 0; i < userPersonalPage.getUserGameList().size(); i++) {
            ImageView imageView = new ImageView(this);
            String imageUrl = userPersonalPage.getUserGameList().get(i).getGameIcon();
            if (!TextUtils.isEmpty(imageUrl)) {
                imageView.setTag(imageUrl);
                Bitmap bitmap = imageLoader.loadImage(imageView, imageUrl);
                if (bitmap != null) {
                    imageView.setImageBitmap(bitmap);
                }
            }
            LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
            lp.width = 48 * 3;
            lp.height = 48 * 3;
            lp.setMargins(15, 15, 15, 15);
            imageView.setLayoutParams(lp);
            llGameRecordimg.addView(imageView);
            if (i == 2) {
                break;
            }
        }

        for (UserMedalList userMedalList : medalList) {
            ImageView imageView = new ImageView(this);
            imageView.setImageResource(medalResource[userMedalList.getMedalId() - 1]);
            LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
            lp.setMargins(6, 6, 6, 6);
            imageView.setLayoutParams(lp);
            llMedalimages.addView(imageView);
        }
        if (medalList.size() < 0) {
            llMedalimages.setVisibility(View.GONE);
        }
    }

    public void initView(User user) {
        String district = null;
        tvLikeCount.setText(String.valueOf(user.getTotalLikeCount()));
        if (user.getLevelName() != null) {
            tvGameLevelName.setText(String.valueOf(user.getLevelName()));
        }
        tvGameLevel.setText(String.valueOf(user.getUserLevel() + 1));
        tvCurrentPoint.setText(String.valueOf(user.getAchievementPoint()));
        tvNextPoint.setText(String.valueOf(user.getNextLevelPoint()));
        progressBar.setProgress(user.getAchievementPoint() - user.getCurrentLevelPoint());
        progressBar.setMax(user.getNextLevelPoint() - user.getCurrentLevelPoint());
        if (user.getProvince() != null) {
            district = user.getProvince();
            district += " ";
        }
        if (user.getCity() != null) {
            district = user.getCity();
        }
        if (district != null) {
            tvProvince.setText(district);
        } else {
            tvProvince.setVisibility(View.GONE);
        }
    }


    public void saveUserInfo(UserPersonalPage userPersonalPage) {
        UserDao userDao = new UserDao(this);
        UserKPI userKPI = userPersonalPage.getUserKPI();
        User user = new User();
        user.setUserName(userKPI.getUserName());
        user.setUsernick(userKPI.getUserNickname());
        user.setUserImage(userKPI.getUserHeadImage());
        user.setTotalLikeCount(userKPI.getTotalLikeCount());
        user.setUserLevel(userKPI.getUserLevel());
        user.setLevelName(userKPI.getLevelName());
        user.setTotalGameCount(userKPI.getTotalGameCount());
        user.setAchievementPoint(userKPI.getAchievementPoint());
        user.setNextLevelPoint(userKPI.getNextLevelPoint());
        user.setCurrentLevelPoint(userKPI.getCurrentLevelPoint());
        if (userKPI.getBelongingProvince() > 0) {
            user.setProvince(dbOprate.getProvinceName(userKPI.getBelongingProvince()));
        }
        if (userKPI.getBelongingCity() > 0) {
            user.setCity(dbOprate.getCityName(userKPI.getBelongingCity()));
        }
        if (userDao.isUserInfo(userKPI.getUserName())) {
            userDao.updateUserInfo(user);
        } else {
            userDao.saveContact(user);
        }
    }
}