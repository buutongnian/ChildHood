package edu.buu.childhood.util;

import java.io.InputStream;
import java.net.HttpURLConnection;

/**
 * Created by Administrator on 2016/9/9.
 */
public interface ImageLoaderCallBack {
    public InputStream getInputStream(HttpURLConnection urlConnection);
}
