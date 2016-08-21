package com.ancun.xinhu.biz.mappers;

import java.util.HashMap;
import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.ancun.xinhu.domain.model.CardApplyEnt;
import com.ancun.xinhu.domain.query.CardApplyEntQuery;

import java.io.Serializable;

public interface CardApplyEntMapper  {
	/**
	 * 根据 名片号 和  企业号 查询
	* @param cardApplyEnt
	* @return
	* <p>
	* author: xyy
	* create at: 2014年4月16日上午12:34:54
	 */
	CardApplyEnt load(CardApplyEnt cardApplyEnt);
	
	
	/**
	 * 
	* @param CardId
	* @return
	* <p>
	* author: xyy
	* create at: 2014年4月16日上午12:34:54
	 */
	CardApplyEnt loadByCardId(Integer CardId);
	
	/**
	 * 该表  card_id 和  ent_id 作为 联合唯一 键
	 * 更新的时候， card_id 和  ent_id 都做为查询条件
	* @param cardApplyEnt
	* <p>
	* author: xyy
	* create at: 2014年4月16日上午12:34:54
	 */
	void update2(CardApplyEnt cardApplyEnt);
	

	/***/
	void insert(CardApplyEnt cardApplyEnt);

	/***/
	void update(CardApplyEnt cardApplyEnt);

	/***/
	void updateStatus(@Param("id") Integer id, @Param("status") Serializable status);

	/***/
	void delete(Integer id);
	
	/**
	 * 
	* @param id
	* <p>
	* author: xyy
	* create at: 2014年4月16日上午12:34:54
	 */
	void deleteByCardId(Integer cardId);

	/***/
	List<CardApplyEnt> queryList(CardApplyEntQuery cardApplyEntQuery);

	/***/
	int queryCount(CardApplyEntQuery cardApplyEntQuery);
	
	/**获取未审核的名片申请企业记录*/
	HashMap<String,Object> getCardApplyEntNoAudit(Integer cardId);
	
	/**获取未审核或已审核的名片申请企业id*/
	String getCardApplyEntNoOrYesAudit(Integer cardId);
	
	/**删除未审核的申请绑定记录*/
	void deleteCardApplyEntNoAudit(Integer cardId);
}