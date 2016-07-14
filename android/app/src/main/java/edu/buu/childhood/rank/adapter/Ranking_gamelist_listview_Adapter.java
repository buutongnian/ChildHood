package edu.buu.childhood.rank.adapter;

/**
 * Created by lcc on 2016/7/2.
 */

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import edu.buu.childhood.R;
import edu.buu.childhood.rank.pojo.ranking_gamelist_itembean;

/**
 * Created by lcc on 2016/6/25.
 */
public class Ranking_gamelist_listview_Adapter extends BaseAdapter {
    private LayoutInflater rankLayoutInflater;
    private List<ranking_gamelist_itembean> rankDataList;

    public Ranking_gamelist_listview_Adapter(Context findListener, List<ranking_gamelist_itembean> list) {
        rankLayoutInflater = LayoutInflater.from(findListener);
        rankDataList = list;
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


    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;
        if (convertView == null) {
            holder = new ViewHolder();
            convertView = rankLayoutInflater.inflate(R.layout.ranking_list_model, null);
            holder.img = (ImageView) convertView.findViewById(R.id.ranking_list_model_imageView);
            holder.tv1 = (TextView) convertView.findViewById(R.id.ranking_list_model_textview1);
            holder.tv2 = (TextView) convertView.findViewById(R.id.ranking_list_model_textview2);
            holder.tv3 = (TextView) convertView.findViewById(R.id.ranking_list_model_textview3);
            convertView.setTag(holder);
        }
        else {
            holder = (ViewHolder) convertView.getTag();
        }
        ranking_gamelist_itembean bean = rankDataList.get(position);
        holder.tv1.setText(bean.gameCode);
        holder.tv2.setText(bean.gameName);
        holder.tv3.setText(bean.gameHeat);
        holder.img.setImageResource(bean.gameIcon);
        return convertView;

    }
    public class ViewHolder{
        public ImageView img;
        public TextView tv1;
        public TextView tv2;
        public TextView tv3;
    }
}


