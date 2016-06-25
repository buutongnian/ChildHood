package edu.buu.childhood;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Administrator on 2016/6/22.
 */
public class DBOprate {
    private SQLiteDatabase db;
    private DBHelper dbHelper;
    public DBOprate(Context context) {
        this.dbHelper = new DBHelper(context, "ChildHood.db", null, 1);
        this.db = dbHelper.getReadableDatabase();
    }
    /*
    *
    * @author tt
    * 返回区域的map集合对象，key是区域名称，value是区域编码
    **/
    public Map<String ,Integer> getArea(){
        Map<String,Integer> map=new HashMap<String,Integer>();
        Cursor cu=db.rawQuery("select * from T_PUB_AREA",null);
        while(cu.moveToNext()){
            String areaName=cu.getString(cu.getColumnIndex("AREA_NAME"));
            int areaCode=cu.getInt(cu.getColumnIndex("AREA_CODE"));
            map.put(areaName,areaCode);
        }
        return map;

    }
    /*
     * @author tt
     * 返回省份的map集合对象，key是省份名称，value是省份编码
     */

    public Map<String ,Integer> getProvince(int areaCode){
        Map<String,Integer> map=new HashMap<String,Integer>();
        Cursor cu=db.rawQuery("select * from T_PUB_PROVINCE where BELONGING=?",new String[]{String.valueOf(areaCode)});
        while(cu.moveToNext()){
            String provinceName=cu.getString(cu.getColumnIndex("PROVINCE_NAME"));
            int provinceCode=cu.getInt(cu.getColumnIndex("PROVINCE_CODE"));
            map.put(provinceName,provinceCode);
        }
        return map;
    }
    /*
     * @author tt
     * 返回城市的map集合对象，key是城市名称，value是城市编码
     */
    public Map<String ,Integer> getCity(int pCode){
        Map<String,Integer> map=new HashMap<String,Integer>();
        Cursor cu=db.rawQuery("select * from T_PUB_CITY where BELONGING=?",new String[]{String.valueOf(pCode)});
        while(cu.moveToNext()){
            String cityName=cu.getString(cu.getColumnIndex("CITY_NAME"));
            int cityCode=cu.getInt(cu.getColumnIndex("CITY_CODE"));
            map.put(cityName,cityCode);
        }
        return map;

    }

    /*
   * @author tt
   * 返回区县的map集合对象，key是区县名称，value是区县编码
   */
    public Map<String ,Integer> getDistrict(int cCode){
        Map<String,Integer> map=new HashMap<String,Integer>();
        Cursor cu=db.rawQuery("select * from T_PUB_DISTRICT where BELONGING=?",new String[]{String.valueOf(cCode)});
        while(cu.moveToNext()){
            String districtName=cu.getString(cu.getColumnIndex("DISTRICT_NAME"));
            int districtCode=cu.getInt(cu.getColumnIndex("DISTRICT_CODE"));
            map.put(districtName,districtCode);
        }
        return map;

    }

}
