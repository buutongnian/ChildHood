package edu.buu.childhood.baidumap;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
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

import edu.buu.childhood.R;
import edu.buu.childhood.edu.buu.childhood.baidumap.service.LocationService;

public class baiduMapActivity extends AppCompatActivity {
    MapView mMapView = null;
    BaiduMap mBaiduMap;
    Context context=this;
    private LatLng myPoint;

    //private LocationClient mLocationClient;
    private MyLocationListener mLocationListener;
    private LocationService locationService;
    private boolean isFirstIn = true;//是否第一次请求定位
    private BitmapDescriptor bitmapDescriptor;//自定义定位图标
    private Marker marker;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SDKInitializer.initialize(getApplicationContext());
        setContentView(R.layout.baidumap_activity);
        //获取地图控件引用
        initMap();
        initLocation();
        addMarker();


    }

    private void initLocation() {
         locationService=new LocationService(this);
        mLocationListener=new MyLocationListener();
        locationService.registerListener(mLocationListener);
        LocationClientOption locationClientOption=locationService.getDefaultLocationClientOption();
        locationService.setLocationOption(locationClientOption);
        bitmapDescriptor =BitmapDescriptorFactory.fromResource(R.drawable.icon_marka1);//初始化自定义定位图标
    }

    private void addMarker() {
        //定义Maker坐标点
         LatLng point = new LatLng(39.998397, 116.357981);
        LatLng point1 = new LatLng(39.998618, 116.436457);
        //构建Marker图标
        BitmapDescriptor bitmap = BitmapDescriptorFactory
                .fromResource(R.drawable.icon_marka1);
        //构建MarkerOption，用于在地图上添加Marker
        OverlayOptions option = new MarkerOptions()
                .position(point)
                .icon(bitmap);
        OverlayOptions option1 = new MarkerOptions()
                .position(point1)
                .icon(bitmap);

        //在地图上添加Marker，并显示
        mBaiduMap.addOverlay(option);
        mBaiduMap.addOverlay(option1);
       // mBaiduMap.addOverlay(myOption);
        mBaiduMap.setOnMarkerClickListener(new BaiduMap.OnMarkerClickListener(){

            @Override
            public boolean onMarkerClick(Marker marker) {
               LatLng point= marker.getPosition();
               TextView tx=new TextView(getApplicationContext());
                tx.setText("zheshi");
                InfoWindow mInfoWindow = new InfoWindow(tx, point, -47);
                mBaiduMap.showInfoWindow(mInfoWindow);

                return true;
            }
        });
        mBaiduMap.setOnMapClickListener(new BaiduMap.OnMapClickListener() {
            @Override
            public void onMapClick(LatLng latLng) {
                mBaiduMap.hideInfoWindow();
            }

            @Override
            public boolean onMapPoiClick(MapPoi mapPoi) {
                return false;
            }
        });
    }

    private void initMap() {
        mMapView = (MapView) findViewById(R.id.bmapView);
        mBaiduMap = mMapView.getMap();
       MapStatusUpdate msu= MapStatusUpdateFactory.zoomTo(15.0f);//设置地图默认显示比例
        mBaiduMap.setMapStatus(msu);
        mBaiduMap.setMapType(BaiduMap.MAP_TYPE_NORMAL);

        //mBaiduMap.setMyLocationEnabled(true);
    }

    protected void onStart() {
        // TODO Auto-generated method stub
      //  mBaiduMap.setMyLocationEnabled(true);
        /*if (!(locationService.getClient().isStarted()))
            locationService.start();*/
        if(!locationService.getClient().isStarted())
            locationService.start();
        super.onStart();

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //在activity执行onDestroy时执行mMapView.onDestroy()，实现地图生命周期管理
        mMapView.onDestroy();
    }

    @Override
    protected void onResume() {
        super.onResume();
        //在activity执行onResume时执行mMapView. onResume ()，实现地图生命周期管理
        mMapView.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        //在activity执行onPause时执行mMapView. onPause ()，实现地图生命周期管理
        mMapView.onPause();
    }

    @Override
    protected void onStop() {
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
            MyLocationData data = new MyLocationData.Builder()//
                    .accuracy(Location.getRadius())//
                    .latitude(Location.getLatitude())//
                    .longitude(Location.getLongitude())//
                    .build();
            mBaiduMap.setMyLocationData(data);
            //删除上一个标记点
            if(marker!=null)
            marker.remove();

            //添加我的位置覆盖物
            myPoint=new LatLng(Location.getLatitude(),Location.getLongitude());

            BitmapDescriptor bitmap = BitmapDescriptorFactory
                    .fromResource(R.drawable.icon_marka1);
            OverlayOptions myOption= new MarkerOptions()
                    .position(myPoint)
                    .icon(bitmap);
            marker=(Marker) mBaiduMap.addOverlay(myOption);
            //设置自定义图标
            MyLocationConfiguration config=new MyLocationConfiguration(MyLocationConfiguration.LocationMode.NORMAL,true,bitmapDescriptor);
            mBaiduMap.setMyLocationConfigeration(config);
            Toast.makeText(context,Location.getLatitude()+"222",Toast.LENGTH_SHORT).show();
            if (isFirstIn) {
                LatLng latLng = new LatLng(Location.getLatitude(), Location.getLongitude());
                MapStatusUpdate msu = MapStatusUpdateFactory.newLatLng(latLng);
                mBaiduMap.animateMapStatus(msu);
                isFirstIn = false;
            }


            Toast.makeText(getApplicationContext(), String.valueOf(Location.getLocType()), Toast.LENGTH_SHORT).show();

        }
    }

}
