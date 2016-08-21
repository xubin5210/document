package com.ancun.xinhu.domain.query;

import java.io.Serializable;
import java.util.Date;

import com.ancun.products.core.query.AbstractQueryParam;
import com.ancun.products.core.query.QueryParam;

/**
 * 企业职位表查询对象
 */
public class EntPositionQuery extends AbstractQueryParam implements QueryParam,
		Serializable {
	private static final long serialVersionUID = 14320923552931L;

	private Integer[] idArray;// 职位id
	private Integer entId;// 企业ID
	private String positionName;// 职位名称
	private String positionEname;// 英文名称
	private Date gmtCreateFrom;// 创建时间
	private Date gmtCreateTo;// 创建时间
	private Date gmtModifyFrom;// 修改时间
	private Date gmtModifyTo;// 修改时间

	/*** 职位id */
	public Integer[] getIdArray() {
		return idArray;
	}

	/*** 职位id */
	public void setIdArray(Integer... idArray) {
		this.idArray = idArray;
	}

	/*** 企业ID */
	public Integer getEntId() {
		return entId;
	}

	/*** 企业ID */
	public void setEntId(Integer entId) {
		this.entId = entId;
	}

	/*** 职位名称 */
	public String getPositionName() {
		return positionName;
	}

	/*** 职位名称 */
	public void setPositionName(String positionName) {
		this.positionName = positionName;
	}

	/*** 英文名称 */
	public String getPositionEname() {
		return positionEname;
	}

	/*** 英文名称 */
	public void setPositionEname(String positionEname) {
		this.positionEname = positionEname;
	}

	/*** 创建时间 */
	public Date getGmtCreateFrom() {
		return gmtCreateFrom;
	}

	/*** 创建时间 */
	public void setGmtCreateFrom(Date gmtCreateFrom) {
		this.gmtCreateFrom = gmtCreateFrom;
	}

	/*** 创建时间 */
	public Date getGmtCreateTo() {
		return gmtCreateTo;
	}

	/*** 创建时间 */
	public void setGmtCreateTo(Date gmtCreateTo) {
		this.gmtCreateTo = gmtCreateTo;
	}

	/*** 修改时间 */
	public Date getGmtModifyFrom() {
		return gmtModifyFrom;
	}

	/*** 修改时间 */
	public void setGmtModifyFrom(Date gmtModifyFrom) {
		this.gmtModifyFrom = gmtModifyFrom;
	}

	/*** 修改时间 */
	public Date getGmtModifyTo() {
		return gmtModifyTo;
	}

	/*** 修改时间 */
	public void setGmtModifyTo(Date gmtModifyTo) {
		this.gmtModifyTo = gmtModifyTo;
	}
}
