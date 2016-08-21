package com.ancun.xinhu.biz.mappers;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.ancun.xinhu.domain.model.Cpb;
import com.ancun.xinhu.domain.query.CpbQuery;

import java.io.Serializable;

public interface CpbMapper  {
	/***/
	Cpb load(Integer id);

	/***/
	void insert(Cpb cpb);

	/***/
	void update(Cpb cpb);

	/***/
	void updateStatus(@Param("id") Integer id, @Param("status") Serializable status);

	/***/
	void delete(Integer id);

	/***/
	List<Cpb> queryList(CpbQuery cpbQuery);

	/***/
	int queryCount(CpbQuery cpbQuery);
}