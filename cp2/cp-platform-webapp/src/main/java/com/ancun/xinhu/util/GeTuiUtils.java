package com.ancun.xinhu.util;

import java.util.HashMap;
import java.util.Map;

import com.alibaba.fastjson.JSONObject;

public class GeTuiUtils {
	
	/**递交新名片*/
	public final static int CODE_DeliverNewCard = 1;
	
	/**接收新名片*/
	public final static int CODE_AcceptNewCard = 2;
	
	/**名片动态*/
	public final static int CODE_CardDynamic = 3;
	
	/**名片删除*/
	public final static int CODE_CardDelete = 4;
	
	/**申请绑定企业不通过*/
	public final static int CODE_ApplyBindEntUnpass = 5;
	
	/**申请绑定企业通过*/
	public final static int CODE_ApplyBindEntPass = 6;
	
	/**用户登录新手机，通知原手机信乎下线*/
	public final static int CODE_LoginOtherMobile = 7;
	
	public static String getJson(int code,String messageId,Object object){
		Map<String,Object> map  = new HashMap<String, Object>();
		map.put("code", code);
		map.put("messageId", messageId);
		if(object==null){
			map.put("data", "");
		}else{
			map.put("data", object);
		}
		return JSONObject.toJSONString(map);
	}

}
