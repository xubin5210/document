package com.ancun.xinhu.domain.model;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * 企业信息表实体类
 */
public class EntInfo implements Serializable {
	private static final long serialVersionUID = 14334906328212L;

	private Integer id;// 企业ID
	private String mobile;// 手机号码
	private String pwd;// 密码
	private String orgType;// 组织机构类型
	private String industryId;// 行业性质
	private String orgCode;// 组织机构代码
	private String orgName;// 组织机构名称
	private String aliasName;// 别名
	private String artificialPerson;// 法人代表
	private String registrationno;// 登记号
	private Date registerDate;// 注册日期
	private String validDateFrom;// 起始有效期
	private String validDateTo;// 起始有效期
	private String entAddress;// 地址
	private Integer certificationStatus;// 认证状态
	private String logoUrl;// 机构logoURL
	private String orgEname;// 机构英文名称
	private String orgIntroduction;// 机构简介
	private String officialWebsite;// 企业官网
	private String phone;// 客服电话
	private String email;// 邮箱
	private String weiXin;// 微信公众号
	private String weiXinUrl;// 微信公众号二维码
	private Integer checkStatus;// 审核状态
	private Integer telFareNum;// 话费奖励金额
	private Date gmtCreate;// 创建时间
	private Date gmtModify;// 信息修改时间
	private List<EntIndustryLink> industryList;//行业集合
	private List<EntPaper> paperList;//证书集合

	private String code;//验证码
	
	private Integer entStaffNum;//企业员工数
	private Integer entConnNum;//企业人脉数量
	List<CardInfoSnapshot> connList;//企业人脉列表
	private Integer entId;//企业id
	private Integer index;//位置
	
	private String entImage;//企业照片
	
	private boolean ifRemember;//是否记住密码
	
	private Integer x;
	private Integer y;
	private Integer w;
	private Integer h;
	
	
	
	
	public Integer getX() {
		return x;
	}

	public void setX(Integer x) {
		this.x = x;
	}

	public Integer getY() {
		return y;
	}

	public void setY(Integer y) {
		this.y = y;
	}

	public Integer getW() {
		return w;
	}

	public void setW(Integer w) {
		this.w = w;
	}

	public Integer getH() {
		return h;
	}

	public void setH(Integer h) {
		this.h = h;
	}

	public String getEntImage() {
		return entImage;
	}

	public void setEntImage(String entImage) {
		this.entImage = entImage;
	}

	public boolean isIfRemember() {
		return ifRemember;
	}

	public void setIfRemember(boolean ifRemember) {
		this.ifRemember = ifRemember;
	}

	public EntInfo() {
	}

	/**
	 *
	 * @param id
	 *            -- 企业ID
	 */
	public EntInfo(Integer id) {
		this.id = id;
	}

	/** 企业ID */
	public Integer getId() {
		return id;
	}

	/** 企业ID */
	public void setId(Integer id) {
		this.id = id;
	}

	/** 手机号码 */
	public String getMobile() {
		return mobile;
	}

	public Integer getIndex() {
		return index;
	}

	public void setIndex(Integer index) {
		this.index = index;
	}

	/** 手机号码 */
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	/** 密码 */
	public String getPwd() {
		return pwd;
	}

	/** 密码 */
	public void setPwd(String pwd) {
		this.pwd = pwd;
	}

	public List<EntIndustryLink> getIndustryList() {
		return industryList;
	}

	public void setIndustryList(List<EntIndustryLink> industryList) {
		this.industryList = industryList;
	}

	public List<EntPaper> getPaperList() {
		return paperList;
	}

	public void setPaperList(List<EntPaper> paperList) {
		this.paperList = paperList;
	}

	/** 组织机构类型 */
	public String getOrgType() {
		return orgType;
	}

	/** 组织机构类型 */
	public void setOrgType(String orgType) {
		this.orgType = orgType;
	}

	/** 行业性质 */
	public String getIndustryId() {
		return industryId;
	}

	/** 行业性质 */
	public void setIndustryId(String industryId) {
		this.industryId = industryId;
	}

	/** 组织机构代码 */
	public String getOrgCode() {
		return orgCode;
	}

	/** 组织机构代码 */
	public void setOrgCode(String orgCode) {
		this.orgCode = orgCode;
	}

	/** 组织机构名称 */
	public String getOrgName() {
		return orgName;
	}

	public String getValidDateFrom() {
		return validDateFrom;
	}

	public void setValidDateFrom(String validDateFrom) {
		this.validDateFrom = validDateFrom;
	}

	public String getValidDateTo() {
		return validDateTo;
	}

	public void setValidDateTo(String validDateTo) {
		this.validDateTo = validDateTo;
	}

	/** 组织机构名称 */
	public void setOrgName(String orgName) {
		this.orgName = orgName;
	}

	/** 别名 */
	public String getAliasName() {
		return aliasName;
	}

	/** 别名 */
	public void setAliasName(String aliasName) {
		this.aliasName = aliasName;
	}

	/** 法人代表 */
	public String getArtificialPerson() {
		return artificialPerson;
	}

	/** 法人代表 */
	public void setArtificialPerson(String artificialPerson) {
		this.artificialPerson = artificialPerson;
	}

	/** 登记号 */
	public String getRegistrationno() {
		return registrationno;
	}

	/** 登记号 */
	public void setRegistrationno(String registrationno) {
		this.registrationno = registrationno;
	}

	/** 注册日期 */
	public Date getRegisterDate() {
		return registerDate;
	}

	/** 注册日期 */
	public void setRegisterDate(Date registerDate) {
		this.registerDate = registerDate;
	}


	/** 地址 */
	public String getEntAddress() {
		return entAddress;
	}

	/** 地址 */
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

	/** 机构英文名称 */
	public String getOrgEname() {
		return orgEname;
	}

	/** 机构英文名称 */
	public void setOrgEname(String orgEname) {
		this.orgEname = orgEname;
	}

	/** 机构简介 */
	public String getOrgIntroduction() {
		return orgIntroduction;
	}

	/** 机构简介 */
	public void setOrgIntroduction(String orgIntroduction) {
		this.orgIntroduction = orgIntroduction;
	}

	/** 企业官网 */
	public String getOfficialWebsite() {
		return officialWebsite;
	}

	/** 企业官网 */
	public void setOfficialWebsite(String officialWebsite) {
		this.officialWebsite = officialWebsite;
	}

	/** 客服电话 */
	public String getPhone() {
		return phone;
	}

	/** 客服电话 */
	public void setPhone(String phone) {
		this.phone = phone;
	}

	/** 邮箱 */
	public String getEmail() {
		return email;
	}

	/** 邮箱 */
	public void setEmail(String email) {
		this.email = email;
	}

	/** 微信公众号 */
	public String getWeiXin() {
		return weiXin;
	}

	/** 微信公众号 */
	public void setWeiXin(String weiXin) {
		this.weiXin = weiXin;
	}

	/** 微信公众号二维码 */
	public String getWeiXinUrl() {
		return weiXinUrl;
	}

	/** 微信公众号二维码 */
	public void setWeiXinUrl(String weiXinUrl) {
		this.weiXinUrl = weiXinUrl;
	}

	/** 审核状态 */
	public Integer getCheckStatus() {
		return checkStatus;
	}

	/** 审核状态 */
	public void setCheckStatus(Integer checkStatus) {
		this.checkStatus = checkStatus;
	}

	/** 话费奖励金额 */
	public Integer getTelFareNum() {
		return telFareNum;
	}

	/** 话费奖励金额 */
	public void setTelFareNum(Integer telFareNum) {
		this.telFareNum = telFareNum;
	}

	/** 创建时间 */
	public Date getGmtCreate() {
		return gmtCreate;
	}

	/** 创建时间 */
	public void setGmtCreate(Date gmtCreate) {
		this.gmtCreate = gmtCreate;
	}

	/** 信息修改时间 */
	public Date getGmtModify() {
		return gmtModify;
	}

	/** 信息修改时间 */
	public void setGmtModify(Date gmtModify) {
		this.gmtModify = gmtModify;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}


	public Integer getEntStaffNum() {
		return entStaffNum;
	}

	public void setEntStaffNum(Integer entStaffNum) {
		this.entStaffNum = entStaffNum;
	}

	public List<CardInfoSnapshot> getConnList() {
		return connList;
	}

	public void setConnList(List<CardInfoSnapshot> connList) {
		this.connList = connList;
	}

	public Integer getEntId() {
		return entId;
	}

	public void setEntId(Integer entId) {
		this.entId = entId;
	}

	public Integer getEntConnNum() {
		return entConnNum;
	}

	public void setEntConnNum(Integer entConnNum) {
		this.entConnNum = entConnNum;
	}
	
	
}
