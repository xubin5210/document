package com.ancun.xinhu.domain.query;

import java.io.Serializable;
import java.util.Date;

import com.ancun.products.core.query.AbstractQueryParam;
import com.ancun.products.core.query.QueryParam;

/**
 * 举报信息表查询对象
 */
public class CardReportQuery extends AbstractQueryParam implements QueryParam,
		Serializable {
	private static final long serialVersionUID = 14346057590371L;

	private Integer[] idArray;// 举报id
	private Integer reportUseridFrom;// 举报人
	private Integer reportUserid;// 举报对象
	private String reportType;// 举报对象类型(1:个人,2:企业)
	private String reportReason;// 举报原因
	private String reportComments;// 补充说明
	private Date gmtReportFrom;// 举报时间
	private Date gmtReportTo;// 举报时间

	/*** 举报id */
	public Integer[] getIdArray() {
		return idArray;
	}

	/*** 举报id */
	public void setIdArray(Integer... idArray) {
		this.idArray = idArray;
	}

	/*** 举报人 */
	public Integer getReportUseridFrom() {
		return reportUseridFrom;
	}

	/*** 举报人 */
	public void setReportUseridFrom(Integer reportUseridFrom) {
		this.reportUseridFrom = reportUseridFrom;
	}

	/*** 举报对象 */
	public Integer getReportUserid() {
		return reportUserid;
	}

	/*** 举报对象 */
	public void setReportUserid(Integer reportUserid) {
		this.reportUserid = reportUserid;
	}

	/*** 举报对象类型(1:个人,2:企业) */
	public String getReportType() {
		return reportType;
	}

	/*** 举报对象类型(1:个人,2:企业) */
	public void setReportType(String reportType) {
		this.reportType = reportType;
	}

	/*** 举报原因 */
	public String getReportReason() {
		return reportReason;
	}

	/*** 举报原因 */
	public void setReportReason(String reportReason) {
		this.reportReason = reportReason;
	}

	/*** 补充说明 */
	public String getReportComments() {
		return reportComments;
	}

	/*** 补充说明 */
	public void setReportComments(String reportComments) {
		this.reportComments = reportComments;
	}

	/*** 举报时间 */
	public Date getGmtReportFrom() {
		return gmtReportFrom;
	}

	/*** 举报时间 */
	public void setGmtReportFrom(Date gmtReportFrom) {
		this.gmtReportFrom = gmtReportFrom;
	}

	/*** 举报时间 */
	public Date getGmtReportTo() {
		return gmtReportTo;
	}

	/*** 举报时间 */
	public void setGmtReportTo(Date gmtReportTo) {
		this.gmtReportTo = gmtReportTo;
	}
}
