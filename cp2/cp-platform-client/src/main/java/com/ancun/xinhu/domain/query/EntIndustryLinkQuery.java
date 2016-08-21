package com.ancun.xinhu.domain.query;

import java.io.Serializable;
import java.util.Date;

import com.ancun.products.core.query.AbstractQueryParam;
import com.ancun.products.core.query.QueryParam;

/**
 * 企业行业关系表查询对象
 */
public class EntIndustryLinkQuery extends AbstractQueryParam implements
		QueryParam, Serializable {
	private static final long serialVersionUID = 14355244762641L;

	private Integer entId;// 企业id
	private Integer industryId;// 行业性质id

	/*** 企业id */
	public Integer getEntId() {
		return entId;
	}

	/*** 企业id */
	public void setEntId(Integer entId) {
		this.entId = entId;
	}

	/*** 行业性质id */
	public Integer getIndustryId() {
		return industryId;
	}

	/*** 行业性质id */
	public void setIndustryId(Integer industryId) {
		this.industryId = industryId;
	}
}
