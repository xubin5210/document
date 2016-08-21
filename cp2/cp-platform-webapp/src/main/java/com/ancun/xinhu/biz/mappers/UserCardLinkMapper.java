package com.ancun.xinhu.biz.mappers;

import java.util.HashMap;
import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.ancun.xinhu.domain.dto.XinHuQuery;
import com.ancun.xinhu.domain.model.UserCardLink;
import com.ancun.xinhu.domain.query.UserCardLinkQuery;

import java.io.Serializable;

public interface UserCardLinkMapper  {
	/***/
	UserCardLink load(Integer id);

	/***/
	void insert(UserCardLink userCardLink);

	/***/
	void update(UserCardLink userCardLink);

	/***/
	void updateStatus(@Param("id") Integer id, @Param("status") Serializable status);

	/**删除*/
	void delete(UserCardLink userCardLink);

	/***/
	List<UserCardLink> queryList(UserCardLinkQuery userCardLinkQuery);

	/***/
	int queryCount(UserCardLinkQuery userCardLinkQuery);
	
	/**获取指定两张名片是否存在关系*/
	int getTwoCardIsExistsLink(UserCardLink userCardLink);
	
	/**人脉统计接口*/
	HashMap<String,Object> getConnStatisticalInfo(Integer cardId);
	
	/**推荐名片_1度关系搜索*/
	List<HashMap<String,String>> getRecommendCardList_one(XinHuQuery xinHuQuery);
	
	/**推荐名片_同事*/
	List<HashMap<String,String>> getRecommendCardList_colleague(XinHuQuery xinHuQuery);
	
}