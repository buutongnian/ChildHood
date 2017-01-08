package edu.buu.childhood.util;

import android.content.Context;
import android.util.Log;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;

/**
 * Created by lcc on 2016/7/3.
 */
public class CreateDatabase {
    private static String DATABASES_DIR = "/data/data/edu.buu.childhood/databases";
    private static String DATABASE_NAME = "ChildHood.db";

    public void copyDatabaseFile(Context context) {
        // Toast.makeText(this, "22222", Toast.LENGTH_SHORT).show();
        File dir = new File(DATABASES_DIR);
        if (!dir.exists()) {
            try {
                dir.mkdir();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        File dest = new File(dir, DATABASE_NAME);
        if (dest.exists()) {
            Log.i("ff","exists");
            return;
        }

        try {
            if (dest.exists()) {
                dest.delete();
            }
            Log.i("ff","exists111");
            dest.createNewFile();
            InputStream fileInputStream = context.getAssets().open("ChildHood.db");
            //InputStream(context.getClass().getClassLoader().getResourceAsStream("assets/ChildHood.db"));
            int size = fileInputStream.available();
            //  Toast.makeText(this, size + "111", Toast.LENGTH_SHORT).show();
            byte buf[] = new byte[size];
            fileInputStream.read(buf);
            fileInputStream.close();
            FileOutputStream out = new FileOutputStream(dest);
            out.write(buf);
            //Toast.makeText(this, "3333", Toast.LENGTH_SHORT).show();
            out.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
