package edu.buu.childhood.rank.service;

import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import edu.buu.childhood.common.C;
import edu.buu.childhood.common.CallBackPage;
import edu.buu.childhood.common.Message;
import edu.buu.childhood.common.Page;
import edu.buu.childhood.rank.pojo.UserRank;
import edu.buu.childhood.rank.pojo.ranking_personlist_itembean;

/**
 * Created by lcc on 2016/7/13.
 */
public class UserRankServiceImpl implements UserRankService {
    Gson json = new Gson();
    private int i=1;

    public CallBackPage<ranking_personlist_itembean>getUserRankHeadInf(String result){
        Message<Page<UserRank>> message = json.fromJson(result, new TypeToken<Message<Page<UserRank>>>() {
        }.getType());
        if(C.rank.USER_RANK_QUERY_SUCCESS.equals(message.getMessageCode())){
            CallBackPage<ranking_personlist_itembean>callBackPage=new CallBackPage<ranking_personlist_itembean>();
            Page<UserRank> page = (Page<UserRank>) message.getMessageContent();
            List<ranking_personlist_itembean> list = new ArrayList<ranking_personlist_itembean>();
            Iterator iter=page.getDataList().iterator();
            while(iter.hasNext()){
                ranking_personlist_itembean userlist=new ranking_personlist_itembean();
                UserRank userrank=(UserRank)iter.next();
                userlist.setUserName(userrank.getUserName());
                userlist.setUserCode(i+"");
                userlist.setLikeCount(userrank.getLikeCount());
                //userlist.setUserImage();
                i++;
                list.add(userlist);

            }
            callBackPage.setDatalist(list);
            callBackPage.setCurrentPage(page.getCurrentPage());
            callBackPage.setPageSize(page.getPageSize());
            callBackPage.setTotalPage(page.getTotalPage());
            return callBackPage;
        }
        return null;
    }
    @Override
    public String getUserRankContentInf() {
        return null;
    }

}
