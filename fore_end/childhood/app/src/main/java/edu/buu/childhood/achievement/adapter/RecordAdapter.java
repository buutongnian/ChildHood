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
import edu.buu.childhood.achievement.pojo.UserHistoryGameList;
import edu.buu.childhood.util.AsyncImageLoader;

/**
 * Created by lcc on 2016/7/5.
 */
public class RecordAdapter extends BaseAdapter {
    private LayoutInflater layoutInflater;
    private List<UserHistoryGameList> recordDatalist;
    private AsyncImageLoader imageLoader;

    public RecordAdapter(Context context, List<UserHistoryGameList> list) {
        layoutInflater = LayoutInflater.from(context);
        recordDatalist = list;
        imageLoader=new AsyncImageLoader(context);
    }

    @Override
    public int getCount() {
        return recordDatalist.size();
    }

    @Override
    public Object getItem(int position) {
        return recordDatalist.get(position);
    }

    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;
        if (convertView == null) {
            holder = new ViewHolder();
            convertView = layoutInflater.inflate(R.layout.item_game_records, null);
            holder.name = (TextView) convertView.findViewById(R.id.tv_game_name);
            holder.date = (TextView) convertView.findViewById(R.id.tv_game_date);
            holder.img = (ImageView) convertView.findViewById(R.id.iv_game_img);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        UserHistoryGameList userHistoryGameList = recordDatalist.get(position);
        String gameImageUrl=userHistoryGameList.getGameIcon();
        if(!TextUtils.isEmpty(gameImageUrl)){
            holder.img.setTag(gameImageUrl);
            Bitmap bitmap=imageLoader.loadImage(holder.img,gameImageUrl);
            if(bitmap!=null){
                holder.img.setImageBitmap(bitmap);
            }
        }
        SimpleDateFormat simpleDateFormat=new SimpleDateFormat("MM 月 dd 日");
        holder.name.setText(userHistoryGameList.getGameTitle());
        holder.date.setText(simpleDateFormat.format(userHistoryGameList.getFinishTime()));
        return convertView;

    }


    private class ViewHolder {
        public TextView name;
        public ImageView img;
        public TextView date;
    }

}
