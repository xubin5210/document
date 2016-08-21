package com.ancun.xinhu.domain.query;

import java.io.Serializable;
import java.util.Date;

import com.ancun.products.core.query.AbstractQueryParam;
import com.ancun.products.core.query.QueryParam;

/**
 * 代码证扫描件表查询对象
 */
public class EntPaperQuery extends AbstractQueryParam implements QueryParam,
		Serializable {
	private static final long serialVersionUID = 14327510511831L;

	private Integer entId;// 企业ID
	private Integer paperId;// 代码证id
	private String paperUrl;// 代码证url

	/*** 企业ID */
	public Integer getEntId() {
		return entId;
	}

	/*** 企业ID */
	public void setEntId(Integer entId) {
		this.entId = entId;
	}

	/*** 代码证id */
	public Integer getPaperId() {
		return paperId;
	}

	/*** 代码证id */
	public void setPaperId(Integer paperId) {
		this.paperId = paperId;
	}

	/*** 代码证url */
	public String getPaperUrl() {
		return paperUrl;
	}

	/*** 代码证url */
	public void setPaperUrl(String paperUrl) {
		this.paperUrl = paperUrl;
	}
}
