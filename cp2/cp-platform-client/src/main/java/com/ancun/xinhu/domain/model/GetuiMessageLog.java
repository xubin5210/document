package com.ancun.xinhu.domain.model;

import java.io.Serializable;
import java.util.Date;

public class GetuiMessageLog implements Serializable {

	private static final long serialVersionUID = 215800311100004584L;
	
	private String id;//消息id
	private Integer cardId;//名片id
	private Integer pushType;//推送类型(1:透传)
	private Integer messageCode;//消息编号
	private String title;//消息标题
	private String message;//消息
	private Date gmtCreate;//创建时间
	private Date gmtModify;//修改时间
    private Integer isAccept;//是否接收(0:未接受 1:已接收)
    private Date gmtAccept;//接收时间
    private Integer sendCount;//发送次数
    
    private Integer userId;//用户id
    private String clientId;//手机端id
    
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public Integer getCardId() {
		return cardId;
	}
	public void setCardId(Integer cardId) {
		this.cardId = cardId;
	}
	public Integer getPushType() {
		return pushType;
	}
	public void setPushType(Integer pushType) {
		this.pushType = pushType;
	}
	public Integer getMessageCode() {
		return messageCode;
	}
	public void setMessageCode(Integer messageCode) {
		this.messageCode = messageCode;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
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
	public Integer getIsAccept() {
		return isAccept;
	}
	public void setIsAccept(Integer isAccept) {
		this.isAccept = isAccept;
	}
	public Date getGmtAccept() {
		return gmtAccept;
	}
	public void setGmtAccept(Date gmtAccept) {
		this.gmtAccept = gmtAccept;
	}
	public Integer getSendCount() {
		return sendCount;
	}
	public void setSendCount(Integer sendCount) {
		this.sendCount = sendCount;
	}
	public Integer getUserId() {
		return userId;
	}
	public void setUserId(Integer userId) {
		this.userId = userId;
	}
	public String getClientId() {
		return clientId;
	}
	public void setClientId(String clientId) {
		this.clientId = clientId;
	}
    
    

}
