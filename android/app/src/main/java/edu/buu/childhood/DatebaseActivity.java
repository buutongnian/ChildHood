package edu.buu.childhood;

import android.app.Activity;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.widget.Toast;

/**
 * Created by lcc on 2016/6/18.
 */
public class DatebaseActivity extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.datebase);
        SQLiteDatabase db = openOrCreateDatabase("ChildHood.db",this.MODE_PRIVATE, null);

        // db.execSQL("CREATE TABLE T_PUB_AREA(AREA_CODE INT PRIMARY KEY,AREA_NAME VARCHAR(100))");
        //  db.execSQL("CREATE TABLE T_PUB_PROVINCE(PROVINCE_CODE INT PRIMARY KEY,BELONGING INT,PROVINCE_NAME VARCHAR(100))");
        Cursor c=db.rawQuery("select * from T_PUB_AREA",null);
        while(c.moveToNext()){
            String a=c.getString(c.getColumnIndex("AREA_NAME"));
            Toast.makeText(this,a+"2",Toast.LENGTH_SHORT).show();
        }
    }
}
