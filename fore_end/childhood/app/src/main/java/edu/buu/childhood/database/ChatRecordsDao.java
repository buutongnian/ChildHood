package edu.buu.childhood.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

import edu.buu.childhood.chat.bean.ChatRecords;

/**
 * Created by Administrator on 2016/9/26.
 * 操作聊天记录
 */
public class ChatRecordsDao {
    public static final String TABLE_NAME = "conversation";
    public static final String COLUMN_MESSAGE_ID = "messageid";
    public static final String COLUMN_MESSAGE_USERNAME = "username";
    public static final String COLUMN_MESSAGE_FROM = "from_username";
    public static final String COLUMN_MESSAGE_CONTENT = "message_content";
    public static final String COLUMN_MESSAGE_TIME = "message_time";
    public static final String COLUMN_IS_SELF = "isself";
    public static final String COLUMN_MESSAGE_SATUS = "isread";
    private DbOpenHelper dbHelper;

    public ChatRecordsDao(Context context) {
        dbHelper = DbOpenHelper.getInstance(context);
    }

    /**
     * 保存具体的聊天消息
     */

    public Integer saveChatMessage(ChatRecords chatRecords) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        int id = -1;
        if (db.isOpen()) {
            ContentValues values = new ContentValues();
            values.put(COLUMN_MESSAGE_USERNAME, chatRecords.getUserName());
            values.put(COLUMN_MESSAGE_FROM, chatRecords.getFromUser());
            values.put(COLUMN_MESSAGE_CONTENT, chatRecords.getMessageContent());
            values.put(COLUMN_MESSAGE_TIME, chatRecords.getMessageTime());
            values.put(COLUMN_IS_SELF,chatRecords.getIsSelf());
            values.put(COLUMN_MESSAGE_SATUS, chatRecords.getIsRead());
            db.insert(TABLE_NAME, null, values);
            Cursor cursor = db.rawQuery("select last_insert_rowid() from " + TABLE_NAME,null);
            if(cursor.moveToFirst()){
                id = cursor.getInt(0);
            }
        }
        return id;
    }

    /**
     * 更新聊天记录
     */

    public void updateChatMessage(ContentValues values, String[] where) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        if (db.isOpen()) {
            db.update(TABLE_NAME, values, COLUMN_MESSAGE_USERNAME + " = ? and " + COLUMN_MESSAGE_FROM + " = ?", where);
        }
    }

    /**
     * 根据消息id
     * 更新聊天记录已读
     * @param msgId
     */
    public void updateMessageStatus(int msgId){
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        if (db.isOpen()) {
            db.execSQL("update "+TABLE_NAME+" set "+COLUMN_MESSAGE_SATUS+" = 1 where "+COLUMN_MESSAGE_ID+" = "+msgId);
        }
    }

    /**
     * 获取与某个人的聊天记录
     *
     * @param userName
     * @param fromUser
     * @return
     */
    public List<ChatRecords> getChatMessage(String userName, String fromUser) {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        List<ChatRecords> msg = new ArrayList<>();
        if (db.isOpen()) {
            Cursor cursor = db.rawQuery("select * from " + TABLE_NAME + " where " + COLUMN_MESSAGE_USERNAME + "='" + userName +
                    "' and " + COLUMN_MESSAGE_FROM + "='" + fromUser + "' order by " + COLUMN_MESSAGE_ID + " asc", null);
            if (cursor.getCount() > 0) {
                while (cursor.moveToNext()) {
                    ChatRecords chatRecords = new ChatRecords();
                    chatRecords.setMessageId(cursor.getInt(cursor.getColumnIndex(COLUMN_MESSAGE_ID)));
                    chatRecords.setUserName(cursor.getString(cursor.getColumnIndex(COLUMN_MESSAGE_USERNAME)));
                    chatRecords.setFromUser(cursor.getString(cursor.getColumnIndex(COLUMN_MESSAGE_FROM)));
                    chatRecords.setMessageContent(cursor.getString(cursor.getColumnIndex(COLUMN_MESSAGE_CONTENT)));
                    chatRecords.setMessageTime(cursor.getString(cursor.getColumnIndex(COLUMN_MESSAGE_TIME)));
                    chatRecords.setIsRead(cursor.getInt(cursor.getColumnIndex(COLUMN_MESSAGE_SATUS)));
                    chatRecords.setIsSelf(cursor.getInt(cursor.getColumnIndex(COLUMN_IS_SELF)));
                    msg.add(chatRecords);
                }
                cursor.close();
                return msg;
            }
        }
        return null;
    }

    /**
     * 得到未读消息的条数
     *
     * @param userName
     * @param fromUser
     * @return
     */
    public int getMessageCount(String userName, String fromUser) {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        int count = -1;
        if (db.isOpen()) {
            Cursor cursor = db.rawQuery("select * from " + TABLE_NAME + " where " + COLUMN_MESSAGE_USERNAME + "='" +
                    userName + "' and " + COLUMN_MESSAGE_FROM +"='"+ fromUser + "' and " + COLUMN_MESSAGE_SATUS + "=0", null);
            if (cursor.getCount() > 0) {
                count = cursor.getCount();
            }
            cursor.close();
        }
        return count;

    }

    /**
     *
     * 得到唯独消息的条数
     * @param userName
     * @return
     */
    public int getMessageCount(String userName){
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        int count = -1;
        if (db.isOpen()) {
            Cursor cursor = db.rawQuery("select * from " + TABLE_NAME + " where " + COLUMN_MESSAGE_USERNAME + "='" +
                    userName + "' and "+ COLUMN_MESSAGE_SATUS + "=0", null);
            if (cursor.getCount() > 0) {
                count = cursor.getCount();
            }
            cursor.close();
        }
        return count;
    }
}
