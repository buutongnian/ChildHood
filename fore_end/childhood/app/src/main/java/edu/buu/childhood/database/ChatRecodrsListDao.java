package edu.buu.childhood.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

import edu.buu.childhood.chat.bean.ChatRecordsList;

/**
 * Created by Administrator on 2016/9/26.
 * 聊天列表操作
 */
public class ChatRecodrsListDao {
    public static final String TABLE_NAME = "chatlist";
    public static final String CHAT_LIST_ID = "chatlistid";
    public static final String CHAT_LIST_USERNAME = "username";
    public static final String CHAT_LIST_FROMUSER = "fromuser";
    public static final String CHAT_LIST_LASTMESSAGE = "lastmessage";
    public static final String CHAT_LIST_LAST_TIME = "lasttime";

    private DbOpenHelper dbOpenHelper;

    public ChatRecodrsListDao(Context context) {
        dbOpenHelper = DbOpenHelper.getInstance(context);
    }

    public void saveChatRecordsList(ChatRecordsList chatRecordsList) {
        SQLiteDatabase db = dbOpenHelper.getWritableDatabase();
        if (db.isOpen()) {
            ContentValues values = new ContentValues();
            values.put(CHAT_LIST_USERNAME, chatRecordsList.getUserName());
            values.put(CHAT_LIST_FROMUSER, chatRecordsList.getFromUser());
           values.put(CHAT_LIST_LASTMESSAGE, chatRecordsList.getLastMessage());
            values.put(CHAT_LIST_LAST_TIME, chatRecordsList.getLastTime());
            db.insert(TABLE_NAME, null, values);
        }
    }

    public void updateChatRecordsList(ChatRecordsList chatRecordsList) {
        SQLiteDatabase db = dbOpenHelper.getWritableDatabase();
        if (db.isOpen()) {
            ContentValues values = new ContentValues();
            values.put(CHAT_LIST_LASTMESSAGE, chatRecordsList.getLastMessage());
            values.put(CHAT_LIST_LAST_TIME, chatRecordsList.getLastTime());
            db.update(TABLE_NAME, values, CHAT_LIST_USERNAME + "='" + chatRecordsList.getUserName() + "' and " +
                    CHAT_LIST_FROMUSER + "='" + chatRecordsList.getFromUser()+"'", null);
            //db.update(TABLE_NAME,);
        }
    }

    public List<ChatRecordsList> getChatRecordsList(String userName) {
        SQLiteDatabase db = dbOpenHelper.getWritableDatabase();
        List<ChatRecordsList> list = new ArrayList<>();
        if (db.isOpen()) {
            ChatRecordsList chatRecordsList = new ChatRecordsList();
            Cursor cursor = db.rawQuery("select * from " + TABLE_NAME + " where " + CHAT_LIST_USERNAME + "='" + userName+"'", null);
            while (cursor.moveToNext()) {
                chatRecordsList.setUserName(cursor.getString(cursor.getColumnIndex(CHAT_LIST_USERNAME)));
                chatRecordsList.setFromUser(cursor.getString(cursor.getColumnIndex(CHAT_LIST_FROMUSER)));
                chatRecordsList.setLastMessage(cursor.getString(cursor.getColumnIndex(CHAT_LIST_LASTMESSAGE)));
                chatRecordsList.setLastTime(cursor.getString(cursor.getColumnIndex(CHAT_LIST_LAST_TIME)));
                list.add(chatRecordsList);
            }
            cursor.close();
            return list;
        }
        return null;
    }

    public void deleteChatRecordsList(String userName,String fromUser){
        SQLiteDatabase db=dbOpenHelper.getWritableDatabase();
        db.execSQL("delete from "+TABLE_NAME+" where "+CHAT_LIST_USERNAME+"='"+userName+"' and "+
        CHAT_LIST_FROMUSER+"='"+fromUser+"'");
    }

    public Boolean isHaveRecord(String userName,String fromUser){
        SQLiteDatabase db=dbOpenHelper.getWritableDatabase();
        Cursor cursor=db.rawQuery("select * from "+TABLE_NAME+" where "+CHAT_LIST_USERNAME+" = '"+
        userName+"' and "+CHAT_LIST_FROMUSER+" ='"+fromUser+"'",null);
        if(cursor.getCount()>0){
            return true;
        }
        cursor.close();
        return false;
    }
}
