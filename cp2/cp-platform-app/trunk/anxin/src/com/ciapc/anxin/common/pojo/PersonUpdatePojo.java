package com.ciapc.anxin.common.pojo;

import java.io.Serializable;

public class PersonUpdatePojo implements Serializable {

	/**
	 * @Fields serialVersionUID : TODO(用一句话描述这个变量表示什么)
	 */
	private static final long serialVersionUID = 1L;

	private String dynamicTime;
	private String dynamicMobile;
	private String dynamicDepartment;
	private String dynamicPosition;
	// 在职状态
	private String dynamicActiveStatus;

	public String getDynamicActiveStatus() {
		return dynamicActiveStatus;
	}

	public void setDynamicActiveStatus(String dynamicActiveStatus) {
		this.dynamicActiveStatus = dynamicActiveStatus;
	}

	public String getDynamicTime() {
		return dynamicTime;
	}

	public void setDynamicTime(String dynamicTime) {
		this.dynamicTime = dynamicTime;
	}

	public String getDynamicMobile() {
		return dynamicMobile;
	}

	public void setDynamicMobile(String dynamicMobile) {
		this.dynamicMobile = dynamicMobile;
	}

	public String getDynamicDepartment() {
		return dynamicDepartment;
	}

	public void setDynamicDepartment(String dynamicDepartment) {
		this.dynamicDepartment = dynamicDepartment;
	}

	public String getDynamicPosition() {
		return dynamicPosition;
	}

	public void setDynamicPosition(String dynamicPosition) {
		this.dynamicPosition = dynamicPosition;
	}

}
