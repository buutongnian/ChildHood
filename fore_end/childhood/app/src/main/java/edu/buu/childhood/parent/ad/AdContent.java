package edu.buu.childhood.parent.ad;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.webkit.WebView;
import android.widget.TextView;

import edu.buu.childhood.R;

/**
 * Created by joe on 2016/11/5.
 */

public class AdContent extends Activity {
    private TextView title;
    private WebView adContentView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.parent_ad_content);
        title = (TextView) findViewById(R.id.parent_ad_content_title);
        adContentView = (WebView) findViewById(R.id.parent_ad_content_webview);
        Intent intent = getIntent();
        String adName = intent.getStringExtra("adName");
        String adUrl = intent.getStringExtra("adUrl");
        title.setText(adName);
        adContentView.getSettings().setJavaScriptEnabled(true);
        adContentView.loadUrl(adUrl);
    }

    @Override
    protected void onPause() {
        adContentView.onPause();
        super.onPause();
    }
}
