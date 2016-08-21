package com.ancun.xinhu.domain.model;

import java.io.Serializable;
import java.util.Date;

/**
 * 用户职位操作记录表实体类
 */
public class CardPositionLinkLog implements Serializable {
	private static final long serialVersionUID = 14320077678502L;

	private Integer entId;// 企业ID
	private Integer cardId;// 用户id
	private Integer positionId;// 职位id
	private String positionName;// 职位名称
	private String positionEname;// 职位英文名称
	private Date gmtBind;// 绑定时间
	private Date gmtUnbind;// 解除时间
	private Integer isLastLink;// 是否是最后的职位关系

	/**
	*
		*/
	public CardPositionLinkLog() {
	}

	/** 企业ID */
	public Integer getEntId() {
		return entId;
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

	/** 职位id */
	public Integer getPositionId() {
		return positionId;
	}

	/** 职位id */
	public void setPositionId(Integer positionId) {
		this.positionId = positionId;
	}

	/** 职位名称 */
	public String getPositionName() {
		return positionName;
	}

	/** 职位名称 */
	public void setPositionName(String positionName) {
		this.positionName = positionName;
	}

	/** 职位英文名称 */
	public String getPositionEname() {
		return positionEname;
	}

	/** 职位英文名称 */
	public void setPositionEname(String positionEname) {
		this.positionEname = positionEname;
	}

	/** 绑定时间 */
	public Date getGmtBind() {
		return gmtBind;
	}

	/** 绑定时间 */
	public void setGmtBind(Date gmtBind) {
		this.gmtBind = gmtBind;
	}

	/** 解除时间 */
	public Date getGmtUnbind() {
		return gmtUnbind;
	}

	/** 解除时间 */
	public void setGmtUnbind(Date gmtUnbind) {
		this.gmtUnbind = gmtUnbind;
	}

	/** 是否是最后的职位关系 */
	public Integer getIsLastLink() {
		return isLastLink;
	}

	/** 是否是最后的职位关系 */
	public void setIsLastLink(Integer isLastLink) {
		this.isLastLink = isLastLink;
	}

	@Override
	public String toString() {
		return "CardPositionLinkLog [ entId=" + entId + ", cardId=" + cardId
				+ ", positionId=" + positionId + ", positionName="
				+ positionName + ", positionEname=" + positionEname
				+ ", gmtBind=" + gmtBind + ", gmtUnbind=" + gmtUnbind
				+ ", isLastLink=" + isLastLink + "]";
	}
}
