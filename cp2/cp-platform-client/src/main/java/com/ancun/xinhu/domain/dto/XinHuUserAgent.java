package com.ancun.xinhu.domain.dto;

import java.io.Serializable;
import java.util.Date;

import com.ancun.products.core.model.OperatorAgent;

public class XinHuUserAgent implements OperatorAgent , Serializable {
	
	private static final long serialVersionUID = -7292195113157000129L;
	
	private long id;
	private String account;
	private String ip;
	
	
	private String mobile;//手机号码
	private Integer defaultCardId;// 默认名片id
	private Date gmtMessageRead;// 消息读取时间
	private String idCard;// 身份证号
	private String code;//验证码
	private Long gmtCode;//验证码获取时间
	
	private boolean ifRemember;//是否自动登录

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public void setId(long id) {
		this.id = id;
	}

	public boolean isIfRemember() {
		return ifRemember;
	}

	public void setIfRemember(boolean ifRemember) {
		this.ifRemember = ifRemember;
	}

	public void setAccount(String account) {
		this.account = account;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	@Override
	public Long getId() {
		return id;
	}

	@Override
	public String getType() {
		return "XinHuUser";
	}

	@Override
	public String getAccount() {
		return account;
	}

	@Override
	public String getIp() {
		return ip;
	}
	
	@Override
	public String toString() {
		return "UserUserAgent [id=" + id + ", account=" + account + ", mobile=" + mobile
				+ ", ip=" + ip + ", code=" + code + ", gmtCode=" + gmtCode + "]";
	}

	public Integer getDefaultCardId() {
		return defaultCardId;
	}

	public void setDefaultCardId(Integer defaultCardId) {
		this.defaultCardId = defaultCardId;
	}

	public Date getGmtMessageRead() {
		return gmtMessageRead;
	}

	public void setGmtMessageRead(Date gmtMessageRead) {
		this.gmtMessageRead = gmtMessageRead;
	}

	public String getIdCard() {
		return idCard;
	}

	public void setIdCard(String idCard) {
		this.idCard = idCard;
	}

	public Long getGmtCode() {
		return gmtCode;
	}

	public void setGmtCode(Long gmtCode) {
		this.gmtCode = gmtCode;
	}

}
