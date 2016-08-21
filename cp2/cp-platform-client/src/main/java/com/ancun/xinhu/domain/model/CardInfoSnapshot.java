package com.ancun.xinhu.domain.model;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * 安信_名片信息快照表实体类
 */
public class CardInfoSnapshot implements Serializable {
	private static final long serialVersionUID = 14330344795032L;

	private Integer id;// 快照id
	private Integer cardId;// 用户id
	private Integer userId;// 用户id
	private Integer cardType;// 名片类型(0:官方,1:普通)
	private String mobile;// 手机号
	private Integer activationStatus;// 激活状态
	private String userIdcard;// 身份证号
	private String trueName;// 姓名
	private String nickName;// 昵称
	private String sex;// 性别
	private String iconUrl;// 头像url
	private String motto;// 座右铭
	private String ename;// 英文名称
	private String phone;// 座机
	private String phoneShort;// 短号
	private String fax;// 传真
	private String qq;// QQ号码
	private String weiXin;// 微信号码
	private String email;// 邮箱
	private String email2;// 邮箱2
	private String qrcodeUrl;// 二维码url
	private String searchControlSwitch;// 搜索控制开关
	private Date gmtCreateCard;// 创建时间名片
	private Integer entId;// 企业id
	private String orgName;// 组织机构名称
	private String orgEname;// 机构英文名称
	private String officialWebsite;// 企业官网
	private String entAddress;// 企业地址
	private Integer certificationStatus;// 认证状态
	private String logoUrl;// 机构logoURL
	private Integer entActiveStatus;// 在职状态
	private Date snapshotDate;// 快照时间
	
	private Date entEntryDate;//入职时间
	private Date gmtModifyCard;//信息修改时间
	
	private List<EntPosition> positionList;//职位列表
	private List<EntDepartment> departmentList;//部门列表
	private String positionName;//职位名称
	
	private String isExchange;// 是否交换
	private String applyType;//申请类型
	private String applyNote;//申请说明
	private String tnFirstSpell;//真实姓名汉字首字母拼音
	private Date gmtDynamicLastRead;// 动态最后读取时间
	private Integer applyId;//名片申请记录id
	private Date gmtApply;//名片申请时间
	private Date gmtExchange;// 交换时间

	public CardInfoSnapshot() {
	}

	/**
	 *
	 * @param id
	 *            -- 快照id
	 */
	public CardInfoSnapshot(Integer id) {
		this.id = id;
	}

	/** 快照id */
	public Integer getId() {
		return id;
	}

	/** 快照id */
	public void setId(Integer id) {
		this.id = id;
	}

	/** 用户id */
	public Integer getCardId() {
		return cardId;
	}

	/** 用户id */
	public void setCardId(Integer cardId) {
		this.cardId = cardId;
	}

	/** 用户id */
	public Integer getUserId() {
		return userId;
	}

	/** 用户id */
	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	/** 名片类型(0:官方,1:普通) */
	public Integer getCardType() {
		return cardType;
	}

	/** 名片类型(0:官方,1:普通) */
	public void setCardType(Integer cardType) {
		this.cardType = cardType;
	}

	/** 手机号 */
	public String getMobile() {
		return mobile;
	}

	/** 手机号 */
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	/** 激活状态 */
	public Integer getActivationStatus() {
		return activationStatus;
	}

	/** 激活状态 */
	public void setActivationStatus(Integer activationStatus) {
		this.activationStatus = activationStatus;
	}

	/** 身份证号 */
	public String getUserIdcard() {
		return userIdcard;
	}

	/** 身份证号 */
	public void setUserIdcard(String userIdcard) {
		this.userIdcard = userIdcard;
	}

	/** 姓名 */
	public String getTrueName() {
		return trueName;
	}

	/** 姓名 */
	public void setTrueName(String trueName) {
		this.trueName = trueName;
	}

	/** 昵称 */
	public String getNickName() {
		return nickName;
	}

	/** 昵称 */
	public void setNickName(String nickName) {
		this.nickName = nickName;
	}

	/** 性别 */
	public String getSex() {
		return sex;
	}

	/** 性别 */
	public void setSex(String sex) {
		this.sex = sex;
	}

	/** 头像url */
	public String getIconUrl() {
		return iconUrl;
	}

	/** 头像url */
	public void setIconUrl(String iconUrl) {
		this.iconUrl = iconUrl;
	}

	/** 座右铭 */
	public String getMotto() {
		return motto;
	}

	/** 座右铭 */
	public void setMotto(String motto) {
		this.motto = motto;
	}

	/** 英文名称 */
	public String getEname() {
		return ename;
	}

	/** 英文名称 */
	public void setEname(String ename) {
		this.ename = ename;
	}

	/** 座机 */
	public String getPhone() {
		return phone;
	}

	/** 座机 */
	public void setPhone(String phone) {
		this.phone = phone;
	}

	/** 短号 */
	public String getPhoneShort() {
		return phoneShort;
	}

	/** 短号 */
	public void setPhoneShort(String phoneShort) {
		this.phoneShort = phoneShort;
	}

	/** 传真 */
	public String getFax() {
		return fax;
	}

	/** 传真 */
	public void setFax(String fax) {
		this.fax = fax;
	}

	/** QQ号码 */
	public String getQq() {
		return qq;
	}

	/** QQ号码 */
	public void setQq(String qq) {
		this.qq = qq;
	}

	/** 微信号码 */
	public String getWeiXin() {
		return weiXin;
	}

	/** 微信号码 */
	public void setWeiXin(String weiXin) {
		this.weiXin = weiXin;
	}

	/** 邮箱 */
	public String getEmail() {
		return email;
	}

	/** 邮箱 */
	public void setEmail(String email) {
		this.email = email;
	}

	/** 邮箱2 */
	public String getEmail2() {
		return email2;
	}

	/** 邮箱2 */
	public void setEmail2(String email2) {
		this.email2 = email2;
	}

	/** 二维码url */
	public String getQrcodeUrl() {
		return qrcodeUrl;
	}

	/** 二维码url */
	public void setQrcodeUrl(String qrcodeUrl) {
		this.qrcodeUrl = qrcodeUrl;
	}

	/** 搜索控制开关 */
	public String getSearchControlSwitch() {
		return searchControlSwitch;
	}

	/** 搜索控制开关 */
	public void setSearchControlSwitch(String searchControlSwitch) {
		this.searchControlSwitch = searchControlSwitch;
	}

	/** 创建时间名片 */
	public Date getGmtCreateCard() {
		return gmtCreateCard;
	}

	/** 创建时间名片 */
	public void setGmtCreateCard(Date gmtCreateCard) {
		this.gmtCreateCard = gmtCreateCard;
	}

	/** 企业id */
	public Integer getEntId() {
		return entId;
	}

	/** 企业id */
	public void setEntId(Integer entId) {
		this.entId = entId;
	}

	/** 组织机构名称 */
	public String getOrgName() {
		return orgName;
	}

	/** 组织机构名称 */
	public void setOrgName(String orgName) {
		this.orgName = orgName;
	}

	/** 机构英文名称 */
	public String getOrgEname() {
		return orgEname;
	}

	/** 机构英文名称 */
	public void setOrgEname(String orgEname) {
		this.orgEname = orgEname;
	}

	/** 企业官网 */
	public String getOfficialWebsite() {
		return officialWebsite;
	}

	/** 企业官网 */
	public void setOfficialWebsite(String officialWebsite) {
		this.officialWebsite = officialWebsite;
	}

	/** 企业地址 */
	public String getEntAddress() {
		return entAddress;
	}

	/** 企业地址 */
	public void setEntAddress(String entAddress) {
		this.entAddress = entAddress;
	}

	/** 认证状态 */
	public Integer getCertificationStatus() {
		return certificationStatus;
	}

	/** 认证状态 */
	public void setCertificationStatus(Integer certificationStatus) {
		this.certificationStatus = certificationStatus;
	}

	/** 机构logoURL */
	public String getLogoUrl() {
		return logoUrl;
	}

	/** 机构logoURL */
	public void setLogoUrl(String logoUrl) {
		this.logoUrl = logoUrl;
	}

	/** 在职状态 */
	public Integer getEntActiveStatus() {
		return entActiveStatus;
	}

	/** 在职状态 */
	public void setEntActiveStatus(Integer entActiveStatus) {
		this.entActiveStatus = entActiveStatus;
	}

	/** 快照时间 */
	public Date getSnapshotDate() {
		return snapshotDate;
	}

	/** 快照时间 */
	public void setSnapshotDate(Date snapshotDate) {
		this.snapshotDate = snapshotDate;
	}

	@Override
	public String toString() {
		return "CardInfoSnapshot [ id=" + id + ", cardId=" + cardId
				+ ", userId=" + userId + ", cardType=" + cardType + ", mobile="
				+ mobile + ", activationStatus=" + activationStatus
				+ ", userIdcard=" + userIdcard + ", trueName=" + trueName
				+ ", nickName=" + nickName + ", sex=" + sex + ", iconUrl="
				+ iconUrl + ", motto=" + motto + ", ename=" + ename
				+ ", phone=" + phone + ", phoneShort=" + phoneShort + ", fax="
				+ fax + ", qq=" + qq + ", weiXin=" + weiXin + ", email="
				+ email + ", email2=" + email2 + ", qrcodeUrl=" + qrcodeUrl
				+ ", searchControlSwitch=" + searchControlSwitch
				+ ", gmtCreateCard=" + gmtCreateCard + ", entId=" + entId
				+ ", orgName=" + orgName + ", orgEname=" + orgEname
				+ ", officialWebsite=" + officialWebsite + ", entAddress="
				+ entAddress + ", certificationStatus=" + certificationStatus
				+ ", logoUrl=" + logoUrl + ", entActiveStatus="
				+ entActiveStatus + ", snapshotDate=" + snapshotDate + "]";
	}

	public Date getEntEntryDate() {
		return entEntryDate;
	}

	public void setEntEntryDate(Date entEntryDate) {
		this.entEntryDate = entEntryDate;
	}

	public Date getGmtModifyCard() {
		return gmtModifyCard;
	}

	public void setGmtModifyCard(Date gmtModifyCard) {
		this.gmtModifyCard = gmtModifyCard;
	}

	public List<EntPosition> getPositionList() {
		return positionList;
	}

	public void setPositionList(List<EntPosition> positionList) {
		this.positionList = positionList;
	}

	public List<EntDepartment> getDepartmentList() {
		return departmentList;
	}

	public void setDepartmentList(List<EntDepartment> departmentList) {
		this.departmentList = departmentList;
	}

	public String getPositionName() {
		return positionName;
	}

	public void setPositionName(String positionName) {
		this.positionName = positionName;
	}

	public String getIsExchange() {
		return isExchange;
	}

	public void setIsExchange(String isExchange) {
		this.isExchange = isExchange;
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

	public String getTnFirstSpell() {
		return tnFirstSpell;
	}

	public void setTnFirstSpell(String tnFirstSpell) {
		this.tnFirstSpell = tnFirstSpell;
	}

	public Integer getApplyId() {
		return applyId;
	}

	public void setApplyId(Integer applyId) {
		this.applyId = applyId;
	}

	public Date getGmtApply() {
		return gmtApply;
	}

	public void setGmtApply(Date gmtApply) {
		this.gmtApply = gmtApply;
	}

	public Date getGmtExchange() {
		return gmtExchange;
	}

	public void setGmtExchange(Date gmtExchange) {
		this.gmtExchange = gmtExchange;
	}

	public Date getGmtDynamicLastRead() {
		return gmtDynamicLastRead;
	}

	public void setGmtDynamicLastRead(Date gmtDynamicLastRead) {
		this.gmtDynamicLastRead = gmtDynamicLastRead;
	}
	
	
}
