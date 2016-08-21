package com.ciapc.anxin.common.pojo;

import java.io.Serializable;

public class JobPojo implements Serializable {

	private static final long serialVersionUID = 1L;

	// 职位名称
	private String positionName;

	// 职位
	private String positionEname;

	// 部门名称
	private String deptName;
	
	//人脉头像
	private String iconUrl;
	
	public String getIconUrl() {
		return iconUrl;
	}

	public void setIconUrl(String iconUrl) {
		this.iconUrl = iconUrl;
	}

	public String getPositionName() {
		return positionName;
	}

	public void setPositionName(String positionName) {
		this.positionName = positionName;
	}

	public String getPositionEname() {
		return positionEname;
	}

	public void setPositionEname(String positionEname) {
		this.positionEname = positionEname;
	}

	public String getDeptName() {
		return deptName;
	}

	public void setDeptName(String deptName) {
		this.deptName = deptName;
	}

	@Override
	public String toString() {
		return "JobPojo [positionName=" + positionName + ", positionEname="
				+ positionEname + ", deptName=" + deptName + ", iconUrl="
				+ iconUrl + "]";
	}
	
	
}
