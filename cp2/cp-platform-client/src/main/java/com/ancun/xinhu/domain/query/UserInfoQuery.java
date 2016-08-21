package com.ancun.xinhu.domain.query;

import java.io.Serializable;
import java.util.Date;

import com.ancun.products.core.query.AbstractQueryParam;
import com.ancun.products.core.query.QueryParam;

/**
 * 用户信息表查询对象
 */
public class UserInfoQuery extends AbstractQueryParam implements QueryParam,
		Serializable {
	private static final long serialVersionUID = 14323697405131L;

	private Integer[] idArray;// 用户id
	private String mobile;// 手机号
	private String pwd;// 密码
	private Integer defaultCardId;// 默认名片id
	private Date gmtMessageReadFrom;// 消息读取时间
	private Date gmtMessageReadTo;// 消息读取时间
	private String idCard;// 身份证号
	private Date gmtCreateFrom;// 创建时间
	private Date gmtCreateTo;// 创建时间
	private Date gmtModifyFrom;// 修改时间
	private Date gmtModifyTo;// 修改时间

	/*** 用户id */
	public Integer[] getIdArray() {
		return idArray;
	}

	/*** 用户id */
	public void setIdArray(Integer... idArray) {
		this.idArray = idArray;
	}

	/*** 手机号 */
	public String getMobile() {
		return mobile;
	}

	/*** 手机号 */
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	/*** 密码 */
	public String getPwd() {
		return pwd;
	}

	/*** 密码 */
	public void setPwd(String pwd) {
		this.pwd = pwd;
	}

	/*** 默认名片id */
	public Integer getDefaultCardId() {
		return defaultCardId;
	}

	/*** 默认名片id */
	public void setDefaultCardId(Integer defaultCardId) {
		this.defaultCardId = defaultCardId;
	}

	/*** 消息读取时间 */
	public Date getGmtMessageReadFrom() {
		return gmtMessageReadFrom;
	}

	/*** 消息读取时间 */
	public void setGmtMessageReadFrom(Date gmtMessageReadFrom) {
		this.gmtMessageReadFrom = gmtMessageReadFrom;
	}

	/*** 消息读取时间 */
	public Date getGmtMessageReadTo() {
		return gmtMessageReadTo;
	}

	/*** 消息读取时间 */
	public void setGmtMessageReadTo(Date gmtMessageReadTo) {
		this.gmtMessageReadTo = gmtMessageReadTo;
	}

	/*** 身份证号 */
	public String getIdCard() {
		return idCard;
	}

	/*** 身份证号 */
	public void setIdCard(String idCard) {
		this.idCard = idCard;
	}

	/*** 创建时间 */
	public Date getGmtCreateFrom() {
		return gmtCreateFrom;
	}

	/*** 创建时间 */
	public void setGmtCreateFrom(Date gmtCreateFrom) {
		this.gmtCreateFrom = gmtCreateFrom;
	}

	/*** 创建时间 */
	public Date getGmtCreateTo() {
		return gmtCreateTo;
	}

	/*** 创建时间 */
	public void setGmtCreateTo(Date gmtCreateTo) {
		this.gmtCreateTo = gmtCreateTo;
	}

	/*** 修改时间 */
	public Date getGmtModifyFrom() {
		return gmtModifyFrom;
	}

	/*** 修改时间 */
	public void setGmtModifyFrom(Date gmtModifyFrom) {
		this.gmtModifyFrom = gmtModifyFrom;
	}

	/*** 修改时间 */
	public Date getGmtModifyTo() {
		return gmtModifyTo;
	}

	/*** 修改时间 */
	public void setGmtModifyTo(Date gmtModifyTo) {
		this.gmtModifyTo = gmtModifyTo;
	}
}
