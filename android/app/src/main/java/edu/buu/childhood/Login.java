package edu.buu.childhood;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

/**
 * Created by lcc on 2016/6/20.
 */
public class Login extends Activity {
    private Button login1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);
        login1=(Button)findViewById(R.id.loginButton);
        login1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Login.this, Main_interface.class));
            }
        });
    }
    public void registerClick(View view) {
    startActivity(new Intent(Login.this,Register.class));
}
}
