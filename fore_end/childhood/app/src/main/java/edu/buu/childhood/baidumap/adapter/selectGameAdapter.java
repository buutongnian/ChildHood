package edu.buu.childhood.baidumap.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import edu.buu.childhood.R;
import edu.buu.childhood.baidumap.pojo.MarkItem;

/**
 * Created by lcc on 2016/7/5.
 */
public class selectGameAdapter extends BaseAdapter {
    private LayoutInflater layoutInflater;
    private List<MarkItem> selecGametDatalist;

    public selectGameAdapter(Context findListener, List<MarkItem> list) {
        layoutInflater = LayoutInflater.from(findListener);
        selecGametDatalist = list;
    }

    @Override
    public int getCount() {
        return selecGametDatalist.size();
    }

    @Override
    public Object getItem(int position) {
        return selecGametDatalist.get(position);
    }

    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;
        if (convertView == null) {
            holder = new ViewHolder();
            convertView = layoutInflater.inflate(R.layout.select_game_model, null);
            holder.code = (TextView) convertView.findViewById(R.id.select_game_code);
            holder.gameName = (TextView) convertView.findViewById(R.id.select_game_text);
            holder.Icon=(ImageView)convertView.findViewById(R.id.select_game_image);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        MarkItem bean = selecGametDatalist.get(position);
        holder.code.setText(Integer.toString(bean.getGameSelectCode()));
        holder.gameName.setText(bean.getSelectGameTitle());
        holder.Icon.setImageResource(R.drawable.ungame_icon);

        if (position == selectItem) {
            holder.Icon.setImageResource(R.drawable.select_icon);
        }
        else {
            holder.Icon.setImageResource(R.drawable.unselect_icon);
        }
        return convertView;

    }
    public  void setSelectItem(int selectItem) {
        this.selectItem = selectItem;
    }
    private int  selectItem=-1;

    public class ViewHolder {
        public TextView code;
        public TextView gameName;
        private ImageView Icon;



    }
}
