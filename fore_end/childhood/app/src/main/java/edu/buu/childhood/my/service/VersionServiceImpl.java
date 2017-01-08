package edu.buu.childhood.my.service;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import edu.buu.childhood.common.C;
import edu.buu.childhood.common.CallBackPage;
import edu.buu.childhood.common.Message;
import edu.buu.childhood.my.pojo.BackItem;
import edu.buu.childhood.my.pojo.VersionBean;
import edu.buu.childhood.my.pojo.VersionInfo;

/**
 * Created by lcc on 2016/7/15.
 */
public class VersionServiceImpl implements VersionService {
    Gson json = new Gson();

    public CallBackPage<VersionInfo> getVersionHeadInf(String result) {
        Message<VersionBean> message = json.fromJson(result, new TypeToken<Message<VersionBean>>() {
        }.getType());
        if (C.version.GET_LEAST_VERSION_SUCCESS.equals(message.getMessageCode())) {
            List<VersionInfo> list = new ArrayList<VersionInfo>();
            CallBackPage<VersionInfo> callBackPage = new CallBackPage<VersionInfo>();
            VersionBean page = (VersionBean) message.getMessageContent();
            VersionInfo versionInfo = new VersionInfo();
            versionInfo.setVersion(page.getVersion() + "");
            versionInfo.setEnable(page.getEnable());
            versionInfo.setPackageUrl(page.getPackageUrl());
            list.add(versionInfo);
            callBackPage.setDatalist(list);

            return callBackPage;
        }

        return null;
    }

    public String getVersionContentInf() {
        return null;
    }
}