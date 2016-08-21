package com.ancun.xinhu.domain.model;

import java.io.Serializable;
import java.util.Date;

/**
 * 用户信息表实体类
 */
public class UserInfo implements Serializable {
	private static final long serialVersionUID = 14323697405132L;

	private Integer id;// 用户id
	private String mobile;// 手机号
	private String pwd;// 密码
	private Integer defaultCardId;// 默认名片id
	private Date gmtMessageRead;// 消息读取时间
	private String idCard;// 身份证号
	private Date gmtCreate;// 创建时间
	private Date gmtModify;// 修改时间
	private String clientId;//客户端id

	private String tokenid;// 令牌

	public UserInfo() {
	}

	/**
	 *
	 * @param id
	 *            -- 用户id
	 */
	public UserInfo(Integer id) {
		this.id = id;
	}

	/** 用户id */
	public Integer getId() {
		return id;
	}

	/** 用户id */
	public void setId(Integer id) {
		this.id = id;
	}

	/** 手机号 */
	public String getMobile() {
		return mobile;
	}

	/** 手机号 */
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	/** 密码 */
	public String getPwd() {
		return pwd;
	}

	/** 密码 */
	public void setPwd(String pwd) {
		this.pwd = pwd;
	}

	/** 默认名片id */
	public Integer getDefaultCardId() {
		return defaultCardId;
	}

	/** 默认名片id */
	public void setDefaultCardId(Integer defaultCardId) {
		this.defaultCardId = defaultCardId;
	}

	/** 消息读取时间 */
	public Date getGmtMessageRead() {
		return gmtMessageRead;
	}

	/** 消息读取时间 */
	public void setGmtMessageRead(Date gmtMessageRead) {
		this.gmtMessageRead = gmtMessageRead;
	}

	/** 身份证号 */
	public String getIdCard() {
		return idCard;
	}

	/** 身份证号 */
	public void setIdCard(String idCard) {
		this.idCard = idCard;
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
		return "UserInfo [ id=" + id + ", mobile=" + mobile + ", pwd=" + pwd
				+ ", defaultCardId=" + defaultCardId + ", gmtMessageRead="
				+ gmtMessageRead + ", idCard=" + idCard + ", gmtCreate="
				+ gmtCreate + ", gmtModify=" + gmtModify + "]";
	}

	public String getTokenid() {
		return tokenid;
	}

	public void setTokenid(String tokenid) {
		this.tokenid = tokenid;
	}

	public String getClientId() {
		return clientId;
	}

	public void setClientId(String clientId) {
		this.clientId = clientId;
	}

}
