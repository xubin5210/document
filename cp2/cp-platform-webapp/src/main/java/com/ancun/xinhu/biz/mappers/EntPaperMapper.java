package com.ancun.xinhu.biz.mappers;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.ancun.xinhu.domain.model.EntPaper;
import com.ancun.xinhu.domain.query.EntPaperQuery;

import java.io.Serializable;

public interface EntPaperMapper  {
	/***/
	EntPaper load(Integer id);

	/***/
	void insert(EntPaper entPaper);

	/***/
	void update(EntPaper entPaper);

	/***/
	void updateStatus(@Param("id") Integer id, @Param("status") Serializable status);

	/***/
	void delete(Integer id);

	/***/
	List<EntPaper> queryList(EntPaperQuery entPaperQuery);

	/***/
	int queryCount(EntPaperQuery entPaperQuery);
}