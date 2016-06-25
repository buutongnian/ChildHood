package edu.buu.childhood;

/**
 * Created by lcc on 2016/6/14.
 */
import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.util.Log;

public class Edite_name extends Activity {

   private Button     m_button;
   private EditText  m_editText;
   public Edite_name() {
      // TODO Auto-generated constructor stub
    }

    protected void onCreate(Bundle savedInstanceState) {
     // TODO Auto-generated method stub
     super.onCreate(savedInstanceState);
     setContentView(R.layout.edite_name);
     findViewById(R.id.edite_name_image).setOnClickListener(new OnClickListener() {
      @Override
      public void onClick(View v) {
       finish();
      }
     });//返回键
     m_button = (Button)findViewById(R.id.edite_name_button);
     m_editText = (EditText)findViewById(R.id.edite_name_editText);
     m_editText.selectAll();

     m_button.setOnClickListener(new ButtonListener());
    }

    class ButtonListener implements OnClickListener {
    public void onClick(View v) {
       // TODO Auto-generated method stub
     Log.v("EditText", m_editText.getText().toString());
     }
    }
}
