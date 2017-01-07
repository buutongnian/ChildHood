package edu.buu.childhood.util;

import com.taobao.api.ApiException;
import com.taobao.api.DefaultTaobaoClient;
import com.taobao.api.TaobaoClient;
import com.taobao.api.request.AlibabaAliqinFcSmsNumSendRequest;
import com.taobao.api.response.AlibabaAliqinFcSmsNumSendResponse;
import edu.buu.childhood.common.C;

public abstract class SMSUtil {
 public static String sendSMS(String telNum,String vcode) throws ApiException{
	 TaobaoClient client = new DefaultTaobaoClient(C.vcode.ALI_APIURL, 
				C.vcode.ALI_APPKEY,C.vcode.ALI_APPSECRET);
		AlibabaAliqinFcSmsNumSendRequest req = new AlibabaAliqinFcSmsNumSendRequest();
		req.setExtend("");
		req.setSmsType("normal");
		req.setSmsFreeSignName(C.vcode.SMS_FREE_SIGNNAME);
		req.setSmsParamString(vcode);
		req.setRecNum(telNum);
		req.setSmsTemplateCode(C.vcode.SMS_TEMPLATE_CODE);
		AlibabaAliqinFcSmsNumSendResponse rsp = client.execute(req);
		return rsp.getErrorCode();
 }
}
