package com.ancun.xinhu.domain.model;

import java.io.Serializable;
import java.util.Date;

/**
 * 企业部门表实体类
 */
public class EntDepartment implements Serializable {
	private static final long serialVersionUID = 14337207322972L;

	private Integer id;// 部门id
	private Integer entId;// 企业ID
	private String deptName;// 部门名称
	private Date gmtCreate;// 创建时间
	private Date gmtModify;// 修改时间

	public EntDepartment() {
	}

	/**
	 *
	 * @param id
	 *            -- 部门id
	 */
	public EntDepartment(Integer id) {
		this.id = id;
	}

	/** 部门id */
	public Integer getId() {
		return id;
	}

	/** 部门id */
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

	/** 部门名称 */
	public String getDeptName() {
		return deptName;
	}

	/** 部门名称 */
	public void setDeptName(String deptName) {
		this.deptName = deptName;
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
		return "EntDepartment [ id=" + id + ", entId=" + entId + ", deptName="
				+ deptName + ", gmtCreate=" + gmtCreate + ", gmtModify="
				+ gmtModify + "]";
	}
}
