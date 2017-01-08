package edu.buu.childhood.util;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;

public class BitmapUtil {

    public static String bitmapToString(Bitmap bitmap) {
        return bitmapToString(bitmap,100);
    }

    public static String bitmapToString(Bitmap bitmap, int bitmapQuality) {
        String string = null;
        ByteArrayOutputStream bStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, bitmapQuality, bStream);
        byte[] bytes = bStream.toByteArray();
        Log.d("byteImage",(new String(bytes)).length()+"sss");
        string = edu.buu.childhood.util.Base64.encodeToString(bytes,false);
        return string;
    }

    public static Bitmap stringToBitmap(String str) {
        Bitmap bitmap = null;
        try {
            byte[] bitmapArray = edu.buu.childhood.util.Base64.decode(str);
            bitmap = BitmapFactory.decodeByteArray(bitmapArray, 0, bitmapArray.length);
        } catch (Exception e) {
            Log.e("error", "convert string to bitmap failed.detail:" + e.getMessage());
            e.printStackTrace();
        }
        return bitmap;
    }

    public static InputStream stringToIs(String str){
        try{
            byte[] data = edu.buu.childhood.util.Base64.decode(str);
            return new ByteArrayInputStream(data);
        }catch(Exception e){
            Log.e("error", "convert string to inputstream failed.detail:" + e.getMessage());
            e.printStackTrace();
        }
        return null;
    }
}
