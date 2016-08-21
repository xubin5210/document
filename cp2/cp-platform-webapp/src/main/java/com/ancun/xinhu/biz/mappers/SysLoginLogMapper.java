package com.ancun.xinhu.biz.mappers;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.ancun.xinhu.domain.model.SysLoginLog;
import com.ancun.xinhu.domain.query.SysLoginLogQuery;

import java.io.Serializable;

public interface SysLoginLogMapper  {
	/***/
	SysLoginLog load(Integer id);

	/***/
	void insert(SysLoginLog sysLoginLog);

	/***/
	void update(SysLoginLog sysLoginLog);

	/***/
	void updateStatus(@Param("id") Integer id, @Param("status") Serializable status);

	/***/
	void delete(Integer id);

	/***/
	List<SysLoginLog> queryList(SysLoginLogQuery sysLoginLogQuery);

	/***/
	int queryCount(SysLoginLogQuery sysLoginLogQuery);
}