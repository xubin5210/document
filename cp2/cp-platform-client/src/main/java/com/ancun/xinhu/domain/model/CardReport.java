package com.ancun.xinhu.domain.model;

import java.io.Serializable;
import java.util.Date;

/**
 * 举报信息表实体类
 */
public class CardReport implements Serializable {
	private static final long serialVersionUID = 14346057590372L;

	private Integer id;// 举报id
	private Integer reportUseridFrom;// 举报人
	private Integer reportUserid;// 举报对象
	private String reportType;// 举报对象类型(1:个人,2:企业)
	private String reportReason;// 举报原因
	private String reportComments;// 补充说明
	private Date gmtReport;// 举报时间

	public CardReport() {
	}

	/**
	 *
	 * @param id
	 *            -- 举报id
	 */
	public CardReport(Integer id) {
		this.id = id;
	}

	/** 举报id */
	public Integer getId() {
		return id;
	}

	/** 举报id */
	public void setId(Integer id) {
		this.id = id;
	}

	/** 举报人 */
	public Integer getReportUseridFrom() {
		return reportUseridFrom;
	}

	/** 举报人 */
	public void setReportUseridFrom(Integer reportUseridFrom) {
		this.reportUseridFrom = reportUseridFrom;
	}

	/** 举报对象 */
	public Integer getReportUserid() {
		return reportUserid;
	}

	/** 举报对象 */
	public void setReportUserid(Integer reportUserid) {
		this.reportUserid = reportUserid;
	}

	/** 举报对象类型(1:个人,2:企业) */
	public String getReportType() {
		return reportType;
	}

	/** 举报对象类型(1:个人,2:企业) */
	public void setReportType(String reportType) {
		this.reportType = reportType;
	}

	/** 举报原因 */
	public String getReportReason() {
		return reportReason;
	}

	/** 举报原因 */
	public void setReportReason(String reportReason) {
		this.reportReason = reportReason;
	}

	/** 补充说明 */
	public String getReportComments() {
		return reportComments;
	}

	/** 补充说明 */
	public void setReportComments(String reportComments) {
		this.reportComments = reportComments;
	}

	/** 举报时间 */
	public Date getGmtReport() {
		return gmtReport;
	}

	/** 举报时间 */
	public void setGmtReport(Date gmtReport) {
		this.gmtReport = gmtReport;
	}

	@Override
	public String toString() {
		return "CardReport [ id=" + id + ", reportUseridFrom="
				+ reportUseridFrom + ", reportUserid=" + reportUserid
				+ ", reportType=" + reportType + ", reportReason="
				+ reportReason + ", reportComments=" + reportComments
				+ ", gmtReport=" + gmtReport + "]";
	}
}
