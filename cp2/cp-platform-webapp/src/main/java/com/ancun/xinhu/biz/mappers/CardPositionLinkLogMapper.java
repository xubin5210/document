package com.ancun.xinhu.biz.mappers;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.ancun.xinhu.domain.model.CardPositionLinkLog;
import com.ancun.xinhu.domain.query.CardPositionLinkLogQuery;

import java.io.Serializable;

public interface CardPositionLinkLogMapper  {
	/***/
	CardPositionLinkLog load(Integer id);

	/***/
	void insert(CardPositionLinkLog cardPositionLinkLog);

	/***/
	void update(CardPositionLinkLog cardPositionLinkLog);

	/***/
	void updateStatus(@Param("id") Integer id, @Param("status") Serializable status);

	/***/
	void delete(Integer id);

	/***/
	List<CardPositionLinkLog> queryList(CardPositionLinkLogQuery cardPositionLinkLogQuery);

	/***/
	int queryCount(CardPositionLinkLogQuery cardPositionLinkLogQuery);

	/***/
	void savePositionLog(Map<String, Object> paraMap);
}