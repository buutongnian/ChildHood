package edu.buu.childhood.rank.adapter;

/**
 * Created by lcc on 2016/7/2.
 */

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
import edu.buu.childhood.rank.pojo.ranking_gamelist_itembean;
import edu.buu.childhood.util.AsyncImageLoader;

/**
 * Created by lcc on 2016/6/25.
 */
public class Ranking_gamelist_listview_Adapter extends BaseAdapter {
    private LayoutInflater rankLayoutInflater;
    private List<ranking_gamelist_itembean> rankDataList;
    private AsyncImageLoader imageLoader;
    private int heat;

    public Ranking_gamelist_listview_Adapter(Context findListener, List<ranking_gamelist_itembean> list) {
        rankLayoutInflater = LayoutInflater.from(findListener);
        rankDataList = list;
        imageLoader = new AsyncImageLoader(findListener);
    }

    @Override
    public int getCount() {
        return rankDataList.size();
    }

    @Override
    public Object getItem(int position) {
        return rankDataList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }


    public View getView(final int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;
        if (convertView == null) {
            holder = new ViewHolder();
            convertView = rankLayoutInflater.inflate(R.layout.ranking_list_model, null);
            holder.img = (ImageView) convertView.findViewById(R.id.ranking_list_model_imageView);
            holder.img1 = (ImageView) convertView.findViewById(R.id.ranking_list_model_imageView1);
            holder.tv1 = (TextView) convertView.findViewById(R.id.ranking_list_model_textview1);
            holder.tv2 = (TextView) convertView.findViewById(R.id.ranking_list_model_textview2);
            holder.tv3 = (TextView) convertView.findViewById(R.id.ranking_list_model_textview3);
            holder.parise= (ImageView) convertView.findViewById(R.id.like);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        ranking_gamelist_itembean bean = rankDataList.get(position);
        holder.parise.setVisibility(View.INVISIBLE);

        if(position==0){
            holder.img1.setImageResource(R.drawable.first);
            holder.tv1.setText(" ");
        } else if (position == 1) {
            holder.img1.setImageResource(R.drawable.third);
            holder.tv1.setText(" ");
        } else if (position == 2) {
            holder.img1.setImageResource(R.drawable.second);
            holder.tv1.setText(" ");
        } else {
            holder.tv1.setText("   " + (position + 1));
            holder.img1.setImageBitmap(null);
        }
        holder.tv2.setText(bean.gameName);
        holder.tv3.setText(bean.getGameHeat());
        Log.i("result",bean.getGameName());
        String imageUrl=bean.getGameIcon();
        if (!TextUtils.isEmpty(imageUrl)) {
            holder.img.setTag(imageUrl);
           Bitmap bitmap=imageLoader.loadImage(holder.img,imageUrl);
            if(bitmap!=null){
                holder.img.setImageBitmap(bitmap);
            }
        }
        return convertView;

    }

    public class ViewHolder {
        public ImageView img;
        public ImageView img1;
        public ImageView parise;
        public TextView tv1;
        public TextView tv2;
        public TextView tv3;
    }
}


