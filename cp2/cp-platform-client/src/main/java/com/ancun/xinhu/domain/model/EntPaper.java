package com.ancun.xinhu.domain.model;

import java.io.Serializable;
import java.util.Date;

/**
 * 代码证扫描件表实体类
 */
public class EntPaper implements Serializable {
	private static final long serialVersionUID = 14327510511832L;

	private Integer entId;// 企业ID
	private Integer paperId;// 代码证id
	private String paperUrl;// 代码证url

	/**
	*
		*/
	public EntPaper() {
	}

	/** 企业ID */
	public Integer getEntId() {
		return entId;
	}

	/** 企业ID */
	public void setEntId(Integer entId) {
		this.entId = entId;
	}

	/** 代码证id */
	public Integer getPaperId() {
		return paperId;
	}

	/** 代码证id */
	public void setPaperId(Integer paperId) {
		this.paperId = paperId;
	}

	/** 代码证url */
	public String getPaperUrl() {
		return paperUrl;
	}

	/** 代码证url */
	public void setPaperUrl(String paperUrl) {
		this.paperUrl = paperUrl;
	}

	@Override
	public String toString() {
		return "EntPaper [ entId=" + entId + ", paperId=" + paperId
				+ ", paperUrl=" + paperUrl + "]";
	}
}
