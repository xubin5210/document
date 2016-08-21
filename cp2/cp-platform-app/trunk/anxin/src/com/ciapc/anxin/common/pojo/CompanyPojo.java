package com.ciapc.anxin.common.pojo;

import java.util.ArrayList;
import java.util.List;

/**
 * @author zhuwt
 * @Description:绑定企业
 * @ClassName: CompanyPojo.java
 * @date 2015年6月10日 下午3:26:21
 * @说明 代码版权归 杭州反盗版中心有限公司 所有
 */

public class CompanyPojo {

	private String entId;

	private String logoUrl;

	// 企业名称
	private String orgName;

	private String orgEname;

	// "企业认证状态"
	private String certificationStatus;

	// 组织机构代码
	private String orgCode;

	// 法人代表
	private String artificialPerson;
	// 机构登记号
	private String registrationno;
	// 地址
	private String entAddress;
	// 注册期
	private String registerDate;
	// 有效期
	private String validDateFrom;

	// 有效期
	private String validDateTo;
	// 企业简介
	private String orgIntroduction;
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

	// 企业官网
	private String officialWebsite;
	// 客服电话
	private String phone;
	// 企业邮箱
	private String email;
	// 微信公众号
	private String weiXin;
	// 微信公众号二维码url
	private String weiXinUrl;
	// 企业人脉数量
	private String entConnNum;

	private List<JobPojo> connList = new ArrayList<JobPojo>();
	// 企业人脉数量
	private String connLists;

	private String orgTypeName;

	private String entStaffNum;

	public String getArtificialPerson() {
		return artificialPerson;
	}

	public void setArtificialPerson(String artificialPerson) {
		this.artificialPerson = artificialPerson;
	}

	public String getEntAddress() {
		return entAddress;
	}

	public void setEntAddress(String entAddress) {
		this.entAddress = entAddress;
	}

	public String getRegisterDate() {
		return registerDate;
	}

	public void setRegisterDate(String registerDate) {
		this.registerDate = registerDate;
	}

	public String getOrgIntroduction() {
		return orgIntroduction;
	}

	public void setOrgIntroduction(String orgIntroduction) {
		this.orgIntroduction = orgIntroduction;
	}

	public String getWeiXin() {
		return weiXin;
	}

	public void setWeiXin(String weiXin) {
		this.weiXin = weiXin;
	}

	public String getCertificationStatus() {
		return certificationStatus;
	}

	public void setCertificationStatus(String certificationStatus) {
		this.certificationStatus = certificationStatus;
	}

	public String getOrgCode() {
		return orgCode;
	}

	public void setOrgCode(String orgCode) {
		this.orgCode = orgCode;
	}

	public String getRegistrationno() {
		return registrationno;
	}

	public void setRegistrationno(String registrationno) {
		this.registrationno = registrationno;
	}

	public String getOfficialWebsite() {
		return officialWebsite;
	}

	public void setOfficialWebsite(String officialWebsite) {
		this.officialWebsite = officialWebsite;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getWeiXinUrl() {
		return weiXinUrl;
	}

	public void setWeiXinUrl(String weiXinUrl) {
		this.weiXinUrl = weiXinUrl;
	}

	public String getEntConnNum() {
		return entConnNum;
	}

	public void setEntConnNum(String entConnNum) {
		this.entConnNum = entConnNum;
	}

	public List<JobPojo> getConnList() {
		return connList;
	}

	public void setConnList(List<JobPojo> connList) {
		this.connList = connList;
	}

	public String getConnLists() {
		return connLists;
	}

	public void setConnLists(String connLists) {
		this.connLists = connLists;
	}

	public String getOrgTypeName() {
		return orgTypeName;
	}

	public void setOrgTypeName(String orgTypeName) {
		this.orgTypeName = orgTypeName;
	}

	public String getEntId() {
		return entId;
	}

	public void setEntId(String entId) {
		this.entId = entId;
	}

	public String getLogoUrl() {
		return logoUrl;
	}

	public void setLogoUrl(String logoUrl) {
		this.logoUrl = logoUrl;
	}

	public String getOrgName() {
		return orgName;
	}

	public void setOrgName(String orgName) {
		this.orgName = orgName;
	}

	public String getEntStaffNum() {
		return entStaffNum;
	}

	public void setEntStaffNum(String entStaffNum) {
		this.entStaffNum = entStaffNum;
	}

	public String getOrgEname() {
		return orgEname;
	}

	public void setOrgEname(String orgEname) {
		this.orgEname = orgEname;
	}

}
