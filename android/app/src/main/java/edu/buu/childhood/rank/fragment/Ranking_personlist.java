package edu.buu.childhood.rank.fragment;



import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import edu.buu.childhood.R;
import edu.buu.childhood.common.CallBackPage;
import edu.buu.childhood.rank.adapter.Ranking_gamelist_listview_Adapter;
import edu.buu.childhood.rank.adapter.Ranking_personlist_listview_Adapter;
import edu.buu.childhood.rank.pojo.ranking_personlist_itembean;
import edu.buu.childhood.rank.service.GameRankServiceImpl;
import edu.buu.childhood.rank.service.UserRankServiceImpl;
import edu.buu.childhood.util.CallBack;
import edu.buu.childhood.util.HttpUtil;
import edu.buu.childhood.util.NetAsyncTask;
import edu.buu.childhood.util.URLUtil;

/**
 * Created by lcc on 2016/6/25.
 */
public class Ranking_personlist extends Fragment implements CallBack{
    private ListView listView;
    private List<ranking_personlist_itembean> rankDatalist;
    private String url;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.ranking_personlist,container,false);
        listView=(ListView)view.findViewById(R.id.ranking_personlist_listview);
        rankDatalist = new ArrayList<ranking_personlist_itembean>();
        Map args = new HashMap();
        args.put("userName", "oytt");
        args.put("pageNum", 1);
        url = URLUtil.getURL("userRank", args);
        new NetAsyncTask(this).execute(url);
        return view;
    }
    @Override
    public void getResult(CallBackPage result) {

        rankDatalist.addAll(result.getDatalist());
        Ranking_personlist_listview_Adapter userRank_adapter = new Ranking_personlist_listview_Adapter(getActivity(), rankDatalist);
        if (result != null) {
            listView.setAdapter(userRank_adapter);
        }
    }

    @Override
    public CallBackPage doInBackground (String url){
        HttpUtil httpUtil = new HttpUtil(url);
        UserRankServiceImpl rankService = new UserRankServiceImpl();
        return rankService.getUserRankHeadInf(new String(httpUtil.getHttpData()));
    }
}
