package edu.buu.childhood.game.adapter;

/**
 * Created by lcc on 2016/6/16.
 */

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.List;

import edu.buu.childhood.R;
import edu.buu.childhood.game.pojo.ItemBean;
import edu.buu.childhood.util.AsyncImageLoader;

public class GameRules_Adapter extends BaseAdapter{
    private LayoutInflater mLayoutInflater;
    private List<ItemBean> mDataList;
    private AsyncImageLoader imageLoader;

    public GameRules_Adapter(Context findListener, List<ItemBean> list) {
        mLayoutInflater = LayoutInflater.from(findListener);
        mDataList = list;
        imageLoader=new AsyncImageLoader(findListener);
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
        holder.tv1.setText(bean.getAgeRank());
        holder.tv2.setText(bean.getMemNumSize());
        holder.tv3.setText(bean.getGameSynopsis());
       // holder.img.setImageResource(bean.Image);
       /*   if(bean.Image== C.score.ONESTAR){
            holder.img.setImageResource(R.drawable.one_star);
        }
        if(bean.Image== C.score.TWOSTARS){
            holder.img.setImageResource(R.drawable.two_star);
        }
        if(bean.Image== C.score.THREESTARS){
            holder.img.setImageResource(R.drawable.three_star);
        }
        if(bean.Image== C.score.FOURSTARS){
            holder.img.setImageResource(R.drawable.four_star);
        }
        if(bean.Image== C.score.FIVESTARS){
            holder.img.setImageResource(R.drawable.five);
        }*/
        holder.rl.setBackgroundResource(R.drawable.lod);

        String imageUrl=bean.getImageUrl();
        if(!TextUtils.isEmpty(imageUrl)){
            holder.rl.setTag(imageUrl);
            Bitmap bitmap=imageLoader.loadImage(holder.rl,imageUrl);
            if(bitmap!=null){
                holder.rl.setBackgroundDrawable(new BitmapDrawable(bitmap));
            }
        }
        return convertView;
    }
    public class ViewHolder{
        public TextView tv1;
        public TextView tv2;
        public TextView tv3;
        public RelativeLayout rl;
    }
}

