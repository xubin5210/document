package com.ancun.xinhu.domain.model;

import java.io.Serializable;
import java.util.Date;

/**
 * 企业职位表实体类
 */
public class EntPosition implements Serializable {
	private static final long serialVersionUID = 14320923552932L;

	private Integer id;// 职位id
	private Integer entId;// 企业ID
	private String positionName;// 职位名称
	private String positionEname;// 英文名称
	private Date gmtCreate;// 创建时间
	private Date gmtModify;// 修改时间

	public EntPosition() {
	}

	/**
	 *
	 * @param id
	 *            -- 职位id
	 */
	public EntPosition(Integer id) {
		this.id = id;
	}

	/** 职位id */
	public Integer getId() {
		return id;
	}

	/** 职位id */
	public void setId(Integer id) {
		this.id = id;
	}

	/** 企业ID */
	public Integer getEntId() {
		return entId;
	}

	/** 企业ID */
	public void setEntId(Integer entId) {
		this.entId = entId;
	}

	/** 职位名称 */
	public String getPositionName() {
		return positionName;
	}

	/** 职位名称 */
	public void setPositionName(String positionName) {
		this.positionName = positionName;
	}

	/** 英文名称 */
	public String getPositionEname() {
		return positionEname;
	}

	/** 英文名称 */
	public void setPositionEname(String positionEname) {
		this.positionEname = positionEname;
	}

	/** 创建时间 */
	public Date getGmtCreate() {
		return gmtCreate;
	}

	/** 创建时间 */
	public void setGmtCreate(Date gmtCreate) {
		this.gmtCreate = gmtCreate;
	}

	/** 修改时间 */
	public Date getGmtModify() {
		return gmtModify;
	}

	/** 修改时间 */
	public void setGmtModify(Date gmtModify) {
		this.gmtModify = gmtModify;
	}

	@Override
	public String toString() {
		return "EntPosition [ id=" + id + ", entId=" + entId
				+ ", positionName=" + positionName + ", positionEname="
				+ positionEname + ", gmtCreate=" + gmtCreate + ", gmtModify="
				+ gmtModify + "]";
	}
}
