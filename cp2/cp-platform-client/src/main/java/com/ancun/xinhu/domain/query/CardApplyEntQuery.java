package com.ancun.xinhu.domain.query;

import java.io.Serializable;
import java.util.Date;

import com.ancun.products.core.query.AbstractQueryParam;
import com.ancun.products.core.query.QueryParam;

/**
 * 用户申请绑定企业表查询对象
 */
public class CardApplyEntQuery extends AbstractQueryParam implements
		QueryParam, Serializable {
	private static final long serialVersionUID = 14322270353461L;

	private Integer entId;// 企业ID
	private Integer cardId;// 用户id
	private Date gmtApplyFrom;// 申请时间
	private Date gmtApplyTo;// 申请时间
	private Integer bindStatus;// 绑定状态
	private Date gmtBindFrom;// 绑定时间
	private Date gmtBindTo;// 绑定时间

	/*** 企业ID */
	public Integer getEntId() {
		return entId;
	}

	/*** 企业ID */
	public void setEntId(Integer entId) {
		this.entId = entId;
	}

	/*** 用户id */
	public Integer getCardId() {
		return cardId;
	}

	/*** 用户id */
	public void setCardId(Integer cardId) {
		this.cardId = cardId;
	}

	/*** 申请时间 */
	public Date getGmtApplyFrom() {
		return gmtApplyFrom;
	}

	/*** 申请时间 */
	public void setGmtApplyFrom(Date gmtApplyFrom) {
		this.gmtApplyFrom = gmtApplyFrom;
	}

	/*** 申请时间 */
	public Date getGmtApplyTo() {
		return gmtApplyTo;
	}

	/*** 申请时间 */
	public void setGmtApplyTo(Date gmtApplyTo) {
		this.gmtApplyTo = gmtApplyTo;
	}

	/*** 绑定状态 */
	public Integer getBindStatus() {
		return bindStatus;
	}

	/*** 绑定状态 */
	public void setBindStatus(Integer bindStatus) {
		this.bindStatus = bindStatus;
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
