package edu.buu.childhood.my.Adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import edu.buu.childhood.R;
import edu.buu.childhood.my.pojo.FindResult;
import edu.buu.childhood.util.AsyncImageLoader;

/**
 * Created by lcc on 2016/9/11.
 */
public class getPartnerAdapter extends BaseAdapter {
    private LayoutInflater getPartnerInflater;
    private AsyncImageLoader asyncImageLoader;
    List<FindResult> getPartnerList;

    public getPartnerAdapter(Context findListener, List<FindResult> list) {
        getPartnerInflater = LayoutInflater.from(findListener);
        getPartnerList = list;
        asyncImageLoader = new AsyncImageLoader(findListener);
    }

    @Override
    public int getCount() {
        return getPartnerList.size();
    }

    @Override
    public Object getItem(int position) {
        return getPartnerList.get(position);
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
            convertView = getPartnerInflater.inflate(R.layout.get_partner_model, null);
            holder.img = (ImageView) convertView.findViewById(R.id.get_partner_model_im);
            holder.tv1 = (TextView) convertView.findViewById(R.id.get_partner_model_model_textview1);
            holder.tv2 = (TextView) convertView.findViewById(R.id.get_partner_model_model_textview2);
            // holder.tv3 = (TextView) convertView.findViewById(R.id.message);
            holder.userName = (TextView) convertView.findViewById(R.id.getPatentUserName);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        FindResult bean = getPartnerList.get(position);
        holder.img.setTag(bean.getUserHeadimage());
        Log.d("headImageUrl", bean.getUserHeadimage());
        holder.img.setImageBitmap(asyncImageLoader.loadImage(holder.img, bean.getUserHeadimage()));
        holder.tv1.setText(bean.getYoungNickname());
        holder.tv2.setText(bean.getMessage());
        // holder.tv3.setText(bean.getMessage());
        holder.userName.setText(bean.getUserName());

        return convertView;
    }

    public class ViewHolder {
        public ImageView img;
        public TextView tv1;
        public TextView tv2;
        public TextView tv3;
        public TextView userName;

    }
}
