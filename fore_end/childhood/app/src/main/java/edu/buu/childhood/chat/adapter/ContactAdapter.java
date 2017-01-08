package edu.buu.childhood.chat.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import edu.buu.childhood.R;
import edu.buu.childhood.chat.bean.User;
import edu.buu.childhood.util.AsyncImageLoader;


/**
 * Created by Administrator on 2016/9/25.
 */
public class ContactAdapter extends BaseAdapter {
    private LayoutInflater mLayoutInflater;
    private List<User> userDataList;
    private AsyncImageLoader imageLoader;

    public ContactAdapter(Context context, List<User> userDataList) {
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
            convertView = mLayoutInflater.inflate(R.layout.item_contact_list, null);
            holder.img = (ImageView) convertView.findViewById(R.id.iv_avatar);
            holder.nickName=(TextView) convertView.findViewById(R.id.tv_name);
            convertView.setTag(holder);
        }else {
            holder = (ViewHolder) convertView.getTag();
        }
        User user=userDataList.get(position);
        holder.nickName.setText(user.getNick());
        holder.img.setImageResource(R.drawable.default_useravatar);
        String imgUrl=user.getUserImage();
        if(!TextUtils.isEmpty(imgUrl)){
            holder.img.setTag(imgUrl);
            Bitmap bitmap=imageLoader.loadImage(holder.img,imgUrl);
            if(bitmap!=null){
                holder.img.setImageBitmap(bitmap);
            }
        }
        return convertView;
    }

    public class ViewHolder {
        public TextView userNameTextView;
        public TextView nickName;
        public ImageView img;
    }
}
