package edu.buu.childhood.my.activity;

/**
 * Created by lcc on 2016/6/14.
 */
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.util.Log;

import edu.buu.childhood.R;

public class Edit_name extends Activity {

   private Button m_button;
   private EditText m_editText;
   private String backname;
   public Edit_name() {
      // TODO Auto-generated constructor stub
    }

    protected void onCreate(Bundle savedInstanceState) {
     // TODO Auto-generated method stub
     super.onCreate(savedInstanceState);
     setContentView(R.layout.edite_name);
     //接收数据
     Intent intent=getIntent();
     backname=intent.getStringExtra("name");
     findViewById(R.id.edite_name_image).setOnClickListener(new OnClickListener() {
      @Override
      public void onClick(View v) {
       System.exit(0);
      }
     });//返回键
     m_button = (Button)findViewById(R.id.edite_name_button);
     m_editText = (EditText)findViewById(R.id.edite_name_editText);
     m_editText.selectAll();
     m_editText.setText(backname);
     m_button.setOnClickListener(new ButtonListener());
    }
    class ButtonListener implements OnClickListener {
    public void onClick(View v) {
     //发送数据
     String a=m_editText.getText().toString();
     Intent intent=new Intent();
     intent.putExtra("name",a);
     intent.setClass(Edit_name.this,My_content.class);
     Edit_name.this.startActivity(intent);
     }
    }
}
