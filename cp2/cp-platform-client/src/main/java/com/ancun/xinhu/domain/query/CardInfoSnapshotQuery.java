package com.ancun.xinhu.domain.query;

import java.io.Serializable;
import java.util.Date;

import com.ancun.products.core.query.AbstractQueryParam;
import com.ancun.products.core.query.QueryParam;

/**
 * 安信_名片信息快照表查询对象
 */
public class CardInfoSnapshotQuery extends AbstractQueryParam implements
		QueryParam, Serializable {
	private static final long serialVersionUID = 14330344795031L;

	private Integer id;//快照id
	private Integer[] idArray;// 快照id
	private Integer cardId;// 名片id
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
	private Date gmtCreateCardFrom;// 创建时间名片
	private Date gmtCreateCardTo;// 创建时间名片
	private Integer entId;// 企业id
	private String orgName;// 组织机构名称
	private String orgEname;// 机构英文名称
	private String officialWebsite;// 企业官网
	private String entAddress;// 企业地址
	private Integer certificationStatus;// 认证状态
	private String logoUrl;// 机构logoURL
	private Integer entActiveStatus;// 在职状态
	private Date snapshotDateFrom;// 快照时间
	private Date snapshotDateTo;// 快照时间
	
	private String isExchange;// 是否交换
	
	private String searchType;//搜索类型
	private String searchTime;//查询时间

	/*** 快照id */
	public Integer[] getIdArray() {
		return idArray;
	}

	/*** 快照id */
	public void setIdArray(Integer... idArray) {
		this.idArray = idArray;
	}

	/*** 用户id */
	public Integer getCardId() {
		return cardId;
	}

	/*** 用户id */
	public void setCardId(Integer cardId) {
		this.cardId = cardId;
	}

	/*** 用户id */
	public Integer getUserId() {
		return userId;
	}

	/*** 用户id */
	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	/*** 名片类型(0:官方,1:普通) */
	public Integer getCardType() {
		return cardType;
	}

	/*** 名片类型(0:官方,1:普通) */
	public void setCardType(Integer cardType) {
		this.cardType = cardType;
	}

	/*** 手机号 */
	public String getMobile() {
		return mobile;
	}

	/*** 手机号 */
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	/*** 激活状态 */
	public Integer getActivationStatus() {
		return activationStatus;
	}

	/*** 激活状态 */
	public void setActivationStatus(Integer activationStatus) {
		this.activationStatus = activationStatus;
	}

	/*** 身份证号 */
	public String getUserIdcard() {
		return userIdcard;
	}

	/*** 身份证号 */
	public void setUserIdcard(String userIdcard) {
		this.userIdcard = userIdcard;
	}

	/*** 姓名 */
	public String getTrueName() {
		return trueName;
	}

	/*** 姓名 */
	public void setTrueName(String trueName) {
		this.trueName = trueName;
	}

	/*** 昵称 */
	public String getNickName() {
		return nickName;
	}

	/*** 昵称 */
	public void setNickName(String nickName) {
		this.nickName = nickName;
	}

	/*** 性别 */
	public String getSex() {
		return sex;
	}

	/*** 性别 */
	public void setSex(String sex) {
		this.sex = sex;
	}

	/*** 头像url */
	public String getIconUrl() {
		return iconUrl;
	}

	/*** 头像url */
	public void setIconUrl(String iconUrl) {
		this.iconUrl = iconUrl;
	}

	/*** 座右铭 */
	public String getMotto() {
		return motto;
	}

	/*** 座右铭 */
	public void setMotto(String motto) {
		this.motto = motto;
	}

	/*** 英文名称 */
	public String getEname() {
		return ename;
	}

	/*** 英文名称 */
	public void setEname(String ename) {
		this.ename = ename;
	}

	/*** 座机 */
	public String getPhone() {
		return phone;
	}

	/*** 座机 */
	public void setPhone(String phone) {
		this.phone = phone;
	}

	/*** 短号 */
	public String getPhoneShort() {
		return phoneShort;
	}

	/*** 短号 */
	public void setPhoneShort(String phoneShort) {
		this.phoneShort = phoneShort;
	}

	/*** 传真 */
	public String getFax() {
		return fax;
	}

	/*** 传真 */
	public void setFax(String fax) {
		this.fax = fax;
	}

	/*** QQ号码 */
	public String getQq() {
		return qq;
	}

	/*** QQ号码 */
	public void setQq(String qq) {
		this.qq = qq;
	}

	/*** 微信号码 */
	public String getWeiXin() {
		return weiXin;
	}

	/*** 微信号码 */
	public void setWeiXin(String weiXin) {
		this.weiXin = weiXin;
	}

	/*** 邮箱 */
	public String getEmail() {
		return email;
	}

	/*** 邮箱 */
	public void setEmail(String email) {
		this.email = email;
	}

	/*** 邮箱2 */
	public String getEmail2() {
		return email2;
	}

	/*** 邮箱2 */
	public void setEmail2(String email2) {
		this.email2 = email2;
	}

	/*** 二维码url */
	public String getQrcodeUrl() {
		return qrcodeUrl;
	}

	/*** 二维码url */
	public void setQrcodeUrl(String qrcodeUrl) {
		this.qrcodeUrl = qrcodeUrl;
	}

	/*** 搜索控制开关 */
	public String getSearchControlSwitch() {
		return searchControlSwitch;
	}

	/*** 搜索控制开关 */
	public void setSearchControlSwitch(String searchControlSwitch) {
		this.searchControlSwitch = searchControlSwitch;
	}

	/*** 创建时间名片 */
	public Date getGmtCreateCardFrom() {
		return gmtCreateCardFrom;
	}

	/*** 创建时间名片 */
	public void setGmtCreateCardFrom(Date gmtCreateCardFrom) {
		this.gmtCreateCardFrom = gmtCreateCardFrom;
	}

	/*** 创建时间名片 */
	public Date getGmtCreateCardTo() {
		return gmtCreateCardTo;
	}

	/*** 创建时间名片 */
	public void setGmtCreateCardTo(Date gmtCreateCardTo) {
		this.gmtCreateCardTo = gmtCreateCardTo;
	}

	/*** 企业id */
	public Integer getEntId() {
		return entId;
	}

	/*** 企业id */
	public void setEntId(Integer entId) {
		this.entId = entId;
	}

	/*** 组织机构名称 */
	public String getOrgName() {
		return orgName;
	}

	/*** 组织机构名称 */
	public void setOrgName(String orgName) {
		this.orgName = orgName;
	}

	/*** 机构英文名称 */
	public String getOrgEname() {
		return orgEname;
	}

	/*** 机构英文名称 */
	public void setOrgEname(String orgEname) {
		this.orgEname = orgEname;
	}

	/*** 企业官网 */
	public String getOfficialWebsite() {
		return officialWebsite;
	}

	/*** 企业官网 */
	public void setOfficialWebsite(String officialWebsite) {
		this.officialWebsite = officialWebsite;
	}

	/*** 企业地址 */
	public String getEntAddress() {
		return entAddress;
	}

	/*** 企业地址 */
	public void setEntAddress(String entAddress) {
		this.entAddress = entAddress;
	}

	/*** 认证状态 */
	public Integer getCertificationStatus() {
		return certificationStatus;
	}

	/*** 认证状态 */
	public void setCertificationStatus(Integer certificationStatus) {
		this.certificationStatus = certificationStatus;
	}

	/*** 机构logoURL */
	public String getLogoUrl() {
		return logoUrl;
	}

	/*** 机构logoURL */
	public void setLogoUrl(String logoUrl) {
		this.logoUrl = logoUrl;
	}

	/*** 在职状态 */
	public Integer getEntActiveStatus() {
		return entActiveStatus;
	}

	/*** 在职状态 */
	public void setEntActiveStatus(Integer entActiveStatus) {
		this.entActiveStatus = entActiveStatus;
	}

	/*** 快照时间 */
	public Date getSnapshotDateFrom() {
		return snapshotDateFrom;
	}

	/*** 快照时间 */
	public void setSnapshotDateFrom(Date snapshotDateFrom) {
		this.snapshotDateFrom = snapshotDateFrom;
	}

	/*** 快照时间 */
	public Date getSnapshotDateTo() {
		return snapshotDateTo;
	}

	/*** 快照时间 */
	public void setSnapshotDateTo(Date snapshotDateTo) {
		this.snapshotDateTo = snapshotDateTo;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getSearchType() {
		return searchType;
	}

	public void setSearchType(String searchType) {
		this.searchType = searchType;
	}

	public String getSearchTime() {
		return searchTime;
	}

	public void setSearchTime(String searchTime) {
		this.searchTime = searchTime;
	}

	public String getIsExchange() {
		return isExchange;
	}

	public void setIsExchange(String isExchange) {
		this.isExchange = isExchange;
	}
	
}
