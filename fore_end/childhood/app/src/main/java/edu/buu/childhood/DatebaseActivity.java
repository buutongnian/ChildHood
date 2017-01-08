package edu.buu.childhood;

import android.app.Activity;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.widget.Toast;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;

/**
 * Created by lcc on 2016/6/18.
 */
public class DatebaseActivity extends Activity {
    private static String DATABASES_DIR = "/data/data/edu.buu.childhood/databases";
    private static String DATABASE_NAME = "ChildHood.db";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.datebase);
        copyDatabaseFile(this, false);
        SQLiteDatabase db = openOrCreateDatabase("ChildHood.db", this.MODE_PRIVATE, null);

        // db.execSQL("CREATE TABLE T_PUB_AREA(AREA_CODE INT PRIMARY KEY,AREA_NAME VARCHAR(100))");
        //  db.execSQL("CREATE TABLE T_PUB_PROVINCE(PROVINCE_CODE INT PRIMARY KEY,BELONGING INT,PROVINCE_NAME VARCHAR(100))");
        Cursor c = db.rawQuery("select * from T_PUB_AREA", null);
        while (c.moveToNext()) {
            String a = c.getString(c.getColumnIndex("AREA_NAME"));
            Toast.makeText(this, a + "2", Toast.LENGTH_SHORT).show();
        }

    }

    public void copyDatabaseFile(Context context, boolean isfored) {
        Toast.makeText(this, "22222", Toast.LENGTH_SHORT).show();
        File dir = new File(DATABASES_DIR);
        if (!dir.exists() || isfored) {
            try {
                dir.mkdir();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        File dest = new File(dir, DATABASE_NAME);
        if (dest.exists() && !isfored) {
            return;
        }

        try {
            if (dest.exists()) {
                dest.delete();
            }
            dest.createNewFile();
            InputStream fileInputStream = context.getAssets().open("ChildHood.db");
            //InputStream(context.getClass().getClassLoader().getResourceAsStream("assets/ChildHood.db"));
            int size = fileInputStream.available();
            Toast.makeText(this, size + "111", Toast.LENGTH_SHORT).show();
            byte buf[] = new byte[size];
            fileInputStream.read(buf);
            fileInputStream.close();
            FileOutputStream out = new FileOutputStream(dest);
            out.write(buf);
            Toast.makeText(this, "3333", Toast.LENGTH_SHORT).show();
            out.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}
