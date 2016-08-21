package com.ancun.xinhu.domain.query;

import java.io.Serializable;
import java.util.Date;

import com.ancun.products.core.query.AbstractQueryParam;
import com.ancun.products.core.query.QueryParam;

/**
 * 登录日志表查询对象
 */
public class SysLoginLogQuery extends AbstractQueryParam implements QueryParam,
		Serializable {
	private static final long serialVersionUID = 14325462701581L;

	private Integer[] idArray;// 登录日志id
	private String userName;// 登录名
	private Integer userType;// 用户类型(0:个人,1:企业)
	private Integer loginStatus;// 登录状态(0:失败,1:成功,2:注销)
	private Date gmtCreateFrom;// 登录时间
	private Date gmtCreateTo;// 登录时间
	private String ip;// 登录ip

	/*** 登录日志id */
	public Integer[] getIdArray() {
		return idArray;
	}

	/*** 登录日志id */
	public void setIdArray(Integer... idArray) {
		this.idArray = idArray;
	}

	/*** 登录名 */
	public String getUserName() {
		return userName;
	}

	/*** 登录名 */
	public void setUserName(String userName) {
		this.userName = userName;
	}

	/*** 用户类型(0:个人,1:企业) */
	public Integer getUserType() {
		return userType;
	}

	/*** 用户类型(0:个人,1:企业) */
	public void setUserType(Integer userType) {
		this.userType = userType;
	}

	/*** 登录状态(0:失败,1:成功,2:注销) */
	public Integer getLoginStatus() {
		return loginStatus;
	}

	/*** 登录状态(0:失败,1:成功,2:注销) */
	public void setLoginStatus(Integer loginStatus) {
		this.loginStatus = loginStatus;
	}

	/*** 登录时间 */
	public Date getGmtCreateFrom() {
		return gmtCreateFrom;
	}

	/*** 登录时间 */
	public void setGmtCreateFrom(Date gmtCreateFrom) {
		this.gmtCreateFrom = gmtCreateFrom;
	}

	/*** 登录时间 */
	public Date getGmtCreateTo() {
		return gmtCreateTo;
	}

	/*** 登录时间 */
	public void setGmtCreateTo(Date gmtCreateTo) {
		this.gmtCreateTo = gmtCreateTo;
	}

	/*** 登录ip */
	public String getIp() {
		return ip;
	}

	/*** 登录ip */
	public void setIp(String ip) {
		this.ip = ip;
	}
}
