package com.ciapc.anxin.common.pojo;

import com.master.util.db.annotation.Column;
import com.master.util.db.annotation.Id;
import com.master.util.db.annotation.Table;

@Table(tableName = "cardInfo")
public class ExchangePojo extends Object {

	@Id(idName = "id", length = "125")
	private Integer id;
	
	//当前用户Id(cardId)
	@Column(columnName = "userId", lengtn = "50")
	private int UserId;
	
	// 名片ID
	@Column(columnName = "cardId", lengtn = "50")
	private Integer cardId;

	// 头像地址
	@Column(columnName = "iconUrl", lengtn = "50")
	private String iconUrl;

	// 真实名字
	@Column(columnName = "trueName", lengtn = "50")
	private String trueName;

	@Column(columnName = "orgName", lengtn = "50")
	private String orgName;
	
	//交换状态
	private String isExchange;
	
	private String nickName;
	
	public String getNickName() {
		return nickName;
	}

	public void setNickName(String nickName) {
		this.nickName = nickName;
	}

	//添加时间
	@Column(columnName = "gmtExchange", lengtn = "50")
	private String gmtExchange;

	public String getGmtExchange() {
		return gmtExchange;
	}

	public void setGmtExchange(String gmtExchange) {
		this.gmtExchange = gmtExchange;
	}

	private String job;
	
	@Column(columnName = "mobile", lengtn = "50")
	private String mobile;
	
	//企业认证状态"(0:未认证,1:已认证)
	private String certificationStatus;
	
	private String applyId;//申请接收名片ID
	
	private String applyType;
	
	private String applyNote;
	
	public String getApplyId() {
		return applyId;
	}

	public void setApplyId(String applyId) {
		this.applyId = applyId;
	}

	public String getApplyType() {
		return applyType;
	}

	public void setApplyType(String applyType) {
		this.applyType = applyType;
	}

	public String getApplyNote() {
		return applyNote;
	}

	public void setApplyNote(String applyNote) {
		this.applyNote = applyNote;
	}

	public String getCertificationStatus() {
		return certificationStatus;
	}

	public void setCertificationStatus(String certificationStatus) {
		this.certificationStatus = certificationStatus;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}


	public int getCardId() {
		return cardId;
	}

	public void setCardId(int cardId) {
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

	public String getJob() {
		return job;
	}

	public void setJob(String job) {
		this.job = job;
	}

	public String getIsExchange() {
		return isExchange;
	}

	public void setIsExchange(String isExchange) {
		this.isExchange = isExchange;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public int getUserId() {
		return UserId;
	}

	public void setUserId(int userId) {
		UserId = userId;
	}

	public void setCardId(Integer cardId) {
		this.cardId = cardId;
	}

}
