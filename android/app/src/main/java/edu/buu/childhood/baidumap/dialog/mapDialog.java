package edu.buu.childhood.baidumap.dialog;

/**
 * Created by lcc on 2016/7/4.
 */
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import edu.buu.childhood.R;

public class mapDialog extends Dialog{

    private Context context;
    private LinearLayout gameSelect,playSite,definGame;
    private TextView gamename;
    private Button nButton,pButton;
    private View.OnClickListener listener;

  /*  public interface LeaveMyDialogListener{
        public void onClick(View view);
    }*/

    public mapDialog(Context context) {
        super(context);
        // TODO Auto-generated constructor stub
        this.context = context;
    }

    public mapDialog(Context context,int theme) {
        super(context,theme);
        // TODO Auto-generated constructor stub
        this.context = context;
    }
    public void setListener(View.OnClickListener listener){
        this.listener=listener;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        this.setContentView(R.layout.map_dialog);
        gameSelect = (LinearLayout) findViewById(R.id.map_dialog_game);
        playSite   = (LinearLayout)findViewById(R.id.map_dialog_playsite);
        definGame     = (LinearLayout)findViewById(R.id.map_dialog_defingame);
        gamename       = (TextView)findViewById(R.id.map_dialog_game_text);
        nButton=(Button)findViewById(R.id.map_dialog_negativeButton);
        pButton=(Button)findViewById(R.id.map_dialog_positiveButton);
        gameSelect.setOnClickListener(listener);
        playSite.setOnClickListener(listener);
        definGame.setOnClickListener(listener);
        gamename.setOnClickListener(listener);
        nButton.setOnClickListener(listener);
        pButton.setOnClickListener(listener);
    }

  /*  @Override
    public void onClick(View v) {
        // TODO Auto-generated method stub
        listener.onClick(v);
    }*/
}