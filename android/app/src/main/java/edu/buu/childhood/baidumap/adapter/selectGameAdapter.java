package edu.buu.childhood.baidumap.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import java.util.List;
import edu.buu.childhood.R;
import edu.buu.childhood.baidumap.poj.selectGameItemBean;

/**
 * Created by lcc on 2016/7/5.
 */
public class selectGameAdapter extends BaseAdapter {
    private LayoutInflater layoutInflater;
    private List<selectGameItemBean> selecGametDatalist;
    public selectGameAdapter(Context findListener, List<selectGameItemBean> list){
        layoutInflater=LayoutInflater.from(findListener);
        selecGametDatalist=list;
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
        ViewHolder holder=null;
        if (convertView == null) {
            holder = new ViewHolder();
            convertView = layoutInflater.inflate(R.layout.select_game_model, null);
            holder.img1 = (ImageView) convertView.findViewById(R.id.select_game_model_iv1);
            holder.img2 = (ImageView) convertView.findViewById(R.id.select_game_model_iv2);
            holder.img3 = (ImageView) convertView.findViewById(R.id.select_game_model_iv3);
            convertView.setTag(holder);
        }
        else {
            holder = (ViewHolder) convertView.getTag();
        }
    selectGameItemBean bean = selecGametDatalist.get(position);
    holder.img1.setImageResource(bean.image1);
    holder.img2.setImageResource(bean.image2);
    holder.img3.setImageResource(bean.image3);
    return convertView;

}
public class ViewHolder{
    public ImageView img1;
    public ImageView img2;
    public ImageView img3;

}
}
