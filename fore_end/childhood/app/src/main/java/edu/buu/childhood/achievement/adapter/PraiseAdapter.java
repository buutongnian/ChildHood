package edu.buu.childhood.achievement.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.List;

import edu.buu.childhood.R;
import edu.buu.childhood.achievement.pojo.LikeDetail;
import edu.buu.childhood.util.AsyncImageLoader;

/**
 * Created by lcc on 2016/7/5.
 */
public class PraiseAdapter extends BaseAdapter {
    private LayoutInflater layoutInflater;
    private List<LikeDetail> Datalist;
    private AsyncImageLoader imageLoader;

    public PraiseAdapter(Context findListener, List<LikeDetail> list) {
        layoutInflater = LayoutInflater.from(findListener);
        Datalist = list;
        imageLoader=new AsyncImageLoader(findListener);
    }

    @Override
    public int getCount() {
        return Datalist.size();
    }

    @Override
    public Object getItem(int position) {
        return Datalist.get(position);
    }

    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;
        if (convertView == null) {
            holder = new ViewHolder();
            convertView = layoutInflater.inflate(R.layout.praise_detail_model, null);
            holder.achievement = (TextView) convertView.findViewById(R.id.praiseData);
            holder.nicheng = (TextView) convertView.findViewById(R.id.praiseNicheng);
            holder.img = (ImageView) convertView.findViewById(R.id.praiseTouxing);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        LikeDetail bean = Datalist.get(position);
        holder.nicheng.setText(bean.getUserNickname());
        SimpleDateFormat simpleDateFormat=new SimpleDateFormat("MM 月 dd 日");
        holder.achievement.setText(simpleDateFormat.format(bean.getLikeTime()));
        String imageUrl=bean.getUserHeadImage();
        if(!TextUtils.isEmpty(imageUrl)){
            holder.img.setTag(imageUrl);
            Bitmap bitmap=imageLoader.loadImage(holder.img,imageUrl);
            if(bitmap!=null){
                holder.img.setImageBitmap(bitmap);
            }
        }
        //holder.img.setImageBitmap();
        return convertView;

    }


    public class ViewHolder {
        public TextView nicheng;
        public TextView achievement;
        public ImageView img;



    }
}
