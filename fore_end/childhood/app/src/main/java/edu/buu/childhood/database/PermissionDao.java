package edu.buu.childhood.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import edu.buu.childhood.common.Permission;

/**
 * Created by Administrator on 2016/10/11.
 */
public class PermissionDao {
    public static final String TABLE_NAME = "PERMISSON";
    public static final String COLUMN_PER_ID = "ID";
    public static final String COLUMN_PER_USERNAME = "USERNAME";
    public static final String COLUMN_PER_NAME = "PERNAME";
    public static final String COLUMN_PER_STATUS = "PERSTATUS";

    private DbOpenHelper dbOpenHelper;

    public PermissionDao(Context context) {
        dbOpenHelper = DbOpenHelper.getInstance(context);
    }

    public void savePermission(Permission permission) {
        SQLiteDatabase db = dbOpenHelper.getWritableDatabase();
        if (db.isOpen()) {
            ContentValues values = new ContentValues();
            values.put(COLUMN_PER_USERNAME, permission.getUserName());
            values.put(COLUMN_PER_NAME, permission.getPermissionName());
            values.put(COLUMN_PER_STATUS, permission.getPermissionStatus());
            db.insert(TABLE_NAME, null, values);

        }
    }

    public void updatePermission(Permission permission) {
        SQLiteDatabase db = dbOpenHelper.getWritableDatabase();
        if (db.isOpen()) {
            ContentValues values = new ContentValues();
            values.put(COLUMN_PER_STATUS, permission.getPermissionStatus());
            db.update(TABLE_NAME, values, COLUMN_PER_USERNAME + "='" + permission.getUserName() + "' and " + COLUMN_PER_NAME + "='"
                    + permission.getPermissionName() + "'", null);
        }
    }

    public Boolean haveUserPerssion(String userName, String permissionName) {
        SQLiteDatabase db = dbOpenHelper.getWritableDatabase();
        Cursor cursor = db.rawQuery("select * from " + TABLE_NAME + " where " + COLUMN_PER_USERNAME + "='" + userName + "' and " +
                COLUMN_PER_NAME + "='" + permissionName + "'", null);
        if (cursor.getCount() > 0) {
            return true;
        }
        cursor.close();
        return false;
    }

    public String getPerssion(String userName, String permissionName) {
        String result = null;
        SQLiteDatabase db = dbOpenHelper.getWritableDatabase();
        Cursor cursor = db.rawQuery("select * from " + TABLE_NAME + " where " + COLUMN_PER_USERNAME + "='" + userName + "' and " +
                COLUMN_PER_NAME + "='" + permissionName + "'", null);
        while (cursor.moveToNext()) {
            result = cursor.getString(cursor.getColumnIndex(COLUMN_PER_STATUS));
        }
        return result;
    }
}
