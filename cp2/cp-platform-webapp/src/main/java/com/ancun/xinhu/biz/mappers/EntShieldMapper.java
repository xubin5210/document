package com.ancun.xinhu.biz.mappers;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.ancun.xinhu.domain.model.EntShield;
import com.ancun.xinhu.domain.query.EntShieldQuery;

import java.io.Serializable;

public interface EntShieldMapper  {
	/***/
	EntShield load(Integer id);

	/***/
	void insert(EntShield entShield);

	/***/
	void update(EntShield entShield);

	/***/
	void updateStatus(@Param("id") Integer id, @Param("status") Serializable status);

	/***/
	void delete(Integer id);

	/***/
	List<EntShield> queryList(EntShieldQuery entShieldQuery);

	/***/
	int queryCount(EntShieldQuery entShieldQuery);

	/***/
	void deleteBySelect(EntShield entShield);
}