package com.ancun.xinhu.domain.query;

import java.io.Serializable;
import java.util.Date;

import com.ancun.products.core.query.AbstractQueryParam;
import com.ancun.products.core.query.QueryParam;

/**
 * 用户企业关系表查询对象
 */
public class CardDeptLinkQuery extends AbstractQueryParam implements
		QueryParam, Serializable {
	private static final long serialVersionUID = 14329870747451L;

	private Integer cardId;// 用户id
	private Integer deptId;// 部门id
	private Date gmtBindFrom;// 绑定时间
	private Date gmtBindTo;// 绑定时间

	/*** 用户id */
	public Integer getCardId() {
		return cardId;
	}

	/*** 用户id */
	public void setCardId(Integer cardId) {
		this.cardId = cardId;
	}

	/*** 部门id */
	public Integer getDeptId() {
		return deptId;
	}

	/*** 部门id */
	public void setDeptId(Integer deptId) {
		this.deptId = deptId;
	}

	/*** 绑定时间 */
	public Date getGmtBindFrom() {
		return gmtBindFrom;
	}

	/*** 绑定时间 */
	public void setGmtBindFrom(Date gmtBindFrom) {
		this.gmtBindFrom = gmtBindFrom;
	}

	/*** 绑定时间 */
	public Date getGmtBindTo() {
		return gmtBindTo;
	}

	/*** 绑定时间 */
	public void setGmtBindTo(Date gmtBindTo) {
		this.gmtBindTo = gmtBindTo;
	}
}
