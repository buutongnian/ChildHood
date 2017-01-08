package edu.buu.childhood.baidumap.fragment;


import android.app.Dialog;
import android.app.Fragment;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClientOption;
import com.baidu.mapapi.SDKInitializer;
import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.BitmapDescriptor;
import com.baidu.mapapi.map.BitmapDescriptorFactory;
import com.baidu.mapapi.map.MapPoi;
import com.baidu.mapapi.map.MapStatusUpdate;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.Marker;
import com.baidu.mapapi.map.MarkerOptions;
import com.baidu.mapapi.map.MyLocationConfiguration;
import com.baidu.mapapi.map.MyLocationData;
import com.baidu.mapapi.map.OverlayOptions;
import com.baidu.mapapi.model.LatLng;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import edu.buu.childhood.MemorySelectGame;
import edu.buu.childhood.R;
import edu.buu.childhood.baidumap.activity.MainBaidu;
import edu.buu.childhood.baidumap.activity.TeamerNameActivity;
import edu.buu.childhood.baidumap.adapter.selectGameAdapter;
import edu.buu.childhood.baidumap.dialog.CreatingGameDialog;
import edu.buu.childhood.baidumap.pojo.MarkItem;
import edu.buu.childhood.baidumap.service.CreateGameServiceImpl;
import edu.buu.childhood.baidumap.service.GameInfoServiceImpl;
import edu.buu.childhood.baidumap.service.JoinGameServiceImpl;
import edu.buu.childhood.baidumap.service.LocationService;
import edu.buu.childhood.baidumap.service.PersonInfoServiceImpl;
import edu.buu.childhood.baidumap.service.SelectGameServiceImpl;
import edu.buu.childhood.common.CallBackPage;
import edu.buu.childhood.common.GlobalVeriable;
import edu.buu.childhood.game.refresh.AutoListView;
import edu.buu.childhood.login.activity.Login;
import edu.buu.childhood.util.CallBack;
import edu.buu.childhood.util.HttpUtil;
import edu.buu.childhood.util.NetAsyncTask;
import edu.buu.childhood.util.URLUtil;
import edu.buu.childhood.util.ValidateUtil;

public class baiduMap extends Fragment implements CallBack, AutoListView.OnRefreshListener, AutoListView.OnLoadListener {
    MapView mMapView = null;
    BaiduMap baiduMap;
    private LatLng myPoint;
    private Dialog dialog1;
    private Dialog dialog;
    private Dialog selectDialog;
    private MyLocationListener mLocationListener;
    private LocationService locationService;
    //是否第一次请求定位
    private boolean isFirstIn = true;
    //自定义定位图标
    private BitmapDescriptor bitmapDescriptor;
    private Marker marker;
    private View view;
    private List<MarkItem> personInfo = new ArrayList<MarkItem>();
    private List<MarkItem> gameInfo = new ArrayList<MarkItem>();
    private List<MarkItem> unPersonInfo = new ArrayList<MarkItem>();
    private String url1;
    private String url2;
    private String url3;
    private String joinGameUrl;
    private String oneKeyUrl;
    private String selectGameUrl;
    private String gameTitle;
    private String gatherPlace;
    private Integer customCount;
    private Date startTime;
    private int gameCode;
    private String userNickname;
    private IntentFilter filter;
    private MyReceiver mReceiver;
    private String userName;
    //选择游戏存储
    private List<MarkItem> gamelist = new ArrayList<MarkItem>();
    //选择游戏Listview
    private AutoListView selectGameList;
    //为listview添加适配器
    selectGameAdapter selectListAdapter;
    //请求选择游戏数据
    private int pageNum = 1;
    private boolean isFirstRequestGameList = true;
    private boolean isReshView = false;//是否是上拉刷新
    private String selectGameName;//选择游戏的名字
    private String selectGameCode;//选择游戏的编码4
    private TextView diaGameName;//一键呼唤所需要的游戏名称
    private TextView diaGameCode;//一键呼唤所需要游戏编码
    private Boolean isShield;
    private int joinGameId;
    private FrameLayout onekeSearch;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        SDKInitializer.initialize(getActivity().getApplicationContext());
        view = inflater.inflate(R.layout.baidumap_activity, container, false);
        onekeSearch = (FrameLayout) view.findViewById(R.id.onkey_search);
        //获取地图控件引用
        if (((GlobalVeriable) getActivity().getApplication()).getLogin())
            userName = ((GlobalVeriable) getActivity().getApplication()).getUserName();
        isShield = ((GlobalVeriable) getActivity().getApplication()).getShield();
        initMap();
        initLocation();
        jianting();
        mReceiver = new MyReceiver();
        filter = new IntentFilter("android.net.conn.CONNECTIVITY_CHANGE");
        getActivity().registerReceiver(mReceiver, filter);
        onekeSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MapStatusUpdate mapStatusUpdate = MapStatusUpdateFactory.newLatLng(myPoint);
                baiduMap.animateMapStatus(mapStatusUpdate);
            }
        });
        return view;
    }

    private void initLocation() {
        locationService = new LocationService(getActivity());
        mLocationListener = new MyLocationListener();
        locationService.registerListener(mLocationListener);
        LocationClientOption locationClientOption = locationService.getDefaultLocationClientOption();
        locationService.setLocationOption(locationClientOption);
        bitmapDescriptor = BitmapDescriptorFactory.fromResource(R.drawable.icon_mark);//初始化自定义定位图标
    }

    public void addMarker() {
        Marker marker = null;
        LatLng point = null;
        MarkerOptions option = null;
        BitmapDescriptor bitmap = BitmapDescriptorFactory
                .fromResource(R.drawable.icon_mark);
        BitmapDescriptor bitmap2 = BitmapDescriptorFactory
                .fromResource(R.drawable.game_icon);
        //定义Maker坐标点

        for (MarkItem info : personInfo) {
            if (personInfo != null) {
                int index = 0;
                // 位置
                point = new LatLng(info.getLastLatitude(), info.getLastLongitude());
                // 图标
                //构建Marker图标
                //构建MarkerOption，用于在地图上添加Marker
                option = new MarkerOptions()
                        .position(point)
                        .icon(bitmap);
                option.animateType(MarkerOptions.MarkerAnimateType.drop);
                //在地图上添加Marker，并显示
                marker = (Marker) (baiduMap.addOverlay(option));

                marker.setZIndex(index);
                /*Bundle bundle = new Bundle();
                bundle.putSerializable("info", info);
                marker.setExtraInfo(bundle);*/
            }
        }
        for (MarkItem info : gameInfo) {
            if (gameInfo != null) {
                int index = 1;
                // 位置
                point = new LatLng(info.getGameLatitude(), info.getGameLongitude());
                gameTitle = info.getGameTitle();
                gatherPlace = info.getGatherPlace();
                customCount = info.getCustomCount();
                startTime = info.getStartTime();
                gameCode = info.getGameCode();
                userNickname = info.getUserNickname();
                // 图标
                //构建Marker图标
                //构建MarkerOption，用于在地图上添加Marker
                option = new MarkerOptions()
                        .position(point)
                        .icon(bitmap2);
                option.animateType(MarkerOptions.MarkerAnimateType.drop);
                //在地图上添加Marker，并显示
                marker = (Marker) (baiduMap.addOverlay(option));
                marker.setZIndex(index);

                Bundle bundle = new Bundle();
                bundle.putSerializable("info", info);
                marker.setExtraInfo(bundle);
            }
        }
    }

    public void jianting() {
        final GlobalVeriable globalVeriable = (GlobalVeriable) getActivity().getApplication();
        baiduMap.setOnMarkerClickListener(new BaiduMap.OnMarkerClickListener() {
            @Override
            public boolean onMarkerClick(Marker marker) {
                if (isShield) {
                    if (globalVeriable.getLogin()) {
                        if (marker.getZIndex() == 0) {
                           /* MarkItem info = (MarkItem) marker.getExtraInfo().get("info");
                            // 生成一个TextView用户在地图中显示InfoWindow
                            TextView location = new TextView(getActivity().getApplicationContext());
                            location.setBackgroundResource(R.color.my_bar_color);
                            location.setPadding(30, 20, 30, 50);
                            //location.setText(info.getUserNickname());
                            LatLng point = marker.getPosition();
                            InfoWindow mInfoWindow = new InfoWindow(location, point, -47);
                            baiduMap.showInfoWindow(mInfoWindow);*/
                        } else if (marker.getZIndex() == 2) {
                            //dialog
                            final CreatingGameDialog dialog = new CreatingGameDialog(getActivity(), R.style.Dialog);
                            //TextView createdUser = (TextView) dialog.findViewById(R.id.map_dialog_creater);
                            // createdUser.setText(userName);
                            dialog.setListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    GlobalVeriable globalVeriable = (GlobalVeriable) getActivity().getApplication();
                                    switch (view.getId()) {
                                        case R.id.map_dialog_game_text:
                                        case R.id.map_dialog_game:
                                            pageNum = 1;
                                            Map args1 = new HashMap();
                                            args1.put("pageNum", pageNum);
                                            args1.put("userName", globalVeriable.getUserName());
                                            selectGameUrl = URLUtil.getURL("gameHead", args1);
                                            new NetAsyncTask(baiduMap.this).execute(selectGameUrl);
                                            gameDialog();
                                            break;
                                        case R.id.map_dialog_positiveButton:
                                            EditText playPlace = (EditText) dialog.findViewById(R.id.map_dialog_playPlace);
                                            EditText peopleCount = (EditText) dialog.findViewById(R.id.map_dialog_peopleCount);
                                            EditText customInfo = (EditText) dialog.findViewById(R.id.map_dialog_customInfo);
                                            Log.i("place", playPlace.getText().toString().length() + "dd");
                                            if (!ValidateUtil.isEmpty(diaGameCode.getText().toString()) && !ValidateUtil.isEmpty(playPlace.getText().toString())) {
                                                if (!globalVeriable.getGameRunning()) {
                                                    Map args = new HashMap();
                                                    args.put("userName", userName);
                                                    args.put("gameCode", Integer.parseInt((String) diaGameCode.getText()));
                                                    args.put("gatherPlace", playPlace.getText().toString());
                                                    if (!ValidateUtil.isEmpty(peopleCount.getText().toString())) {
                                                        args.put("customCount", Integer.parseInt(peopleCount.getText().toString()));
                                                    }
                                                    if (!ValidateUtil.isEmpty(customInfo.getText().toString())) {
                                                        args.put("customInf", customInfo.getText());
                                                    }
                                                    oneKeyUrl = URLUtil.getURL("onekeyConvene", args);
                                                    Log.d("oneKey", oneKeyUrl);
                                                    new NetAsyncTask(baiduMap.this).execute(oneKeyUrl);
                                                } else {
                                                    Toast.makeText(getActivity(), "游戏进行中", Toast.LENGTH_SHORT).show();
                                                }
                                            } else {
                                                Toast.makeText(getActivity(), "游戏名称或游戏地点不能为空", Toast.LENGTH_SHORT).show();
                                            }
                                            break;
                                        case R.id.map_dialog_negativeButton:
                                            dialog.dismiss();
                                            break;
                                        default:
                                            break;
                                    }

                                }
                            });
                            /*WindowManager.LayoutParams params = dialog.getWindow().getAttributes();

                            int width = getActivity().getWindowManager().getDefaultDisplay().getWidth();
                            params.width = (int) (width * 0.5);
                            params.height = getActivity().getWindowManager().getDefaultDisplay().getHeight();
                            Log.i("height", params.width + " " + params.height);
                            // dialog.getWindow().setAttributes(getWidthAndHegith());
                            dialog.getWindow().setAttributes(params);*/
                            dialog.show();
                            // Log.i("dialog", params.width + "sss");
                            diaGameName = (TextView) dialog.findViewById(R.id.map_dialog_game_text);

                            diaGameCode = (TextView) dialog.findViewById(R.id.map_dialog_gamecode);
                        } else {
                            MarkItem info = (MarkItem) marker.getExtraInfo().get("info");
                            final LayoutInflater inflater = LayoutInflater.from(getActivity());
                            final LinearLayout layout = (LinearLayout) inflater.inflate(R.layout.created_game, null);
                            dialog1 = new AlertDialog.Builder(getActivity()).create();
                            dialog1.setCancelable(false);
                            dialog1.show();
                            dialog1.getWindow().setContentView(layout);
                            dialog1.getWindow().setAttributes(getWidthAndHegith(dialog1));
                            final TextView gameId = (TextView) layout.findViewById(R.id.created_game_gameId);
                            gameId.setText(String.valueOf(info.getGameId()));
                            TextView palyPlace = (TextView) layout.findViewById(R.id.play_place);
                            palyPlace.setText(info.getGatherPlace());
                            TextView palyCount = (TextView) layout.findViewById(R.id.playCount);
                            palyCount.setText(String.valueOf(info.getCustomCount()));
                            TextView gameName = (TextView) layout.findViewById(R.id.gameName);
                            gameName.setText(info.getGameTitle());
                            TextView createdName = (TextView) layout.findViewById(R.id.createdName);
                            createdName.setText(info.getUserNickname());
                            TextView playJoiner = (TextView) layout.findViewById(R.id.playJoiner);
                            playJoiner.setText(info.getUserNickname() + "（发起人）");
                            TextView gameContent = (TextView) layout.findViewById(R.id.gameContent);
                            gameContent.setText(info.getGameContent());
                            TextView recommendCount = (TextView) layout.findViewById(R.id.referrals);
                            recommendCount.setText(String.valueOf(info.getRecommendCount()));
                            Button btnOK = (Button) layout.findViewById(R.id.positiveButton);

                            RelativeLayout membersInfo = (RelativeLayout) layout.findViewById(R.id.created_game_members);
                            membersInfo.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    TextView gameIdText = (TextView) layout.findViewById(R.id.created_game_gameId);
                                    Intent intent = new Intent(getActivity(), TeamerNameActivity.class);
                                    intent.putExtra("gameId", gameIdText.getText().toString());
                                    startActivity(intent);
                                }
                            });

                            btnOK.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    if (!globalVeriable.getGameRunning()) {
                                        GlobalVeriable globalVeriable = (GlobalVeriable) getActivity().getApplication();
                                        TextView gameIdText = (TextView) layout.findViewById(R.id.created_game_gameId);
                                        joinGameId = Integer.parseInt(gameIdText.getText().toString());
                                        Map args = new HashMap();
                                        args.put("userName", userName);
                                        args.put("gameId", joinGameId);
                                        joinGameUrl = URLUtil.getURL("joinGame", args);
                                        Log.i("joingame", joinGameUrl);
                                        globalVeriable.setMySelf(true);
                                        new NetAsyncTask(baiduMap.this).execute(joinGameUrl);
                                        globalVeriable.setGameId(joinGameId);
                                    } else {
                                        Toast.makeText(getActivity(), "你已经加入游戏", Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });
                            Button btnCancel = (Button) layout.findViewById(R.id.negativeButton);
                            btnCancel.setOnClickListener(new View.OnClickListener() {

                                @Override
                                public void onClick(View v) {
                                    dialog1.dismiss();
                                }
                            });

                        }
                    } else {
                        AlertDialog.Builder builder = new AlertDialog.Builder(
                                getActivity());
                        builder.setTitle("提示");
                        builder.setInverseBackgroundForced(true);
                        builder.setMessage("你还未登录，是否现在登陆");
                        builder.setIcon(R.drawable.logo);
                        builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {

                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                startActivity(new Intent(getActivity(), Login.class));
                            }
                        });
                        builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {

                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        });

                        builder.show();

                        //Toast.makeText(getActivity(),"你还未登录，请先登录！！！",Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(getActivity(), "功能已被禁用，好好学习！", Toast.LENGTH_SHORT).show();
                }
                return true;

            }

        });

        baiduMap.setOnMapClickListener(new BaiduMap.OnMapClickListener() {
            @Override
            public void onMapClick(LatLng latLng) {
                baiduMap.hideInfoWindow();
            }

            @Override
            public boolean onMapPoiClick(MapPoi mapPoi) {
                return false;
            }
        });

    }

    public void initMap() {
        mMapView = (MapView) view.findViewById(R.id.bmapView);
        baiduMap = mMapView.getMap();
        MapStatusUpdate msu = MapStatusUpdateFactory.zoomTo(17.0f);//设置地图默认显示比例
        baiduMap.setMapStatus(msu);
        baiduMap.setMapType(BaiduMap.MAP_TYPE_NORMAL);

        //mBaiduMap.setMyLocationEnabled(true);
    }

    public void onStart() {
        // TODO Auto-generated method stub
        //  mBaiduMap.setMyLocationEnabled(true);
        /*if (!(locationService.getClient().isStarted()))
            locationService.start();*/
        if (!locationService.getClient().isStarted())
            locationService.start();
        super.onStart();

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        //在activity执行onDestroy时执行mMapView.onDestroy()，实现地图生命周期管理
        mMapView.onDestroy();
    }

    @Override
    public void onResume() {

        super.onResume();
        //在activity执行onResume时执行mMapView. onResume ()，实现地图生命周期管理
        //缓存数据
        CallBackPage C = MemorySelectGame.getInstance().getCbp1();
        gamelist.addAll(C.getDatalist());
        mMapView.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
        try {
            getActivity().unregisterReceiver(mReceiver);
            //在activity执行onPause时执行mMapView. onPause ()，实现地图生命周期管理}
        } catch (Exception e) {

        }
        mMapView.onPause();
    }

    @Override
    public void onStop() {
        // TODO Auto-generated method stub
        //mBaiduMap.setMyLocationEnabled(false);
        // locationService.unregisterListener(mListener); //注销掉监听
        // locationService.stop(); //停止定位服务
        locationService.stop();
        super.onStop();
    }

    @Override
    public void onRefresh() {
        isReshView = true;
        pageNum = 1;
        Map args = new HashMap();
        args.put("pageNum", pageNum);
        selectGameUrl = URLUtil.getURL("gameHead", args);
        Log.i("url1", selectGameUrl);
        new NetAsyncTask(baiduMap.this).execute(selectGameUrl);
    }

    @Override
    public void onLoad() {
        isReshView = false;
        pageNum++;
        Map args = new HashMap();
        args.put("pageNum", pageNum);
        selectGameUrl = URLUtil.getURL("gameHead", args);
        Log.i("url", selectGameUrl);
        new NetAsyncTask(baiduMap.this).execute(selectGameUrl);
    }


    /*
    *地图定位location的监听以及重写返回方法
    *
     */
    private class MyLocationListener implements BDLocationListener {
        private int index = 2;

        @Override
        public void onReceiveLocation(BDLocation Location) {
            MyLocationData data = new MyLocationData.Builder()
                    .accuracy(Location.getRadius())
                    .latitude(Location.getLatitude())
                    .longitude(Location.getLongitude())
                    .build();
            baiduMap.setMyLocationData(data);
            //删除上一个标记点
            if (marker != null)
                marker.remove();

            //添加我的位置覆盖物
            myPoint = new LatLng(Location.getLatitude(), Location.getLongitude());

            BitmapDescriptor bitmap = BitmapDescriptorFactory
                    .fromResource(R.drawable.start);
            OverlayOptions myOption = new MarkerOptions()
                    .position(myPoint)
                    .icon(bitmap);
            marker = (Marker) baiduMap.addOverlay(myOption);
            marker.setZIndex(index);
            //设置自定义图标
            MyLocationConfiguration config = new MyLocationConfiguration(MyLocationConfiguration.LocationMode.NORMAL, true, bitmapDescriptor);
            baiduMap.setMyLocationConfigeration(config);

            // Toast.makeText(getActivity().getApplicationContext(),Location.getLatitude()+"222",Toast.LENGTH_SHORT).show();
            if (isFirstIn) {
                LatLng latLng = new LatLng(Location.getLatitude(), Location.getLongitude());
                MapStatusUpdate msu = MapStatusUpdateFactory.newLatLng(latLng);
                baiduMap.animateMapStatus(msu);
                isFirstIn = false;
            }


            //  Toast.makeText(getActivity().getApplicationContext(), String.valueOf(Location.getLocType()), Toast.LENGTH_SHORT).show();        }

        }
    }

    /*请求数据*/
    @Override
    public void getResult(CallBackPage result) {
        if (result != null) {
            GlobalVeriable globalVeriable = (((GlobalVeriable) getActivity().getApplication()).getInstance());
            Log.i("ssss", result.getDatalist().size() + "s");
            if (result.getDatalist().size() > 0) {
                MainBaidu mainBaidu = (MainBaidu) getActivity();
                MarkItem bean = (MarkItem) result.getDatalist().get(0);
                Log.i("resultxds", bean.getSelect());
                if (bean.getSelect().equals("2")) {
                    personInfo.addAll(result.getDatalist());
                } else if (bean.getSelect().equals("1")) {
                    Log.i("gameInfo", "game");
                    gameInfo.addAll(result.getDatalist());
                } else if (bean.getSelect().equals("3")) {
                    unPersonInfo.addAll(result.getDatalist());
                } else if (bean.getSelect().equals("6")) {
                    if (isReshView) {
                        gamelist.clear();
                        selectGameList.onRefreshComplete();
                        gamelist.addAll(result.getDatalist());
                    } else {
                        gamelist.addAll(result.getDatalist());
                        selectGameList.onLoadComplete();
                    }
                    selectListAdapter.notifyDataSetChanged();
                    selectGameList.setPageSize(result.getPageSize());
                    selectGameList.setResultSize(result.getDatalist().size());
                } else if (bean.getSelect().equals("4")) {
                    Toast.makeText(getActivity(), "加入成功", Toast.LENGTH_LONG).show();
                    if (joinGameId != 0) {
                        ArrayList<CharSequence> list = new ArrayList<CharSequence>();
                        list.add(String.valueOf(joinGameId));
                        //TODO 用户名
                        list.add(((GlobalVeriable) getActivity().getApplication()).getUserName());
                        ((MainBaidu) getActivity()).sendToService(list, 4);
                    }
                    dialog1.dismiss();
                    globalVeriable.setGameRunning(true);
                    //创建聊天室界面
                    mainBaidu.refresh();
                } else if (bean.getSelect().equals("5")) {
                    globalVeriable.setGameRunning(true);
                    globalVeriable.setGameId(bean.getGameId());
                    ArrayList<CharSequence> list = new ArrayList<CharSequence>();
                    list.add(String.valueOf(bean.getGameId()));
                    list.add((((GlobalVeriable) getActivity().getApplication()).getUserName()));
                    ((MainBaidu) getActivity()).sendToService(list, 3);
                    globalVeriable.setGameFounder(true);
                    Log.i("ganmeId", bean.getGameId() + "" + list.get(0) + list.get(1));
                    //创建聊天室界面
                    mainBaidu.refresh();
                    Toast.makeText(getActivity(), "呼唤成功", Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(getActivity(), "已加入游戏", Toast.LENGTH_LONG).show();
                }
                addMarker();
            }
            isFirstRequestGameList = false;
        }
    }

    @Override
    public CallBackPage doInBackground(String url) {
        HttpUtil httpUtil = new HttpUtil(url);
        byte[] bytes = httpUtil.getHttpData();
        if (bytes != null) {
            if (url.equals(url2)) {
                PersonInfoServiceImpl service = new PersonInfoServiceImpl();
                return service.getPersonInfoHeadInf(new String(bytes));
            } else if (url.equals(selectGameUrl)) {
                SelectGameServiceImpl service = new SelectGameServiceImpl();
                return service.getGameHeadInf(new String(bytes));
            } else if (url.equals(url1)) {
                GameInfoServiceImpl service = new GameInfoServiceImpl();
                return service.getGameInfoHeadInf(new String(bytes));
            } else if (url.equals(joinGameUrl)) {
                JoinGameServiceImpl service = new JoinGameServiceImpl();
                return service.getJoinGameHeadInf(new String(bytes));
            } else {
                CreateGameServiceImpl service = new CreateGameServiceImpl();
                return service.getCreateGameHeadInf(new String(bytes));
            }
        }
        return null;
    }

    public class MyReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            if (!checkNetWorkStatus(getActivity())) {
                setNetwork();
            } else {
                if (((GlobalVeriable) getActivity().getApplication()).getLogin()) {
                    Map args = new HashMap();
                    args.put("userName", userName);
                    url2 = URLUtil.getURL("userCanInvite", args);
                    new NetAsyncTask(baiduMap.this).execute(url2);

                    Map args2 = new HashMap();
                    args2.put("userName", userName);
                    url1 = URLUtil.getURL("gameCanJoin", args2);
                    Log.i("url", url1 + url2 + "ss");
                    new NetAsyncTask(baiduMap.this).execute(url1);
                }
//            Map args3 = new HashMap();
//            args3.put("userName", "oytt");
//            url3 = URLUtil.getURL("amUserList", args3);
//            new NetAsyncTask(baiduMap.this).execute(url3);
            }
        }
    }

    /*网络连接检测*/
    private boolean checkNetWorkStatus(Context context) {
        boolean result;
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netinfo = cm.getActiveNetworkInfo();
        if (netinfo != null && netinfo.isConnected()) {
            result = true;
        } else {
            result = false;
        }
        return result;
    }


    /**
     * 网络未连接时，调用设置方法
     */
    private void setNetwork() {

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

    public void gameDialog() {
        LayoutInflater inflater = LayoutInflater.from(getActivity());
        final LinearLayout layout = (LinearLayout) inflater.inflate(R.layout.select_game, null);
        selectDialog = new AlertDialog.Builder(getActivity()).create();
        selectDialog.setCancelable(false);
        selectDialog.show();
        selectDialog.getWindow().setContentView(layout);
        selectDialog.getWindow().setAttributes(getWidthAndHegith(selectDialog));
        selectGameList = (AutoListView) layout.findViewById(R.id.select_game_list);
        selectListAdapter = new selectGameAdapter(getActivity(), gamelist);
        selectGameList.setAdapter(selectListAdapter);
        selectGameList.setOnLoadListener(this);
        selectGameList.setOnRefreshListener(this);
        selectGameList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long C) {
                Log.i("baiduMap arg2", String.valueOf(isFirstRequestGameList) + "/" + String.valueOf(arg2));
                if ((!isFirstRequestGameList && arg2 > 0) || (!isReshView && arg2 > 1)) {
                    ImageView img = (ImageView) layout.findViewById(R.id.select_game_image);
                    img.setImageResource(R.drawable.select_icon);
                    // 设置适配器的选中项
                    selectListAdapter.setSelectItem(arg2 - 1);
                    Log.i("您点击了", arg2 - 1 + ";");
                    TextView gameCode = (TextView) arg1.findViewById(R.id.select_game_code);
                    TextView gameTitle = (TextView) arg1.findViewById(R.id.select_game_text);
                    selectGameCode = String.valueOf(gameCode.getText());
                    selectGameName = String.valueOf(gameTitle.getText());
                    Log.i("gameInfo", selectGameCode + selectGameName + "ss");
                    // 更新列表框
                    selectListAdapter.notifyDataSetInvalidated();
                }
            }
        });

     /*   selectGameList.setXListViewListener(new XListView.IXListViewListener() {
            @Override
            public void onRefresh() {
                isReshView = true;
                pageNum = 1;
                Map args = new HashMap();
                args.put("pageNum", pageNum);
                selectGameUrl = URLUtil.getURL("gameHead", args);
                new NetAsyncTask(baiduMap.this).execute(selectGameUrl);
            }

            @Override
            public void onLoadMore() {
                isReshView = false;
                pageNum++;
                Map args = new HashMap();
                args.put("pageNum", pageNum);
                selectGameUrl = URLUtil.getURL("gameHead", args);
                new NetAsyncTask(baiduMap.this).execute(selectGameUrl);
            }
        });*/

        Button okButton = (Button) layout.findViewById(R.id.positiveButton);
        okButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                diaGameName.setText(selectGameName);
                diaGameCode.setText(selectGameCode);
                selectDialog.dismiss();
            }
        });
        Button cancelButton = (Button) layout.findViewById(R.id.negativeButton);
        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectDialog.dismiss();
            }
        });
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
