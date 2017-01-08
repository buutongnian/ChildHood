package edu.buu.childhood.util;

/**
 * Created by lcc on 2016/6/22.
 */
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteDatabase.CursorFactory;

public class DBHelper extends SQLiteOpenHelper{

    private String dbName;
    //调用父类构造器
    public DBHelper(Context context, String name, CursorFactory factory, int version) {
        super(context, name, factory, version);
        this.dbName=name;
    }

    /**
     * 当数据库首次创建时执行该方法，一般将创建表等初始化操作放在该方法中执行.
     * 重写onCreate方法，调用execSQL方法创建表
     * */
    @Override
    public void onCreate(SQLiteDatabase db) {
        if(!dbName.equals("ChildHood.db")){
            String createSql = "chatRoomMessage(id integer primary key autoincrement,roomid integer ,username varchar(50),fromname varchar(50),messagecontent varchar(1000),isread integer,isroommessage integer)";
            db.execSQL("CREATE TABLE IF NOT EXISTS "+createSql);
            String createPushSql="pushMessage(id integer primary key autoincrement,username varchar(50),gameid integer,gametitle varchar(100),gamefounder varchar(50),gameicon varchar(200),foundernickname varchar(50),foundtime varchar(50),gatherplace varchar(100),custominf varchar(1000),customcount integer,isread integer)";
            db.execSQL("CREATE TABLE IF NOT EXISTS "+createPushSql);
        }
    }
    //当打开数据库时传入的版本号与当前的版本号不同时会调用该方法
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
    }

}