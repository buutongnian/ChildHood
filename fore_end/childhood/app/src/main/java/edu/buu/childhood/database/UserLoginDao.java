package edu.buu.childhood.database;

/**
 * Created by lcc on 2016/7/29.
 */

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import edu.buu.childhood.my.pojo.UserLoginInfo;

public class UserLoginDao {
    public static final String TABLENAME = "loginuser";
    public static final String ID = "_id";
    public static final String TOKEN = "token";
    public static final String TOKENSECRET = "tokenSecret";
    public static final String USERNAME = "userName";
    public static final String USERPASSWORD = "userPassword";
    public static final String LATITUDE = "myLatitude";
    public static final String LONGTITUDE = "myLongitude";
    public static final String ISPERFECTINFO = "isperfectinfo";
    public static final String ISLOGIN = "isLogin";

    private SQLiteDatabase db;
    private DbOpenHelper dbHelper;

    public UserLoginDao(Context context) {
        dbHelper = DbOpenHelper.getInstance(context);
        db = dbHelper.getWritableDatabase();
    }

    public void Close() {
        db.close();
        dbHelper.close();
    }

    // 获取users表中的UserID、Access Token、Access Secret的记录
    public List<UserLoginInfo> GetUserList() {
        List<UserLoginInfo> userList = new ArrayList<UserLoginInfo>();
        Cursor cursor = db.query(TABLENAME, null, null, null, null,
                null, ID + " DESC");
        cursor.moveToFirst();
        while (!cursor.isAfterLast() && (cursor.getString(3) != null)) {
            UserLoginInfo user = new UserLoginInfo();
            user.setId(cursor.getString(0));
            user.setToken(cursor.getString(1));
            user.setTokenSecret(cursor.getString(2));
            user.setUserName(cursor.getString(3));
            user.setUserPassword(cursor.getString(4));
            user.setMyLatitude(cursor.getString(5));
            user.setMyLongitude(cursor.getString(6));
//            user.setIsPerfect(cursor.getInt(7));
            userList.add(user);
            cursor.moveToNext();
        }
        cursor.close();
        return userList;
    }

    // 判断users表中的是否包含某个UserID的记录
    public Boolean HaveUserInfo(String userName) {
        Boolean b = false;
        Cursor cursor = db.query(TABLENAME, null, USERNAME
                + "=?", new String[]{userName}, null, null, null);
        b = cursor.moveToFirst();
        Log.e("HaveUserInfo", b.toString());
        cursor.close();
        return b;
    }

    /**
     * 获取已登录且未退出登陆的用户信息
     * 通过其他模块的逻辑控制使得数据库中
     * 同一时刻仅有一条记录的登录状态被标记为1
     * 2016/11/04
     *
     * @return 登录用户信息
     * @auther joe
     */
    public UserLoginInfo getLoginUser() {
        UserLoginInfo userLoginInfo = new UserLoginInfo();
        Cursor cursor = db.query(TABLENAME, null, ISLOGIN + "=?", new String[]{"1"}, null,
                null, ID + " DESC");
        cursor.moveToFirst();
        if (cursor.getCount() != 0) {
            userLoginInfo.setId(cursor.getString(0));
            userLoginInfo.setToken(cursor.getString(1));
            userLoginInfo.setTokenSecret(cursor.getString(2));
            userLoginInfo.setUserName(cursor.getString(3));
            userLoginInfo.setUserPassword(cursor.getString(4));
            userLoginInfo.setMyLatitude(cursor.getString(5));
            userLoginInfo.setMyLongitude(cursor.getString(6));
            userLoginInfo.setIsPerfect(cursor.getInt(7));
            userLoginInfo.setIsLogin(cursor.getInt(8));
            return userLoginInfo;
        }
        return null;
    }


    // 更新users表的记录
    public int UpdateUserInfo(UserLoginInfo user) {
        ContentValues values = new ContentValues();
        //values.put(UserLoginInfo. USERNAME, user.getUserName());
        values.put(USERPASSWORD, user.getUserPassword());
        values.put(TOKEN, user.getToken());
        values.put(TOKENSECRET, user.getTokenSecret());
        values.put(LATITUDE, user.getMyLatitude());
        values.put(LONGTITUDE, user.getMyLongitude());
        values.put(ISPERFECTINFO, user.getIsPerfect());
        values.put(ISLOGIN, user.getIsLogin());
        int id = db.update(TABLENAME, values, USERNAME + "='"
                + user.getUserName() + "'", null);
        return id;
    }

    public int UpdateUserLoginInfo(String userName, int isLogin) {
        ContentValues values = new ContentValues();
        values.put(ISLOGIN, isLogin);
        int id = db.update(TABLENAME, values, USERNAME + "='"
                + userName + "'", null);
        return id;
    }

    public int UpdateUserPerfectInfo(String userName, int isPerfect) {
        ContentValues values = new ContentValues();
        values.put(ISPERFECTINFO, isPerfect);
        Log.d("initUserInfo_Update", String.valueOf(isPerfect));
        int id = db.update(TABLENAME, values, USERNAME + "='"
                + userName + "'", null);
        return id;
    }

    // 添加users表的记录
    public Long SaveUserInfo(UserLoginInfo user) {
        ContentValues values = new ContentValues();
        values.put(USERNAME, user.getUserName());
        values.put(USERPASSWORD, user.getUserPassword());
        values.put(TOKEN, user.getToken());
        values.put(TOKENSECRET, user.getTokenSecret());
        values.put(LATITUDE, user.getMyLatitude());
        values.put(LONGTITUDE, user.getMyLongitude());
        values.put(ISPERFECTINFO, user.getIsPerfect());
        values.put(ISLOGIN, user.getIsLogin());
        Long uid = db.insert(TABLENAME, ID, values);
        return uid;
    }


    // 删除users表的记录
    public int DelUserInfo(String UserName) {
        int id = db.delete(TABLENAME,
                USERNAME + "=?", new String[]{UserName});
        Log.e("DelUserLoginInfo", id + "");
        return id;
    }

    public static UserLoginInfo getUserByName(String userName, List<UserLoginInfo> userList) {
        UserLoginInfo UserLoginInfo = null;
        int size = userList.size();
        for (int i = 0; i < size; i++) {
            if (userName.equals(userList.get(i).getUserName())) {
                UserLoginInfo = userList.get(i);
                break;
            }
        }
        return UserLoginInfo;
    }

    public void deleteAll() {
        String sql = "delete from " + TABLENAME;
        db.execSQL(sql);
    }

    public Boolean queryIsNull() {
        Boolean b = false;
        Cursor cursor = db.query(TABLENAME, null, null
                , null, null, null, null);
        b = cursor.moveToFirst();
        Log.e("HaveUserInfo", b.toString());
        cursor.close();
        return b;
    }
}