package com.ancun.xinhu.domain.query;

import java.io.Serializable;
import java.util.Date;

import com.ancun.products.core.query.AbstractQueryParam;
import com.ancun.products.core.query.QueryParam;

/**
 * 用户名片关系表查询对象
 */
public class UserCardLinkQuery extends AbstractQueryParam implements
		QueryParam, Serializable {
	private static final long serialVersionUID = 14330601376471L;

	private Integer applyId;// 申请id
	private Integer cardIdFrom;// 申请人
	private Integer cardId;// 申请对象

	/*** 申请id */
	public Integer getApplyId() {
		return applyId;
	}

	/*** 申请id */
	public void setApplyId(Integer applyId) {
		this.applyId = applyId;
	}

	/*** 申请人 */
	public Integer getCardIdFrom() {
		return cardIdFrom;
	}

	/*** 申请人 */
	public void setCardIdFrom(Integer cardIdFrom) {
		this.cardIdFrom = cardIdFrom;
	}

	/*** 申请对象 */
	public Integer getCardId() {
		return cardId;
	}

	/*** 申请对象 */
	public void setCardId(Integer cardId) {
		this.cardId = cardId;
	}
}
