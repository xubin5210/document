package com.ancun.xinhu.biz.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ancun.xinhu.biz.mappers.UserClientInfoMapper;
import com.ancun.xinhu.biz.mappers.UserInfoMapper;
import com.ancun.xinhu.biz.service.GeTuiService;
import com.ancun.xinhu.biz.service.UserClientInfoService;
import com.ancun.xinhu.domain.model.UserClientInfo;
import com.ancun.xinhu.domain.model.UserInfo;
import com.ancun.xinhu.domain.query.UserClientInfoQuery;
import com.ancun.xinhu.util.GeTuiUtils;

@Service
public class UserClientInfoServiceImpl implements UserClientInfoService {
	@Autowired
	private UserClientInfoMapper userClientInfoMapper;
	
	@Autowired
	private GeTuiService geTuiService;
	
	@Autowired
	private UserInfoMapper userInfoMapper;
	
	
	/**
	* 参数  userClientInfo 包含了 clientId， 和  userId 两个参数
	* 
	* author: xyy
	* create at: 2014年4月16日上午12:34:54
	 */
	@Override
	public void hanlderUserClientInfo(UserClientInfo userClientInfo) {
		List<UserClientInfo> list = userClientInfoMapper.queryListFClient(userClientInfo);
		if(list.size()==2){
			/*
			 * 表：表中存在两条记录
			 * 及：参数用户在另一台手机（该手机原先是有用户登录过）上登录，
			 */
			
			//把该用户登录在之前的手机号码， 数据更新，并且推送数据使其下线
			for(UserClientInfo u: list){
				if(!userClientInfo.getClientId().trim().equals(u.getClientId().trim())){
					//把之前的登录的手机 用户ID 设为 -1
					UserClientInfo uci4U = new UserClientInfo();
					uci4U.setClientId(u.getClientId());
					uci4U.setUserId(-1);
					userClientInfoMapper.update(uci4U);
					
					//推送消息
					UserInfo userInfo = new UserInfo();
					userInfo.setId(userClientInfo.getUserId());
					userInfo.setClientId(u.getClientId());
					userInfo.setDefaultCardId(-1);
					geTuiService.transmissionTemplate(userInfo, GeTuiUtils.CODE_LoginOtherMobile, "{userId:"+userClientInfo.getUserId()+"}");
				}
			}
			
			//更新当前登录手机的用户ID
			UserClientInfo uci4U = new UserClientInfo();
			uci4U.setClientId(userClientInfo.getClientId());
			uci4U.setUserId(userClientInfo.getUserId());
			userClientInfoMapper.update(uci4U);
			
		}else if(list.size()==1){
			UserClientInfo u = list.get(0);
			
			//用户在自己手机上重新登录了一次
			if(userClientInfo.getUserId().equals(u.getUserId()) && userClientInfo.getClientId().equals(u.getClientId())){
				// DO NOTHING
				
			}else if(userClientInfo.getUserId().equals(u.getUserId())){
				/**
				 * 表：存在一条 有用户ID 的数据
				 * 及：该用户在一台新手机上登录了账号
				 */
				
				//更新该条记录 的USERID 为 -1
				UserClientInfo uci4U = new UserClientInfo();
				uci4U.setClientId(u.getClientId());
				uci4U.setUserId(-1);
				userClientInfoMapper.update(uci4U);
				
				//推送消息
				UserInfo userInfo = new UserInfo();
				userInfo.setId(userClientInfo.getUserId());
				userInfo.setClientId(u.getClientId());
				userInfo.setDefaultCardId(-1);
				geTuiService.transmissionTemplate(userInfo, GeTuiUtils.CODE_LoginOtherMobile, "{userId:"+userClientInfo.getUserId()+"}");
				
				//插入一条新数据
				userClientInfoMapper.insert(userClientInfo);
				
			}else{
				/**
				 * 表：存在一条数据， clientId 为 参数的clientID
				 * 及：新用户 在一台已经登录过的手机上登录
				 * 更新该条记录即可
				 */
				
				UserClientInfo uci4U = new UserClientInfo();
				uci4U.setClientId(u.getClientId());
				uci4U.setUserId(userClientInfo.getUserId());
				userClientInfoMapper.update(uci4U);
			}
		}else{
			/*
			 * 表： user_client_info 表中不存在 参数的 clientId, 和 userId
			 * 及：新用户（没有在手机登录过的账号）在新手机上登录
			 * 只要插入一条新记录即可
			 */
			userClientInfoMapper.insert(userClientInfo);
		}
		
		// 更新 userInfo 信息
		UserInfo userInfo = new UserInfo();
		userInfo.setClientId(userClientInfo.getClientId());
		userInfo.setId(userClientInfo.getUserId());
		userInfoMapper.update(userInfo);
	}

}
