package edu.buu.childhood.my.activity;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import edu.buu.childhood.R;
import edu.buu.childhood.common.CallBackPage;
import edu.buu.childhood.common.GlobalVeriable;
import edu.buu.childhood.common.activity.BaseAvtivity;
import edu.buu.childhood.util.BitmapUtil;
import edu.buu.childhood.util.CallBack;
import edu.buu.childhood.util.CloseActivity;
import edu.buu.childhood.util.HttpUtil;
import edu.buu.childhood.util.ImageXMLUtil;
import edu.buu.childhood.util.NetAsyncTask;
import edu.buu.childhood.util.NetWorkStatusUtil;
import edu.buu.childhood.util.URLUtil;


public class ChangeHeadPicture extends BaseAvtivity implements CallBack {

    private static final int REQUEST_CODE_PICK_IMAGE = 3301;
    private static final int REQUEST_CODE_CAPTURE_CAMEIA = 3302;
    private static final int PHOTO_REQUEST_CUT = 3303;
    /*拍照的照片存储位置*/
    private static final File PHOTO_DIR = new File(Environment.getExternalStorageDirectory() + "/DCIM/Camera");
    private String filePath = Environment.getExternalStorageDirectory() + "/DCIM/Camera";
    private File mCurrentPhotoFile;//照相机拍照得到的图片
    private ImageView imageView;
    private Button cancelChangeHeadimage;
    private Button userPicture;
    private Bitmap bitmap;
    private Dialog loadDialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        CloseActivity.activityList.add(this);
        setContentView(R.layout.change_head_picture);
        imageView = (ImageView) findViewById(R.id.change_head_imageView);
        cancelChangeHeadimage = (Button) findViewById(R.id.cancel_change_headimage);
        userPicture = (Button) findViewById(R.id.use_picture);
        Intent intent = getIntent();
        if (intent != null) {
            bitmap = intent.getParcelableExtra("bitmap");
            imageView.setImageBitmap(bitmap);
        }

        cancelChangeHeadimage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        userPicture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (bitmap != null) {
                    if (NetWorkStatusUtil.checkNetWorkStatus(getApplicationContext())) {
                        Map map = new HashMap();
                        map.put("userName", ((GlobalVeriable) getApplication()).getUserName());
                        new NetAsyncTask(ChangeHeadPicture.this).execute(URLUtil.getURL("uploadHeadImage", map));
                        loadDialog = showDialog(ChangeHeadPicture.this);
                    }
                }
            }
        });

    }

    @Override
    public CallBackPage doInBackground(String url) {
        HttpUtil httpUtil = new HttpUtil();

        if (bitmap != null) {
            Map imageMap = new HashMap();
            String bitmapStr = new String();
            bitmapStr = BitmapUtil.bitmapToString(bitmap);
            String imageXML = ImageXMLUtil.getXML(bitmapStr);
            //int bitmapSize = bitmap.getWidth()*bitmap.getHeight();
            //Log.d("bitmapSize",String.valueOf(bitmapSize);
            imageMap.put("userHeadImage", imageXML);
            BitmapUtil.bitmapToString(bitmap);
            CallBackPage callBackPage = new CallBackPage();
            callBackPage.setFalg(httpUtil.doPost(url, imageMap));
            return callBackPage;
        }
        return null;
    }

    @Override
    public void getResult(CallBackPage result) {
        if (result != null) {
            if ("SUCCESS".equals(result.getFalg())) {
                Intent intent = new Intent(ChangeHeadPicture.this, HeadPicture.class);
                intent.putExtra("imageBitmap", bitmap);
                loadDialog.dismiss();
                startActivity(intent);
                finish();
                bitmap.recycle();
            }
        }
    }
}