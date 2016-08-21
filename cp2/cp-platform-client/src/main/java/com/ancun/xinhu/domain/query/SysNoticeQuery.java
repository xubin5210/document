package com.ancun.xinhu.domain.query;

import java.io.Serializable;
import java.util.Date;

import com.ancun.products.core.query.AbstractQueryParam;
import com.ancun.products.core.query.QueryParam;

/**
 * 系统公告表查询对象
 */
public class SysNoticeQuery extends AbstractQueryParam implements QueryParam,
		Serializable {
	private static final long serialVersionUID = 14345343836541L;

	private Integer[] idArray;// 公告id
	private String title;// 公告名称
	private String content;// 公告内容
	private Date gmtCreateFrom;// 创建时间
	private Date gmtCreateTo;// 创建时间
	private Integer isFirst;// 是否置顶

	/*** 公告id */
	public Integer[] getIdArray() {
		return idArray;
	}

	/*** 公告id */
	public void setIdArray(Integer... idArray) {
		this.idArray = idArray;
	}

	/*** 公告名称 */
	public String getTitle() {
		return title;
	}

	/*** 公告名称 */
	public void setTitle(String title) {
		this.title = title;
	}

	/*** 公告内容 */
	public String getContent() {
		return content;
	}

	/*** 公告内容 */
	public void setContent(String content) {
		this.content = content;
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

	/*** 是否置顶 */
	public Integer getIsFirst() {
		return isFirst;
	}

	/*** 是否置顶 */
	public void setIsFirst(Integer isFirst) {
		this.isFirst = isFirst;
	}
}
