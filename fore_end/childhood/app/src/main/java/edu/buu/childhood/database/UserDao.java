
package edu.buu.childhood.database;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import edu.buu.childhood.chat.bean.User;


@SuppressLint("DefaultLocale")
public class UserDao {
    public static final String TABLE_NAME = "uers";
    public static final String COLUMN_NAME_ID = "username";
    public static final String COLUMN_NAME_NICK = "nick";
    public static final String COLUMN_NAME_AVATAR = "avatar";//图像
    public static final String COLUMN_NAME_SIGN = "sign";//备注
    public static final String COLUMN_NAME_TEL = "tel";//电话
    public static final String COLUMN_NAME_IS_STRANGER = "is_stranger";//是否为陌生人
    public static final String COLUMN_NAME_ACHIEVEMENT_POINT = "achievementpoint";//用户成就点
    public static final String COLUMN_NAME_USER_LEVEL = "userlevel";//用户等级
    public static final String COLUMN_NAME_CURRENT_LEVEL_POINT = "currentLevelPoint";//用户当前等级点
    public static final String COLUMN_NAME_NEXT_LEVLE_POINT = "nextLevelPoint";//用户下一个等级点
    public static final String COLUMN_NAME_TOTAL_GAME_COUNT = "totalGameCount";//用户游戏数量
    public static final String COLUMN_NAME_TOTAL_LIKE_COUNT = "totalLikeCount";//用户被点赞量
    public static final String COLUMN_NAME_LEVELNAME = "levelname";//用户等级称号
    public static final String COLUMN_NAME_PROVINCE = "province";
    public static final String COLUMN_NAME_CITY = "city";


    private DbOpenHelper dbHelper;

    public UserDao(Context context) {
        dbHelper = DbOpenHelper.getInstance(context);
    }

    /**
     * 保存好友list
     *
     * @param contactList
     */
    public void saveContactList(List<User> contactList) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        if (db.isOpen()) {
            db.delete(TABLE_NAME, null, null);
            for (User user : contactList) {
                ContentValues values = new ContentValues();
                values.put(COLUMN_NAME_ID, user.getUserName());
                if (user.getNick() != null) {
                    values.put(COLUMN_NAME_NICK, user.getNick());
                }
                if (user.getTel() != null) {
                    values.put(COLUMN_NAME_TEL, user.getTel());
                }
                if (user.getAvatar() != null) {
                    values.put(COLUMN_NAME_AVATAR, user.getAvatar());
                }
                if (user.getSign() != null) {
                    values.put(COLUMN_NAME_SIGN, user.getSign());
                }
                values.put(COLUMN_NAME_ACHIEVEMENT_POINT, user.getAchievementPoint());
                values.put(COLUMN_NAME_CURRENT_LEVEL_POINT, user.getCurrentLevelPoint());
                values.put(COLUMN_NAME_NEXT_LEVLE_POINT, user.getNextLevelPoint());
                values.put(COLUMN_NAME_TOTAL_GAME_COUNT, user.getTotalGameCount());
                values.put(COLUMN_NAME_TOTAL_LIKE_COUNT, user.getTotalLikeCount());
                values.put(COLUMN_NAME_USER_LEVEL, user.getUserLevel());
                values.put(COLUMN_NAME_LEVELNAME, user.getLevelName());
                if (user.getProvince() != null) {
                    values.put(COLUMN_NAME_PROVINCE, user.getProvince());
                }
                if (user.getCity() != null) {
                    values.put(COLUMN_NAME_CITY, user.getCity());
                }
                db.replace(TABLE_NAME, null, values);
            }
        }
    }

    /**
     * 获取好友list
     *
     * @return
     */
    @SuppressLint("DefaultLocale")
    public Map<String, User> getContactList() {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Map<String, User> users = new HashMap<String, User>();
        if (db.isOpen()) {
            Cursor cursor = db.rawQuery("select * from " + TABLE_NAME /* + " desc" */, null);
            while (cursor.moveToNext()) {
                String username = cursor.getString(cursor.getColumnIndex(COLUMN_NAME_ID));
                String nick = cursor.getString(cursor.getColumnIndex(COLUMN_NAME_NICK));
                String avatar = cursor.getString(cursor.getColumnIndex(COLUMN_NAME_AVATAR));
                String tel = cursor.getString(cursor.getColumnIndex(COLUMN_NAME_TEL));
                String sign = cursor.getString(cursor.getColumnIndex(COLUMN_NAME_SIGN));
                User user = new User();
                user.setUserName(username);
                user.setNick(nick);
                user.setSign(sign);
                user.setTel(tel);
                user.setAvatar(avatar);
                user.setAchievementPoint(cursor.getInt(cursor.getColumnIndex(COLUMN_NAME_ACHIEVEMENT_POINT)));
                user.setCurrentLevelPoint(cursor.getInt(cursor.getColumnIndex(COLUMN_NAME_CURRENT_LEVEL_POINT)));
                user.setNextLevelPoint(cursor.getInt(cursor.getColumnIndex(COLUMN_NAME_NEXT_LEVLE_POINT)));
                user.setTotalGameCount(cursor.getInt(cursor.getColumnIndex(COLUMN_NAME_TOTAL_GAME_COUNT)));
                user.setTotalLikeCount(cursor.getInt(cursor.getColumnIndex(COLUMN_NAME_TOTAL_LIKE_COUNT)));
                user.setUserLevel(cursor.getInt(cursor.getColumnIndex(COLUMN_NAME_USER_LEVEL)));
                user.setLevelName(cursor.getString(cursor.getColumnIndex(COLUMN_NAME_LEVELNAME)));
                user.setProvince(cursor.getString(cursor.getColumnIndex(COLUMN_NAME_PROVINCE)));
                user.setCity(cursor.getString(cursor.getColumnIndex(COLUMN_NAME_CITY)));
                users.put(username, user);
            }
            cursor.close();
        }
        return users;
    }

    /**
     * 删除一个联系人
     *
     * @param username
     */
    public void deleteContact(String username) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        if (db.isOpen()) {
            db.delete(TABLE_NAME, COLUMN_NAME_ID + " = ?", new String[]{username});
        }
    }

    /**
     * 保存一个联系人
     *
     * @param user
     */
    public void saveContact(User user) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_NAME_ID, user.getUserName());
        if (user.getNick() != null) {
            values.put(COLUMN_NAME_NICK, user.getNick());
        }
        if (user.getTel() != null) {
            values.put(COLUMN_NAME_TEL, user.getTel());
        }
        if (user.getUserImage() != null) {
            values.put(COLUMN_NAME_AVATAR, user.getUserImage());
        }
        if (user.getSign() != null) {
            values.put(COLUMN_NAME_SIGN, user.getSign());
        }
        values.put(COLUMN_NAME_ACHIEVEMENT_POINT, user.getAchievementPoint());
        values.put(COLUMN_NAME_CURRENT_LEVEL_POINT, user.getCurrentLevelPoint());
        values.put(COLUMN_NAME_NEXT_LEVLE_POINT, user.getNextLevelPoint());
        values.put(COLUMN_NAME_TOTAL_GAME_COUNT, user.getTotalGameCount());
        values.put(COLUMN_NAME_TOTAL_LIKE_COUNT, user.getTotalLikeCount());
        values.put(COLUMN_NAME_USER_LEVEL, user.getUserLevel());
        values.put(COLUMN_NAME_LEVELNAME, user.getLevelName());
        if (user.getProvince() != null) {
            values.put(COLUMN_NAME_PROVINCE, user.getProvince());
        }
        if (user.getCity() != null) {
            values.put(COLUMN_NAME_CITY, user.getCity());
        }
        if (db.isOpen()) {
            db.replace(TABLE_NAME, null, values);
        }
    }

    public User getUserInfo(String userName) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        Cursor cursor = db.rawQuery("select * from " + TABLE_NAME + " where " + COLUMN_NAME_ID + "='" + userName + "'", null);
        User user = new User();
        if (cursor.getCount() > 0) {
            while (cursor.moveToNext()) {
                user.setUserName(cursor.getString(cursor.getColumnIndex(COLUMN_NAME_ID)));
                user.setUserImage(cursor.getString(cursor.getColumnIndex(COLUMN_NAME_AVATAR)));
                user.setUsernick(cursor.getString(cursor.getColumnIndex(COLUMN_NAME_NICK)));
                user.setSign(cursor.getString(cursor.getColumnIndex(COLUMN_NAME_SIGN)));
                user.setTel(cursor.getString(cursor.getColumnIndex(COLUMN_NAME_TEL)));
                user.setAchievementPoint(cursor.getInt(cursor.getColumnIndex(COLUMN_NAME_ACHIEVEMENT_POINT)));
                user.setCurrentLevelPoint(cursor.getInt(cursor.getColumnIndex(COLUMN_NAME_CURRENT_LEVEL_POINT)));
                user.setNextLevelPoint(cursor.getInt(cursor.getColumnIndex(COLUMN_NAME_NEXT_LEVLE_POINT)));
                user.setTotalGameCount(cursor.getInt(cursor.getColumnIndex(COLUMN_NAME_TOTAL_GAME_COUNT)));
                user.setTotalLikeCount(cursor.getInt(cursor.getColumnIndex(COLUMN_NAME_TOTAL_LIKE_COUNT)));
                user.setUserLevel(cursor.getInt(cursor.getColumnIndex(COLUMN_NAME_USER_LEVEL)));
                user.setLevelName(cursor.getString(cursor.getColumnIndex(COLUMN_NAME_LEVELNAME)));
                user.setProvince(cursor.getString(cursor.getColumnIndex(COLUMN_NAME_PROVINCE)));
                user.setCity(cursor.getString(cursor.getColumnIndex(COLUMN_NAME_CITY)));
            }
            return user;
        }
        return null;
    }

    public void updateUserInfo(User user) {
        Log.i("point", user.getAchievementPoint() + " " + user.getNextLevelPoint() + " " + user.getProvince());
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_NAME_AVATAR, user.getUserImage());
        values.put(COLUMN_NAME_NICK, user.getNick());
        values.put(COLUMN_NAME_ACHIEVEMENT_POINT, user.getAchievementPoint());
        Log.i("sss", user.getAchievementPoint() + " ");
        values.put(COLUMN_NAME_CURRENT_LEVEL_POINT, user.getCurrentLevelPoint());
        values.put(COLUMN_NAME_NEXT_LEVLE_POINT, user.getNextLevelPoint());
        values.put(COLUMN_NAME_TOTAL_GAME_COUNT, user.getTotalGameCount());
        values.put(COLUMN_NAME_TOTAL_LIKE_COUNT, user.getTotalLikeCount());
        values.put(COLUMN_NAME_USER_LEVEL, user.getUserLevel());
        values.put(COLUMN_NAME_LEVELNAME, user.getLevelName());
        if (user.getProvince() != null) {
            values.put(COLUMN_NAME_PROVINCE, user.getProvince());
        }
        if (user.getCity() != null) {
            values.put(COLUMN_NAME_CITY, user.getCity());
        }
        // values.put(COLUMN_NAME_TEL,user.get);
        db.update(TABLE_NAME, values, COLUMN_NAME_ID + " = ?", new String[]{user.getUserName()});
    }

    /**
     * 查询是否存在该用户
     *
     * @param userName
     * @return
     */
    public Boolean isUserInfo(String userName) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        Cursor cursor = db.rawQuery("select * from " + TABLE_NAME + " where " + COLUMN_NAME_ID + "='" +
                userName + "'", null);
        if (cursor.getCount() > 0) {
            return true;
        }
        cursor.close();
        return false;
    }

    public void saveUser(edu.buu.childhood.my.pojo.User user) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_NAME_ID, user.getUserName());
        values.put(COLUMN_NAME_NICK, user.getUserNickname());
        values.put(COLUMN_NAME_TEL, user.getUserTelNum());
        values.put(COLUMN_NAME_AVATAR, user.getUserHeadImage());
        values.put(COLUMN_NAME_ACHIEVEMENT_POINT, user.getAchievementPoint());
        values.put(COLUMN_NAME_USER_LEVEL, user.getUserLevel());
        values.put(COLUMN_NAME_TOTAL_LIKE_COUNT,user.getLikeCount());
        if (db.isOpen()) {
            db.replace(TABLE_NAME, null, values);
        }
    }
}
