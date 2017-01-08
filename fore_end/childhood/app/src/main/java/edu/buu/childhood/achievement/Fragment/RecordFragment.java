package edu.buu.childhood.achievement.Fragment;

import android.app.Dialog;
import android.content.ComponentName;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RatingBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import edu.buu.childhood.AnimationTools.AnimationTools;
import edu.buu.childhood.Interface.BtnListener;
import edu.buu.childhood.R;
import edu.buu.childhood.achievement.adapter.PariseMembersAdapter;
import edu.buu.childhood.achievement.adapter.RecordAdapter;
import edu.buu.childhood.achievement.pojo.UserHistoryGameList;
import edu.buu.childhood.achievement.service.AchievementServiceImpl;
import edu.buu.childhood.baidumap.pojo.MemberInfo;
import edu.buu.childhood.common.C;
import edu.buu.childhood.common.CallBackPage;
import edu.buu.childhood.common.GlobalVeriable;
import edu.buu.childhood.game.refresh.AutoListView;
import edu.buu.childhood.util.CallBack;
import edu.buu.childhood.util.HttpUtil;
import edu.buu.childhood.util.NetAsyncTask;
import edu.buu.childhood.util.NetWorkStatusUtil;
import edu.buu.childhood.util.URLUtil;

/**
 * Created by lcc on 2016/9/27.
 */
public class RecordFragment extends Fragment implements CallBack, AutoListView.OnLoadListener, AutoListView.OnRefreshListener, BtnListener {
    private AutoListView lvUserHistoryGameList;
    private TextView tvNoRecords;
    private Dialog Dialog;
    private TextView tvGameName;
    private LinearLayout submit;
    private RecordAdapter recordAdapter;
    private List<UserHistoryGameList> userHistoryGameLists = new ArrayList<UserHistoryGameList>();
    private Boolean isRefresh = true;
    private int pageNum = 1;
    private Button btPariseOk;
    private Button btPariseCancle;
    private RatingBar ratingBar;
    private ListView lvMembers;
    private String getMemBersUrl;
    private String getUserGameLIsturl;
    private PariseMembersAdapter pariseMembersAdapter;
    private List<MemberInfo> memberInfoList = new ArrayList<>();
    private String likeUserUrl;
    private int likeUserPositaion;
    private Boolean isGameScored;
    private String gameScoreUrl;
    private int gameListPositation;
    private int clickGameId;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.record_fragment, container, false);
        tvNoRecords = (TextView) view.findViewById(R.id.record_no);
        lvUserHistoryGameList = (AutoListView) view.findViewById(R.id.listView);
        if (userHistoryGameLists.size() > 0) {
            recordAdapter = new RecordAdapter(getActivity(), userHistoryGameLists);
            lvUserHistoryGameList.setAdapter(recordAdapter);
        }
        lvUserHistoryGameList.setOnLoadListener(this);
        lvUserHistoryGameList.setOnRefreshListener(this);
        lvUserHistoryGameList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (position <= userHistoryGameLists.size()) {
                    gameListPositation = position;
                    UserHistoryGameList userHistoryGameList = (UserHistoryGameList) userHistoryGameLists.get(position - 1);
                    if (userHistoryGameList != null) {
                        int gameId = userHistoryGameList.getGameId();
                        String gameName = userHistoryGameList.getGameTitle();
                        clickGameId = gameId;
                        char isScored = userHistoryGameList.getIsScored();
                        if (isScored == 'Y') {
                            isGameScored = true;
                        } else {
                            isGameScored = false;
                        }
                        gameDialog(userHistoryGameList);
                        Map map = new HashMap();
                        map.put("gameId", gameId);
                        map.put("userName", ((GlobalVeriable) getActivity().getApplication()).getUserName());
                        getMemBersUrl = URLUtil.getURL("getTeamMembers", map);
                        new NetAsyncTask(RecordFragment.this).execute(getMemBersUrl);
                    }
                }
            }
        });
        return view;
    }

    public void gameDialog(UserHistoryGameList userHistoryGameList) {
        LayoutInflater inflater = LayoutInflater.from(getActivity());
        RelativeLayout layout = (RelativeLayout) inflater.inflate(R.layout.praise, null);
        Dialog = new AlertDialog.Builder(getActivity()).create();
        //Dialog.setCancelable(false);
        Dialog.show();
        Dialog.getWindow().setContentView(layout);
        Dialog.getWindow().setAttributes(getWidthAndHegith(Dialog));
        lvMembers = (ListView) layout.findViewById(R.id.parise_listview);
        tvGameName = (TextView) layout.findViewById(R.id.gameName);
        btPariseOk = (Button) layout.findViewById(R.id.parise_ok);
        btPariseCancle = (Button) layout.findViewById(R.id.parise_cancle);
        ratingBar = (RatingBar) layout.findViewById(R.id.parise_ratingbar);
        if (isGameScored) {
            ratingBar.setIsIndicator(true);
            Log.i("rating", userHistoryGameList.getGameScore() + "ss");
            ratingBar.setRating(Integer.parseInt(String.valueOf(userHistoryGameList.getGameScore())));
        } else {
            ratingBar.setIsIndicator(false);
        }
        tvGameName.setText(userHistoryGameList.getGameTitle());
        if (memberInfoList.size() > 0) {
            pariseMembersAdapter = new PariseMembersAdapter(getActivity(), memberInfoList, RecordFragment.this);
            lvMembers.setAdapter(pariseMembersAdapter);
        }

        btPariseOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!isGameScored) {
                    Map map = new HashMap();
                    map.put("gameId", userHistoryGameLists.get(gameListPositation - 1).getGameId());
                    map.put("userName", ((GlobalVeriable) getActivity().getApplication()).getUserName());
                    map.put("gameScore", ratingBar.getRating());
                    gameScoreUrl = URLUtil.getURL("scoreGame", map);
                    new NetAsyncTask(RecordFragment.this).execute(gameScoreUrl);
                } else {
                    Dialog.dismiss();
                }
            }
        });
        btPariseCancle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Dialog.dismiss();
            }
        });
    }

    @Override
    public void onResume() {
        requestNetWork(pageNum);
        super.onResume();
    }

    @Override
    public CallBackPage doInBackground(String url) {
        HttpUtil httpUtil = new HttpUtil(url);
        byte[] bytes = httpUtil.getHttpData();
        if (bytes != null) {
            AchievementServiceImpl achievementService = new AchievementServiceImpl();
            if (url.equals(getUserGameLIsturl)) {
                return achievementService.getUserHistoryGameList(new String(bytes));
            } else if (url.equals(getMemBersUrl)) {
                return achievementService.getMemberInfo(new String(bytes));
            } else if (url.equals(likeUserUrl)) {
                return achievementService.likeUser(new String(bytes));
            } else if (url.equals(gameScoreUrl)) {
                return achievementService.gameSore(new String(bytes));
            }
        }
        return null;
    }

    @Override
    public void getResult(CallBackPage result) {
        if (result != null) {
            if (C.achvmt.GET_USER_GAME_LIST_SUCCESS.equals(result.getFalg())) {
                handleUserGameList(result);
            } else if (C.convene.GET_TEAM_MEMBERS_SUCCESS.equals(result.getFalg())) {
                memberInfoList.clear();
                memberInfoList.addAll(result.getDatalist());
                if (pariseMembersAdapter == null) {
                    pariseMembersAdapter = new PariseMembersAdapter(getActivity(), memberInfoList, RecordFragment.this);
                    lvMembers.setAdapter(pariseMembersAdapter);
                }
                pariseMembersAdapter.notifyDataSetChanged();
            } else if (C.rank.LIKE_USER_SUCCESS.equals(result.getFalg())) {
                View view = lvMembers.getChildAt(likeUserPositaion);
                PariseMembersAdapter.ViewHolder holder = (PariseMembersAdapter.ViewHolder) view.getTag();
                AnimationTools.scale(holder.parise);
                holder.parise.setImageResource(R.drawable.zan);
                holder.parise.setClickable(false);
            } else if (C.onekey.SCORE_SUCCESS.equals(result.getFalg())) {
                Dialog.dismiss();
                userHistoryGameLists.get(gameListPositation - 1).setIsScored('Y');
                Toast.makeText(getContext(), "评分成功", Toast.LENGTH_SHORT).show();
            }
        }
    }


    public void requestNetWork(int pageNum) {
        if (NetWorkStatusUtil.checkNetWorkStatus(getActivity())) {
            Map map = new HashMap<>();
            map.put("userName", ((GlobalVeriable) getActivity().getApplication()).getUserName());
            map.put("pageNum", pageNum);
            getUserGameLIsturl = URLUtil.getURL("getUserGameList", map);
            new NetAsyncTask(this).execute(getUserGameLIsturl);
        } else {
            setNetwork();
        }
    }

    @Override
    public void onLoad() {
        isRefresh = false;
        pageNum++;
        requestNetWork(pageNum);
    }

    @Override
    public void onRefresh() {
        Log.i("ssss", "fffff");
        isRefresh = true;
        requestNetWork(1);
    }

    public void handleUserGameList(CallBackPage result) {
        if (result.getDatalist().size() > 0) {
            if (isRefresh) {
                lvUserHistoryGameList.onRefreshComplete();
                userHistoryGameLists.clear();
                userHistoryGameLists.addAll(result.getDatalist());
            } else {
                lvUserHistoryGameList.onLoadComplete();
                userHistoryGameLists.addAll(result.getDatalist());
            }
            if (recordAdapter == null) {
                recordAdapter = new RecordAdapter(getActivity(), userHistoryGameLists);
                lvUserHistoryGameList.setAdapter(recordAdapter);
            }
            if (result.getCurrentPage() == result.getTotalPage()) {
                lvUserHistoryGameList.setPageSize(result.getPageSize());
                lvUserHistoryGameList.setResultSize(result.getDatalist().size() - 1);
            } else {
                lvUserHistoryGameList.setPageSize(result.getPageSize());
                lvUserHistoryGameList.setResultSize(result.getDatalist().size());
            }
            recordAdapter.notifyDataSetChanged();
        } else {
            tvNoRecords.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void onClick(View v, int positation) {
        likeUserPositaion = positation;
        MemberInfo memberInfo = memberInfoList.get(positation);
        Map map = new HashMap();
        map.put("userName", ((GlobalVeriable) getActivity().getApplication()).getUserName());
        map.put("access", C.def.ACCESS_GAMEOVER);
        map.put("likeUser", memberInfo.getUserName());
        map.put("gameId", clickGameId);
        likeUserUrl = URLUtil.getURL("likeUser", map);
        Log.i("likeUrl", likeUserUrl);
        new NetAsyncTask(this).execute(likeUserUrl);
    }

    /**
     * 网络未连接时，调用设置方法
     */
    private void setNetwork() {
        Toast.makeText(getActivity(), "wifi is closed!", Toast.LENGTH_SHORT).show();

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setIcon(R.drawable.error);
        builder.setTitle("网络提示信息");
        builder.setMessage("网络不可用，如果继续，请先设置网络！");
        builder.setPositiveButton("设置", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Intent intent = null;
                /**
                 * 判断手机系统的版本！如果API大于10 就是3.0+
                 * 因为3.0以上的版本的设置和3.0以下的设置不一样，调用的方法不同
                 */
                if (android.os.Build.VERSION.SDK_INT > 10) {
                    intent = new Intent(android.provider.Settings.ACTION_WIFI_SETTINGS);
                } else {
                    intent = new Intent();
                    ComponentName component = new ComponentName(
                            "com.android.settings",
                            "com.android.settings.WirelessSettings");
                    intent.setComponent(component);
                    intent.setAction("android.intent.action.VIEW");
                }
                startActivity(intent);
            }
        });

        builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        builder.create();
        builder.show();
    }

    public WindowManager.LayoutParams getWidthAndHegith(Dialog dialog) {
        WindowManager m = getActivity().getWindowManager();
        Display d = m.getDefaultDisplay(); // 获取屏幕宽、高用
        WindowManager.LayoutParams p = dialog.getWindow().getAttributes(); // 获取对话框当前的参数值
        p.height = (int) (d.getHeight() * 0.77); // 高度设置为屏幕的0.77
        p.width = (int) (d.getWidth() * 0.77); // 宽度设置为屏幕的0.7
        return p;
    }

}
