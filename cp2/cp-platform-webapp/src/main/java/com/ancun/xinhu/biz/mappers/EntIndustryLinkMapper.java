package com.ancun.xinhu.biz.mappers;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.ancun.xinhu.domain.model.EntIndustryLink;
import com.ancun.xinhu.domain.query.EntIndustryLinkQuery;

import java.io.Serializable;

public interface EntIndustryLinkMapper  {
	/***/
	EntIndustryLink load(Integer id);

	/***/
	void insert(EntIndustryLink entIndustryLink);

	/***/
	void update(EntIndustryLink entIndustryLink);

	/***/
	void updateStatus(@Param("id") Integer id, @Param("status") Serializable status);

	/***/
	void delete(Integer id);

	/***/
	List<EntIndustryLink> queryList(EntIndustryLinkQuery entIndustryLinkQuery);

	/***/
	int queryCount(EntIndustryLinkQuery entIndustryLinkQuery);
}