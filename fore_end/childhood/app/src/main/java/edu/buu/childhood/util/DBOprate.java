package edu.buu.childhood.util;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import edu.buu.childhood.baidumap.pojo.PushMessage;
import edu.buu.childhood.common.ChatMessage;

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

    public DBOprate(Context context, String dbName) {
        this.dbHelper = new DBHelper(context, dbName, null, 1);
        this.db = dbHelper.getWritableDatabase();
    }

    /*
    *
    * @author tt
    * 返回区域的map集合对象，key是区域名称，value是区域编码
    **/
    public Map<String, Integer> getArea() {
        Map<String, Integer> map = new HashMap<String, Integer>();
        Cursor cu = db.rawQuery("select * from T_PUB_AREA", null);
        while (cu.moveToNext()) {
            String areaName = cu.getString(cu.getColumnIndex("AREA_NAME"));
            int areaCode = cu.getInt(cu.getColumnIndex("AREA_CODE"));
            map.put(areaName, areaCode);
        }
        return map;

    }
    /*
     * @author tt
     * 返回省份的map集合对象，key是省份名称，value是省份编码
     */

    public Map<String, Integer> getProvince(int areaCode) {
        Map<String, Integer> map = new HashMap<String, Integer>();
        Cursor cu = db.rawQuery("select * from T_PUB_PROVINCE where BELONGING=? order by PROVINCE_CODE", new String[]{String.valueOf(areaCode)});
        while (cu.moveToNext()) {
            String provinceName = cu.getString(cu.getColumnIndex("PROVINCE_NAME"));
            int provinceCode = cu.getInt(cu.getColumnIndex("PROVINCE_CODE"));
            map.put(provinceName, provinceCode);
        }
        return map;
    }

    public String getProvinceName(int provinceId) {
        String result = null;
        Cursor cu = db.rawQuery("select * from T_PUB_PROVINCE where PROVINCE_CODE=?", new String[]{String.valueOf(provinceId)});
        while (cu.moveToNext()) {
            result = cu.getString(cu.getColumnIndex("PROVINCE_NAME"));
        }
        return result;
    }

    public Map<String, Integer> getProvince() {
        Map<String, Integer> map = new HashMap<String, Integer>();
        Cursor cu = db.rawQuery("select * from T_PUB_PROVINCE order by PROVINCE_CODE", null);
        while (cu.moveToNext()) {
            String provinceName = cu.getString(cu.getColumnIndex("PROVINCE_NAME"));
            int provinceCode = cu.getInt(cu.getColumnIndex("PROVINCE_CODE"));
            map.put(provinceName, provinceCode);
        }
        return map;
    }

    /*
     * @author tt
     * 返回城市的map集合对象，key是城市名称，value是城市编码
     */
    public Map<String, Integer> getCity(int pCode) {
        Map<String, Integer> map = new HashMap<String, Integer>();
        Cursor cu = db.rawQuery("select * from T_PUB_CITY where BELONGING=? order by CITY_CODE", new String[]{String.valueOf(pCode)});
        while (cu.moveToNext()) {
            String cityName = cu.getString(cu.getColumnIndex("CITY_NAME"));
            int cityCode = cu.getInt(cu.getColumnIndex("CITY_CODE"));
            map.put(cityName, cityCode);
        }
        return map;

    }

    public String getCityName(int cityId) {
        String cityName = null;
        Cursor cu = db.rawQuery("select * from T_PUB_CITY where CITY_CODE=?", new String[]{String.valueOf(cityId)});
        while (cu.moveToNext()) {
            cityName = cu.getString(cu.getColumnIndex("CITY_NAME"));
        }
        return cityName;
    }

    /*
   * @author tt
   * 返回区县的map集合对象，key是区县名称，value是区县编码
   */
    public Map<String, Integer> getDistrict(int cCode) {
        Map<String, Integer> map = new HashMap<String, Integer>();
        Cursor cu = db.rawQuery("select * from T_PUB_DISTRICT where BELONGING=? order by DISTRICT_CODE", new String[]{String.valueOf(cCode)});
        while (cu.moveToNext()) {
            String districtName = cu.getString(cu.getColumnIndex("DISTRICT_NAME"));
            int districtCode = cu.getInt(cu.getColumnIndex("DISTRICT_CODE"));
            map.put(districtName, districtCode);
        }
        return map;
    }

    /*
    返回游戏选择字段：年龄
     */
    public Map<String, Integer> getAge() {
        Map<String, Integer> map = new HashMap<>();
        Cursor cu = db.rawQuery("select * from T_PUB_GAME_AGE order by AGE_CODE", null);
        while (cu.moveToNext()) {
            String age = cu.getString(cu.getColumnIndex("AGE_INTERVAL"));
            int ageCode = cu.getInt(cu.getColumnIndex("AGE_CODE"));
            map.put(age, ageCode);
        }
        return map;
    }

    /*
返回游戏选择字段：人数
 */
    public Map<String, Integer> getCount() {
        Map<String, Integer> map = new HashMap<>();
        Cursor cu = db.rawQuery("select * from T_PUB_GAME_MEMNUM ", null);
        while (cu.moveToNext()) {
            String count = cu.getString(cu.getColumnIndex("MEMNUM_INTERVAL"));
            int countCode = cu.getInt(cu.getColumnIndex("MEMNUM_CODE"));
            map.put(count, countCode);
        }
        return map;
    }

    /**
     * 储存聊天室离线消息
     *
     * @param chatMessage
     */
    public void saveChatMessage(ChatMessage chatMessage) {
        ContentValues values = new ContentValues();
        values.put("roomid", chatMessage.getRoomId());
        values.put("fromname", chatMessage.getFromName());
        values.put("messagecontent", chatMessage.getMessageContent());
        values.put("username", chatMessage.getUserName());
        values.put("isread", chatMessage.getIsRead());
        values.put("isroommessage", chatMessage.getIsRoomMessage());
        Log.i("sss", chatMessage.getRoomId()+" "+chatMessage.getFromName()+" "+chatMessage.getUserName());
        db.insert("chatRoomMessage", "id", values);
    }

    /**
     * 获取聊天室离线消息
     *
     * @param sql
     * @return
     */
    public List<ChatMessage> getChatMessage(String sql) {
        List<ChatMessage> list = new ArrayList<>();
        Cursor cursor = db.rawQuery(sql, null);
        if (cursor.getCount() > 0) {
            while (cursor.moveToNext()) {
                ChatMessage chatMessage = new ChatMessage();
                chatMessage.setRoomId(cursor.getInt(cursor.getColumnIndex("roomid")));
                chatMessage.setUserName(cursor.getString(cursor.getColumnIndex("username")));
                chatMessage.setFromName(cursor.getString(cursor.getColumnIndex("fromname")));
                chatMessage.setMessageContent(cursor.getString(cursor.getColumnIndex("messagecontent")));
                list.add(chatMessage);
            }
        }
        cursor.close();
        return list;
    }

    /**
     * 删除聊天室的历史以前消息
     */
    public void deleteMuliChat(String userName, int roomId) {
        Cursor cursor = db.rawQuery("select * from chatRoomMessage where userName='" + userName + "' and roomid=" + roomId, null);
        if (cursor.getCount() > 0) {
            db.execSQL("delete  from chatRoomMessage where userName='" + userName + "' and roomid=" + roomId);
        }
    }

    /**
     * 存储pushmaster推送过来的游戏邀请信息
     *
     * @param pushMessage
     */
    public void savePushMessage(PushMessage pushMessage) {
        ContentValues values = new ContentValues();
        values.put("username", pushMessage.getUserName());
        values.put("gameid", pushMessage.getGameId());
        values.put("gametitle", pushMessage.getGameTitle());
        values.put("gamefounder", pushMessage.getGameFounder());
        values.put("gameicon", pushMessage.getGameIcon());
        values.put("foundernickname", pushMessage.getFounderNickName());
        values.put("foundtime", pushMessage.getFoundTime());
        values.put("gatherplace", pushMessage.getGatherPlace());
        values.put("custominf", pushMessage.getCustomInf());
        values.put("customcount", pushMessage.getCustomCount());
        values.put("isread", pushMessage.getIsRead());
        db.insert("pushMessage", "id", values);
    }

    /**
     * 获取pushmaster推送的离线消息
     */

    public List<PushMessage> getPushMessage(String sql) {
        List<PushMessage> list = new ArrayList<>();
        Cursor cursor = db.rawQuery(sql, null);
        if (cursor.getCount() > 0) {
            while (cursor.moveToNext()) {
                PushMessage pushMessage = new PushMessage();
                pushMessage.setUserName(cursor.getString(cursor.getColumnIndex("username")));
                pushMessage.setGameId(cursor.getInt(cursor.getColumnIndex("gameid")));
                pushMessage.setGameTitle(cursor.getString(cursor.getColumnIndex("gametitle")));
                pushMessage.setGameFounder(cursor.getString(cursor.getColumnIndex("gamefounder")));
                pushMessage.setFounderNickName(cursor.getString(cursor.getColumnIndex("foundernickname")));
                pushMessage.setFoundTime(cursor.getString(cursor.getColumnIndex("foundtime")));
                pushMessage.setGatherPlace(cursor.getString(cursor.getColumnIndex("gatherplace")));
                pushMessage.setCustomInf(cursor.getString(cursor.getColumnIndex("custominf")));
                pushMessage.setCustomCount(cursor.getInt(cursor.getColumnIndex("customcount")));
                pushMessage.setGameIcon(cursor.getString(cursor.getColumnIndex("gameicon")));
                list.add(pushMessage);
            }
        }
        cursor.close();
        return list;
    }

    public void updatePushMessage(PushMessage pushMessage) {
        Log.i("update", "update" + "update pushMessage set isread=1 where username='" + pushMessage.getUserName() + "'");
        db.execSQL("update pushMessage set isread=1 where username='" + pushMessage.getUserName() + "'" + " " + "and gameid =" + pushMessage.getGameId());

    }
}
