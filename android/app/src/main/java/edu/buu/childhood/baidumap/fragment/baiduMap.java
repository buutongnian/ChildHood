package edu.buu.childhood.baidumap.fragment;


import android.app.Fragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

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
import java.util.Iterator;
import java.util.List;

import edu.buu.childhood.R;
import edu.buu.childhood.baidumap.dialog.gameDialog;
import edu.buu.childhood.baidumap.dialog.mapDialog;
import edu.buu.childhood.baidumap.dialog.selectGame;
import edu.buu.childhood.baidumap.poj.baiduItemBean;
import edu.buu.childhood.baidumap.service.LocationService;

public class baiduMap extends Fragment {
    MapView mMapView = null;
    BaiduMap baiduMap;
    private LatLng myPoint;
    private MyLocationListener mLocationListener;
    private LocationService locationService;
    private boolean isFirstIn = true;//是否第一次请求定位
    private BitmapDescriptor bitmapDescriptor;//自定义定位图标
    private Marker marker;
    private View view;
    public static List<baiduItemBean> infos = new ArrayList<baiduItemBean>();
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        SDKInitializer.initialize(getActivity().getApplicationContext());
        view = inflater.inflate(R.layout.baidumap_activity, container, false);
        infos.add(new baiduItemBean(39.998397, 116.357981, "健健小朋友"));
        infos.add(new baiduItemBean(39.998597, 116.367897,  "涛涛小朋友" ));
        infos.add(new baiduItemBean(34.245152, 108.971972,  "嘉铭小朋友" ));
        infos.add(new baiduItemBean(34.242152, 108.971971, "楚嫣小朋友"));
        infos.add(new baiduItemBean(39.998618, 116.436457, "诚诚小朋友"));
        //获取地图控件引用
        initMap();
        initLocation();
        addMarker();
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

    private void addMarker() {
        Marker marker=null;
        LatLng point=null;
        OverlayOptions option=null;
        BitmapDescriptor bitmap = BitmapDescriptorFactory
                .fromResource(R.drawable.icon_mark);
        //定义Maker坐标点
        for (baiduItemBean info : infos)
        {
            // 位置
             point= new LatLng(info.getLatitude(), info.getLongitude());
            // 图标
            //构建Marker图标
            Log.i("info",info.toString());

            //构建MarkerOption，用于在地图上添加Marker
           option = new MarkerOptions()
                    .position(point)
                    .icon(bitmap);
            //在地图上添加Marker，并显示
            marker = (Marker)(baiduMap.addOverlay(option));
            Bundle bundle = new Bundle();
            bundle.putSerializable("info", info);
            marker.setExtraInfo(bundle);
        }

        baiduMap.setOnMarkerClickListener(new BaiduMap.OnMarkerClickListener() {
            @Override
            public boolean onMarkerClick(Marker marker) {
                baiduItemBean info = (baiduItemBean) marker.getExtraInfo().get("info");
                Toast.makeText(getActivity(),info.getName()+"",Toast.LENGTH_SHORT).show();
                // 生成一个TextView用户在地图中显示InfoWindow
                TextView location = new TextView(getActivity().getApplicationContext());
                location.setBackgroundResource(R.color.my_bar_color);
                location.setPadding(30, 20, 30, 50);
                location.setText(info.getName());
                LatLng point= marker.getPosition();
                InfoWindow mInfoWindow = new InfoWindow(location, point, -47);
                baiduMap.showInfoWindow(mInfoWindow);

                //dialog

                final mapDialog dialog = new mapDialog(getActivity(), R.style.Dialog);
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
        Log.d("baidumap", baiduMap + "");
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
                    .fromResource(R.drawable.icon_mark);
            OverlayOptions myOption = new MarkerOptions()
                    .position(myPoint)
                    .icon(bitmap);
            marker = (Marker) baiduMap.addOverlay(myOption);
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


            //  Toast.makeText(getActivity().getApplicationContext(), String.valueOf(Location.getLocType()), Toast.LENGTH_SHORT).show();

        }
    }

}
