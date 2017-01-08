package edu.buu.childhood;

import edu.buu.childhood.common.CallBackPage;

/**
 * Created by lcc on 2016/8/20.
 */
public class MemoryCache  {
    private CallBackPage cbp1;
    private static MemoryCache mInstance;
    public static synchronized MemoryCache getInstance()
    {
        if (mInstance == null)
        {
            mInstance = new MemoryCache();
        }
        return mInstance;
    }

    public CallBackPage getCbp1() {
        if(cbp1==null){
            cbp1= new CallBackPage<>();
        }
        return cbp1;
    }

    public void setCbp1(CallBackPage cbp1) {
        this.cbp1 = cbp1;
    }
}
