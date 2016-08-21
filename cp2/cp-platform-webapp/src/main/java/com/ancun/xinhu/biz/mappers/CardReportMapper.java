package com.ancun.xinhu.biz.mappers;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.ancun.xinhu.domain.model.CardReport;
import com.ancun.xinhu.domain.query.CardReportQuery;

import java.io.Serializable;

public interface CardReportMapper  {
	/***/
	CardReport load(Integer id);

	/***/
	void insert(CardReport cardReport);

	/***/
	void update(CardReport cardReport);

	/***/
	void updateStatus(@Param("id") Integer id, @Param("status") Serializable status);

	/***/
	void delete(Integer id);

	/***/
	List<CardReport> queryList(CardReportQuery cardReportQuery);

	/***/
	int queryCount(CardReportQuery cardReportQuery);
}