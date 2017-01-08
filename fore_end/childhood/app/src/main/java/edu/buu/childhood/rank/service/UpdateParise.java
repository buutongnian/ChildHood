package edu.buu.childhood.rank.service;

import android.util.Log;
import android.view.View;
import android.widget.ListView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import edu.buu.childhood.AnimationTools.AnimationTools;
import edu.buu.childhood.R;
import edu.buu.childhood.common.C;
import edu.buu.childhood.common.CallBackPage;
import edu.buu.childhood.common.Message;
import edu.buu.childhood.rank.adapter.Ranking_personlist_listview_Adapter;
import edu.buu.childhood.rank.fragment.Ranking_personlist;
import edu.buu.childhood.util.CallBack;
import edu.buu.childhood.util.HttpUtil;
import edu.buu.childhood.util.NetAsyncTask;

/**
 * Created by Administrator on 2016/10/6.
 */
public class UpdateParise implements CallBack {
    private int positation;
    private String url;
    private ListView listView;
    private int heat;
    private String userName;

    public UpdateParise(int heat, ListView listView, int positation, String url, String userName) {
        this.heat = heat;
        this.listView = listView;
        this.positation = positation;
        this.url = url;
        this.userName = userName;
        Log.i("urlll", url);
        new NetAsyncTask(this).execute(url);
    }

    @Override
    public void getResult(CallBackPage result) {
        if (result != null) {
            Log.i("result", result.getFalg());
            if (result.getFalg().equals(C.rank.LIKE_USER_SUCCESS)) {
                Ranking_personlist.isCouldLikeUser.put(userName, "true");
                heat += 1;
                int firstVisiblePosition = listView.getFirstVisiblePosition();
                int lastVisiblePosition = listView.getLastVisiblePosition();
                if (positation >= firstVisiblePosition && positation <= lastVisiblePosition) {
                    View view = (View) listView.getChildAt(positation - firstVisiblePosition);
                    Ranking_personlist_listview_Adapter.ViewHolder holder = (Ranking_personlist_listview_Adapter.ViewHolder) view.getTag();
                    Log.i("holder", holder.tv3.getText().toString());
                    AnimationTools.scale(holder.parise);
                    holder.tv3.setText(String.valueOf(heat));
                    holder.parise.setImageResource(R.drawable.zan);
                    holder.parise.setClickable(false);
                }
            } else {

            }
        }

    }

    @Override
    public CallBackPage doInBackground(String url) {
        HttpUtil httpUtil = new HttpUtil(url);
        byte[] bytes = httpUtil.getHttpData();
        if (bytes != null) {
            Gson json = new Gson();
            Message<String> message = json.fromJson(new String(bytes), new TypeToken<Message>() {
            }.getType());
            if (C.rank.LIKE_USER_SUCCESS.equals(message.getMessageCode())) {
                CallBackPage callBackPage = new CallBackPage();
                callBackPage.setFalg(C.rank.LIKE_USER_SUCCESS);
                return callBackPage;
            } else {
                CallBackPage callBackPage = new CallBackPage();
                callBackPage.setFalg(C.rank.LIKE_USER_UNSUCCESS);
                return callBackPage;
            }
        }
        return null;
    }
}
