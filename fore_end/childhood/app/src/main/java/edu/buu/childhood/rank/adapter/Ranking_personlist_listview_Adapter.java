package edu.buu.childhood.rank.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import edu.buu.childhood.Interface.BtnListener;
import edu.buu.childhood.R;
import edu.buu.childhood.rank.pojo.UserRank;
import edu.buu.childhood.util.AsyncImageLoader;

/**
 * Created by lcc on 2016/6/25.
 */
public class Ranking_personlist_listview_Adapter extends BaseAdapter {
    private LayoutInflater rankLayoutInflater;
    private List<UserRank> rankDataList;
    private AsyncImageLoader imageLoader;
    private int heat;
    private BtnListener btnListener;

    public Ranking_personlist_listview_Adapter(Context findListener, List<UserRank> list, BtnListener btnListener) {
        rankLayoutInflater = LayoutInflater.from(findListener);
        rankDataList = list;
        imageLoader = new AsyncImageLoader(findListener);
        this.btnListener = btnListener;
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
            holder.parise = (ImageView) convertView.findViewById(R.id.like);
            holder.backGround = convertView.findViewById(R.id.rank_user);
            //holder.rl=(RelativeLayout) convertView.findViewById(R.id.rl1);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        UserRank bean = rankDataList.get(position);
        //为button加监听事件,点赞数增加
        if (bean.isCouldLike()) {
            holder.parise.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // heat=a+1;
                /*finalHolder.tv3.setText(Integer.toString(heat));
                Btn.setEnabled(false);*/
                    btnListener.onClick(v, position);
                }

            });
        } else {
            holder.parise.setImageResource(R.drawable.zan);
        }
        if (position == 0) {
            holder.tv1.setText("  " + bean.getRank());
            holder.parise.setVisibility(View.INVISIBLE);
            if (holder.backGround != null) {
                holder.backGround.setBackgroundResource(R.drawable.rank_bg);
            }
            if (holder.img1 != null) {
                holder.img1.setImageBitmap(null);
            }
        } else {
            holder.backGround = null;
            if (position == 1) {
                holder.img1.setImageResource(R.drawable.first);
                holder.tv1.setText(" ");
            } else if (position == 2) {
                holder.img1.setImageResource(R.drawable.third);
                holder.tv1.setText(" ");
            } else if (position == 3) {
                holder.img1.setImageResource(R.drawable.second);
                holder.tv1.setText(" ");
            } else {
                holder.tv1.setText("   " + (position));
                holder.img1.setImageBitmap(null);
            }
        }
        if (bean.getUserName().equals(rankDataList.get(0).getUserName())) {
            holder.parise.setVisibility(View.INVISIBLE);
        }
        String imageUrl = bean.getUserHeadImage();
        if (!TextUtils.isEmpty(imageUrl)) {
            Bitmap bitmap = imageLoader.loadImage(holder.img, imageUrl);
            if (bitmap != null) {
                holder.img.setImageBitmap(bitmap);
            }
        }
        holder.tv2.setText(bean.getUsernNickname());
        holder.tv3.setText(String.valueOf(bean.getAchievementPoint()));
        return convertView;

    }

    public class ViewHolder {
        public ImageView img;
        public ImageView img1;
        public ImageView parise;
        public TextView tv1;
        public TextView tv2;
        public TextView tv3;
        public View backGround;
        //public RelativeLayout rl;
    }
}

