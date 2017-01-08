package edu.buu.childhood.baidumap.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import edu.buu.childhood.R;
import edu.buu.childhood.Interface.BtnListener;
import edu.buu.childhood.baidumap.pojo.NewMessage_ItemBean;
import edu.buu.childhood.util.AsyncImageLoader;

/**
 * Created by lcc on 2016/7/2.
 */
public class NewMessage_Adapter extends BaseAdapter {
    private LayoutInflater rankLayoutInflater;
    private List<NewMessage_ItemBean> newsDataList;
    private BtnListener btnListener;
    private AsyncImageLoader imageLoader;


    public NewMessage_Adapter(Context findListener, List<NewMessage_ItemBean> list, BtnListener btnListener) {
        rankLayoutInflater = LayoutInflater.from(findListener);
        newsDataList = list;
        this.btnListener = btnListener;
        imageLoader = new AsyncImageLoader(findListener);
    }

    @Override
    public int getCount() {
        return newsDataList.size();
    }

    @Override
    public Object getItem(int position) {
        return newsDataList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }


    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;
        if (convertView == null) {
            holder = new ViewHolder();
            convertView = rankLayoutInflater.inflate(R.layout.new_message_model, null);
            holder.gameId= (TextView) convertView.findViewById(R.id.new_message_model_gameid);
            holder.img = (ImageView) convertView.findViewById(R.id.new_message_model_im);
            holder.tv1 = (TextView) convertView.findViewById(R.id.new_message_model_model_textview1);
            holder.tv2 = (TextView) convertView.findViewById(R.id.new_message_model_model_textview2);
            holder.tv3 = (TextView) convertView.findViewById(R.id.gameIntroduction);
            holder.but = (Button) convertView.findViewById(R.id.new_message_model_model_button);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        NewMessage_ItemBean bean = newsDataList.get(position);
        //
        holder.tv1.setText(bean.gameFounder);
        holder.gameId.setText(String.valueOf(bean.getGameId()));
        holder.tv2.setText(bean.gameTitle);
        holder.tv3.setText(bean.gameSynopsis);
        holder.img.setImageResource(R.drawable.touxiang);
        String gameIcon = bean.getGameIcoUrl();
        if (!TextUtils.isEmpty(gameIcon)) {
            Log.i("gameIcon",gameIcon);
            Bitmap bitmap = imageLoader.loadImage(holder.img, gameIcon);
            if (bitmap != null) {
                Log.i("sss","sss");
                holder.img.setImageBitmap(bitmap);
            }
        }
        holder.but.setText(bean.join);
        holder.but.setOnClickListener(new MyListener(position));
        return convertView;
    }

    public class ViewHolder {
        public ImageView img;
        public TextView gameId;
        public TextView tv1;
        public TextView tv2;
        public TextView tv3;
        public Button but;
    }

    private class MyListener implements View.OnClickListener {
        private int positation;
        public MyListener(int positation){
            this.positation=positation;
        }
        @Override
        public void onClick(View v) {
            btnListener.onClick(v,positation);
        }

    }
}