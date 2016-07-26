package edu.buu.childhood.my.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;

import edu.buu.childhood.R;
import edu.buu.childhood.my.mylock.MyLockView;

/**
 * Created by lcc on 2016/7/23.
 */
public class ParentLock extends Activity implements MyLockView.lockListener {
    private MyLockView lockView;
    private String passwordString = "";
    private String mima;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.parent_lock);
        lockView = (MyLockView) findViewById(R.id.lockView);
        lockView.setLockListener(this);
        Intent intent = getIntent();
        mima = intent.getStringExtra("pw");
    }



    @Override
    public void getStringPassword ( String password ) {
        passwordString = password;
        Toast.makeText( this , password , Toast.LENGTH_SHORT ).show();
    }

    @Override
    public boolean isPassword ( ) {
        if ( passwordString.equals(mima) ) {
            return true;
        }
        return false;
    }

    public void traParentLayout(){
        startActivity(new Intent(ParentLock.this,ParentLayout.class));
        finish();
    }
}
