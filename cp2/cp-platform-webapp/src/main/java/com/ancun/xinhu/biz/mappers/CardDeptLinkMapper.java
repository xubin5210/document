package com.ancun.xinhu.biz.mappers;

import java.util.HashMap;
import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.ancun.xinhu.domain.dto.XinHuQuery;
import com.ancun.xinhu.domain.model.CardDeptLink;
import com.ancun.xinhu.domain.query.CardDeptLinkQuery;

import java.io.Serializable;

public interface CardDeptLinkMapper  {
	/***/
	CardDeptLink load(Integer id);

	/***/
	void insert(CardDeptLink cardDeptLink);

	/***/
	void update(CardDeptLink cardDeptLink);

	/***/
	void updateStatus(@Param("id") Integer id, @Param("status") Serializable status);

	/***/
	void delete(Integer id);

	/***/
	List<CardDeptLink> queryList(CardDeptLinkQuery cardDeptLinkQuery);

	/***/
	int queryCount(CardDeptLinkQuery cardDeptLinkQuery);
	
	/**
	 * 根据entId 和  部门名称（模糊匹配） 搜出该企业下的部门列表
	 * 包括部门ID, 部门名称， 以及该部门下的员工数
	* @param xinHuQuery
	* 	entId ：企业ID
	* 	content : 查询内容，部门名称（模糊）
	* @return
	* 	[{
	* 	  deptId ： 部门ID
	* 	  deptName ： 部门名称
	* 	  deptStaffNum ： 该企业该部门下的员工数 （这点比较难，要表结合）
	* 	}...]
	* <p>
	* author: xyy
	* create at: 2014年4月16日上午12:34:54
	 */
	List<HashMap<String,Object>> getEntDeptList(XinHuQuery xinHuQuery);
}