package com.ancun.xinhu.biz.service;

import java.util.List;

import com.ancun.xinhu.domain.model.UserInfo;

public interface GeTuiService {
	
	/**对单个用户推送消息*/
	String pushMessageToSingle(String clientId,String title,String text,String userId);
	
	/**透传消息模板_单用户*/
	String transmissionTemplate(UserInfo userInfo,Integer code,Object content);
	
	/**透传消息模板_多用户*/
	String transmissionTemplate(List<UserInfo> userInfoList,Integer code,Object content);
	
	/**透传消息模板_单用户_只用于发送，无需插入数据库*/
	String transmissionTemplateOnlyPush(String clientId,String contentText);

}
