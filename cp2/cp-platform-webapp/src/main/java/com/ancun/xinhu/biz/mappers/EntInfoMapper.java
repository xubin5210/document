package com.ancun.xinhu.biz.mappers;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.ancun.xinhu.domain.dto.XinHuQuery;
import com.ancun.xinhu.domain.model.EntInfo;
import com.ancun.xinhu.domain.query.EntInfoQuery;
import com.ancun.xinhu.domain.query.EntShieldQuery;

public interface EntInfoMapper  {
	/***/
	EntInfo load(Integer id);

	/***/
	void insert(EntInfo entInfo);

	/***/
	void update(EntInfo entInfo);

	/***/
	void updateStatus(@Param("id") Integer id, @Param("status") Serializable status);

	/***/
	void delete(Integer id);

	/***/
	List<EntInfo> queryList(EntInfoQuery entInfoQuery);
	
	/***/
	EntInfo queryEnt(EntInfo entInfo);

	/***/
	int queryCount(EntInfoQuery entInfoQuery);
	
	EntInfo checkLogin(EntInfo entInfo);

	
	Integer checkMobile(EntInfo entInfo);

	
	int checkPwd(EntInfo entInfo);

	Integer getEntCodeByName(String name);
	
	/**搜索结果为 企业 加 个人 接口*/
	List<EntInfo> queryEntCardList(XinHuQuery query);
	
	int queryEntCardCount(XinHuQuery query);
	
	/**搜索已经注册的企业接口*/
	List<EntInfo> searchEntList(XinHuQuery xinHuQuery);
	
	/**获取搜索企业详情接口*/
	EntInfo getSearchEntCardInfo(XinHuQuery xinHuQuery);

	/**获取屏蔽公司列表*/
	List<Map<String, Object>> getEntShieldList(EntShieldQuery entShieldQuery);

	
	/**获取屏蔽用户列表*/
	List<Map<String, Object>> getUserShieldList(EntShieldQuery entShieldQuery);
	
	/**获取屏蔽用户或者公司总数*/
	int queryShieldCount(EntShieldQuery entShieldQuery);

	/**根据手机号码获取公司信息*/
	EntInfo getEntInfoByMobile(String mobile);

	/**验证用户是否完全注册*/
	int accoutIfExist(EntInfo entInfo);
	
	/**企业名片详情获取接口*/
	EntInfo getEntCardInfo(XinHuQuery xinHuQuery);

	/**是否存在该企业*/
	int ifExistEnt(EntInfo entInfo);
}