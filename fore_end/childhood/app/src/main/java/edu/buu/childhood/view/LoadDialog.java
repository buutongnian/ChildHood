package edu.buu.childhood.view;

import android.app.Dialog;
import android.content.Context;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.widget.ImageView;

import edu.buu.childhood.R;

/**
 * Created by Administrator on 2016/10/12.
 */
public class LoadDialog extends Dialog {
    private AnimationDrawable animationDrawable;

    public LoadDialog(Context context) {
        super(context);
    }
    public LoadDialog(Context context, int theme) {
        super(context,theme);
    }


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setContentView(R.layout.load_dialog);
        ImageView imageView = (ImageView) findViewById(R.id.load_dialog_img);
        imageView.setBackgroundResource(R.drawable.load_anim);
        animationDrawable = (AnimationDrawable) imageView
                .getBackground();
        imageView.post(new Runnable() {
            @Override
            public void run() {
                animationDrawable.start();
            }
        });
    }
}
