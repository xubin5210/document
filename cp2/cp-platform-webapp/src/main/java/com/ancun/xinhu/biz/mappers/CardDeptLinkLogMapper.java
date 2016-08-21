package com.ancun.xinhu.biz.mappers;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.ancun.xinhu.domain.model.CardDeptLinkLog;
import com.ancun.xinhu.domain.query.CardDeptLinkLogQuery;

import java.io.Serializable;

public interface CardDeptLinkLogMapper  {
	/***/
	CardDeptLinkLog load(Integer id);

	/***/
	void insert(CardDeptLinkLog cardDeptLinkLog);

	/***/
	void update(CardDeptLinkLog cardDeptLinkLog);

	/***/
	void updateStatus(@Param("id") Integer id, @Param("status") Serializable status);

	/***/
	void delete(Integer id);

	/***/
	List<CardDeptLinkLog> queryList(CardDeptLinkLogQuery cardDeptLinkLogQuery);

	/***/
	int queryCount(CardDeptLinkLogQuery cardDeptLinkLogQuery);

	/***/
	void saveDeptLog(Map<String, Object> paraMap);
}