package com.ancun.xinhu.domain.model;

import java.io.Serializable;
import java.util.Date;

/**
 * 用户部门操作记录表实体类
 */
public class CardDeptLinkLog implements Serializable {
	private static final long serialVersionUID = 14330349384772L;

	private Integer entId;// 企业ID
	private Integer cardId;// 用户id
	private Integer deptId;// 部门id
	private String deptName;// 部门名称
	private Date gmtBind;// 绑定时间
	private Date gmtUnbind;// 解除时间
	private Integer isLastLink;// 是否是最后的部门关系

	/**
	*
		*/
	public CardDeptLinkLog() {
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

	/** 部门id */
	public Integer getDeptId() {
		return deptId;
	}

	/** 部门id */
	public void setDeptId(Integer deptId) {
		this.deptId = deptId;
	}

	/** 部门名称 */
	public String getDeptName() {
		return deptName;
	}

	/** 部门名称 */
	public void setDeptName(String deptName) {
		this.deptName = deptName;
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

	/** 是否是最后的部门关系 */
	public Integer getIsLastLink() {
		return isLastLink;
	}

	/** 是否是最后的部门关系 */
	public void setIsLastLink(Integer isLastLink) {
		this.isLastLink = isLastLink;
	}

	@Override
	public String toString() {
		return "CardDeptLinkLog [ entId=" + entId + ", cardId=" + cardId
				+ ", deptId=" + deptId + ", deptName=" + deptName
				+ ", gmtBind=" + gmtBind + ", gmtUnbind=" + gmtUnbind
				+ ", isLastLink=" + isLastLink + "]";
	}
}
