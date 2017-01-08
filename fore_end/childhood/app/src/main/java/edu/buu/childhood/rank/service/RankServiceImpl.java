package edu.buu.childhood.rank.service;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import edu.buu.childhood.common.C;
import edu.buu.childhood.common.Message;
import edu.buu.childhood.rank.pojo.RankList;

/**
 * Created by Administrator on 2016/10/8.
 */
public class RankServiceImpl implements RankService {
    Gson json = new Gson();

    @Override
    public RankList getUserRank(String result) {
        Message message = json.fromJson(result, new TypeToken<Message>() {
        }.getType());
        if (C.rank.USER_RANK_QUERY_SUCCESS.equals(message.getMessageCode())) {
            Message<RankList> messageRank = json.fromJson(result, new TypeToken<Message<RankList>>() {
            }.getType());
            return messageRank.getMessageContent();
        }
        return null;
    }
}
