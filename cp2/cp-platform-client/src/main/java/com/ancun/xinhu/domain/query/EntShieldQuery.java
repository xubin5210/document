package com.ancun.xinhu.domain.query;

import java.io.Serializable;

import com.ancun.products.core.query.AbstractQueryParam;
import com.ancun.products.core.query.QueryParam;

/**
 * 企业权限设置表查询对象
 */
public class EntShieldQuery extends AbstractQueryParam implements QueryParam,
		Serializable {
	private static final long serialVersionUID = 14325216513321L;

	private Integer entId;// 企业ID
	private Integer shieldId;// 屏蔽对象id
	private String shieldType;// 屏蔽对象类型(1:个人;2:企业)

	/*** 企业ID */
	public Integer getEntId() {
		return entId;
	}

	/*** 企业ID */
	public void setEntId(Integer entId) {
		this.entId = entId;
	}

	/*** 屏蔽对象id */
	public Integer getShieldId() {
		return shieldId;
	}

	/*** 屏蔽对象id */
	public void setShieldId(Integer shieldId) {
		this.shieldId = shieldId;
	}

	/*** 屏蔽对象类型(1:个人;2:企业) */
	public String getShieldType() {
		return shieldType;
	}

	/*** 屏蔽对象类型(1:个人;2:企业) */
	public void setShieldType(String shieldType) {
		this.shieldType = shieldType;
	}
}
