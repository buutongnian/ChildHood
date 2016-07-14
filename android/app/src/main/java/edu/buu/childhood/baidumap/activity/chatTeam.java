package edu.buu.childhood.baidumap.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;

import edu.buu.childhood.R;

/**
 * Created by lcc on 2016/7/3.
 */
public class chatTeam extends Activity {
    private LinearLayout teamerName;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.chat_team);
        teamerName= (LinearLayout) findViewById(R.id.chat_team_teamername);
        teamerName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(chatTeam.this,teamerName.class));
            }
        });
    }
}
