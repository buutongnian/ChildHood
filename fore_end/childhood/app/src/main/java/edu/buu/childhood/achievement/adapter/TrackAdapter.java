package edu.buu.childhood.achievement.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import edu.buu.childhood.R;
import edu.buu.childhood.achievement.pojo.UserEventList;
import edu.buu.childhood.util.EventUtil;

/**
 * Created by lcc on 2016/7/5.
 */
public class TrackAdapter extends BaseAdapter {
    private LayoutInflater layoutInflater;
    private List<UserEventList> trackDatalist;
    private static final int typeDay = 0;
    private static final int typeMessage = 1;
    private int lastDay = 0;

    public TrackAdapter(Context findListener, List<UserEventList> list) {
        layoutInflater = LayoutInflater.from(findListener);
        trackDatalist = list;
    }

    @Override
    public int getItemViewType(int position) {
        UserEventList userEventList = trackDatalist.get(position);
        if ("Day ".equals(userEventList.getTemplateStr())) {
            return typeDay;
        } else if (!"Day ".equals(userEventList.getTemplateStr())) {
            return typeMessage;
        } else {
            return super.getItemViewType(position);
        }
    }

    @Override
    public int getViewTypeCount() {
        return 2;
    }

    @Override
    public int getCount() {
        return trackDatalist.size();
    }

    @Override
    public Object getItem(int position) {
        return trackDatalist.get(position);
    }

    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        int type = getItemViewType(position);
        ViewHolderMessage holderMessage = null;
        ViewHolderDay holderDay = null;
        if (convertView == null) {
            switch (type) {
                case typeMessage:
                    holderMessage = new ViewHolderMessage();
                    convertView = layoutInflater.inflate(R.layout.item_track_trackadapter_message, null);
                    holderMessage.text = (TextView) convertView.findViewById(R.id.modelText);
                    //holder.img = (ImageView) convertView.findViewById(R.id.modelImg);
                    holderMessage.firstImg = (ImageView) convertView.findViewById(R.id.track_img_first);
                    holderMessage.secondImg = (ImageView) convertView.findViewById(R.id.track_small_icon_second);
                    convertView.setTag(R.id.Tag_track_adapter_message, holderMessage);
                    break;
                case typeDay:
                    holderDay = new ViewHolderDay();
                    convertView = layoutInflater.inflate(R.layout.item_track_trackadapter_day, null);
                    holderDay.text = (TextView) convertView.findViewById(R.id.modelText);
                    holderDay.firstImg = (ImageView) convertView.findViewById(R.id.track_img_first);
                    convertView.setTag(R.id.Tag_track_adapter_Day, holderDay);
                    break;
            }
        } else {
            switch (type) {
                case typeMessage:
                    holderMessage = (ViewHolderMessage) convertView.getTag(R.id.Tag_track_adapter_message);
                    break;
                case typeDay:
                    holderDay = (ViewHolderDay) convertView.getTag(R.id.Tag_track_adapter_Day);
                    break;

            }
        }
        UserEventList userEventList = trackDatalist.get(position);
        switch (type) {
            case typeDay:
                if (holderDay != null) {
                    holderDay.firstImg.setImageResource(R.drawable.track_achievement_day);
                    lastDay = Integer.parseInt(userEventList.getDay());
                    holderDay.text.setText(userEventList.getTemplateStr() + userEventList.getEventParams());
                }
                break;
            case typeMessage:
                if (holderMessage != null) {
                    holderMessage.firstImg.setImageResource(R.drawable.track_achievement_message);
                    holderMessage.text.setText(EventUtil.replaceStr(userEventList.getTemplateStr(), userEventList.getEventParams()));
                }
                break;
        }

        return convertView;

    }

    public class ViewHolderDay {
        public TextView text;
        public ImageView firstImg;
    }

    public class ViewHolderMessage {
        public TextView text;
        public ImageView firstImg;
        public ImageView secondImg;
    }
}
