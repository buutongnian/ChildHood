package edu.buu.childhood.mainui.activity;


import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import edu.buu.childhood.R;
import edu.buu.childhood.baidumap.activity.MainBaidu;
import edu.buu.childhood.forum.someWebview;
import edu.buu.childhood.game.activity.GameRules;
import edu.buu.childhood.my.activity.My;
import edu.buu.childhood.rank.activity.MainRank;


public class Main_interface extends AppCompatActivity {
    private Button buttongame;
    private Button buttonforum;
    private Button buttonrank;
    private Button buttonmap;
    private Button buttonmy;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_interface);
        buttonforum= (Button) findViewById(R.id.button3);
        buttongame= (Button) findViewById(R.id.main_button);
        buttonrank= (Button) findViewById(R.id.button5);
        buttonmap= (Button) findViewById(R.id.button2);
        buttonmy= (Button) findViewById(R.id.button4);
        buttongame.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Main_interface.this,GameRules.class));
            }
        });
        buttonforum.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Main_interface.this,someWebview.class));
            }
        });
        buttonmap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Main_interface.this,MainBaidu.class));
            }
        });
        buttonrank.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Main_interface.this,MainRank.class));
            }
        });
        buttonmy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Main_interface.this,My.class));
            }
        });
    }


}

