	package com.ciapc.anxin.common.pojo;

import java.io.Serializable;

public class UpdatePojo implements Serializable {

	private static final long serialVersionUID = 1L;

	// 名片ID
	private String cardId;

	// 头像地址
	private String iconUrl;

	// 真实名字
	private String trueName;
	
	private String orgName;
	
	//修改时间
	private String dynamicTime;

	private String dynamicMobile;
	
	//在职状态
	private String dynamicActiveStatus;
	
	//职务
	private String dynamicDepartment;
	
	//职位
	private String dynamicPosition;
	

	public String getCardId() {
		return cardId;
	}

	public void setCardId(String cardId) {
		this.cardId = cardId;
	}

	public String getIconUrl() {
		return iconUrl;
	}

	public void setIconUrl(String iconUrl) {
		this.iconUrl = iconUrl;
	}

	public String getTrueName() {
		return trueName;
	}

	public void setTrueName(String trueName) {
		this.trueName = trueName;
	}

	public String getOrgName() {
		return orgName;
	}

	public void setOrgName(String orgName) {
		this.orgName = orgName;
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

	public String getDynamicActiveStatus() {
		return dynamicActiveStatus;
	}

	public void setDynamicActiveStatus(String dynamicActiveStatus) {
		this.dynamicActiveStatus = dynamicActiveStatus;
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
