package com.ancun.xinhu.biz.mappers;

import java.io.Serializable;
import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.ancun.xinhu.domain.model.SysNotice;
import com.ancun.xinhu.domain.query.SysNoticeQuery;

public interface SysNoticeMapper  {
	/***/
	SysNotice load(Integer id);

	/***/
	void insert(SysNotice sysNotice);

	/***/
	void update(SysNotice sysNotice);

	/***/
	void updateStatus(@Param("id") Integer id, @Param("status") Serializable status);

	/***/
	void delete(Integer id);

	/***/
	List<SysNotice> queryList(SysNoticeQuery sysNoticeQuery);

	/***/
	int queryCount(SysNoticeQuery sysNoticeQuery);
}