package com.example.lcc.childhood;


import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;


public class Main_interface extends AppCompatActivity {
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_interface);}
    public void onClick(View view) {
        startActivity(new Intent(this, My.class));
    }

}

