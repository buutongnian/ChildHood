package edu.buu.childhood.baidumap.fragment;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import java.util.ArrayList;
import java.util.List;
import edu.buu.childhood.R;
import edu.buu.childhood.baidumap.adapter.NewMessage_Adapter;
import edu.buu.childhood.baidumap.poj.NewMessage_ItemBean;

/**
 * Created by lcc on 2016/7/2.
 */
public class NewMessage extends Fragment {
    private ListView listView;
    private List<NewMessage_ItemBean> newsDatalist;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.new_message,container,false);
        listView=(ListView)view.findViewById(R.id.new_message_listView);
        newsDatalist = new ArrayList<NewMessage_ItemBean>();
        newsDatalist.add(new NewMessage_ItemBean(R.drawable.pre,"刘诚诚","我是刘诚诚","添加","已添加"));
        listView.setAdapter(new NewMessage_Adapter(getActivity(),newsDatalist));
        return view;
    }
}