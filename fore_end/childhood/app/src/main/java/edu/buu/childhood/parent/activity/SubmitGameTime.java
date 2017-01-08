package edu.buu.childhood.parent.activity;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import edu.buu.childhood.R;
import edu.buu.childhood.baidumap.adapter.selectGameAdapter;
import edu.buu.childhood.baidumap.pojo.MarkItem;
import edu.buu.childhood.baidumap.service.SelectGameServiceImpl;
import edu.buu.childhood.common.C;
import edu.buu.childhood.common.CallBackPage;
import edu.buu.childhood.common.GlobalVeriable;
import edu.buu.childhood.common.Message;
import edu.buu.childhood.game.refresh.AutoListView;
import edu.buu.childhood.util.CallBack;
import edu.buu.childhood.util.CloseActivity;
import edu.buu.childhood.util.HttpUtil;
import edu.buu.childhood.util.NetAsyncTask;
import edu.buu.childhood.util.NewCallBack;
import edu.buu.childhood.util.NewNetAsyncTask;
import edu.buu.childhood.util.URLUtil;
import edu.buu.childhood.util.ValidateUtil;

/**
 * Created by lcc on 2016/10/30.
 */
public class SubmitGameTime extends Activity implements CallBack, NewCallBack, AutoListView.OnRefreshListener, AutoListView.OnLoadListener {
    private TextView selectGame;
    private TextView selectGameCode;
    private TextView selectTime;
    private EditText gameNote;
    private Button submit;
    //选择日期
    private int year;
    private int month;
    private int day;

    private Dialog dialog1;
    private Dialog dialog;
    private Dialog selectDialog;
    //选择游戏Listview
    private AutoListView selectGameList;
    //选择游戏存储
    private List<MarkItem> gamelist = new ArrayList<MarkItem>();
    //为listview添加适配器
    selectGameAdapter selectListAdapter;
    //请求选择游戏数据
    private int pageNum = 1;
    private boolean isFirstRequestGameList = true;
    private boolean isRefreshView = false;//是否是上拉刷新
    private String selectGameName;//选择游戏的名字
    private Boolean isShield;
    private int joinGameId;

    private String selectGameUrl;

    private Gson json = new Gson();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        CloseActivity.activityList.add(this);
        setContentView(R.layout.parent_submit_game_time);
        selectGame = (TextView) findViewById(R.id.pu_select_game_tv);
        selectGameCode = (TextView) findViewById(R.id.parent_submit_game_code);
        selectTime = (TextView) findViewById(R.id.pu_game_time_tv);
        submit = (Button) findViewById(R.id.parent_submit_game_time_btn1);
        gameNote = (EditText) findViewById(R.id.parent_submit_game_game_note);

        selectTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                initDiallog();
            }
        });
        selectGame.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                gameDialog();
            }
        });
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ValidateUtil.isEmpty(String.valueOf(selectGameCode.getText()))) {
                    Toast.makeText(SubmitGameTime.this, "请选择游戏", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (ValidateUtil.isEmpty(String.valueOf(selectTime.getText()))) {
                    Toast.makeText(SubmitGameTime.this, "请选择游戏时间", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (ValidateUtil.isEmpty(String.valueOf(gameNote.getText()))) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(
                            SubmitGameTime.this);
                    builder.setTitle("提示");
                    builder.setMessage("确定不记录宝贝表现吗？");
                    builder.setIcon(R.drawable.logo);
                    builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            Map<String, String> args = new HashMap<String, String>();
                            args.put("userName", ((GlobalVeriable) getApplication()).getUserName());
                            args.put("gameCode", String.valueOf(selectGameCode.getText()));
                            args.put("gameTime", String.valueOf(selectTime.getText()));
                            args.put("gameNote", String.valueOf(gameNote.getText()));
                            String url = URLUtil.getURL("addFamilyGame", args);
                            new NewNetAsyncTask(SubmitGameTime.this).execute(url);
                        }
                    });
                    builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                            return;
                        }
                    });
                    builder.show();
                } else {
                    Map<String, String> args = new HashMap<String, String>();
                    args.put("userName", ((GlobalVeriable) getApplication()).getUserName());
                    args.put("gameCode", String.valueOf(selectGameCode.getText()));
                    args.put("gameTime", String.valueOf(selectTime.getText()));
                    args.put("gameNote", String.valueOf(gameNote.getText()));
                    String url = URLUtil.getURL("addFamilyGame", args);
                    new NewNetAsyncTask(SubmitGameTime.this).execute(url);
                }
            }
        });
    }

    /*
    *处理请求结果的方法
     */
    @Override
    public void getResult(CallBackPage result) {
        if (result != null) {
            if (result.getDatalist().size() > 0) {
                MarkItem bean = (MarkItem) result.getDatalist().get(0);
                if (bean.getSelect().equals("6")) {
                    if (isRefreshView) {
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
                }
            }
            isFirstRequestGameList = false;
        }
    }

    /**
     * 新回调函数，传入API请求返回json数据进行处理
     */
    @Override
    public void getResult(String jsonStr) {
        if (jsonStr != null) {
            Message reciveMsg = json.fromJson(jsonStr, new TypeToken<Message<?>>() {
            }.getType());
            if (reciveMsg.getMessageCode() != null && reciveMsg.getMessageCode() instanceof String) {
                switch (reciveMsg.getMessageCode()) {
                    case C.parent.ADD_FAMILY_GAME_SUCCESS:
                        selectGame.setText("");
                        selectTime.setText("");
                        gameNote.setText("");
                        Toast.makeText(this, "提交成功", Toast.LENGTH_SHORT).show();
                        break;
                    case C.parent.ADD_FAMILY_GAME_UNSUCCESS:
                        Toast.makeText(this, "提交失败，请稍后重试", Toast.LENGTH_SHORT).show();
                        break;
                    default:
                        Toast.makeText(this, "系统异常，请稍后重试", Toast.LENGTH_SHORT).show();
                        break;
                }
            }
        }
    }

    /*
     * 请求url的方法
     */
    @Override
    public CallBackPage doInBackground(String url) {
        HttpUtil httpUtil = new HttpUtil(url);
        byte[] bytes = httpUtil.getHttpData();
        if (bytes != null) {
            if (url.equals(selectGameUrl)) {
                SelectGameServiceImpl service = new SelectGameServiceImpl();
                return service.getGameHeadInf(new String(bytes));
            }
        }
        return null;
    }

    /**
     * 新回调函数，传入url数组，一般情况只传入一个，即url[0]
     * 为兼容旧回调函数故做此处理
     * 2016/11/03
     *
     * @param url 所传入url数组
     * @return 返回请求API接口所得到的json数据
     */
    @Override
    public String doInBackground(String... url) {
        HttpUtil httpUtil = new HttpUtil(url[0]);
        byte[] bytes = httpUtil.getHttpData();
        if (bytes != null) {
            return new String(bytes);
        }
        return null;
    }

    /**
     * 获得日期的方法
     */
    public void initDiallog() {
        Calendar mycalendar = Calendar.getInstance(Locale.CHINA);
        Date mydate = new Date(); //获取当前日期Date对象
        mycalendar.setTime(mydate);////为Calendar对象设置时间为当前日期
        year = mycalendar.get(Calendar.YEAR);
        month = mycalendar.get(Calendar.MONTH);
        day = mycalendar.get(Calendar.DAY_OF_MONTH);
        DatePickerDialog datePickerDialog = new DatePickerDialog(this, Datelistener, year, month, day);
        datePickerDialog.show();
    }

    private DatePickerDialog.OnDateSetListener Datelistener = new DatePickerDialog.OnDateSetListener() {

        @Override
        public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
            selectTime.setText(year + "-" + (monthOfYear + 1) + "-" + dayOfMonth);
        }
    };

    public void gameDialog() {
        LayoutInflater inflater = LayoutInflater.from(this);
        final LinearLayout layout = (LinearLayout) inflater.inflate(R.layout.select_game, null);
        selectDialog = new AlertDialog.Builder(this).create();
        selectDialog.setCancelable(false);
        selectDialog.show();
        selectDialog.getWindow().setContentView(layout);
        selectGameList = (AutoListView) layout.findViewById(R.id.select_game_list);
        selectListAdapter = new selectGameAdapter(this, gamelist);
        selectGameList.setAdapter(selectListAdapter);
        selectGameList.setOnLoadListener(this);
        selectGameList.setOnRefreshListener(this);
        selectGameList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long C) {
                if ((!isFirstRequestGameList && arg2 > 0) || (!isRefreshView && arg2 > 1)) {
                    ImageView img = (ImageView) layout.findViewById(R.id.select_game_image);
                    img.setImageResource(R.drawable.select_icon);
                    // 设置适配器的选中项
                    selectListAdapter.setSelectItem(arg2 - 1);
                    TextView gameCode = (TextView) arg1.findViewById(R.id.select_game_code);
                    TextView gameTitle = (TextView) arg1.findViewById(R.id.select_game_text);
                    selectGameCode.setText(String.valueOf(gameCode.getText()));
                    selectGameName = String.valueOf(gameTitle.getText());
                    // 更新列表框
                    selectListAdapter.notifyDataSetInvalidated();
                }
            }
        });

        Button okButton = (Button) layout.findViewById(R.id.positiveButton);
        okButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectGame.setText(selectGameName);
                selectGameCode.setText(selectGameCode.getText());
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
        onShow();
    }


    public void onShow() {
        isRefreshView = false;
        Map args = new HashMap();
        args.put("userName", ((GlobalVeriable) getApplication()).getUserName());
        args.put("pageNum", pageNum);
        selectGameUrl = URLUtil.getURL("gameHead", args);
        new NetAsyncTask(SubmitGameTime.this).execute(selectGameUrl);
    }

    @Override
    public void onLoad() {
        isRefreshView = false;
        pageNum++;
        Map args = new HashMap();
        args.put("userName", ((GlobalVeriable) getApplication()).getUserName());
        args.put("pageNum", pageNum);
        selectGameUrl = URLUtil.getURL("gameHead", args);
        new NetAsyncTask(SubmitGameTime.this).execute(selectGameUrl);
    }

    @Override
    public void onRefresh() {
        isRefreshView = true;
        pageNum = 1;
        Map args = new HashMap();
        args.put("userName", ((GlobalVeriable) getApplication()).getUserName());
        args.put("pageNum", pageNum);
        selectGameUrl = URLUtil.getURL("gameHead", args);
        new NetAsyncTask(SubmitGameTime.this).execute(selectGameUrl);
    }
}
