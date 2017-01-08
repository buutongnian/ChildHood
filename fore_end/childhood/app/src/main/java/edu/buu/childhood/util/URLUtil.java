package edu.buu.childhood.util;

import java.util.Iterator;
import java.util.Map;

/**
 * Created by lcc on 2016/7/9.
 */
public class URLUtil {
    public static String getURL(String APIName,Map args){
        StringBuffer url=new StringBuffer("http://123.57.52.135:8080/ChildHood/"+APIName+".do?webOrApp=app");
        Iterator<Map.Entry<?,?>> iter=args.entrySet().iterator();
        while (iter.hasNext()){
            Map.Entry<?,?> entry=iter.next();
            url.append("&");
            url.append(entry.getKey());
            url.append("=");
            url.append(entry.getValue());
        }
        return url.toString();
    }
}
