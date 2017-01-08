package edu.buu.childhood.chat.activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import edu.buu.childhood.R;
import edu.buu.childhood.common.GlobalVeriable;
import edu.buu.childhood.common.activity.BaseAvtivity;
import edu.buu.childhood.util.AsyncImageLoader;
import edu.buu.childhood.util.CloseActivity;

/**
 * Created by Administrator on 2016/9/28.
 */
public class ChatUserInfoActivity extends BaseAvtivity {
    private ImageView imageView;
    private TextView userNickTextView;
    private Button sendMsgButton;
    private AsyncImageLoader imageLoader;
    private Boolean isStranger = true;
    private ArrayList<String> userList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        CloseActivity.activityList.add(this);
        imageLoader = new AsyncImageLoader(this);
        setContentView(R.layout.activity_userinfo);
        imageView = (ImageView) findViewById(R.id.iv_avatar);
        userNickTextView = (TextView) findViewById(R.id.tv_name);
        sendMsgButton = (Button) findViewById(R.id.btn_sendmsg);
        Intent intent = getIntent();
        if (intent != null) {
            userList = intent.getStringArrayListExtra("userList");
            if (!TextUtils.isEmpty(userList.get(2))) {
                Bitmap bitmap = imageLoader.loadImage(imageView, userList.get(2));
                if (bitmap != null) {
                    imageView.setImageBitmap(bitmap);
                }
            }
            userNickTextView.setText(userList.get(1));
            if (userList.get(3).equals("true")) {
                isStranger = false;
                sendMsgButton.setText("发送消息");
            }
        }
        sendMsgButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isStranger) {
                    Intent intent = new Intent(ChatUserInfoActivity.this, AddFriendsFinalActivity.class);
                    intent.putExtra("fromUser", userList.get(0));
                    startActivity(intent);
                } else {
                    ((GlobalVeriable) getApplication()).setToChatingUser(userList.get(0));
                    Intent intent = new Intent(ChatUserInfoActivity.this, ChatRoomActivity.class);
                }
            }
        });
    }
}
