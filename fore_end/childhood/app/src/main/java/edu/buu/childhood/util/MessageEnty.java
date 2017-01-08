package edu.buu.childhood.util;

import java.util.List;

/**
 * Created by Administrator on 2016/9/10.
 */
public class MessageEnty {
    private String flagUrl;//在聊天室里面标志是哪个用户的消息
    private List datalist;

    public String getFlagUrl() {
        return flagUrl;
    }

    public void setFlagUrl(String flagUrl) {
        this.flagUrl = flagUrl;
    }

    public List getDatalist() {
        return datalist;
    }

    public void setDatalist(List datalist) {
        this.datalist = datalist;
    }
}
