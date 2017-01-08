package edu.buu.childhood.baidumap.pojo;


import java.io.Serializable;

/**
 * Created by Administrator on 2016/8/29.
 */
public class ServiceMessage implements Serializable{

    private String userName;
    private String body;

    public ServiceMessage(String userName, String body) {
        this.userName = userName;
        this.body = body;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    @Override
    public String toString() {
        return "ServiceMessage{" +
                "userName='" + userName + '\'' +
                ", body='" + body + '\'' +
                '}';
    }
}
