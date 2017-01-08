package edu.buu.childhood.util;

import android.content.Context;

import edu.buu.childhood.database.UserLoginDao;

/**
 * Created by Administrator on 2016/9/2.
 */
public class UserUtil {
    private Context context;

    public UserUtil(Context context) {
        this.context = context;
    }
    public  String getUserName(){
        UserLoginDao dataHelper=new UserLoginDao(context);

       return  dataHelper.GetUserList().get(dataHelper.GetUserList().size()-1).getUserName();
    }
}
