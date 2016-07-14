package edu.buu.childhood.baidumap.fragment;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListView;
import java.util.ArrayList;
import edu.buu.childhood.R;
import edu.buu.childhood.baidumap.activity.chatTeam;
import edu.buu.childhood.baidumap.adapter.MutiLayoutAdapter;
import edu.buu.childhood.baidumap.poj.App;
import edu.buu.childhood.baidumap.poj.Book;

/**
 * Created by lcc on 2016/7/2.
 */
public class ChatRoom extends Fragment {
    private static final int TYPE_BOOK = 0;
    private static final int TYPE_APP = 1;
    private ListView list_content;
    private ArrayList<Object> mData = null;
    private MutiLayoutAdapter myAdapter = null;
    private ImageView nextTeam;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.chat_room, container, false);
        nextTeam= (ImageView) view.findViewById(R.id.chat_team_chat_team);
        nextTeam.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(),chatTeam.class));
            }
        });


        //数据准备：
        mData = new ArrayList<Object>();
        for (int i = 0; i < 20; i++) {
            switch ((int) (Math.random() * 2)) {
                case TYPE_BOOK:
                    mData.add(new Book(R.mipmap.ic_launcher, "百度"));
                    break;
                case TYPE_APP:
                    mData.add(new App(R.mipmap.ic_launcher, "百度"));
                    break;
            }
        }

        list_content = (ListView) view.findViewById(R.id.chat_room_listView);
        myAdapter = new MutiLayoutAdapter(getActivity(), mData);
        list_content.setAdapter(myAdapter);
        return view;
    }
}
