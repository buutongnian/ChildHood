package edu.buu.childhood;


import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;


public class Main_interface extends AppCompatActivity {
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_interface);
    }

    public void myClick(View view) {
        startActivity(new Intent(this, My.class));
    }

    public void gameClick(View view) {
        startActivity(new Intent(this, GameRules.class));
    }

    public void shareclick(View view) {
        startActivity(new Intent(this, someWebview.class));
    }

    public void dateCclick(View view) {
        startActivity(new Intent(this, Ranking_list.class));
    }

    public void mapClick(View view) {
        startActivity(new Intent(this, edu.buu.childhood.baidumap.baiduMapActivity.class));

    }
}

