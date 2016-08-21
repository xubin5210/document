package com.ancun.xinhu.biz.mappers;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.ancun.xinhu.domain.model.EntPosition;
import com.ancun.xinhu.domain.query.EntPositionQuery;

import java.io.Serializable;

public interface EntPositionMapper  {
	/***/
	EntPosition load(Integer id);

	/***/
	void insert(EntPosition entPosition);

	/***/
	void update(EntPosition entPosition);

	/***/
	void updateStatus(@Param("id") Integer id, @Param("status") Serializable status);

	/***/
	void delete(Integer id);

	/***/
	List<EntPosition> queryList(EntPositionQuery entPositionQuery);

	/***/
	int queryCount(EntPositionQuery entPositionQuery);

	/**根据职位名称获取职位编码*/
	Integer queryCode(EntPosition entPosition);
	
	/**获取用户名片职位列表*/
	List<EntPosition> getUserCardPositionList(Integer cardId);
	
	/**获取用户名片职位列表*/
	List<EntPosition> queryAllList(Integer id);

	
	Integer queryExistCount(Integer id);
	
	/**获取企业职位列表*/
	List<EntPosition> getEntPositionList(Integer entId);
}