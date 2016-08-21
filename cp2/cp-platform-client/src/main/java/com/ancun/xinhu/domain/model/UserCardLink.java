package com.ancun.xinhu.domain.model;

import java.io.Serializable;
import java.util.Date;

/**
 * 用户名片关系表实体类
 */
public class UserCardLink implements Serializable {
	private static final long serialVersionUID = 14330601376472L;

	private Integer applyId;// 申请id
	private Integer cardIdFrom;// 申请人
	private Integer cardId;// 申请对象

	/**
	*
		*/
	public UserCardLink() {
	}

	/** 申请id */
	public Integer getApplyId() {
		return applyId;
	}

	/** 申请id */
	public void setApplyId(Integer applyId) {
		this.applyId = applyId;
	}

	/** 申请人 */
	public Integer getCardIdFrom() {
		return cardIdFrom;
	}

	/** 申请人 */
	public void setCardIdFrom(Integer cardIdFrom) {
		this.cardIdFrom = cardIdFrom;
	}

	/** 申请对象 */
	public Integer getCardId() {
		return cardId;
	}

	/** 申请对象 */
	public void setCardId(Integer cardId) {
		this.cardId = cardId;
	}

	@Override
	public String toString() {
		return "UserCardLink [ applyId=" + applyId + ", cardIdFrom="
				+ cardIdFrom + ", cardId=" + cardId + "]";
	}
}
