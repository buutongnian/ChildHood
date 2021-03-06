package edu.buu.childhood.my.activity;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import edu.buu.childhood.R;
import edu.buu.childhood.common.C;
import edu.buu.childhood.common.GlobalVeriable;
import edu.buu.childhood.common.Message;
import edu.buu.childhood.common.activity.BaseAvtivity;
import edu.buu.childhood.my.pojo.User;
import edu.buu.childhood.util.AsyncImageLoader;
import edu.buu.childhood.util.CloseActivity;
import edu.buu.childhood.util.HttpUtil;
import edu.buu.childhood.util.NewCallBack;
import edu.buu.childhood.util.NewNetAsyncTask;
import edu.buu.childhood.util.URLUtil;


public class HeadPicture extends BaseAvtivity implements NewCallBack {

    private static final int REQUEST_CODE_PICK_IMAGE = 3301;
    private static final int REQUEST_CODE_CAPTURE_CAMEIA = 3302;
    private static final int PHOTO_REQUEST_CUT = 3303;
    /*拍照的照片存储位置*/
    private static final File PHOTO_DIR = new File(Environment.getExternalStorageDirectory() + "/DCIM/Camera");
    private String filePath = Environment.getExternalStorageDirectory() + "/DCIM/Camera";
    private File mCurrentPhotoFile;//照相机拍照得到的图片
    private ImageView imageView;
    private Bitmap bitmap;

    private AsyncImageLoader asyncImageLoader;

    private Gson json = new Gson();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        CloseActivity.activityList.add(this);
        setContentView(R.layout.head_picture);
        asyncImageLoader = new AsyncImageLoader(this);
        imageView = (ImageView) findViewById(R.id.head_imageView);
        Intent intent = getIntent();
        if (intent != null) {
            bitmap = intent.getParcelableExtra("imageBitmap");
            if (bitmap != null) {
                imageView.setImageBitmap(bitmap);
            }
        }
    }

    public void onClick(View view) {
        showPopupMenu(view);
    }  //点击按钮出现下拉菜单

    public void showPopupMenu(View view) {
        PopupMenu popupMenu = new PopupMenu(this, view);
        popupMenu.getMenuInflater().inflate(R.menu.touxiang_menu, popupMenu.getMenu());
        popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                // Intent intent=new Intent(HeadPicture.this,ChangeHeadPicture.class);
                switch (item.getItemId()) {
                    case R.id.menu_album:   //读取相册
                        //intent.putExtra("type",1);
                        getImageFromAlbum();
                        break;
                    case R.id.menu_camera:   //拍照选择
                        //intent.putExtra("type",2);
                        //getImageFromCamera();
                        String status = Environment.getExternalStorageState();
                        if (status.equals(Environment.MEDIA_MOUNTED)) {//判断是否有SD卡
                            doTakePhoto();// 用户点击了从照相机获取
                        } else {
                            Toast.makeText(getApplicationContext(), "没有sd卡", Toast.LENGTH_LONG).show();
                        }
                        break;
                    case R.id.menu_save:    //保存图像到手机
                        break;
                }
                // startActivity(intent);
                return true;
            }
        });

        popupMenu.show();
    }

    @Override
    public void getResult(String jsonStr) {
        if (jsonStr != null) {
            Message reciveMsg = json.fromJson(jsonStr, new TypeToken<Message<?>>() {
            }.getType());
            if (reciveMsg.getMessageCode() != null && reciveMsg.getMessageCode() instanceof String) {
                switch (reciveMsg.getMessageCode()) {
                    case C.my.USER_INF_QUERY_SUCCESS:
                        reciveMsg = json.fromJson(jsonStr, new TypeToken<Message<User>>() {
                        }.getType());
                        User user = ((Message<User>) reciveMsg).getMessageContent();
                        if (imageView != null && asyncImageLoader != null) {
                            imageView.setTag(user.getUserHeadImage());
                            asyncImageLoader.loadImage(imageView, user.getUserHeadImage());
                        }
                        break;
                    default:
                        break;
                }
            }
        }
    }

    /**
     * 新回调函数，传入url数组，一般情况只传入一个，即url[0]
     * 为兼容旧回调函数故做此处理
     * 2016/11/08
     *
     * @param url 所传入url数组
     * @return 返回请求API接口所得到的json数据
     */
    @Override
    public String doInBackground(String... url) {
        HttpUtil httpUtil = new HttpUtil(url[0]);
        byte[] bytes = httpUtil.getHttpData();
        if (bytes != null) {
            return new String(bytes);
        }
        return null;
    }

    public void getImageFromAlbum() {
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT, null);
        intent.setType("image/*");//相片类型
        startActivityForResult(intent, REQUEST_CODE_PICK_IMAGE);
    }

    public void getImageFromCamera() {
        String state = Environment.getExternalStorageState();
        if (state.equals(Environment.MEDIA_MOUNTED)) {
            Intent getImageByCamera = new Intent("android.media.action.IMAGE_CAPTURE");
            startActivityForResult(getImageByCamera, REQUEST_CODE_CAPTURE_CAMEIA);
        } else {
            Toast.makeText(getApplicationContext(), "请确认已经插入SD卡", Toast.LENGTH_LONG).show();
        }
    }

    /**
     * 拍照获取图片
     */
    protected void doTakePhoto() {
        try {
            // Launch camera to take photo for selected contact
            PHOTO_DIR.mkdirs();// 创建照片的存储目录
            mCurrentPhotoFile = new File(PHOTO_DIR, getPhotoFileName());// 给新照的照片文件命名
            String a = getPhotoFileName();
            filePath += a;
            final Intent intent = getTakePickIntent(mCurrentPhotoFile);
            startActivityForResult(intent, REQUEST_CODE_CAPTURE_CAMEIA);
        } catch (ActivityNotFoundException e) {
            Toast.makeText(this, "photoPickerNotFoundText",
                    Toast.LENGTH_LONG).show();
        }
    }

    public static Intent getTakePickIntent(File f) {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE, null);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(f));
        return intent;
    }

    /**
     * 用当前时间给取得的图片命名
     */
    private String getPhotoFileName() {
        Date date = new Date(System.currentTimeMillis());
        SimpleDateFormat dateFormat = new SimpleDateFormat("'IMG'_yyyy-MM-dd HH:mm:ss");
        return dateFormat.format(date) + ".jpg";
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_CODE_PICK_IMAGE) {
            if (data != null) {
                Uri uri = data.getData();
                crop(uri);
            }
        } else if (requestCode == PHOTO_REQUEST_CUT) {
            // 从剪切图片返回的数据
            if (data != null) {
                Bitmap bitmap = data.getParcelableExtra("data");
                Intent intent = new Intent(this, ChangeHeadPicture.class);
                intent.putExtra("bitmap", bitmap);
                startActivity(intent);
                //imageView.setImageBitmap(bitmap);
            }
        } else if (requestCode == REQUEST_CODE_CAPTURE_CAMEIA) {
            doCropPhoto(mCurrentPhotoFile);
        }
    }

    public static void saveImage(Bitmap photo, String spath) {
        try {
            BufferedOutputStream bos = new BufferedOutputStream(
                    new FileOutputStream(spath, false));
            photo.compress(Bitmap.CompressFormat.JPEG, 100, bos);
            bos.flush();
            bos.close();
        } catch (Exception e) {
            e.printStackTrace();

        }


    }

    private void crop(Uri uri) {
        // 裁剪图片意图
        Intent intent = new Intent("com.android.camera.action.CROP");
        intent.setDataAndType(uri, "image/*");
        intent.putExtra("crop", "true");
        // 裁剪框的比例，1：1
        intent.putExtra("aspectX", 1);
        intent.putExtra("aspectY", 1);
        // 裁剪后输出图片的尺寸大小
        intent.putExtra("outputX", 300);
        intent.putExtra("outputY", 300);

        intent.putExtra("outputFormat", "JPEG");// 图片格式
        intent.putExtra("noFaceDetection", true);// 取消人脸识别
        intent.putExtra("return-data", true);
        // 开启一个带有返回值的Activity，请求码为PHOTO_REQUEST_CUT
        startActivityForResult(intent, PHOTO_REQUEST_CUT);
    }

    protected void doCropPhoto(File f) {
        try {
            // 启动gallery去剪辑这个照片
            final Intent intent = getCropImageIntent(Uri.fromFile(f));
            startActivityForResult(intent, PHOTO_REQUEST_CUT);
        } catch (Exception e) {

        }
    }

    public static Intent getCropImageIntent(Uri photoUri) {
        Intent intent = new Intent("com.android.camera.action.CROP");
        intent.setDataAndType(photoUri, "image/*");
        intent.putExtra("crop", "true");
        intent.putExtra("aspectX", 1);
        intent.putExtra("aspectY", 1);
        intent.putExtra("outputX", 300);
        intent.putExtra("outputY", 300);
        intent.putExtra("return-data", true);
        return intent;
    }

    @Override
    protected void onResume() {
        if (imageView != null && asyncImageLoader != null) {
            Map args = new HashMap();
            args.put("userName", ((GlobalVeriable) getApplication()).getUserName());
            String infoUrl = URLUtil.getURL("myInf", args);
            new NewNetAsyncTask(this).execute(infoUrl);
        }
        super.onResume();
    }
}