package com.ancun.xinhu.domain.query;

import java.io.Serializable;
import java.util.Date;

import com.ancun.products.core.query.AbstractQueryParam;
import com.ancun.products.core.query.QueryParam;

/**
 * 企业信息表查询对象
 */
public class EntInfoQuery extends AbstractQueryParam implements QueryParam,
		Serializable {
	private static final long serialVersionUID = 14334906328211L;

	private Integer[] idArray;// 企业ID
	private String mobile;// 手机号码
	private String pwd;// 密码
	private String orgType;// 组织机构类型
	private String industryId;// 行业性质
	private String orgCode;// 组织机构代码
	private String orgName;// 组织机构名称
	private String aliasName;// 别名
	private String artificialPerson;// 法人代表
	private String registrationno;// 登记号
	private Date registerDateFrom;// 注册日期
	private Date registerDateTo;// 注册日期
	private String validDateFrom;// 起始有效期
	private String validDateTo;// 到期有效期
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
	private Date gmtCreateFrom;// 创建时间
	private Date gmtCreateTo;// 创建时间
	private Date gmtModifyFrom;// 信息修改时间
	private Date gmtModifyTo;// 信息修改时间

	/*** 企业ID */
	public Integer[] getIdArray() {
		return idArray;
	}

	/*** 企业ID */
	public void setIdArray(Integer... idArray) {
		this.idArray = idArray;
	}

	/*** 手机号码 */
	public String getMobile() {
		return mobile;
	}

	/*** 手机号码 */
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	/*** 密码 */
	public String getPwd() {
		return pwd;
	}

	/*** 密码 */
	public void setPwd(String pwd) {
		this.pwd = pwd;
	}

	/*** 组织机构类型 */
	public String getOrgType() {
		return orgType;
	}

	/*** 组织机构类型 */
	public void setOrgType(String orgType) {
		this.orgType = orgType;
	}

	/*** 行业性质 */
	public String getIndustryId() {
		return industryId;
	}

	/*** 行业性质 */
	public void setIndustryId(String industryId) {
		this.industryId = industryId;
	}

	/*** 组织机构代码 */
	public String getOrgCode() {
		return orgCode;
	}

	/*** 组织机构代码 */
	public void setOrgCode(String orgCode) {
		this.orgCode = orgCode;
	}

	/*** 组织机构名称 */
	public String getOrgName() {
		return orgName;
	}

	/*** 组织机构名称 */
	public void setOrgName(String orgName) {
		this.orgName = orgName;
	}

	/*** 别名 */
	public String getAliasName() {
		return aliasName;
	}

	/*** 别名 */
	public void setAliasName(String aliasName) {
		this.aliasName = aliasName;
	}

	/*** 法人代表 */
	public String getArtificialPerson() {
		return artificialPerson;
	}

	/*** 法人代表 */
	public void setArtificialPerson(String artificialPerson) {
		this.artificialPerson = artificialPerson;
	}

	/*** 登记号 */
	public String getRegistrationno() {
		return registrationno;
	}

	/*** 登记号 */
	public void setRegistrationno(String registrationno) {
		this.registrationno = registrationno;
	}

	/*** 注册日期 */
	public Date getRegisterDateFrom() {
		return registerDateFrom;
	}

	/*** 注册日期 */
	public void setRegisterDateFrom(Date registerDateFrom) {
		this.registerDateFrom = registerDateFrom;
	}

	/*** 注册日期 */
	public Date getRegisterDateTo() {
		return registerDateTo;
	}

	/*** 注册日期 */
	public void setRegisterDateTo(Date registerDateTo) {
		this.registerDateTo = registerDateTo;
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

	/*** 地址 */
	public String getEntAddress() {
		return entAddress;
	}

	/*** 地址 */
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

	/*** 机构英文名称 */
	public String getOrgEname() {
		return orgEname;
	}

	/*** 机构英文名称 */
	public void setOrgEname(String orgEname) {
		this.orgEname = orgEname;
	}

	/*** 机构简介 */
	public String getOrgIntroduction() {
		return orgIntroduction;
	}

	/*** 机构简介 */
	public void setOrgIntroduction(String orgIntroduction) {
		this.orgIntroduction = orgIntroduction;
	}

	/*** 企业官网 */
	public String getOfficialWebsite() {
		return officialWebsite;
	}

	/*** 企业官网 */
	public void setOfficialWebsite(String officialWebsite) {
		this.officialWebsite = officialWebsite;
	}

	/*** 客服电话 */
	public String getPhone() {
		return phone;
	}

	/*** 客服电话 */
	public void setPhone(String phone) {
		this.phone = phone;
	}

	/*** 邮箱 */
	public String getEmail() {
		return email;
	}

	/*** 邮箱 */
	public void setEmail(String email) {
		this.email = email;
	}

	/*** 微信公众号 */
	public String getWeiXin() {
		return weiXin;
	}

	/*** 微信公众号 */
	public void setWeiXin(String weiXin) {
		this.weiXin = weiXin;
	}

	/*** 微信公众号二维码 */
	public String getWeiXinUrl() {
		return weiXinUrl;
	}

	/*** 微信公众号二维码 */
	public void setWeiXinUrl(String weiXinUrl) {
		this.weiXinUrl = weiXinUrl;
	}

	/*** 审核状态 */
	public Integer getCheckStatus() {
		return checkStatus;
	}

	/*** 审核状态 */
	public void setCheckStatus(Integer checkStatus) {
		this.checkStatus = checkStatus;
	}

	/*** 话费奖励金额 */
	public Integer getTelFareNum() {
		return telFareNum;
	}

	/*** 话费奖励金额 */
	public void setTelFareNum(Integer telFareNum) {
		this.telFareNum = telFareNum;
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

	/*** 信息修改时间 */
	public Date getGmtModifyFrom() {
		return gmtModifyFrom;
	}

	/*** 信息修改时间 */
	public void setGmtModifyFrom(Date gmtModifyFrom) {
		this.gmtModifyFrom = gmtModifyFrom;
	}

	/*** 信息修改时间 */
	public Date getGmtModifyTo() {
		return gmtModifyTo;
	}

	/*** 信息修改时间 */
	public void setGmtModifyTo(Date gmtModifyTo) {
		this.gmtModifyTo = gmtModifyTo;
	}
}
