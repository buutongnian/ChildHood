package edu.buu.childhood.game.service;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
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
import edu.buu.childhood.game.pojo.GameHead;
import edu.buu.childhood.game.pojo.ItemBean;
import edu.buu.childhood.util.HttpUtil;

/**
 * Created by lcc on 2016/7/9.
 */
public class GameServiceImpl implements GameService {
    Gson json = new Gson();

    @Override
    public CallBackPage<ItemBean> getGameHeadInf(String result){
        Message<Page<GameHead>> message = json.fromJson(result, new TypeToken<Message<Page<GameHead>>>() {
        }.getType());
        if (C.game.GAME_HEAD_QUERY_SUCCESS.equals(message.getMessageCode())) {
            CallBackPage<ItemBean> callBackPage = new CallBackPage<ItemBean>();
            Page<GameHead> page = (Page<GameHead>) message.getMessageContent();
            List<ItemBean> list = new ArrayList<ItemBean>();
            Iterator iter = page.getDataList().iterator();
            while (iter.hasNext()) {
                ItemBean itemBean = new ItemBean();
                GameHead gameHead = (GameHead) iter.next();
                itemBean.setAgeRank(gameHead.getAgeCode() + "");
                itemBean.setGameSynopsis(gameHead.getGameSynopsis() + "");
                itemBean.setMemNumSize(gameHead.getMemNumCode() + "");
                // itemBean.setImage(R.drawable.game_icon);
                byte[] bytes=new HttpUtil(gameHead.getGameImage()).getHttpData();
                Bitmap bitmap = BitmapFactory.decodeByteArray(bytes,0,bytes.length);
                BitmapDrawable bd=new BitmapDrawable(bitmap);
                itemBean.setBackground(bd);
                list.add(itemBean);
            }
            Log.d("page++++",list+"");
            callBackPage.setDatalist(list);
            callBackPage.setCurrentPage(page.getCurrentPage());
            callBackPage.setPageSize(page.getPageSize());
            callBackPage.setTotalPage(page.getTotalPage());
            return callBackPage;
        }
        return null;
    }

    @Override
    public String getGameContentInf() {
        return null;
    }
}
