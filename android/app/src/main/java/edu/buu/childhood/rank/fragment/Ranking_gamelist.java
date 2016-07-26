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
import edu.buu.childhood.rank.pojo.ranking_gamelist_itembean;
import edu.buu.childhood.rank.service.GameRankServiceImpl;
import edu.buu.childhood.util.CallBack;
import edu.buu.childhood.util.HttpUtil;
import edu.buu.childhood.util.NetAsyncTask;
import edu.buu.childhood.util.URLUtil;

/**
 * Created by lcc on 2016/7/2.
 */
public class Ranking_gamelist extends Fragment implements CallBack {
    private ListView listView;
    private List<ranking_gamelist_itembean> rankDatalist;
    private String url;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.ranking_gamelist, container, false);
        listView = (ListView) view.findViewById(R.id.ranking_gamelist_listview);
        rankDatalist = new ArrayList<ranking_gamelist_itembean>();
        Map args = new HashMap();
        args.put("userName", "oytt");
        args.put("pageNum", 1);
        url = URLUtil.getURL("gameRank", args);
        new NetAsyncTask(this).execute(url);
        return view;
    }

    @Override
    public void getResult(CallBackPage result) {

        rankDatalist.addAll(result.getDatalist());
        Ranking_gamelist_listview_Adapter gameRank_adapter = new Ranking_gamelist_listview_Adapter(getActivity(), rankDatalist);
        //datalist = new GameServiceImpl().getGameHeadInf(new String(result));
        if (result != null) {
            listView.setAdapter(gameRank_adapter);
        }
    }

        @Override
        public CallBackPage doInBackground (String url){
            HttpUtil httpUtil = new HttpUtil(url);
            GameRankServiceImpl rankService = new GameRankServiceImpl();
            return rankService.getGameRankHeadInf(new String(httpUtil.getHttpData()));
        }
    }
