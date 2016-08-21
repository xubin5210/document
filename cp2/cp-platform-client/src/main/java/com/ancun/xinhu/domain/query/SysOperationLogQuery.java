package com.ancun.xinhu.domain.query;

import java.io.Serializable;
import java.util.Date;

import com.ancun.products.core.query.AbstractQueryParam;
import com.ancun.products.core.query.QueryParam;

/**
 * 记录更改记录表查询对象
 */
public class SysOperationLogQuery extends AbstractQueryParam implements
		QueryParam, Serializable {
	private static final long serialVersionUID = 14340335965141L;

	private String tableName;// 表名
	private String columnName;// 列名
	private String columnValue;// 列值
	private Date gmtModifyFrom;// 修改时间
	private Date gmtModifyTo;// 修改时间

	/*** 表名 */
	public String getTableName() {
		return tableName;
	}

	/*** 表名 */
	public void setTableName(String tableName) {
		this.tableName = tableName;
	}

	/*** 列名 */
	public String getColumnName() {
		return columnName;
	}

	/*** 列名 */
	public void setColumnName(String columnName) {
		this.columnName = columnName;
	}

	/*** 列值 */
	public String getColumnValue() {
		return columnValue;
	}

	/*** 列值 */
	public void setColumnValue(String columnValue) {
		this.columnValue = columnValue;
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
