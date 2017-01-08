package edu.buu.childhood.baidumap.fragment;

import android.app.Dialog;
import android.app.Fragment;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import edu.buu.childhood.R;
import edu.buu.childhood.Interface.BtnListener;
import edu.buu.childhood.baidumap.activity.MainBaidu;
import edu.buu.childhood.baidumap.adapter.NewMessage_Adapter;
import edu.buu.childhood.baidumap.pojo.GameInfBean;
import edu.buu.childhood.baidumap.pojo.MarkItem;
import edu.buu.childhood.baidumap.pojo.NewMessage_ItemBean;
import edu.buu.childhood.baidumap.pojo.PushMessage;
import edu.buu.childhood.baidumap.service.JoinGameServiceImpl;
import edu.buu.childhood.baidumap.service.OnekeyServiceImpl;
import edu.buu.childhood.common.CallBackPage;
import edu.buu.childhood.common.GlobalVeriable;
import edu.buu.childhood.util.CallBack;
import edu.buu.childhood.util.DBOprate;
import edu.buu.childhood.util.HttpUtil;
import edu.buu.childhood.util.NetAsyncTask;
import edu.buu.childhood.util.URLUtil;
import edu.buu.childhood.util.ValidateUtil;

/**
 * Created by lcc on 2016/7/2.
 */
public class NewMessage extends Fragment implements CallBack, BtnListener {
    private ListView listView;
    private List<NewMessage_ItemBean> newsDatalist = null;
    private String joinGameUrl;
    private NewMessage_Adapter newMessage_adapter;
    private String gameInfo;
    private TextView noInviteTip;
    private TextView diaGameTitle;
    private TextView diaGameId;
    private TextView diaGameFounder;
    private TextView diaGamePlace;
    private TextView diaReCount;
    private TextView diaGameSyp;
    private int gameIdTemp;
    private int positationTemp;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.new_message, container, false);
        listView = (ListView) view.findViewById(R.id.new_message_listView);
        noInviteTip = (TextView) view.findViewById(R.id.noInviteTip);
        ((GlobalVeriable) getActivity().getApplication()).setRunningNewMessage(true);
        if (newsDatalist == null) {
            newsDatalist = new ArrayList<>();
        }
        Log.i("newsdata", newsDatalist.size() + "");
        initData(((GlobalVeriable) getActivity().getApplication()).getInstance());
        newMessage_adapter = new NewMessage_Adapter(getActivity(), newsDatalist, this);
        if (newsDatalist.size() > 0) {
            listView.setAdapter(newMessage_adapter);
        }
        final TextView gameIdText = (TextView) view.findViewById(R.id.new_message_model_gameid);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (gameIdText != null) {
                    Map map = new HashMap();
                    map.put("gameId", Integer.parseInt((String) gameIdText.getText()));
                    gameIdTemp = Integer.parseInt((String) gameIdText.getText());
                    gameInfo = URLUtil.getURL("getGameInfo", map);
                    new NetAsyncTask(NewMessage.this).execute(gameInfo);
                }
                positationTemp = position;
                //Toast.makeText(getActivity(), "你点击了第" + (position) + "个item", Toast.LENGTH_SHORT).show();
                LayoutInflater inflater = LayoutInflater.from(getActivity());
                LinearLayout layout = (LinearLayout) inflater.inflate(R.layout.game_invite, null);
                final Dialog dialog1 = new AlertDialog.Builder(getActivity()).create();
                dialog1.setCancelable(false);
                dialog1.show();
                dialog1.getWindow().setContentView(layout);
                diaGameId = (TextView) layout.findViewById(R.id.game_invite_gameid);
                diaGameTitle = (TextView) layout.findViewById(R.id.game_invite_gameName);
                diaGameFounder = (TextView) layout.findViewById(R.id.game_invite_createdName);
                diaGamePlace = (TextView) layout.findViewById(R.id.game_invite_play_place);
                diaReCount = (TextView) layout.findViewById(R.id.game_invite_referrals);
                diaGameSyp = (TextView) layout.findViewById(R.id.game_invite_gamepsy);
                diaGameSyp.setMovementMethod(ScrollingMovementMethod.getInstance());
                Button btnOK = (Button) layout.findViewById(R.id.game_invite_positiveButton);
                btnOK.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        GlobalVeriable globalVeriable = ((GlobalVeriable) getActivity().getApplication());
                        if (globalVeriable.getLogin() && !globalVeriable.getGameRunning()) {
                            if (!ValidateUtil.isEmpty(gameIdText.getText().toString())) {
                                Map args = new HashMap();
                                args.put("userName", globalVeriable.getUserName());
                                args.put("gameId", Integer.parseInt(gameIdText.getText().toString()));
                                joinGameUrl = URLUtil.getURL("joinGame", args);
                                gameIdTemp = Integer.parseInt(gameIdText.getText().toString());
                                Log.i("joingame", joinGameUrl);
                                new NetAsyncTask(NewMessage.this).execute(joinGameUrl);
                                dialog1.dismiss();
                            }
                        } else {
                            Toast.makeText(getActivity(), "您未登录或已经在游戏中", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
                Button btnCancel = (Button) layout.findViewById(R.id.game_invite_negativeButton);
                btnCancel.setOnClickListener(new View.OnClickListener() {

                    @Override
                    public void onClick(View v) {
                        dialog1.dismiss();
                    }
                });
            }
        });
        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                final NewMessage_ItemBean item = (NewMessage_ItemBean) listView.getItemAtPosition(position);
                if (item != null) {
                    //定义AlertDialog.Builder对象，当长按列表项的时候弹出确认删除对话框
                    final AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                    builder.setMessage("确定删除?");
                    builder.setTitle("提示");
                    //添加AlertDialog.Builder对象的setPositiveButton()方法
                    builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            newsDatalist.remove(item);
                            newMessage_adapter.notifyDataSetChanged();
                            DBOprate dbOprate = new DBOprate(getActivity(), "Msf.db");
                            PushMessage pushMessage = new PushMessage();
                            pushMessage.setUserName(((GlobalVeriable) getActivity().getApplication()).getUserName());
                            pushMessage.setGameId(item.getGameId());
                            dbOprate.updatePushMessage(pushMessage);
                        }
                    });

                    //添加AlertDialog.Builder对象的setNegativeButton()方法
                    builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            builder.create().dismiss();
                        }
                    });

                    builder.create().show();
                }
               /* NewMessage_ItemBean item = (NewMessage_ItemBean) listView.getItemAtPosition(position);
                newsDatalist.remove(item);
                newMessage_adapter.notifyDataSetChanged();*/
                return true;
            }
        });

        return view;

    }

    //为listview配置适配器,加监听事件

    @Override
    public void getResult(CallBackPage result) {
        if (result != null) {
            if ("gameInfo".equals(result.getFalg())) {
                if (result.getDatalist().size() > 0) {
                    GameInfBean gameInfBean = (GameInfBean) result.getDatalist().get(0);
                    diaGameId.setText(String.valueOf(gameInfBean.getGameId()));
                    diaGameTitle.setText(gameInfBean.getGameName());
                    diaGamePlace.setText(gameInfBean.getGamePlace());
                    diaReCount.setText(String.valueOf(gameInfBean.getRecommendCount()));
                    diaGameFounder.setText(gameInfBean.getGameFounderNickName());
                    diaGameSyp.setText(gameInfBean.getGameSynopsis());
                }
            } else {
                MarkItem bean = (MarkItem) result.getDatalist().get(0);
                if (bean.getSelect().equals("4")) {
                    if (gameIdTemp != 0) {
                        ArrayList<CharSequence> list = new ArrayList<CharSequence>();
                        list.add(String.valueOf(gameIdTemp));
                        list.add(((GlobalVeriable) getActivity().getApplication()).getUserName());
                        ((MainBaidu) getActivity()).sendToService(list, 4);
                        ((GlobalVeriable) getActivity().getApplication()).setGameId(gameIdTemp);
                        ((GlobalVeriable) getActivity().getApplication()).setGameRunning(true);
                        ((GlobalVeriable) getActivity().getApplication()).setGameFounder(false);
                        newsDatalist.remove(positationTemp);
                        newMessage_adapter.notifyDataSetChanged();
                        DBOprate dbOprate = new DBOprate(getActivity(), "Msf.db");
                        PushMessage pushMessage = new PushMessage();
                        pushMessage.setUserName(((GlobalVeriable) getActivity().getApplication()).getUserName());
                        pushMessage.setGameId(gameIdTemp);
                        dbOprate.updatePushMessage(pushMessage);
                    }
                    ((MainBaidu) getActivity()).refresh();
                    ((GlobalVeriable) getActivity().getApplication()).setGameRunning(true);
                    //  ((GlobalVeriable) getActivity().getApplication()).setGameId();
                    Toast.makeText(getActivity(), "加入成功", Toast.LENGTH_LONG).show();
                }
            }
        }
    }


    @Override
    public CallBackPage doInBackground(String url) {
        HttpUtil httpUtil = new HttpUtil(url);
        byte[] bytes = httpUtil.getHttpData();
        if (bytes != null) {
            if (url.equals(gameInfo)) {
                OnekeyServiceImpl onekeyService = new OnekeyServiceImpl();
                return onekeyService.getGameInfo(new String(bytes));
            } else {
                JoinGameServiceImpl service = new JoinGameServiceImpl();
                return service.getJoinGameHeadInf(new String(bytes));
            }
        }
        return null;
    }

    //初始化邀请列表信息
    public void initData(Context context) {
        if (newsDatalist == null) {
            newsDatalist = new ArrayList<NewMessage_ItemBean>();
        }
        //newsDatalist.add(new NewMessage_ItemBean(118, "刘诚诚", "我是刘诚诚", "互相追逐"));
        Log.i("data", newsDatalist.size() + "ss");
        DBOprate dbOprate = new DBOprate(context, "Msf.db");
        String querySql = "select * from pushMessage where username='" + (((GlobalVeriable) context).getUserName()) + "' " + "and isread = 0";
        List<PushMessage> list = dbOprate.getPushMessage(querySql);
        if (list.size() > 0) {
            for (int i = list.size() - 1; i >= 0; i--) {
                PushMessage pushMessage = list.get(i);
                newsDatalist.add(new NewMessage_ItemBean(pushMessage.getGameId(), pushMessage.getGameIcon(), pushMessage.getGameTitle(), pushMessage.getFounderNickName(), pushMessage.getGatherPlace()));
                //dbOprate.updatePushMessage(pushMessage);
            }
        }
        if (newsDatalist.size() == 0) {
            noInviteTip.setVisibility(View.VISIBLE);
        } else {
            noInviteTip.setVisibility(View.GONE);
        }
    }

    //更新适配器的数据
    public void updateNewMesageAdapter(NewMessage_ItemBean newMessage_itemBean) {
        if (newsDatalist == null) {
            newsDatalist = new ArrayList<NewMessage_ItemBean>();
        }
        newsDatalist.add(newMessage_itemBean);
        newMessage_adapter.notifyDataSetChanged();
        listView.smoothScrollToPosition(listView.getCount() - 1);
    }


    //listView buuton的点击事件
    @Override
    public void onClick(View v, int positation) {
        //  Log.i("sss", "ssssss"+newsDatalist.size()+positation);
        GlobalVeriable globalVeriable = ((GlobalVeriable) getActivity().getApplication());
        NewMessage_ItemBean newMessage_itemBean = newsDatalist.get(positation);
        Log.i("sss", globalVeriable.getLogin().toString() + globalVeriable.getGameRunning() + "dd" + newMessage_itemBean.getGameId());
        if (globalVeriable.getLogin() && !globalVeriable.getGameRunning()) {
            if (newMessage_itemBean.getGameId() != 0) {
                Map args = new HashMap();
                args.put("userName", globalVeriable.getUserName());
                args.put("gameId", newMessage_itemBean.getGameId());
                gameIdTemp = newMessage_itemBean.getGameId();
                joinGameUrl = URLUtil.getURL("joinGame", args);
                new NetAsyncTask(NewMessage.this).execute(joinGameUrl);
            }
        } else {
            Toast.makeText(getActivity(), "您已经在游戏中", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onDestroy() {
        ((GlobalVeriable) getActivity().getApplication()).setRunningNewMessage(false);
        super.onDestroy();
    }
}
