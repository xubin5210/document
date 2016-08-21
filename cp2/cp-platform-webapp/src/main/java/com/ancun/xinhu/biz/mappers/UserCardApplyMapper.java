package com.ancun.xinhu.biz.mappers;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.ancun.xinhu.domain.model.UserCardApply;
import com.ancun.xinhu.domain.query.UserCardApplyQuery;

import java.io.Serializable;

public interface UserCardApplyMapper  {
	/***/
	UserCardApply load(Integer id);

	/***/
	void insert(UserCardApply userCardApply);

	/***/
	void update(UserCardApply userCardApply);

	/***/
	void updateStatus(@Param("id") Integer id, @Param("status") Serializable status);

	/***/
	void delete(Integer id);

	/***/
	List<UserCardApply> queryList(UserCardApplyQuery userCardApplyQuery);

	/***/
	int queryCount(UserCardApplyQuery userCardApplyQuery);
	
	/**修改交换状态*/
	void updateExchangeStatus(UserCardApply userCardApply);
	
	/**通过两张名片号获取对一个的名片申请记录*/
	UserCardApply getUserCardApplyByTwoCardId(UserCardApply userCardApply); 
	
	/**修改删除信息*/
	void updateDeleteInfo(UserCardApply userCardApply);

	/**获取交换名片数量*/
	int getCardExchangeCount(String id);
	
	/**获取交换名片的职位数量*/
	int getCardExchangePositionCount(String id);
	
	/**获取交换名片的企业数量*/
	int getCardExchangeEntCount(String id);
	
	/**获取交换名片的部门排名*/
	Integer getCardExchangeDeptOrder(Map<String,Object> paraMap);
	
	/**获取交换名片的企业排名*/
	Integer getCardExchangeEntOrder(Map<String,Object> paraMap);
	
	/**获取交换名片的同行排名*/
	Integer getCardExchangeIndustryOrder(Map<String,Object> paraMap);
	
	/**获取交换名片的信乎排名*/
	Integer getCardExchangeXinhuOrder(Map<String,Object> paraMap);

}