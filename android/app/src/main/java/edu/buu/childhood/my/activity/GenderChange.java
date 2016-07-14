package edu.buu.childhood.my.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import edu.buu.childhood.R;

/**
 * Created by lcc on 2016/7/1.
 */
public class GenderChange extends Activity {
    private RelativeLayout girl;
    private RelativeLayout man;
    private ImageView girlImage;
    private ImageView manImage;
    private ImageView manImageBack;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.genderchange);
        girl= (RelativeLayout) findViewById(R.id.genderChange_girl);
        man= (RelativeLayout) findViewById(R.id.genderChange_man);
        girlImage=(ImageView)findViewById(R.id.genderChange_girlimage);
        manImage=(ImageView)findViewById(R.id.genderChange_manimage);
        manImageBack= (ImageView) findViewById(R.id.genderChange_back);
        manImageBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                System.exit(0);
            }
        });
        girl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent();
                intent.putExtra("extra","女");
                //从此ctivity传到另一Activity
                intent.setClass(GenderChange.this,My_content.class);
                //启动另一个Activity
                GenderChange.this.startActivity(intent);
                girlImage.setVisibility(View.VISIBLE);


            }
        });
        man.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent();
                intent.putExtra("extra","男");
                //从此ctivity传到另一Activity
                intent.setClass(GenderChange.this,My_content.class);
                //启动另一个Activity
                GenderChange.this.startActivity(intent);
                manImage.setVisibility(View.VISIBLE);



            }
        });

    }
}