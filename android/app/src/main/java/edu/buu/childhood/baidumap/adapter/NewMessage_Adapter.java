package edu.buu.childhood.baidumap.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import edu.buu.childhood.R;
import edu.buu.childhood.baidumap.poj.NewMessage_ItemBean;

/**
 * Created by lcc on 2016/7/2.
 */
public class NewMessage_Adapter extends BaseAdapter {
    private LayoutInflater rankLayoutInflater;
    private List<NewMessage_ItemBean> newsDataList;

    public NewMessage_Adapter(Context findListener, List<NewMessage_ItemBean> list) {
        rankLayoutInflater = LayoutInflater.from(findListener);
        newsDataList = list;
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
            holder.img = (ImageView) convertView.findViewById(R.id.new_message_model_im);
            holder.tv1 = (TextView) convertView.findViewById(R.id.new_message_model_model_textview1);
            holder.tv2 = (TextView) convertView.findViewById(R.id.new_message_model_model_textview2);
            holder.tv3 = (TextView) convertView.findViewById(R.id.new_message_model_model_textview);
            holder.but =(Button)convertView.findViewById(R.id.new_message_model_model_button);
            convertView.setTag(holder);
        }
        else {
            holder = (ViewHolder) convertView.getTag();
        }
        NewMessage_ItemBean bean = newsDataList.get(position);
        //
        holder.tv1.setText(bean.itemtext1);
        holder.tv2.setText(bean.itemtext2);
        holder.tv3.setText(bean.itemtext3);
        holder.img.setImageResource(bean.Image);
        holder.but.setText(bean.itemtext4);
        //  holder.rl.setBackgroundResource(bean.background);
        return convertView;

    }
    public class ViewHolder{
        public ImageView img;
        public TextView tv1;
        public TextView tv2;
        public TextView tv3;
        public Button but;
    }
}