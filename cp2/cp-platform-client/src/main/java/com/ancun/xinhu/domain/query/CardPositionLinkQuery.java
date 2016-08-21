package com.ancun.xinhu.domain.query;

import java.io.Serializable;
import java.util.Date;

import com.ancun.products.core.query.AbstractQueryParam;
import com.ancun.products.core.query.QueryParam;

/**
 * 用户职位表查询对象
 */
public class CardPositionLinkQuery extends AbstractQueryParam implements
		QueryParam, Serializable {
	private static final long serialVersionUID = 14343194595071L;

	private Integer positionId;// 职位id
	private Integer cardId;// 用户id
	private Date gmtBindFrom;// 绑定时间
	private Date gmtBindTo;// 绑定时间

	/*** 职位id */
	public Integer getPositionId() {
		return positionId;
	}

	/*** 职位id */
	public void setPositionId(Integer positionId) {
		this.positionId = positionId;
	}

	/*** 用户id */
	public Integer getCardId() {
		return cardId;
	}

	/*** 用户id */
	public void setCardId(Integer cardId) {
		this.cardId = cardId;
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
