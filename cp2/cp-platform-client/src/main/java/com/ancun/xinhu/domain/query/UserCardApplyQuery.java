package com.ancun.xinhu.domain.query;

import java.io.Serializable;
import java.util.Date;

import com.ancun.products.core.query.AbstractQueryParam;
import com.ancun.products.core.query.QueryParam;

/**
 * 用户名片交换申请表查询对象
 */
public class UserCardApplyQuery extends AbstractQueryParam implements
		QueryParam, Serializable {
	private static final long serialVersionUID = 14315567932531L;

	private Integer[] idArray;// 申请id
	private Integer cardIdFrom;// 申请人
	private Integer cardId;// 申请对象
	private Date gmtApplyFrom;// 申请时间
	private Date gmtApplyTo;// 申请时间
	private String isExchange;// 是否交换
	private Date gmtExchangeFrom;// 交换时间
	private Date gmtExchangeTo;// 交换时间
	private Integer isDelete;// 是否删除
	private Integer cardIdDelete;// 删除人
	private Date gmtDeleteFrom;// 删除时间
	private Date gmtDeleteTo;// 删除时间

	/*** 申请id */
	public Integer[] getIdArray() {
		return idArray;
	}

	/*** 申请id */
	public void setIdArray(Integer... idArray) {
		this.idArray = idArray;
	}

	/*** 申请人 */
	public Integer getCardIdFrom() {
		return cardIdFrom;
	}

	/*** 申请人 */
	public void setCardIdFrom(Integer cardIdFrom) {
		this.cardIdFrom = cardIdFrom;
	}

	/*** 申请对象 */
	public Integer getCardId() {
		return cardId;
	}

	/*** 申请对象 */
	public void setCardId(Integer cardId) {
		this.cardId = cardId;
	}

	/*** 申请时间 */
	public Date getGmtApplyFrom() {
		return gmtApplyFrom;
	}

	/*** 申请时间 */
	public void setGmtApplyFrom(Date gmtApplyFrom) {
		this.gmtApplyFrom = gmtApplyFrom;
	}

	/*** 申请时间 */
	public Date getGmtApplyTo() {
		return gmtApplyTo;
	}

	/*** 申请时间 */
	public void setGmtApplyTo(Date gmtApplyTo) {
		this.gmtApplyTo = gmtApplyTo;
	}

	/*** 是否交换 */
	public String getIsExchange() {
		return isExchange;
	}

	/*** 是否交换 */
	public void setIsExchange(String isExchange) {
		this.isExchange = isExchange;
	}

	/*** 交换时间 */
	public Date getGmtExchangeFrom() {
		return gmtExchangeFrom;
	}

	/*** 交换时间 */
	public void setGmtExchangeFrom(Date gmtExchangeFrom) {
		this.gmtExchangeFrom = gmtExchangeFrom;
	}

	/*** 交换时间 */
	public Date getGmtExchangeTo() {
		return gmtExchangeTo;
	}

	/*** 交换时间 */
	public void setGmtExchangeTo(Date gmtExchangeTo) {
		this.gmtExchangeTo = gmtExchangeTo;
	}

	/*** 是否删除 */
	public Integer getIsDelete() {
		return isDelete;
	}

	/*** 是否删除 */
	public void setIsDelete(Integer isDelete) {
		this.isDelete = isDelete;
	}

	/*** 删除人 */
	public Integer getCardIdDelete() {
		return cardIdDelete;
	}

	/*** 删除人 */
	public void setCardIdDelete(Integer cardIdDelete) {
		this.cardIdDelete = cardIdDelete;
	}

	/*** 删除时间 */
	public Date getGmtDeleteFrom() {
		return gmtDeleteFrom;
	}

	/*** 删除时间 */
	public void setGmtDeleteFrom(Date gmtDeleteFrom) {
		this.gmtDeleteFrom = gmtDeleteFrom;
	}

	/*** 删除时间 */
	public Date getGmtDeleteTo() {
		return gmtDeleteTo;
	}

	/*** 删除时间 */
	public void setGmtDeleteTo(Date gmtDeleteTo) {
		this.gmtDeleteTo = gmtDeleteTo;
	}
}
