package com.ancun.xinhu.domain.model;

import java.io.Serializable;
import java.util.Date;

/**
 * 用户申请绑定企业表实体类
 */
public class CardApplyEnt implements Serializable {
	private static final long serialVersionUID = 14322270353462L;

	private Integer entId;// 企业ID
	private Integer cardId;// 用户id
	private Date gmtApply;// 申请时间
	private Integer bindStatus;// 绑定状态
	private Date gmtBind;// 绑定时间
	private String unpassMes;//不通过原因

	/**
	*
		*/
	public CardApplyEnt() {
	}

	/** 企业ID */
	public Integer getEntId() {
		return entId;
	}

	public String getUnpassMes() {
		return unpassMes;
	}

	public void setUnpassMes(String unpassMes) {
		this.unpassMes = unpassMes;
	}

	/** 企业ID */
	public void setEntId(Integer entId) {
		this.entId = entId;
	}

	/** 用户id */
	public Integer getCardId() {
		return cardId;
	}

	/** 用户id */
	public void setCardId(Integer cardId) {
		this.cardId = cardId;
	}

	/** 申请时间 */
	public Date getGmtApply() {
		return gmtApply;
	}

	/** 申请时间 */
	public void setGmtApply(Date gmtApply) {
		this.gmtApply = gmtApply;
	}

	/** 绑定状态 */
	public Integer getBindStatus() {
		return bindStatus;
	}

	/** 绑定状态 */
	public void setBindStatus(Integer bindStatus) {
		this.bindStatus = bindStatus;
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
		return "CardApplyEnt [ entId=" + entId + ", cardId=" + cardId
				+ ", gmtApply=" + gmtApply + ", bindStatus=" + bindStatus
				+ ", gmtBind=" + gmtBind + "]";
	}
}
