package com.ancun.xinhu.domain.query;

import java.io.Serializable;
import java.util.Date;

import com.ancun.products.core.query.AbstractQueryParam;
import com.ancun.products.core.query.QueryParam;

/**
 * 用户部门操作记录表查询对象
 */
public class CardDeptLinkLogQuery extends AbstractQueryParam implements
		QueryParam, Serializable {
	private static final long serialVersionUID = 14330349384771L;

	private Integer entId;// 企业ID
	private Integer cardId;// 用户id
	private Integer deptId;// 部门id
	private String deptName;// 部门名称
	private Date gmtBindFrom;// 绑定时间
	private Date gmtBindTo;// 绑定时间
	private Date gmtUnbindFrom;// 解除时间
	private Date gmtUnbindTo;// 解除时间
	private Integer isLastLink;// 是否是最后的部门关系

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

	/*** 部门id */
	public Integer getDeptId() {
		return deptId;
	}

	/*** 部门id */
	public void setDeptId(Integer deptId) {
		this.deptId = deptId;
	}

	/*** 部门名称 */
	public String getDeptName() {
		return deptName;
	}

	/*** 部门名称 */
	public void setDeptName(String deptName) {
		this.deptName = deptName;
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

	/*** 解除时间 */
	public Date getGmtUnbindFrom() {
		return gmtUnbindFrom;
	}

	/*** 解除时间 */
	public void setGmtUnbindFrom(Date gmtUnbindFrom) {
		this.gmtUnbindFrom = gmtUnbindFrom;
	}

	/*** 解除时间 */
	public Date getGmtUnbindTo() {
		return gmtUnbindTo;
	}

	/*** 解除时间 */
	public void setGmtUnbindTo(Date gmtUnbindTo) {
		this.gmtUnbindTo = gmtUnbindTo;
	}

	/*** 是否是最后的部门关系 */
	public Integer getIsLastLink() {
		return isLastLink;
	}

	/*** 是否是最后的部门关系 */
	public void setIsLastLink(Integer isLastLink) {
		this.isLastLink = isLastLink;
	}
}
