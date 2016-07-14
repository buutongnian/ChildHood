package edu.buu.childhood.forum;


import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.TextView;
import edu.buu.childhood.R;


/**
 * Created by lcc on 2016/6/17.
 */
public class someWebview extends Activity {
    private WebView webView;
    private Button back;
    private Button refresh;
    private TextView titleview;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    setContentView(R.layout.some_webview);

    webView=(WebView)findViewById(R.id.some_webView_webView);
        back=(Button)findViewById(R.id.some_webView_back);
        refresh=(Button)findViewById(R.id.some_webView_refresh);
        titleview=(TextView)findViewById(R.id.some_webView_title);
        webView.loadUrl("http://www.baidu.com");
        webView.setWebChromeClient(new WebChromeClient(){
            @Override
            public void onReceivedTitle(WebView view, String title) {
                titleview.setText(title);
                super.onReceivedTitle(view, title);
            }
        });
        webView.setWebViewClient(new WebViewClient(){
           public boolean shouldOverrideUrlLoading(WebView view,String url){
               view.loadUrl(url);
               return super.shouldOverrideUrlLoading(view,url);
           }
        });
        back.setOnClickListener(new MyLisenter());
        refresh.setOnClickListener(new MyLisenter());
    }
    class MyLisenter implements View.OnClickListener{
        @Override
        public void onClick(View v) {
            switch (v.getId()){
                case R.id.some_webView_refresh:
                    webView.reload();
                    break;
                case R.id.some_webView_back:
                    finish();
                    break;
                default:
                    break;
            }
        }
    }
}
