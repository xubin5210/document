package com.ancun.xinhu.domain.model;

import java.io.Serializable;
import java.util.Date;

/**
 * 登录会话表
 * create at 2015年7月2日 下午6:04:02
 * @author <a href="mailto:gaobenbiao@ancun.com">GaoBenBiao</a>
 * @version %I%, %G%
 * @see
 */
public class UserLoginSession implements Serializable {

	private static final long serialVersionUID = 14323697405123L;
	
	private String tokenId;//会话id
	private Integer userId;//用户id
	private String sessionContent;//会话
	private Date gmtCreate;//创建时间
	private Date gmtModify;//修改时间
	
	public String getTokenId() {
		return tokenId;
	}
	public void setTokenId(String tokenId) {
		this.tokenId = tokenId;
	}
	public Integer getUserId() {
		return userId;
	}
	public void setUserId(Integer userId) {
		this.userId = userId;
	}
	public String getSessionContent() {
		return sessionContent;
	}
	public void setSessionContent(String sessionContent) {
		this.sessionContent = sessionContent;
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
