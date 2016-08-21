package com.ancun.xinhu.domain.model;

import java.io.Serializable;
import java.util.Date;

/**
 * 系统公告表实体类
 */
public class SysNotice implements Serializable {
	private static final long serialVersionUID = 14345343836542L;

	private Integer id;// 公告id
	private String title;// 公告名称
	private String content;// 公告内容
	private Date gmtCreate;// 创建时间
	private Integer isFirst;// 是否置顶
	private Integer entId;//公司ID
	
	
	

	public Integer getEntId() {
		return entId;
	}

	public void setEntId(Integer entId) {
		this.entId = entId;
	}

	public SysNotice() {
	}

	/**
	 *
	 * @param id
	 *            -- 公告id
	 */
	public SysNotice(Integer id) {
		this.id = id;
	}

	/** 公告id */
	public Integer getId() {
		return id;
	}

	/** 公告id */
	public void setId(Integer id) {
		this.id = id;
	}

	/** 公告名称 */
	public String getTitle() {
		return title;
	}

	/** 公告名称 */
	public void setTitle(String title) {
		this.title = title;
	}

	/** 公告内容 */
	public String getContent() {
		return content;
	}

	/** 公告内容 */
	public void setContent(String content) {
		this.content = content;
	}

	/** 创建时间 */
	public Date getGmtCreate() {
		return gmtCreate;
	}

	/** 创建时间 */
	public void setGmtCreate(Date gmtCreate) {
		this.gmtCreate = gmtCreate;
	}

	/** 是否置顶 */
	public Integer getIsFirst() {
		return isFirst;
	}

	/** 是否置顶 */
	public void setIsFirst(Integer isFirst) {
		this.isFirst = isFirst;
	}

	@Override
	public String toString() {
		return "SysNotice [ id=" + id + ", title=" + title + ", content="
				+ content + ", gmtCreate=" + gmtCreate + ", isFirst=" + isFirst
				+ "]";
	}
}
