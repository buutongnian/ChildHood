package edu.buu.childhood.baidumap.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import edu.buu.childhood.R;
import edu.buu.childhood.baidumap.pojo.MembersBean;
import edu.buu.childhood.util.AsyncImageLoader;

/**
 * Created by Administrator on 2016/9/11.
 */
public class MembersAdapter extends BaseAdapter {
    private Context mContext;
    private ArrayList<MembersBean> mData = null;
    private AsyncImageLoader imageLoader;

    public MembersAdapter(Context mContext, ArrayList<MembersBean> mData) {
        this.mContext = mContext;
        this.mData = mData;
        imageLoader = new AsyncImageLoader(mContext);
    }

    @Override
    public int getCount() {
        return mData.size();
    }

    @Override
    public Object getItem(int position) {
        return mData.get(position);
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
            convertView = LayoutInflater.from(mContext).inflate(R.layout.teamer_name_model, parent,false);
            holder.imageView = (ImageView) convertView.findViewById(R.id.teamer_name_model_image);
            holder.textView = (TextView) convertView.findViewById(R.id.teamer_name_model_text);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        MembersBean membersBean = (MembersBean) mData.get(position);
        if (membersBean != null) {
            String imgUrl=membersBean.getMemberImage();
            //holder.textView.setVisibility();
            holder.textView.setText(membersBean.getMemberName());
            holder.imageView.setImageResource(R.drawable.touxiang);
            if (!TextUtils.isEmpty(imgUrl)){
                Bitmap bitmap = imageLoader.loadImage(holder.imageView,imgUrl);
                if(bitmap!=null){
                    holder.imageView.setImageBitmap(bitmap);
                }
            }
        }
        return convertView;
    }

    private static class ViewHolder {
        ImageView imageView;
        TextView textView;
    }
}