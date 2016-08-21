package com.ancun.xinhu.domain.model;

import java.io.Serializable;

/**
 * 企业行业关系表实体类
 */
public class EntIndustryLink implements Serializable {
	private static final long serialVersionUID = 14355244762642L;

	private Integer entId;// 企业id
	private String industryId;// 行业性质id

	/**
	*
		*/
	public EntIndustryLink() {
	}

	/** 企业id */
	public Integer getEntId() {
		return entId;
	}

	/** 企业id */
	public void setEntId(Integer entId) {
		this.entId = entId;
	}

	/** 行业性质id */
	public String getIndustryId() {
		return industryId;
	}

	/** 行业性质id */
	public void setIndustryId(String industryId) {
		this.industryId = industryId;
	}

	@Override
	public String toString() {
		return "EntIndustryLink [ entId=" + entId + ", industryId="
				+ industryId + "]";
	}
}
