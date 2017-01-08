package edu.buu.childhood.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import edu.buu.childhood.chat.bean.InviteMessage;


public class InviteMessgeDao {
    public static final String TABLE_NAME = "new_friends_msgs";
    public static final String COLUMN_NAME_ID = "id";
    public static final String COLUMN_NAME_USER = "username";
    public static final String COLUMN_NAME_FROM = "fromuser";
    public static final String COLUMN_NAME_HEADERIMG = "image";
    public static final String COLUMN_NAME_TIME = "time";
    public static final String COLUMN_NAME_REASON = "reason";
    public static final String COLUMN_NAME_STATUS = "status";
    public static final String COLUMN_NAME_ISINVITEFROMME = "isInviteFromMe";

    private DbOpenHelper dbHelper;

    public InviteMessgeDao(Context context) {
        dbHelper = DbOpenHelper.getInstance(context);
    }

    /**
     * 保存message
     *
     * @param message
     * @return 返回这条messaged在db中的id
     */
    public synchronized Integer saveMessage(InviteMessage message) {
        Log.i("saveMessage","ssss");
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        int id = -1;
        if (db.isOpen()) {
            ContentValues values = new ContentValues();
            values.put(COLUMN_NAME_USER, message.getUserName());
            values.put(COLUMN_NAME_FROM, message.getFrom());
            values.put(COLUMN_NAME_REASON, message.getReason());
            values.put(COLUMN_NAME_TIME, message.getTime());
            values.put(COLUMN_NAME_STATUS, message.getStatus().ordinal());
            db.insert(TABLE_NAME, null, values);
            Cursor cursor = db.rawQuery("select last_insert_rowid() from " + TABLE_NAME, null);
            if (cursor.moveToFirst()) {
                id = cursor.getInt(0);
            }
            cursor.close();
        }
        return id;
    }

    /**
     * 更新message
     */
    public void updateMessage(InviteMessage inviteMessge) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        if (db.isOpen()) {
            ContentValues values = new ContentValues();
            values.put(COLUMN_NAME_REASON, inviteMessge.getReason());
            values.put(COLUMN_NAME_STATUS, inviteMessge.getStatus().ordinal());
            db.update(TABLE_NAME, values, COLUMN_NAME_USER + " = ? and " + COLUMN_NAME_FROM + " = ?", new String[]{inviteMessge.getUserName(), inviteMessge.getFrom()});
        }
    }

    /**
     * 获取messges
     *
     * @return
     */
    public List<InviteMessage> getMessagesList(String userName) {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        List<InviteMessage> msgs = new ArrayList<InviteMessage>();
        if (db.isOpen()) {
            Cursor cursor = db.rawQuery("select * from " + TABLE_NAME + " where " + COLUMN_NAME_USER + " ='" + userName + "'order by " + COLUMN_NAME_ID + " desc", null);
            while (cursor.moveToNext()) {
                InviteMessage msg = new InviteMessage();
                int id = cursor.getInt(cursor.getColumnIndex(COLUMN_NAME_ID));
                String from = cursor.getString(cursor.getColumnIndex(COLUMN_NAME_FROM));
                String reason = cursor.getString(cursor.getColumnIndex(COLUMN_NAME_REASON));
                long time = cursor.getLong(cursor.getColumnIndex(COLUMN_NAME_TIME));
                int status = cursor.getInt(cursor.getColumnIndex(COLUMN_NAME_STATUS));
                msg.setId(id);
                msg.setFrom(from);
                msg.setReason(reason);
                msg.setTime(time);
                msg.setUserName(userName);
                if (status == InviteMessage.InviteMesageStatus.BEAGREED.ordinal())
                    msg.setStatus(InviteMessage.InviteMesageStatus.BEAGREED);
                else if (status == InviteMessage.InviteMesageStatus.BEREFUSED.ordinal())
                    msg.setStatus(InviteMessage.InviteMesageStatus.BEREFUSED);
                else if (status == InviteMessage.InviteMesageStatus.AGREED.ordinal())
                    msg.setStatus(InviteMessage.InviteMesageStatus.AGREED);
                else if (status == InviteMessage.InviteMesageStatus.REFUSED.ordinal())
                    msg.setStatus(InviteMessage.InviteMesageStatus.REFUSED);
                else if (status == InviteMessage.InviteMesageStatus.BEAPPLYED.ordinal()) {
                    msg.setStatus(InviteMessage.InviteMesageStatus.BEAPPLYED);
                }
                msgs.add(msg);
            }
            cursor.close();
        }
        return msgs;
    }

    public void deleteMessage(String from) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        if (db.isOpen()) {
            db.delete(TABLE_NAME, COLUMN_NAME_FROM + " = ?", new String[]{from});
        }
    }

    public void deleteALLMessage() {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        if (db.isOpen()) {
            db.execSQL("DELETE FROM " + TABLE_NAME);
        }
        if (db != null) {
            db.close();
        }
    }

    public Boolean haveInviteMessage(String userName, String fromUser) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        Cursor cursor = db.rawQuery("select * from " + TABLE_NAME + " where " + COLUMN_NAME_USER + " ='" + userName +
                "' and " + COLUMN_NAME_FROM + " ='" + fromUser + "'", null);
        if (cursor.getCount() > 0) {
            return true;
        }
        cursor.close();
        return false;
    }

    public InviteMessage getInviteMessage(String userName, String fromUser) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        Cursor cursor = db.rawQuery("select * from " + TABLE_NAME + " where " + COLUMN_NAME_USER + " ='" + userName +
                "' and " + COLUMN_NAME_FROM + " ='" + fromUser + "'", null);
        while (cursor.moveToNext()) {
            InviteMessage inviteMessage = new InviteMessage();
            inviteMessage.setUserName(cursor.getString(cursor.getColumnIndex(COLUMN_NAME_USER)));
            inviteMessage.setFrom(cursor.getString(cursor.getColumnIndex(COLUMN_NAME_FROM)));
            inviteMessage.setImageUrl(cursor.getString(cursor.getColumnIndex(COLUMN_NAME_HEADERIMG)));
            inviteMessage.setReason(cursor.getString(cursor.getColumnIndex(COLUMN_NAME_REASON)));
            int status = cursor.getInt(cursor.getColumnIndex(COLUMN_NAME_STATUS));
            if (status == InviteMessage.InviteMesageStatus.BEAGREED.ordinal())
                inviteMessage.setStatus(InviteMessage.InviteMesageStatus.BEAGREED);
            else if (status == InviteMessage.InviteMesageStatus.BEREFUSED.ordinal())
                inviteMessage.setStatus(InviteMessage.InviteMesageStatus.BEREFUSED);
            else if (status == InviteMessage.InviteMesageStatus.AGREED.ordinal())
                inviteMessage.setStatus(InviteMessage.InviteMesageStatus.AGREED);
            else if (status == InviteMessage.InviteMesageStatus.REFUSED.ordinal())
                inviteMessage.setStatus(InviteMessage.InviteMesageStatus.REFUSED);
            else if (status == InviteMessage.InviteMesageStatus.BEAPPLYED.ordinal()) {
                inviteMessage.setStatus(InviteMessage.InviteMesageStatus.BEAPPLYED);
            }
            return inviteMessage;
        }
        cursor.close();
        return  null;
    }
}
