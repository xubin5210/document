package com.ancun.xinhu.domain.model;

import java.io.Serializable;
import java.util.Date;

/**
 * 客户端信息表实体类
 */
public class UserClientInfo implements Serializable {
	private static final long serialVersionUID = 14351897459332L;

	private String clientId;// 客户端id
	private Integer userId;// 用户id
	
	private String channel;//渠道
	private String versions;//版本
	private String platform;//平台
//	private String activite;//激活时间
	private Date gmtCreate;// 创建时间
	private Date gmtModify;// 更改时间
	       

	public UserClientInfo() {
	}

	/**
	 *
	 * @param clientId
	 *            -- 客户端id
	 * @param userId
	 *            -- 用户id
	 */
	public UserClientInfo(String clientId, Integer userId) {
		this.clientId = clientId;
		this.userId = userId;
	}

	/** 客户端id */
	public String getClientId() {
		return clientId;
	}

	/** 客户端id */
	public void setClientId(String clientId) {
		this.clientId = clientId;
	}

	/** 用户id */
	public Integer getUserId() {
		return userId;
	}

	/** 用户id */
	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	@Override
	public String toString() {
		return "UserClientInfo [ clientId=" + clientId + ", userId=" + userId
				+ "]";
	}

	public String getChannel() {
		return channel;
	}

	public void setChannel(String channel) {
		this.channel = channel;
	}

	public String getVersions() {
		return versions;
	}

	public void setVersions(String versions) {
		this.versions = versions;
	}

	public String getPlatform() {
		return platform;
	}

	public void setPlatform(String platform) {
		this.platform = platform;
	}

	public Date getGmtCreate() {
		return gmtCreate;
	}

	public void setGmtCreate(Date gmtCreate) {
		this.gmtCreate = gmtCreate;
	}

	public Date getGmtModify() {
		return gmtModify;
	}

	public void setGmtModify(Date gmtModify) {
		this.gmtModify = gmtModify;
	}
	
	
}
