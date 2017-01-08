package edu.buu.childhood.chat.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import edu.buu.childhood.R;
import edu.buu.childhood.chat.bean.EMConversation;
import edu.buu.childhood.util.AsyncImageLoader;


/**
 * Created by Administrator on 2016/9/25.
 */
public class ConversationAdapter extends BaseAdapter {
    private LayoutInflater mLayoutInflater;
    private List<EMConversation> userDataList;
    private AsyncImageLoader imageLoader;

    public ConversationAdapter(Context context, List<EMConversation> userDataList) {
        this.mLayoutInflater = LayoutInflater.from(context);
        this.userDataList = userDataList;
        imageLoader = new AsyncImageLoader(context);
    }

    @Override
    public int getCount() {
        return userDataList.size();
    }

    @Override
    public Object getItem(int position) {
        return userDataList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;
        if (convertView == null) {
            holder = new ViewHolder();
            convertView = mLayoutInflater.inflate(R.layout.item_conversation_single, null);
            holder.img = (ImageView) convertView.findViewById(R.id.single_iv_avatar);
            holder.userName = (TextView) convertView.findViewById(R.id.single_tv_name);
            holder.tv_content = (TextView) convertView.findViewById(R.id.single_tv_content);
            holder.tv_time = (TextView) convertView.findViewById(R.id.tv_time);
            holder.tv_unread = (TextView) convertView.findViewById(R.id.single_tv_unread);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        EMConversation emConversation = userDataList.get(position);
        if (emConversation.getCountMessage() == -1) {
            holder.tv_unread.setVisibility(View.GONE);
        }
        holder.tv_unread.setText(String.valueOf(emConversation.getCountMessage()));
        holder.tv_time.setText(emConversation.getFromDate());
        holder.userName.setText(emConversation.getUserNick());
        holder.tv_content.setText(emConversation.getMessage());
        holder.img.setImageResource(R.drawable.touxiang);
        String imgUrl = emConversation.getImageUrl();
        if (!TextUtils.isEmpty(imgUrl)) {
            Bitmap bitmap = imageLoader.loadImage(holder.img, imgUrl);
            if (bitmap != null) {
                holder.img.setImageBitmap(bitmap);
            }
        }
        return convertView;
    }

    public static class ViewHolder {
        /**
         * 和谁的聊天记录
         */
        TextView userName;
        /**
         * 消息未读数
         */
        TextView tv_unread;
        /**
         * 最后一条消息的内容
         */
        TextView tv_content;
        /**
         * 最后一条消息的时间
         */
        TextView tv_time;
        /**
         * 用户头像
         */
        ImageView img;
    }
}
