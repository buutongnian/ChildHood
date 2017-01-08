package edu.buu.childhood.achievement.adapter;

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

import edu.buu.childhood.Interface.BtnListener;
import edu.buu.childhood.R;
import edu.buu.childhood.baidumap.pojo.MemberInfo;
import edu.buu.childhood.util.AsyncImageLoader;

/**
 * Created by lcc on 2016/7/5.
 */
public class PariseMembersAdapter extends BaseAdapter {
    private LayoutInflater layoutInflater;
    private List<MemberInfo> Datalist;
    private BtnListener btnListener;
    private AsyncImageLoader imageLoader;

    public PariseMembersAdapter(Context findListener, List<MemberInfo> list, BtnListener btnListener) {
        layoutInflater = LayoutInflater.from(findListener);
        Datalist = list;
        this.btnListener = btnListener;
        imageLoader = new AsyncImageLoader(findListener);
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
    public View getView(final int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;
        if (convertView == null) {
            holder = new ViewHolder();
            convertView = layoutInflater.inflate(R.layout.item_parise_members, null);
            holder.userName = (TextView) convertView.findViewById(R.id.teamer_name_model_text);
            holder.img = (ImageView) convertView.findViewById(R.id.teamer_name_model_image);
            holder.parise = (ImageView) convertView.findViewById(R.id.parise_members_image);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        MemberInfo memberInfo = Datalist.get(position);
        String imageUrl = memberInfo.getUserHeadImage();
        if (!TextUtils.isEmpty(imageUrl)) {
            holder.img.setTag(imageUrl);
            Bitmap bitmap = imageLoader.loadImage(holder.img, imageUrl);
            if (bitmap != null) {
                holder.img.setImageBitmap(bitmap);
            }
        }
        holder.userName.setText(memberInfo.getUsernNickname());
        if (memberInfo.isCouldLike()) {
            holder.parise.setImageResource(R.drawable.rank_zan);
            holder.parise.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    btnListener.onClick(v, position);
                }
            });
        } else {
            holder.parise.setImageResource(R.drawable.zan);
        }
        return convertView;

    }


    public class ViewHolder {
        public TextView userName;
        public ImageView img;
        public ImageView parise;


    }
}
