package edu.buu.childhood.game.adapter;

/**
 * Created by lcc on 2016/6/16.
 */
import android.content.Context;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.List;

import edu.buu.childhood.game.pojo.ItemBean;
import edu.buu.childhood.R;

public class GameRules_Adapter extends BaseAdapter{
    private LayoutInflater mLayoutInflater;
    private List<ItemBean> mDataList;
    public GameRules_Adapter(Context findListener, List<ItemBean> list) {
        mLayoutInflater = LayoutInflater.from(findListener);
        mDataList = list;
    }
    @Override
    public int getCount() {
        return mDataList.size();
    }

    @Override
    public Object getItem(int position) {
        return mDataList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }



    public View getView(int position, View convertView, ViewGroup parent) {



        ViewHolder holder = null;
        if (convertView == null) {
            holder = new ViewHolder();
            convertView = mLayoutInflater.inflate(R.layout.game_rules_listview, null);
            holder.img = (ImageView) convertView.findViewById(R.id.game_rules_listview_image);
            holder.tv1 = (TextView) convertView.findViewById(R.id.game_rules_listview_old);
            holder.tv2 = (TextView) convertView.findViewById(R.id.game_rules_listview_num);
            holder.tv3 = (TextView) convertView.findViewById(R.id.game_rules_listview_conent);
            holder.rl=(RelativeLayout) convertView.findViewById(R.id.game_rules_listview_rl1);
            convertView.setTag(holder);
        }
        else {
            holder = (ViewHolder) convertView.getTag();
        }
        ItemBean bean = mDataList.get(position);
        //
        holder.tv1.setText(bean.ageRank);
        holder.tv2.setText(bean.memNumSize);
        holder.tv3.setText(bean.gameSynopsis);
        holder.img.setImageResource(bean.Image);
        //holder.rl.setb
       holder.rl.setBackgroundDrawable(bean.background);
        return convertView;

    }
    public class ViewHolder{
        public ImageView img;
        public TextView tv1;
        public TextView tv2;
        public TextView tv3;
        public RelativeLayout rl;
    }
}

