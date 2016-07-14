package edu.buu.childhood.baidumap.activity;

/**
 * Created by lcc on 2016/7/2.
 */

import android.app.Activity;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import edu.buu.childhood.R;
import edu.buu.childhood.baidumap.fragment.ChatRoom;
import edu.buu.childhood.baidumap.fragment.NewMessage;
import edu.buu.childhood.baidumap.fragment.baiduMap;

public class MainBaidu extends Activity{
    private RadioGroup rg;
    private RadioButton baidu;
    //Fragment Object
    private baiduMap fg2;
    private ChatRoom fg1;
    private NewMessage fg3;
    private FragmentManager fManager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_baidu);
        fManager = getFragmentManager();
        rg = (RadioGroup) findViewById(R.id.main_baidu_rg);
        baidu = (RadioButton) findViewById(R.id.main_baidu_rg_map);
        baidu.setChecked(true);
        fg2 = new baiduMap();
        fManager.beginTransaction().add(R.id.main_baidu_fl, fg2).commit();
        rg.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
                                          @Override
                                          public void onCheckedChanged(RadioGroup group, int checkedId) {
                                              FragmentTransaction fTransaction = fManager.beginTransaction();
                                              hideAllFragment(fTransaction);
                                              switch (checkedId) {
                                                  case R.id.main_baidu_rg_chat:
                                                      if (fg1 == null) {
                                                          fg1 = new ChatRoom();
                                                          fTransaction.add(R.id.main_baidu_fl, fg1);
                                                      } else {
                                                          fTransaction.show(fg1);
                                                      }
                                                      break;
                                                  case R.id.main_baidu_rg_map:
                                                      if (fg2 == null) {
                                                          fg2 = new baiduMap();
                                                          fTransaction.add(R.id.main_baidu_fl, fg2);
                                                      } else {
                                                          fTransaction.show(fg2);
                                                      }
                                                      break;
                                                  case R.id.main_baidu_rg_info:
                                                      if (fg3 == null) {
                                                          fg3 = new NewMessage();
                                                          fTransaction.add(R.id.main_baidu_fl, fg3);
                                                      } else {
                                                          fTransaction.show(fg3);
                                                      }
                                                      break;

                                              }
                                              fTransaction.commit();
                                          }
                                      }

        );
    }

    //隐藏所有Fragment
    private void hideAllFragment(FragmentTransaction fragmentTransaction) {
        if (fg1 != null) fragmentTransaction.hide(fg1);
        if (fg2 != null) fragmentTransaction.hide(fg2);
        if (fg3 != null) fragmentTransaction.hide(fg3);
    }


}