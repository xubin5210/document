package com.ancun.xinhu.domain.model;

import java.io.Serializable;
import java.util.Date;

/**
 * 用户名片交换申请表实体类
 */
public class UserCardApply implements Serializable {
	private static final long serialVersionUID = 14315567932532L;

	private Integer id;// 申请id
	private Integer cardIdFrom;// 申请人
	private Integer cardId;// 申请对象
	private String applyType;//申请类型
	private String applyNote;//申请说明
	private Date gmtApply;// 申请时间
	private String isExchange;// 是否交换
	private Date gmtExchange;// 交换时间
	private Integer isDelete;// 是否删除
	private Integer cardIdDelete;// 删除人
	private Date gmtDelete;// 删除时间

	public UserCardApply() {
	}

	/**
	 *
	 * @param id
	 *            -- 申请id
	 */
	public UserCardApply(Integer id) {
		this.id = id;
	}

	/** 申请id */
	public Integer getId() {
		return id;
	}

	/** 申请id */
	public void setId(Integer id) {
		this.id = id;
	}

	/** 申请人 */
	public Integer getCardIdFrom() {
		return cardIdFrom;
	}

	/** 申请人 */
	public void setCardIdFrom(Integer cardIdFrom) {
		this.cardIdFrom = cardIdFrom;
	}

	/** 申请对象 */
	public Integer getCardId() {
		return cardId;
	}

	/** 申请对象 */
	public void setCardId(Integer cardId) {
		this.cardId = cardId;
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

	/** 申请时间 */
	public Date getGmtApply() {
		return gmtApply;
	}

	/** 申请时间 */
	public void setGmtApply(Date gmtApply) {
		this.gmtApply = gmtApply;
	}

	/** 是否交换 */
	public String getIsExchange() {
		return isExchange;
	}

	/** 是否交换 */
	public void setIsExchange(String isExchange) {
		this.isExchange = isExchange;
	}

	/** 交换时间 */
	public Date getGmtExchange() {
		return gmtExchange;
	}

	/** 交换时间 */
	public void setGmtExchange(Date gmtExchange) {
		this.gmtExchange = gmtExchange;
	}

	/** 是否删除 */
	public Integer getIsDelete() {
		return isDelete;
	}

	/** 是否删除 */
	public void setIsDelete(Integer isDelete) {
		this.isDelete = isDelete;
	}

	/** 删除人 */
	public Integer getCardIdDelete() {
		return cardIdDelete;
	}

	/** 删除人 */
	public void setCardIdDelete(Integer cardIdDelete) {
		this.cardIdDelete = cardIdDelete;
	}

	/** 删除时间 */
	public Date getGmtDelete() {
		return gmtDelete;
	}

	/** 删除时间 */
	public void setGmtDelete(Date gmtDelete) {
		this.gmtDelete = gmtDelete;
	}

	@Override
	public String toString() {
		return "UserCardApply [ id=" + id + ", cardIdFrom=" + cardIdFrom
				+ ", cardId=" + cardId + ", gmtApply=" + gmtApply
				+ ", isExchange=" + isExchange + ", gmtExchange=" + gmtExchange
				+ ", isDelete=" + isDelete + ", cardIdDelete=" + cardIdDelete
				+ ", gmtDelete=" + gmtDelete + "]";
	}
}
