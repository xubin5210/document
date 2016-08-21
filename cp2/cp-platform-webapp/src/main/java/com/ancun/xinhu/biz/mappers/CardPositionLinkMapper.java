package com.ancun.xinhu.biz.mappers;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.ancun.xinhu.domain.model.CardPositionLink;
import com.ancun.xinhu.domain.query.CardPositionLinkQuery;

import java.io.Serializable;

public interface CardPositionLinkMapper  {
	/***/
	CardPositionLink load(Integer id);

	/***/
	void insert(CardPositionLink cardPositionLink);

	/***/
	void update(CardPositionLink cardPositionLink);

	/***/
	void updateStatus(@Param("id") Integer id, @Param("status") Serializable status);

	/***/
	void delete(Integer id);

	/***/
	List<CardPositionLink> queryList(CardPositionLinkQuery cardPositionLinkQuery);

	/***/
	int queryCount(CardPositionLinkQuery cardPositionLinkQuery);
	
	/***/
	List<CardPositionLink> queryAllList(int id);
	
	/**获取名片职位数量*/
	int getCardPositionCount(int cardId);
}