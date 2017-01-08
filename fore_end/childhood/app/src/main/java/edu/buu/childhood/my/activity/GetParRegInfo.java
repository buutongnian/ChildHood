package edu.buu.childhood.my.activity;

import android.app.DatePickerDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Point;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupMenu;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClientOption;
import com.baidu.mapapi.SDKInitializer;
import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.BitmapDescriptor;
import com.baidu.mapapi.map.MapStatus;
import com.baidu.mapapi.map.MapStatusUpdate;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.Marker;
import com.baidu.mapapi.map.MyLocationData;
import com.baidu.mapapi.model.LatLng;

import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import edu.buu.childhood.R;
import edu.buu.childhood.baidumap.service.LocationService;
import edu.buu.childhood.common.C;
import edu.buu.childhood.common.CallBackPage;
import edu.buu.childhood.common.GlobalVeriable;
import edu.buu.childhood.common.activity.BaseAvtivity;
import edu.buu.childhood.my.pojo.FindPartnerInfo;
import edu.buu.childhood.my.service.DeleteFindServiceImpl;
import edu.buu.childhood.my.service.FindFirstServiceImpl;
import edu.buu.childhood.my.service.FindHasRegServiceImpl;
import edu.buu.childhood.my.service.UpdateFindServiceImpl;
import edu.buu.childhood.util.CallBack;
import edu.buu.childhood.util.CloseActivity;
import edu.buu.childhood.util.HttpUtil;
import edu.buu.childhood.util.NetAsyncTask;
import edu.buu.childhood.util.NetWorkStatusUtil;
import edu.buu.childhood.util.URLUtil;
import edu.buu.childhood.util.ValidateUtil;


/**
 * Created by lcc on 2016/9/8.
 */
public class GetParRegInfo extends BaseAvtivity implements CallBack {
    private MyLocationListener mLocationListener;
    private LocationService locationService;
    //自定义定位图标
    private BitmapDescriptor bitmapDescriptor;
    MapView mMapView = null;
    BaiduMap baiduMap;
    private Marker marker;
    private LatLng myPoint;
    private Point lab;
    //是否第一次请求定位
    private boolean isFirstIn = true;
    //界面上基本数据
    private EditText name;
    private EditText sex;
    private TextView date;
    private EditText niCheng;
    private EditText oldStreet;
    private EditText wantToSay;
    private ImageView button;
    //url
    private String findFirst;
    private String getFind;
    private String deleteFind;
    private String updateFind;
    //判断是否有登记小伙伴的信息
    private Boolean isRegister = false;
    //判断是否清除位置
    private Boolean isDelect = true;
    //用户人账号
    private String userName;
    //注册广播
    private IntentFilter filter;
    private MyReceiver mReceiver;
    //网络出错
    private ImageView netError;
    //清除地理位置
    private Button delete;
    // 性别选择
    private String[] sexArry = new String[]{"女", "男"};
    //选择性别
    private LinearLayout SelectSex;
    //性别转换形式
    private String sexTrans;
    //声明spinner对象
    private Spinner spinner;
    //加在spinner中第一项
    private String first;
    //地图小图标
    private ImageView label;
    //小图标位置
    private double xx;
    private double yy;
    private LinearLayout selectBirthday;
    private TextView tvBirthdy;

    private int year;
    private int month;
    private int day;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        CloseActivity.activityList.add(this);
        SDKInitializer.initialize(this.getApplicationContext());
        setContentView(R.layout.get_par_reg_info);
        //注册广播
        mReceiver = new MyReceiver();
        filter = new IntentFilter("android.net.conn.CONNECTIVITY_CHANGE");
        //根据id获取对象
        //注册事件
        sex = (EditText) findViewById(R.id.findPartnerSex);
        sex.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Dialog();
            }
        });
        SelectSex = (LinearLayout) findViewById(R.id.SelectSex);
        SelectSex.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Dialog();
            }
        });
        selectBirthday = (LinearLayout) findViewById(R.id.dateSelect);
        selectBirthday.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                initDiallog();
            }
        });
        niCheng = (EditText) findViewById(R.id.findPartnerName);
        date = (TextView) findViewById(R.id.get_parter_time);
        oldStreet = (EditText) findViewById(R.id.findPartnerOldStreet);
        wantToSay = (EditText) findViewById(R.id.findPartnerWantToSay);
        netError = (ImageView) findViewById(R.id.netError);
        tvBirthdy = (TextView) findViewById(R.id.get_parter_time);
        delete = (Button) findViewById(R.id.findPartner_delete);
        delete.setOnClickListener(new View.OnClickListener() {
            //点击显示下拉菜单
            @Override
            public void onClick(View v) {
                showPopupMenu(v);
            }
        });
        if (((GlobalVeriable) getApplication()).getLogin()) {
            userName = ((GlobalVeriable) getApplication()).getUserName();
        }
        button = (ImageView) findViewById(R.id.findPartnerButton);
        if (sex.getText().equals("女")) {
            sexTrans = "f";
        } else {
            sexTrans = "m";
        }
        if (!isRegister || isDelect) {
            button.setBackgroundResource(R.drawable.find);
        } else {
            button.setBackgroundResource(R.drawable.partner_update);
        }
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //获得地图小标记的坐标
                label = (ImageView) findViewById(R.id.smallLabel);
                int[] location = new int[2];
                label.getLocationOnScreen(location);
                lab = new Point(location[0], location[1]);

                //LatLng labPoint = baiduMap.getProjection().fromScreenLocation(lab);
                LatLng labPoint = baiduMap.getMapStatus().target;
                DecimalFormat formater = new DecimalFormat();
                formater.setMaximumFractionDigits(6);
                //模式  四舍五入
                formater.setRoundingMode(RoundingMode.UP);
                xx = Double.valueOf(formater.format(labPoint.longitude));
                yy = Double.valueOf(formater.format(labPoint.latitude));
                //xx = labPoint.longitude;
                // yy = labPoint.latitude;

                if (!isRegister) {
                    Map args = new HashMap();
                    args.put("userName", userName);
                    args.put("age", tvBirthdy.getText().toString().substring(0, tvBirthdy.getText().toString().indexOf("-")));
                    args.put("sex", sexTrans);
                    args.put("youngLatitude", yy);
                    args.put("youngLongitude", xx);
                    if (!ValidateUtil.isEmpty(oldStreet.getText().toString()))
                        args.put("youngRoad", oldStreet.getText().toString());
                    if (!ValidateUtil.isEmpty(niCheng.getText().toString()))
                        args.put("youngNickname", niCheng.getText().toString());
                    if (!ValidateUtil.isEmpty(wantToSay.getText().toString()))
                        args.put("message", wantToSay.getText().toString());
                    findFirst = URLUtil.getURL("findPartner", args);
                    Log.d("findFirst", findFirst);
                    new NetAsyncTask(GetParRegInfo.this).execute(findFirst);
                } else {
                    Map args = new HashMap();
                    args.put("userName", userName);
                    if (tvBirthdy.getText().toString().indexOf("-") > 0) {
                        args.put("age", tvBirthdy.getText().toString().substring(0, tvBirthdy.getText().toString().indexOf("-")));
                    } else {
                        args.put("age", tvBirthdy.getText().toString());
                    }
                    args.put("sex", sexTrans);
                    args.put("youngLatitude", yy);
                    args.put("youngLongitude", xx);
                    if (!ValidateUtil.isEmpty(oldStreet.getText().toString())) {
                        args.put("youngRoad", oldStreet.getText().toString());
                    }
                    if (!ValidateUtil.isEmpty(niCheng.getText().toString())) {
                        args.put("youngNickname", niCheng.getText().toString());
                    }
                    if (!ValidateUtil.isEmpty(wantToSay.getText().toString())) {
                        args.put("message", wantToSay.getText().toString());
                    }
                    updateFind = URLUtil.getURL("updateFindInfo", args);
                    Log.d("updateFind", updateFind);
                    new NetAsyncTask(GetParRegInfo.this).execute(updateFind);
                }
            }
        });
        sex.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Dialog();
            }
        });
        tvBirthdy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                initDiallog();
            }
        });
        initMap();
        initLocation();

    }

    private void initLocation() {
        locationService = new LocationService(this);
        mLocationListener = new MyLocationListener();
        locationService.registerListener(mLocationListener);
        LocationClientOption locationClientOption = locationService.getDefaultLocationClientOption();
        locationService.setLocationOption(locationClientOption);

    }

    public void initMap() {
        mMapView = (MapView) findViewById(R.id.bmapView);
        baiduMap = mMapView.getMap();
        MapStatusUpdate msu = MapStatusUpdateFactory.zoomTo(15.0f);//设置地图默认显示比例
        baiduMap.setMapStatus(msu);
        baiduMap.setMapType(BaiduMap.MAP_TYPE_NORMAL);
    }

    public void onStart() {
        // TODO Auto-generated method stub
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
        if (NetWorkStatusUtil.checkNetWorkStatus(this)) {
            Map args = new HashMap();
            args.put("userName", userName);
            getFind = URLUtil.getURL("getFindInfo", args);
            new NetAsyncTask(GetParRegInfo.this).execute(getFind);
        } else {

        }
        super.onResume();
        //在activity执行onResume时执行mMapView. onResume ()，实现地图生命周期管理
        mMapView.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
        //在activity执行onPause时执行mMapView. onPause ()，实现地图生命周期管理
        mMapView.onPause();
        //        unregisterReceiver(mReceiver);
    }

    @Override
    public void onStop() {
        // TODO Auto-generated method stub
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
            if (!isRegister) {
                LatLng latLng = new LatLng(Location.getLatitude(), Location.getLongitude());
                MapStatusUpdate msu = MapStatusUpdateFactory.newLatLng(latLng);
                baiduMap.animateMapStatus(msu);
                isFirstIn = false;
            }
        }
    }

    //定义下拉菜单
    public void showPopupMenu(View view) {
        PopupMenu popupMenu = new PopupMenu(this, view);
        popupMenu.getMenuInflater().inflate(R.menu.find_menu, popupMenu.getMenu());
        popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.menu_find:   //读取童年小伙伴信息
                        startActivity(new Intent(GetParRegInfo.this, HasFoundPartner.class));
                        break;
                    case R.id.menu_delete:   //清除位置并退出
                        Map args = new HashMap();
                        deleteFind = URLUtil.getURL("deleteFindInfo", args);
                        new NetAsyncTask(GetParRegInfo.this).execute(deleteFind);
                        finish();
                        break;
                }
                return true;
            }
        });
        popupMenu.show();
    }

    @Override
    public void getResult(CallBackPage result) {
        if (result != null) {
            FindPartnerInfo info = (FindPartnerInfo) result.getDatalist().get(0);
            if (info.getSelect().equals(C.find.GET_FIND_INFO_SUCCESS)) {
                isRegister = true;
                button.setBackgroundResource(R.drawable.partner_update);

                Log.i("baiduMapLat", String.valueOf(info.getYoungLatitude()));
                Log.i("baiduMapLng", String.valueOf(info.getYoungLongitude()));
                LatLng cenpt = new LatLng(info.getYoungLatitude(), info.getYoungLongitude());
                //定义地图状态
                MapStatusUpdate mMapStatus = MapStatusUpdateFactory.newLatLng(cenpt);
                //改变地图状态
                baiduMap.animateMapStatus(mMapStatus);

                first = String.valueOf(info.getAge());
                if (String.valueOf(info.getSex()).equals("f")) {
                    sex.setText("女");
                } else {
                    sex.setText("男");
                }
                niCheng.setText(info.getYoungNickname());
                date.setText(String.valueOf(info.getAge()));
                oldStreet.setText(info.getYoungRoad());
                wantToSay.setText(info.getMessage());
            } else if (info.getSelect().equals(C.find.USER_UNREGED_FIND)) {
                Toast.makeText(GetParRegInfo.this, "快来填写您的寻找信息吧", Toast.LENGTH_LONG).show();
                isRegister = false;
                button.setBackgroundResource(R.drawable.partner_update);
            } else if (info.getSelect().equals("reg")) {
                button.setBackgroundResource(R.drawable.partner_update);
                Toast.makeText(GetParRegInfo.this, "已发出您的信息", Toast.LENGTH_LONG).show();

            } else if (info.getSelect().equals("update")) {
                Toast.makeText(GetParRegInfo.this, "更新成功", Toast.LENGTH_LONG).show();

            } else if (info.getSelect().equals("delete")) {
                Toast.makeText(GetParRegInfo.this, "您的位置已经清除", Toast.LENGTH_LONG).show();
                isDelect = false;
            }
        }
    }

    public CallBackPage doInBackground(String url) {
        HttpUtil httpUtil = new HttpUtil(url);
        byte[] bytes = httpUtil.getHttpData();
        if (bytes != null) {
            if (url.equals(getFind)) {
                FindHasRegServiceImpl Service = new FindHasRegServiceImpl();
                return Service.getFindHasRegHeadInf(new String(bytes));
            } else if (url.equals(findFirst)) {
                FindFirstServiceImpl Service = new FindFirstServiceImpl();
                return Service.getFindFirstHeadInf(new String(bytes));
            } else if (url.equals(updateFind)) {
                UpdateFindServiceImpl Service = new UpdateFindServiceImpl();
                return Service.getUpdateFindHeadInf(new String(bytes));
            } else {
                DeleteFindServiceImpl Service = new DeleteFindServiceImpl();
                return Service.getDeleteFindHeadInf(new String(bytes));
            }
        }
        return null;
    }

    public class MyReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            if (!checkNetWorkStatus(GetParRegInfo.this)) {
                netError.setVisibility(View.VISIBLE);
            } else {
                Map args = new HashMap();
                args.put("userName", userName);
                getFind = URLUtil.getURL("getFindInfo", args);
                Log.i("sss", getFind);
                new NetAsyncTask(GetParRegInfo.this).execute(getFind);
            }
        }
    }

    /*网络连接检测*/
    public static boolean checkNetWorkStatus(Context context) {
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

    public void Dialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);// 自定义对话框
        int sexId = 0;
        if (sex.getText().toString().equals("男")) {
            sexId = 1;
        } else {
            sexId = 0;
        }
        builder.setSingleChoiceItems(sexArry, sexId, new DialogInterface.OnClickListener() {// 2默认的选中

            @Override
            public void onClick(DialogInterface dialog, int which) {// which是被选中的位置
                sex.setText(sexArry[which]);
                dialog.dismiss();// 随便点击一个item消失对话框，不用点击确认取消
            }
        });
        builder.show();// 让弹出框显示
    }

    public void initDiallog() {
        Calendar mycalendar = Calendar.getInstance(Locale.CHINA);
        Date mydate = new Date(); //获取当前日期Date对象
        mycalendar.setTime(mydate);//为Calendar对象设置时间为当前日期
        year = mycalendar.get(Calendar.YEAR);
        month = mycalendar.get(Calendar.MONTH);
        day = mycalendar.get(Calendar.DAY_OF_MONTH);
        DatePickerDialog datePickerDialog = new DatePickerDialog(this, Datelistener, year, month, day);
        datePickerDialog.show();
    }

    private DatePickerDialog.OnDateSetListener Datelistener = new DatePickerDialog.OnDateSetListener() {

        @Override
        public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
            tvBirthdy.setText(year + "-" + (monthOfYear + 1) + "-" + dayOfMonth);
        }
    };
}
