package edu.buu.childhood.baidumap.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import edu.buu.childhood.R;
import edu.buu.childhood.baidumap.poj.App;
import edu.buu.childhood.baidumap.poj.Book;

/**
 * Created by lcc on 2016/7/2.
 */
public class MutiLayoutAdapter extends BaseAdapter {

    //定义两个类别标志
    private static final int TYPE_BOOK = 0;
    private static final int TYPE_APP = 1;
    private Context mContext;
    private ArrayList<Object> mData = null;


    public MutiLayoutAdapter(Context mContext, ArrayList<Object> mData) {
        this.mContext = mContext;
        this.mData = mData;
    }

    @Override
    public int getCount() {
        return mData.size();
    }

    @Override
    public Object getItem(int position) {
        return mData.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    //多布局的核心，通过这个判断类别
    @Override
    public int getItemViewType(int position) {
        if (mData.get(position) instanceof App) {
            return TYPE_APP;
        } else if (mData.get(position) instanceof Book) {
            return TYPE_BOOK;
        } else {
            return super.getItemViewType(position);
        }
    }

    //类别数目
    @Override
    public int getViewTypeCount() {
        return 2;
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        int type = getItemViewType(position);
        ViewHolder1 holder1 = null;
        ViewHolder2 holder2 = null;
        if(convertView == null){
            switch (type){
                case TYPE_APP:
                    holder1 = new ViewHolder1();
                    convertView = LayoutInflater.from(mContext).inflate(R.layout.chat_room_item_one, parent, false);
                    holder1.img_icon = (ImageView) convertView.findViewById(R.id.item_one_image);
                    holder1.txt_name = (TextView) convertView.findViewById(R.id.item_one_text);
                    convertView.setTag(R.id.Tag_APP,holder1);
                    break;
                case TYPE_BOOK:
                    holder2 = new ViewHolder2();
                    convertView = LayoutInflater.from(mContext).inflate(R.layout.chat_room_item_two, parent, false);
                    holder2.img_icon1 = (ImageView) convertView.findViewById(R.id.item_two_image);
                    holder2.txt_name1 = (TextView) convertView.findViewById(R.id.item_two_text);
                    convertView.setTag(R.id.Tag_Book,holder2);
                    break;
            }
        }else{
            switch (type){
                case TYPE_APP:
                    holder1 = (ViewHolder1) convertView.getTag(R.id.Tag_APP);
                    break;
                case TYPE_BOOK:
                    holder2 = (ViewHolder2) convertView.getTag(R.id.Tag_Book);
                    break;
            }
        }

        Object obj = mData.get(position);
        //设置下控件的值
        switch (type){
            case TYPE_APP:
                App app = (App) obj;
                if(app != null){
                    holder1.img_icon.setImageResource(app.getaIcon());
                    holder1.txt_name.setText(app.getaName());
                }
                break;
            case TYPE_BOOK:
                Book book = (Book) obj;
                if(book != null){
                    holder2.img_icon1.setImageResource(book.getaIcon());
                    holder2.txt_name1.setText(book.getaName());
                }
                break;
        }
        return convertView;
    }


    //两个不同的ViewHolder
    private static class ViewHolder1{
        ImageView img_icon;
        TextView txt_name;
    }

    private static class ViewHolder2{
        ImageView img_icon1;
        TextView txt_name1;
    }
}
