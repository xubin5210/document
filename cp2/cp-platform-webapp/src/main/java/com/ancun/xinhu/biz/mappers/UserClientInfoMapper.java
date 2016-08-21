package com.ancun.xinhu.biz.mappers;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.ancun.xinhu.domain.model.UserClientInfo;
import com.ancun.xinhu.domain.query.UserClientInfoQuery;

import java.io.Serializable;

public interface UserClientInfoMapper  {
	/***/
	UserClientInfo load(UserClientInfo userClientInfo);

	/***/
	void insert(UserClientInfo userClientInfo);

	/***/
	void update(UserClientInfo userClientInfo);

	/***/
	void updateStatus(@Param("id") Integer id, @Param("status") Serializable status);

	/***/
	void delete(Integer id);

	/***/
	List<UserClientInfo> queryList(UserClientInfoQuery userClientInfoQuery);

	/***/
	int queryCount(UserClientInfoQuery userClientInfoQuery);
	
	/**
	 * 根据 clientId 和   userId 去查， 条件用 or
	* @return
	* <p>
	* author: xyy
	* create at: 2014年4月16日上午12:34:54
	 */
	List<UserClientInfo> queryListFClient(UserClientInfo userClientInfo);
}