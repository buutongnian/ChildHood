package edu.buu.childhood.baidumap.fragment;


import android.app.Dialog;
import android.app.Fragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClientOption;
import com.baidu.mapapi.SDKInitializer;
import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.BitmapDescriptor;
import com.baidu.mapapi.map.BitmapDescriptorFactory;
import com.baidu.mapapi.map.InfoWindow;
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

import edu.buu.childhood.R;
import edu.buu.childhood.baidumap.dialog.CreatingGameDialog;
import edu.buu.childhood.baidumap.dialog.selectGame;
import edu.buu.childhood.baidumap.poj.MarkItem;
import edu.buu.childhood.baidumap.service.GameInfoServiceImpl;
import edu.buu.childhood.baidumap.service.LocationService;
import edu.buu.childhood.baidumap.service.PersonInfoServiceImpl;
import edu.buu.childhood.common.CallBackPage;
import edu.buu.childhood.util.CallBack;
import edu.buu.childhood.util.HttpUtil;
import edu.buu.childhood.util.NetAsyncTask;
import edu.buu.childhood.util.URLUtil;

public class baiduMap extends Fragment implements CallBack {
    MapView mMapView = null;
    BaiduMap baiduMap;
    private LatLng myPoint;
    private MyLocationListener mLocationListener;
    private LocationService locationService;
    private boolean isFirstIn = true;//是否第一次请求定位
    private BitmapDescriptor bitmapDescriptor;//自定义定位图标
    private Marker marker;
    private View view;
    private List<MarkItem> personInfo = new ArrayList<MarkItem>();
    private List<MarkItem> gameInfo = new ArrayList<MarkItem>();
    private List<MarkItem> unPersonInfo = new ArrayList<MarkItem>();
    private String url1;
    private String url2;
    private String url3;
    private String gatherPlace;
    private Integer customCount;
    private Date startTime;
    private int gameCode;
    private String userNickname;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        SDKInitializer.initialize(getActivity().getApplicationContext());
        view = inflater.inflate(R.layout.baidumap_activity, container, false);

        //获取地图控件引用
        initMap();
        initLocation();
        jianting();
        Map args = new HashMap();
        args.put("userName", "lcc");
        url2 = URLUtil.getURL("userCanInvite", args);
        new NetAsyncTask(this).execute(url2);

        Map args2 = new HashMap();
        args2.put("userName", "lcc");
        url1 = URLUtil.getURL("gameCanJoin", args2);
        new NetAsyncTask(this).execute(url1);


//            Map args3 = new HashMap();
//            args3.put("userName", "oytt");
//            url3 = URLUtil.getURL("amUserList", args3);
//            new NetAsyncTask(this).execute(url3);

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
        OverlayOptions option = null;
        BitmapDescriptor bitmap = BitmapDescriptorFactory
                .fromResource(R.drawable.icon_mark);
        BitmapDescriptor bitmap2 = BitmapDescriptorFactory
                .fromResource(R.drawable.game_icon);
        //定义Maker坐标点
        for (MarkItem info : personInfo) {
            int index = 0;
            // 位置
            point = new LatLng(info.getLastLatitude(), info.getLastLongitude());
            // 图标
            //构建Marker图标
            //构建MarkerOption，用于在地图上添加Marker
            option = new MarkerOptions()
                    .position(point)
                    .icon(bitmap);
            //在地图上添加Marker，并显示
            marker = (Marker) (baiduMap.addOverlay(option));
            marker.setZIndex(index);
            Bundle bundle = new Bundle();
            bundle.putSerializable("info", info);
            marker.setExtraInfo(bundle);
        }
        for (MarkItem info : gameInfo) {
            int index = 1;
            // 位置
            point = new LatLng(info.getGameLatitude(), info.getGameLongitude());
            gatherPlace = info.getGatherPlace();
            customCount = info.getCustomCount();
            startTime = info.getStartTime();
            gameCode = info.getGameCode();
            userNickname = info.getUserNickname();
            Log.d("PERSON1", point + "");

            // 图标
            //构建Marker图标
            //构建MarkerOption，用于在地图上添加Marker
            option = new MarkerOptions()
                    .position(point)
                    .icon(bitmap2);
            //在地图上添加Marker，并显示
            marker = (Marker) (baiduMap.addOverlay(option));
            marker.setZIndex(index);

            Bundle bundle = new Bundle();
            bundle.putSerializable("info", info);
            marker.setExtraInfo(bundle);
        }
    }

    public void jianting() {
        baiduMap.setOnMarkerClickListener(new BaiduMap.OnMarkerClickListener() {
            @Override
            public boolean onMarkerClick(Marker marker) {
                if (marker.getZIndex() == 0) {
                    MarkItem info = (MarkItem) marker.getExtraInfo().get("info");
                    // 生成一个TextView用户在地图中显示InfoWindow
                    TextView location = new TextView(getActivity().getApplicationContext());
                    location.setBackgroundResource(R.color.my_bar_color);
                    location.setPadding(30, 20, 30, 50);
                    location.setText(info.getUserNickname());
                    LatLng point = marker.getPosition();
                    InfoWindow mInfoWindow = new InfoWindow(location, point, -47);
                    baiduMap.showInfoWindow(mInfoWindow);
                } else if (marker.getZIndex() == 2) {
                    //dialog
                    final CreatingGameDialog dialog = new CreatingGameDialog(getActivity(), R.style.Dialog);
                    dialog.setListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            switch (view.getId()) {
                                case R.id.map_dialog_game:
                                    selectGame.Builder builder = new selectGame.Builder(getActivity());
                                    //builder.setMessage("这个就是自定义的提示框");
                                    //builder.setTitle("提示");
                                    builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                                        public void onClick(DialogInterface dialog, int which) {
                                            dialog.dismiss();
                                            //设置你的操作事项
                                        }
                                    });
                                    builder.setNegativeButton("取消",
                                            new android.content.DialogInterface.OnClickListener() {
                                                public void onClick(DialogInterface dialog, int which) {
                                                    dialog.dismiss();
                                                }
                                            });

                                    builder.create().show();


                                    break;
                                case R.id.map_dialog_playsite:

                                    break;
                                case R.id.map_dialog_defingame:

                                    break;
                                case R.id.map_dialog_game_text:


                                case R.id.map_dialog_positiveButton:

                                    break;
                                case R.id.map_dialog_negativeButton:
                                    dialog.dismiss();
                                    break;
                                default:
                                    break;
                            }
                        }
                    });
                    dialog.show();
                } else {
                    LayoutInflater inflater = LayoutInflater.from(getActivity());
                    LinearLayout layout = (LinearLayout) inflater.inflate(R.layout.created_game, null);
                    final Dialog dialog1 = new AlertDialog.Builder(getActivity()).create();
                    dialog1.setCancelable(false);
                    dialog1.show();
                    dialog1.getWindow().setContentView(layout);
                    TextView palyPlace = (TextView) layout.findViewById(R.id.play_place);
                    palyPlace.setText(gatherPlace);
                    TextView palyCount = (TextView) layout.findViewById(R.id.playCount);
                    palyCount.setText(String.valueOf(customCount));
                    TextView palyTime = (TextView) layout.findViewById(R.id.playTime);
                    palyTime.setText((CharSequence) startTime);
                    TextView gameName = (TextView) layout.findViewById(R.id.gameName);
                    gameName.setText(String.valueOf(gameCode));
                    TextView createdName = (TextView) layout.findViewById(R.id.createdName);
                    createdName.setText(userNickname);


                    Button btnOK = (Button) layout.findViewById(R.id.positiveButton);
                    btnOK.setOnClickListener(new View.OnClickListener() {

                        @Override
                        public void onClick(View v) {
                            dialog1.dismiss();
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
        MapStatusUpdate msu = MapStatusUpdateFactory.zoomTo(15.0f);//设置地图默认显示比例
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
        mMapView.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
        //在activity执行onPause时执行mMapView. onPause ()，实现地图生命周期管理
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
        MarkItem bean = (MarkItem) result.getDatalist().get(0);
        if (bean.getSelect().equals("2")) {
            personInfo.addAll(result.getDatalist());

        } else if (bean.getSelect().equals("1")) {
            gameInfo.addAll(result.getDatalist());
        } else if (bean.getSelect().equals("3")) {
            unPersonInfo.addAll(result.getDatalist());
        }
        addMarker();
    }


    @Override
    public CallBackPage doInBackground(String url) {
        if (url == url2) {
            HttpUtil httpUtil2 = new HttpUtil(url2);
            PersonInfoServiceImpl service = new PersonInfoServiceImpl();
            return service.getPersonInfoHeadInf(new String(httpUtil2.getHttpData()));
        } else {
            HttpUtil httpUtil1 = new HttpUtil(url1);
            GameInfoServiceImpl service = new GameInfoServiceImpl();
            return service.getGameInfoHeadInf(new String(httpUtil1.getHttpData()));
        }
//         else {
//            HttpUtil httpUtil3 = new HttpUtil(url3);
//            UnPersonInfoServiceImpl service = new UnPersonInfoServiceImpl();
//            return service.getUnPersonInfoHeadInf(new String(httpUtil3.getHttpData()));
//        }
    }

}
