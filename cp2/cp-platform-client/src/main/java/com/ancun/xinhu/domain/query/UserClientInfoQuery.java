package com.ancun.xinhu.domain.query;

import java.io.Serializable;
import java.util.Date;

import com.ancun.products.core.query.AbstractQueryParam;
import com.ancun.products.core.query.QueryParam;

/**
 * 客户端信息表查询对象
 */
public class UserClientInfoQuery extends AbstractQueryParam implements
		QueryParam, Serializable {
	private static final long serialVersionUID = 14351897459331L;

	private String[] clientIdArray;// 客户端id
	private Integer[] userIdArray;// 用户id

	/*** 客户端id */
	public String[] getClientIdArray() {
		return clientIdArray;
	}

	/*** 客户端id */
	public void setClientIdArray(String... clientIdArray) {
		this.clientIdArray = clientIdArray;
	}

	/*** 用户id */
	public Integer[] getUserIdArray() {
		return userIdArray;
	}

	/*** 用户id */
	public void setUserIdArray(Integer... userIdArray) {
		this.userIdArray = userIdArray;
	}
}
