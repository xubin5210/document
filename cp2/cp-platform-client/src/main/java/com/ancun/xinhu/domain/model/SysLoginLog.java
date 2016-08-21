package com.ancun.xinhu.domain.model;

import java.io.Serializable;
import java.util.Date;

/**
 * 登录日志表实体类
 */
public class SysLoginLog implements Serializable {
	private static final long serialVersionUID = 14325462701582L;

	private Integer id;// 登录日志id
	private String userName;// 登录名
	private Integer userType;// 用户类型(0:个人,1:企业)
	private Integer loginStatus;// 登录状态(0:失败,1:成功,2:注销)
	private Date gmtCreate;// 登录时间
	private String ip;// 登录ip

	public SysLoginLog() {
	}

	/**
	 *
	 * @param id
	 *            -- 登录日志id
	 */
	public SysLoginLog(Integer id) {
		this.id = id;
	}

	/** 登录日志id */
	public Integer getId() {
		return id;
	}

	/** 登录日志id */
	public void setId(Integer id) {
		this.id = id;
	}

	/** 登录名 */
	public String getUserName() {
		return userName;
	}

	/** 登录名 */
	public void setUserName(String userName) {
		this.userName = userName;
	}

	/** 用户类型(0:个人,1:企业) */
	public Integer getUserType() {
		return userType;
	}

	/** 用户类型(0:个人,1:企业) */
	public void setUserType(Integer userType) {
		this.userType = userType;
	}

	/** 登录状态(0:失败,1:成功,2:注销) */
	public Integer getLoginStatus() {
		return loginStatus;
	}

	/** 登录状态(0:失败,1:成功,2:注销) */
	public void setLoginStatus(Integer loginStatus) {
		this.loginStatus = loginStatus;
	}

	/** 登录时间 */
	public Date getGmtCreate() {
		return gmtCreate;
	}

	/** 登录时间 */
	public void setGmtCreate(Date gmtCreate) {
		this.gmtCreate = gmtCreate;
	}

	/** 登录ip */
	public String getIp() {
		return ip;
	}

	/** 登录ip */
	public void setIp(String ip) {
		this.ip = ip;
	}

	@Override
	public String toString() {
		return "SysLoginLog [ id=" + id + ", userName=" + userName
				+ ", userType=" + userType + ", loginStatus=" + loginStatus
				+ ", gmtCreate=" + gmtCreate + ", ip=" + ip + "]";
	}
}
