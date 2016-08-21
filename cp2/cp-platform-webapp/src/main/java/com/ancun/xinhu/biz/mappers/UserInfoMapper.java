package com.ancun.xinhu.biz.mappers;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.ancun.xinhu.domain.model.UserInfo;
import com.ancun.xinhu.domain.query.UserInfoQuery;

import java.io.Serializable;

public interface UserInfoMapper  {
	/***/
	UserInfo load(Integer id);

	/***/
	void insert(UserInfo userInfo);

	/***/
	void update(UserInfo userInfo);

	/***/
	void updateStatus(@Param("id") Integer id, @Param("status") Serializable status);

	/***/
	void delete(Integer id);

	/***/
	List<UserInfo> queryList(UserInfoQuery userInfoQuery);

	/***/
	int queryCount(UserInfoQuery userInfoQuery);
	
	/**通过手机号和密码获取用户信息*/
	UserInfo getUserInfoByMobileAndPwd(UserInfo userInfo);
	
	/**通过手机号获取用户信息*/
	UserInfo getUserInfoByMobile(UserInfo userInfo);
	
	/**重置密码*/
	void resetPwd(UserInfo userInfo);
	
	/**通过ID获取用户信息*/
	UserInfo getUserInfoById(UserInfo userInfo);
	
	/**修改密码*/
	void updatePwd(UserInfo userInfo);
	
	/**个人信息修改接口*/
	void updateUserCardInfo(UserInfo userInfo);
	
	/**修改手机号*/
	void updateMobile(UserInfo userInfo);
	
	/**消息已读接口*/
	void updateMessageReadTime(int userId);
	
	/**通过名片id获取clientId*/
	UserInfo getClientIdByCardId(int cardId);
	
	/**通过名片id获取好友clientId*/
	List<UserInfo> getFriendClientIdListByCardId(int cardId);
	
}