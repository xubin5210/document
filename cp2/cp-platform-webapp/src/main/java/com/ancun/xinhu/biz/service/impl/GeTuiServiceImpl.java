package com.ancun.xinhu.biz.service.impl;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ancun.products.core.api.AncunApiException;
import com.ancun.products.core.config.SysConfig;
import com.ancun.products.core.utils.UUIDGenerator;
import com.ancun.xinhu.biz.mappers.GetuiMessageLogMapper;
import com.ancun.xinhu.biz.service.GeTuiService;
import com.ancun.xinhu.domain.model.GetuiMessageLog;
import com.ancun.xinhu.domain.model.UserInfo;
import com.ancun.xinhu.util.GeTuiUtils;
import com.gexin.rp.sdk.base.IPushResult;
import com.gexin.rp.sdk.base.impl.ListMessage;
import com.gexin.rp.sdk.base.impl.Message;
import com.gexin.rp.sdk.base.impl.SingleMessage;
import com.gexin.rp.sdk.base.impl.Target;
import com.gexin.rp.sdk.exceptions.RequestException;
import com.gexin.rp.sdk.http.IGtPush;
import com.gexin.rp.sdk.template.NotificationTemplate;
import com.gexin.rp.sdk.template.TransmissionTemplate;

@Service("geTuiService")
public class GeTuiServiceImpl implements GeTuiService {

	@Autowired
	private SysConfig sysConfig;
	
	@Autowired
	private GetuiMessageLogMapper getuiMessageLogMapper;
	
	String appId;
	String appkey;
	String master;
//	String CID;
	String Alias;
	String host;
	boolean offline=true;
	int offlineExpireTime=2 * 1000 * 3600;
	
	@PostConstruct
	public void init() {
		appId = sysConfig.get("getui.appId");
		appkey = sysConfig.get("getui.appkey");
		master = sysConfig.get("getui.master");
//		CID = sysConfig.get("getui.CID");
		Alias = sysConfig.get("getui.Alias");
		host = sysConfig.get("getui.host");		
	}
	
	/**
	 * 对单个用户推送消息
	* @param clientId
	* @param title
	* @param text
	* @return
	* <p>
	* author: <a href="mailto:gaobenbiao@ancun.com">GaoBenBiao</a><br>
	* create at: 2015年7月1日 下午9:27:59
	 */
	public String pushMessageToSingle(String clientId,String title,String text,String userId){
		IGtPush push = new IGtPush(host, appkey, master);

		NotificationTemplate template = new NotificationTemplate();
		template.setAppId(appId);
		template.setAppkey(appkey);
		template.setTitle(title);
		template.setText(text);
		template.setLogo("icon.png");
		/*
		// template.setLogoUrl("");
		// template.setIsRing(true);
		// template.setIsVibrate(true);
		// template.setIsClearable(true);
		 */
		template.setTransmissionType(1);
		template.setTransmissionContent("dddd");

		SingleMessage message = new SingleMessage();
		message.setOffline(offline);
		message.setOfflineExpireTime(offlineExpireTime);
		message.setData(template);

		List<Target> targets = new ArrayList<Target>();
		Target target1 = new Target();
		Target target2 = new Target();
		target1.setAppId(appId);
		target1.setClientId(clientId);
		// target1.setAlias(Alias);
		try {
			IPushResult ret = push.pushMessageToSingle(message, target1);
//			System.out.println("正常：" + ret.getResponse().toString());

		} catch (RequestException e) {
//			String requstId = e.getRequestId();
//			IPushResult ret = push.pushMessageToSingle(message, target1,
//					requstId);
//
//			System.out.println("异常：" + ret.getResponse().toString());
			throw new AncunApiException(e);
		}
		return null;
	}
	
	/**
	 * 透传消息模板_单用户
	* @return
	* <p>
	* author: <a href="mailto:gaobenbiao@ancun.com">GaoBenBiao</a><br>
	* create at: 2015年7月1日 下午9:34:06
	 */
	public String transmissionTemplate(UserInfo userInfo,Integer code,Object content){
		if(userInfo==null){
			return null;
		}
		String messageId=UUIDGenerator.generate();
		String contentText = GeTuiUtils.getJson(code,messageId,content);
		//1、首先将消息插入数据库
		GetuiMessageLog getuiMessageLog = new GetuiMessageLog();
//		#{id},#{userId},#{pushType},#{messageCode},#{title},#{message}
		getuiMessageLog.setId(messageId);
		getuiMessageLog.setCardId(userInfo.getDefaultCardId());
		getuiMessageLog.setPushType(1);
		getuiMessageLog.setMessageCode(code);
		getuiMessageLog.setMessage(contentText);
		getuiMessageLogMapper.insert(getuiMessageLog);
		
		if(userInfo.getClientId()==null || "".equals(userInfo.getClientId().trim())){
			return null;
		}
		
		//2、推送消息
		IGtPush push = new IGtPush(host, appkey, master);
		TransmissionTemplate template = new TransmissionTemplate();
		template.setAppId(appId);
		template.setAppkey(appkey);
		//1 立即打开 2 不立即打开
		template.setTransmissionType(2);
		//内容
		template.setTransmissionContent(contentText);
		SingleMessage message = new SingleMessage();
		message.setData(template);
		message.setOffline(offline);
		message.setOfflineExpireTime(offlineExpireTime);
		//推送对象
		Target target1 = new Target();
		target1.setAppId(appId);
		target1.setClientId(userInfo.getClientId());
		try {
			IPushResult ret = push.pushMessageToSingle(message, target1);
			System.out.println("正常：" + ret.getResponse().toString());
			
		} catch (RequestException e) {
			throw new AncunApiException(e);
		}

		return null;
	}
	
	/**
	 * 透传消息模板_多用户
	* @param clientIdArr
	* @param content
	* @return
	* <p>
	* author: <a href="mailto:gaobenbiao@ancun.com">GaoBenBiao</a><br>
	* create at: 2015年7月2日 下午1:42:08
	 */
	public String transmissionTemplate(List<UserInfo> userInfoList,Integer code,Object content){
		
		if(userInfoList==null || userInfoList.size()==0){
			return null;
		}
		
		String messageId=UUIDGenerator.generate();
		String contentText = GeTuiUtils.getJson(code,messageId,content);
		//1、首先插入数据库
		List<GetuiMessageLog> getuiMessageLogList = new ArrayList<GetuiMessageLog>();
		for(int i=0;i<userInfoList.size();i++){
			GetuiMessageLog getuiMessageLog = new GetuiMessageLog();
//			#{id},#{userId},#{pushType},#{messageCode},#{title},#{message}
			getuiMessageLog.setId(messageId);
			getuiMessageLog.setCardId(userInfoList.get(i).getDefaultCardId());
			getuiMessageLog.setPushType(1);
			getuiMessageLog.setMessageCode(code);
			getuiMessageLog.setMessage(contentText);
			getuiMessageLogList.add(getuiMessageLog);
		}
		
		if(getuiMessageLogList!=null && getuiMessageLogList.size()>0){
			getuiMessageLogMapper.insertBatch(getuiMessageLogList);
		}
		
		//2、推送消息
		IGtPush push = new IGtPush(host, appkey, master);
		TransmissionTemplate template = new TransmissionTemplate();
		template.setAppId(appId);
		template.setAppkey(appkey);
		//1 立即打开 2 不立即打开
		template.setTransmissionType(2);
		//内容
		template.setTransmissionContent(GeTuiUtils.getJson(code,messageId,content));
		ListMessage message = new ListMessage();
		message.setData(template);
		message.setOffline(offline);
		message.setOfflineExpireTime(offlineExpireTime);
		
		//推送对象
		List<Target> targetList = new ArrayList<Target>();
		for(int i=0;i<userInfoList.size();i++){
			String clientId=userInfoList.get(i).getClientId();
			if(clientId==null || "".equals(clientId.trim())){
				continue;
			}
			Target target = new Target();
			target.setAppId(appId);
			target.setClientId(clientId);
			targetList.add(target);
		}
		
		if(targetList.size()==0){
			return null;
		}
		
		String contentId=push.getContentId(message);
		
		try {
			IPushResult ret = push.pushMessageToList(contentId, targetList);
			System.out.println("正常：" + ret.getResponse().toString());
			
		} catch (RequestException e) {
			throw new AncunApiException(e);
		}

		return null;
	}
	
	
	/**透传消息模板_单用户_只用于发送，无需插入数据库*/
	public String transmissionTemplateOnlyPush(String clientId,String contentText){
		
		//推送消息
		IGtPush push = new IGtPush(host, appkey, master);
		TransmissionTemplate template = new TransmissionTemplate();
		template.setAppId(appId);
		template.setAppkey(appkey);
		//1 立即打开 2 不立即打开
		template.setTransmissionType(2);
		//内容
		template.setTransmissionContent(contentText);
		SingleMessage message = new SingleMessage();
		message.setData(template);
		message.setOffline(offline);
		message.setOfflineExpireTime(offlineExpireTime);
		//推送对象
		Target target1 = new Target();
		target1.setAppId(appId);
		target1.setClientId(clientId);
		try {
			IPushResult ret = push.pushMessageToSingle(message, target1);
//			System.out.println("正常：" + ret.getResponse().toString());
			
		} catch (RequestException e) {
			throw new AncunApiException(e);
		}

		return null;
	}
	
	
	
}
