/**
 * Copyright (C) 2013-2014 EaseMob Technologies. All rights reserved.
 * <p>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * http://www.apache.org/licenses/LICENSE-2.0
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package edu.buu.childhood.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;


public class DbOpenHelper extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 1;
    private static DbOpenHelper instance;
    private static final String USERNAME_TABLE_LOGIN = "CREATE TABLE IF NOT EXISTS " +
            UserLoginDao.TABLENAME + "(" +
            UserLoginDao.ID + " integer primary key," +
            UserLoginDao.TOKEN + " varchar," +
            UserLoginDao.TOKENSECRET + " varchar," +
            UserLoginDao.USERNAME + " varchar," +
            UserLoginDao.USERPASSWORD + " varchar," +
            UserLoginDao.LATITUDE + " varchar," +
            UserLoginDao.LONGTITUDE + " varchar, " +
            UserLoginDao.ISPERFECTINFO + " int," +
            UserLoginDao.ISLOGIN + " int)";
    private static final String USERNAME_TABLE_CREATE = "CREATE TABLE "
            + UserDao.TABLE_NAME + " ("
            + UserDao.COLUMN_NAME_NICK + " TEXT, "
            + UserDao.COLUMN_NAME_AVATAR + " TEXT, "
            + UserDao.COLUMN_NAME_SIGN + " TEXT, "
            + UserDao.COLUMN_NAME_TEL + " TEXT, "
            + UserDao.COLUMN_NAME_ID + " TEXT PRIMARY KEY, "
            + UserDao.COLUMN_NAME_ACHIEVEMENT_POINT + " int, "
            + UserDao.COLUMN_NAME_CURRENT_LEVEL_POINT + " int, "
            + UserDao.COLUMN_NAME_NEXT_LEVLE_POINT + " int, "
            + UserDao.COLUMN_NAME_TOTAL_GAME_COUNT + " int, "
            + UserDao.COLUMN_NAME_TOTAL_LIKE_COUNT + " int, "
            + UserDao.COLUMN_NAME_LEVELNAME + " TEXT, "
            + UserDao.COLUMN_NAME_PROVINCE + " Text, "
            + UserDao.COLUMN_NAME_CITY + " Text, "
            + UserDao.COLUMN_NAME_USER_LEVEL + " int);";


    private static final String INIVTE_MESSAGE_TABLE_CREATE = "CREATE TABLE "
            + InviteMessgeDao.TABLE_NAME + " ("
            + InviteMessgeDao.COLUMN_NAME_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + InviteMessgeDao.COLUMN_NAME_USER + " TEXT, "
            + InviteMessgeDao.COLUMN_NAME_FROM + " TEXT, "
            + InviteMessgeDao.COLUMN_NAME_HEADERIMG + " TEXT, "
            + InviteMessgeDao.COLUMN_NAME_REASON + " TEXT, "
            + InviteMessgeDao.COLUMN_NAME_STATUS + " INTEGER, "
            + InviteMessgeDao.COLUMN_NAME_ISINVITEFROMME + " INTEGER, "
            + InviteMessgeDao.COLUMN_NAME_TIME + " TEXT); ";

    private static final String CHAT_RECORDS_TABLE_CREATE = "CREATE TABLE "
            + ChatRecordsDao.TABLE_NAME + "("
            + ChatRecordsDao.COLUMN_MESSAGE_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + ChatRecordsDao.COLUMN_MESSAGE_USERNAME + " TEXT, "
            + ChatRecordsDao.COLUMN_MESSAGE_FROM + " TEXT, "
            + ChatRecordsDao.COLUMN_MESSAGE_CONTENT + " TEXT, "
            + ChatRecordsDao.COLUMN_MESSAGE_TIME + " TEXT, "
            + ChatRecordsDao.COLUMN_IS_SELF + " INTEGER, "
            + ChatRecordsDao.COLUMN_MESSAGE_SATUS + " INTEGER);";

    private static final String CHAT_RECORDS_LIST_TABLE_CREATE = "CREATE TABLE "
            + ChatRecodrsListDao.TABLE_NAME + "("
            + ChatRecodrsListDao.CHAT_LIST_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
            + ChatRecodrsListDao.CHAT_LIST_USERNAME + " TEXT, "
            + ChatRecodrsListDao.CHAT_LIST_FROMUSER + " TEXT, "
            + ChatRecodrsListDao.CHAT_LIST_LASTMESSAGE + " TEXT, "
            + ChatRecodrsListDao.CHAT_LIST_LAST_TIME + " TEXT);";

    private static final String USER_PERSSION_CREATE = "CREATE TABLE "
            + PermissionDao.TABLE_NAME + "("
            + PermissionDao.COLUMN_PER_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + PermissionDao.COLUMN_PER_USERNAME + " TEXT, "
            + PermissionDao.COLUMN_PER_NAME + " TEXT, "
            + PermissionDao.COLUMN_PER_STATUS + " TEXT);";

    private DbOpenHelper(Context context) {
        super(context, getUserDatabaseName(), null, DATABASE_VERSION);
    }

    public static DbOpenHelper getInstance(Context context) {
        if (instance == null) {
            instance = new DbOpenHelper(context.getApplicationContext());
        }
        return instance;
    }

    private static String getUserDatabaseName() {
        return "user.db";
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(USERNAME_TABLE_CREATE);
        db.execSQL(INIVTE_MESSAGE_TABLE_CREATE);
        db.execSQL(USERNAME_TABLE_LOGIN);
        db.execSQL(CHAT_RECORDS_TABLE_CREATE);
        db.execSQL(CHAT_RECORDS_LIST_TABLE_CREATE);
        db.execSQL(USER_PERSSION_CREATE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public void closeDB() {
        if (instance != null) {
            try {
                SQLiteDatabase db = instance.getWritableDatabase();
                db.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
            instance = null;
        }
    }

}
