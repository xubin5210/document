package com.ancun.xinhu.biz.mappers;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.ancun.xinhu.domain.dto.XinHuQuery;
import com.ancun.xinhu.domain.model.CardInfo;
import com.ancun.xinhu.domain.query.CardInfoQuery;

import java.io.Serializable;

public interface CardInfoMapper  {
	/***/
	CardInfo load(Integer id);
	
	CardInfo loadByMobile(String mobile);

	/***/
	void insert(CardInfo cardInfo);

	/***/
	void update(CardInfo cardInfo);

	/***/
	void updateStatus(@Param("id") Integer id, @Param("status") Serializable status);

	/***/
	void delete(Integer id);

	/***/
	List<CardInfo> queryList(CardInfoQuery cardInfoQuery);

	/***/
	int queryCount(CardInfoQuery cardInfoQuery);

	/***/
	Integer getUserCodeByName(String name);

	/***/
	CardInfo queryCardInfo(CardInfo cardInfo);

	/***/
	List<CardInfo> queryAppList(CardInfoQuery cardInfoQuery);
	
	/***/
	int queryAppCount(CardInfoQuery cardInfoQuery);
	
	/**搜索企业个人的接口*/
	List<HashMap<String,Object>> queryEntStaffList(XinHuQuery query);
	
	int queryEntStaffCount(XinHuQuery query);
	
	/**根据部门号搜索企业个人的接口*/
	List<HashMap<String,Object>> queryEntStaffListByDept(XinHuQuery query);
	
	int queryEntStaffListByDeptCount(XinHuQuery query);
	
	/**隐私接口（禁止通关手机号或名片号等搜索到）*/
	void setIsEnableSearch(HashMap<String,Object> map);
	
	/**修改手机号*/
	void updateMobile(CardInfo cardInfo);

	/**获取名片交换记录*/
	List<Map<String,Object>> getCardExchangingRecord(CardInfoQuery cardInfoQuery);

	/**获取名片交换记录总数*/
	int queryExchangRecordCount(CardInfoQuery cardInfoQuery);

	/**获取单条名片交换记录*/
	Map<String, Object> getOneRecord(Map<String,Integer> paraMap);
	
	/**
	 * 获取名片个数通过手机号或身份证号，非绑定用户名片
	* @return
	* <p>
	* author: <a href="mailto:gaobenbiao@ancun.com">GaoBenBiao</a><br>
	* create at: 2015年6月18日 下午2:52:22
	 */
	int getCardCountByMobileOrIdCardNotUserId(CardInfo cardInfo);
	
	/**修改头像url*/
	void updateIconUrl(CardInfo cardInfo);
	
	/**
	 * 获取名片ID(随机取一个)通过手机号或身份证号，非绑定用户名片
	* @return
	* <p>
	* author: <a href="mailto:gaobenbiao@ancun.com">GaoBenBiao</a><br>
	* create at: 2015年6月18日 下午2:52:22
	 */
	Integer getCardIdByMobileOrIdCardNotUserId(CardInfo cardInfo);
	
	/**
	 * 获取名片ID(随机取一个)通过手机号或身份证号，非绑定用户名片
	* @return
	* <p>
	* author: <a href="mailto:gaobenbiao@ancun.com">GaoBenBiao</a><br>
	* create at: 2015年6月18日 下午2:52:22
	 */
	Integer getCardIdByMobileOrIdCardNotUserId_new(CardInfo cardInfo);
	
	/**获取交换花名册数量*/
	Integer querychangeCount(CardInfoQuery cardInfoQuery);
	
	/**获取动态最后读取时间*/
	Date getGmtDynamicLastRead(Integer id);

	/**修改动态最后读取时间*/
	void updateGmtDynamicLastRead(Integer id);

	/**校验该人物是否存在*/
	int checkMobile(String string);
	
}