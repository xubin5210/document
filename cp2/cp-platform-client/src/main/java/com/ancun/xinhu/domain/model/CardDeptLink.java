package com.ancun.xinhu.domain.model;

import java.io.Serializable;
import java.util.Date;

/**
 * 用户企业关系表实体类
 */
public class CardDeptLink implements Serializable {
	private static final long serialVersionUID = 14329870747452L;

	private Integer cardId;// 用户id
	private Integer deptId;// 部门id
	private Date gmtBind;// 绑定时间

	/**
	*
		*/
	public CardDeptLink() {
	}

	/** 用户id */
	public Integer getCardId() {
		return cardId;
	}

	/** 用户id */
	public void setCardId(Integer cardId) {
		this.cardId = cardId;
	}

	/** 部门id */
	public Integer getDeptId() {
		return deptId;
	}

	/** 部门id */
	public void setDeptId(Integer deptId) {
		this.deptId = deptId;
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
		return "CardDeptLink [ cardId=" + cardId + ", deptId=" + deptId
				+ ", gmtBind=" + gmtBind + "]";
	}
}
