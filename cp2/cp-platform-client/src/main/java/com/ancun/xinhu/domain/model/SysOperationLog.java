package com.ancun.xinhu.domain.model;

import java.io.Serializable;
import java.util.Date;

/**
 * 记录更改记录表实体类
 */
public class SysOperationLog implements Serializable {
	private static final long serialVersionUID = 14340335965142L;

	private String pkValue;// 主键值
	private String tableName;// 表名
	private String columnName;// 列名
	private String columnValue;// 列值
	private Date gmtModify;// 修改时间

	/**
	*
		*/
	public SysOperationLog() {
	}
	
	public SysOperationLog(String pkValue,String tableName,String columnName,String columnValue) {
		this.pkValue=pkValue;
		this.tableName=tableName;
		this.columnName=columnName;
		this.columnValue=columnValue;		
	}

	/** 表名 */
	public String getTableName() {
		return tableName;
	}

	/** 表名 */
	public void setTableName(String tableName) {
		this.tableName = tableName;
	}

	/** 列名 */
	public String getColumnName() {
		return columnName;
	}

	/** 列名 */
	public void setColumnName(String columnName) {
		this.columnName = columnName;
	}

	/** 列值 */
	public String getColumnValue() {
		return columnValue;
	}

	/** 列值 */
	public void setColumnValue(String columnValue) {
		this.columnValue = columnValue;
	}

	/** 修改时间 */
	public Date getGmtModify() {
		return gmtModify;
	}

	/** 修改时间 */
	public void setGmtModify(Date gmtModify) {
		this.gmtModify = gmtModify;
	}

	public String getPkValue() {
		return pkValue;
	}

	public void setPkValue(String pkValue) {
		this.pkValue = pkValue;
	}

	@Override
	public String toString() {
		return "SysOperationLog [ tableName=" + tableName + ", columnName="
				+ columnName + ", columnValue=" + columnValue + ", gmtModify="
				+ gmtModify + "]";
	}
}
