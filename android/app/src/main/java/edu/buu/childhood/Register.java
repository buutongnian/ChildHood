package edu.buu.childhood;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * Created by lcc on 2016/6/21.
 */
public class Register extends Activity  {
    //省市区集合声明
    private List<String> provinceList=new ArrayList<>();
    private List<String> cityList=new ArrayList<>();
    private List<String> areaList=new ArrayList<>();
    //spinner声明
    private Spinner provinceSpinner;
    private Spinner citySpinner;
    private Spinner areaSpinner;
    private ArrayAdapter<String> re_Sadapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register);
        /**
         *  spinner 初始化及适配器
         *第一步：添加一个下拉列表项的list，这里添加的项就是下拉列表的菜单项
         */
       provinceList.add("北京");
        provinceList.add("上海");
        provinceList.add("深圳");
        provinceList.add("福州");
        provinceList.add("厦门");
        //第二个spinner数据初始化
        cityList.add("北京");
        cityList.add("上海");
        cityList.add("深圳");
        cityList.add("福州");
        cityList.add("厦门");
        //第三个spinner数据初始化
        areaList.add("北京");
        areaList.add("上海");
        areaList.add("深圳");
        areaList.add("福州");
        areaList.add("厦门");
        provinceSpinner = (Spinner)findViewById(R.id.register_spinner1);
        citySpinner = (Spinner)findViewById(R.id.register_spinner2);
        areaSpinner = (Spinner)findViewById(R.id.register_spinner3);
        re_Sadapter=new ArrayAdapter<String>(this,R.layout.spinner_checked_text, provinceList){
            @Override
            public View getDropDownView(int position, View convertView,ViewGroup parent) {
                View view = View.inflate(getContext(), R.layout.game_spinner_dropdownlist,null);
                TextView label = (TextView) view
                        .findViewById(R.id.spinner_item_label);
                ImageView check = (ImageView) view
                        .findViewById(R.id.spinner_item_checked_image);
                label.setText(provinceList.get(position));
                label.setText(cityList.get(position));
                label.setText(areaList.get(position));
                label.setTextColor(getResources().getColor(R.color.black));
                if (provinceSpinner.getSelectedItemPosition() == position) {
                    view.setBackgroundColor(getResources().getColor(
                            R.color.spinner_green));
                    check.setImageResource(R.drawable.pre);
                } else {
                    view.setBackgroundColor(getResources().getColor(
                            R.color.spinner_light_green));
                    check.setImageResource(R.drawable.pre);
                }

                if (citySpinner.getSelectedItemPosition() == position) {
                    view.setBackgroundColor(getResources().getColor(
                            R.color.spinner_green));
                    check.setImageResource(R.drawable.pre);
                } else {
                    view.setBackgroundColor(getResources().getColor(
                            R.color.spinner_light_green));
                    check.setImageResource(R.drawable.pre);
                }

                if (areaSpinner.getSelectedItemPosition() == position) {
                    view.setBackgroundColor(getResources().getColor(
                            R.color.spinner_green));
                    check.setImageResource(R.drawable.pre);
                } else {
                    view.setBackgroundColor(getResources().getColor(
                            R.color.spinner_light_green));
                    check.setImageResource(R.drawable.pre);
                }
                return view;
            }
        };
        re_Sadapter.setDropDownViewResource(R.layout.game_spinner_dropdownlist);
        //第四步：将适配器添加到下拉列表上
        provinceSpinner.setAdapter(re_Sadapter);
        citySpinner.setAdapter(re_Sadapter);
        areaSpinner.setAdapter(re_Sadapter);

    }
    public void registerBack(View view){
        finish();
    }

}
