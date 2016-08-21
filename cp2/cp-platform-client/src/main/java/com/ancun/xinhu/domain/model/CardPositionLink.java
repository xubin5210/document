package com.ancun.xinhu.domain.model;

import java.io.Serializable;
import java.util.Date;

/**
 * 用户职位表实体类
 */
public class CardPositionLink implements Serializable {
	private static final long serialVersionUID = 14343194595072L;

	private Integer positionId;// 职位id
	private Integer cardId;// 用户id
	private Date gmtBind;// 绑定时间

	/**
	*
		*/
	public CardPositionLink() {
	}

	/** 职位id */
	public Integer getPositionId() {
		return positionId;
	}

	/** 职位id */
	public void setPositionId(Integer positionId) {
		this.positionId = positionId;
	}

	/** 用户id */
	public Integer getCardId() {
		return cardId;
	}

	/** 用户id */
	public void setCardId(Integer cardId) {
		this.cardId = cardId;
	}

	/** 绑定时间 */
	public Date getGmtBind() {
		return gmtBind;
	}

	/** 绑定时间 */
	public void setGmtBind(Date gmtBind) {
		this.gmtBind = gmtBind;
	}

	@Override
	public String toString() {
		return "CardPositionLink [ positionId=" + positionId + ", cardId="
				+ cardId + ", gmtBind=" + gmtBind + "]";
	}
}
