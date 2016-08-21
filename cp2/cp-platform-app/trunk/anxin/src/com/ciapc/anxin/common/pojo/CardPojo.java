package com.ciapc.anxin.common.pojo;

import java.util.ArrayList;
import java.util.List;

public class CardPojo extends Object {

	private Integer id;

	// 名片ID
	private String cardId;
	
	//企业ID
	private String entId;

	// 头像地址
	private String iconUrl;

	// 真实名字
	private String trueName;

	// 英文名字
	private String ename;

	// 名片号
	private String userIdcard;

	// 昵称
	private String nickName;

	// 在职状态 0 在职 
	private String activationStatus;

	// 二维码Url
	private String qrcodeUr;

	// 企业认证状态
	private String certificationStatus;

	// 企业名称
	private String orgName;

	// 企业英文名称
	private String orgEname;

	// 企业官网
	private String officialWebsite;

	// 企业地址
	private String address;

	// 座右铭
	private String motto;

	// 职位列表
	private List<JobPojo> positionList = new ArrayList<JobPojo>();
	
	private List<JobPojo> departmentList = new ArrayList<JobPojo>();

	// 手机号
	private String mobile;

	// 座机
	private String phone;

	// 传真
	private String fax;

	// 邮箱
	private String email;

	private String email2;

	private String qq;

	private String weiXin;
	
	//职位 部门
	private String jobs;
	private String deptNames;
	
	//短号
	private String phoneShort;
	
	private String entActiveStatus;
	
	public String getActivationStatus() {
		return activationStatus;
	}

	public void setActivationStatus(String activationStatus) {
		this.activationStatus = activationStatus;
	}

	public String getPhoneShort() {
		return phoneShort;
	}

	public void setPhoneShort(String phoneShort) {
		this.phoneShort = phoneShort;
	}

	public String getJobs() {
		if(null != getPositionList()){
			StringBuilder sb = new StringBuilder();
			if(positionList.size() > 0){
				for(JobPojo jobPojo : positionList){
					sb.append(jobPojo.getPositionName()+",");
				}
				jobs = sb.toString().subSequence(0, sb.length()-1).toString();
				return jobs;
			}
		}
		return jobs;
	}
	
	public String getEntId() {
		return entId;
	}

	public void setEntId(String entId) {
		this.entId = entId;
	}

	public void setJobs(String jobs) {
		this.jobs = jobs;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getCardId() {
		return cardId;
	}

	public void setCardId(String cardId) {
		this.cardId = cardId;
	}

	public String getIconUrl() {
		return iconUrl;
	}

	public void setIconUrl(String iconUrl) {
		this.iconUrl = iconUrl;
	}

	public String getTrueName() {
		return trueName;
	}

	public void setTrueName(String trueName) {
		this.trueName = trueName;
	}

	public String getEname() {
		return ename;
	}

	public void setEname(String ename) {
		this.ename = ename;
	}

	public String getUserIdcard() {
		return userIdcard;
	}

	public void setUserIdcard(String userIdcard) {
		this.userIdcard = userIdcard;
	}

	public String getNickName() {
		return nickName;
	}

	public void setNickName(String nickName) {
		this.nickName = nickName;
	}

	public String getEntActiveStatus() {
		return entActiveStatus;
	}

	public void setEntActiveStatus(String entActiveStatus) {
		this.entActiveStatus = entActiveStatus;
	}

	public String getQrcodeUr() {
		return qrcodeUr;
	}

	public void setQrcodeUr(String qrcodeUr) {
		this.qrcodeUr = qrcodeUr;
	}

	public String getCertificationStatus() {
		return certificationStatus;
	}

	public void setCertificationStatus(String certificationStatus) {
		this.certificationStatus = certificationStatus;
	}

	public String getOrgName() {
		return orgName;
	}

	public void setOrgName(String orgName) {
		this.orgName = orgName;
	}

	public String getOrgEname() {
		return orgEname;
	}

	public void setOrgEname(String orgEname) {
		this.orgEname = orgEname;
	}

	public String getOfficialWebsite() {
		return officialWebsite;
	}

	public void setOfficialWebsite(String officialWebsite) {
		this.officialWebsite = officialWebsite;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getMotto() {
		return motto;
	}

	public void setMotto(String motto) {
		this.motto = motto;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getFax() {
		return fax;
	}

	public void setFax(String fax) {
		this.fax = fax;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getEmail2() {
		return email2;
	}

	public void setEmail2(String email2) {
		this.email2 = email2;
	}

	public String getQq() {
		return qq;
	}

	public void setQq(String qq) {
		this.qq = qq;
	}

	public List<JobPojo> getPositionList() {
		return positionList;
	}

	public void setPositionList(List<JobPojo> positionList) {
		this.positionList = positionList;
	}

	public List<JobPojo> getDepartmentList() {
		return departmentList;
	}

	public void setDepartmentList(List<JobPojo> departmentList) {
		this.departmentList = departmentList;
	}

	public String getDeptNames() {
		if(null != departmentList){
			StringBuilder sb = new StringBuilder();
			if(departmentList.size() > 0){
				for(JobPojo jobPojo : departmentList){
					sb.append(jobPojo.getDeptName()+" ");
				}
				deptNames = sb.toString().subSequence(0, sb.length()-1).toString();
				return deptNames;
			}
		}
		return deptNames;
	}
	

	public void setDeptNames(String deptNames) {
		this.deptNames = deptNames;
	}
	
	

	public String getWeiXin() {
		return weiXin;
	}

	public void setWeiXin(String weiXin) {
		this.weiXin = weiXin;
	}

	@Override
	public String toString() {
		return "CardPojo [id=" + id + ", cardId=" + cardId + ", iconUrl="
				+ iconUrl + ", trueName=" + trueName + ", ename=" + ename
				+ ", userIdcard=" + userIdcard + ", nickName=" + nickName
				+ ", activeStatus=" + entActiveStatus + ", qrcodeUr=" + qrcodeUr
				+ ", certificationStatus=" + certificationStatus + ", orgName="
				+ orgName + ", orgEname=" + orgEname + ", officialWebsite="
				+ officialWebsite + ", address=" + address + ", motto=" + motto
				+ ", positionList=" + positionList + ", deptName=" + departmentList
				+ ", mobile=" + mobile + ", phone=" + phone + ", fax=" + fax
				+ ", email=" + email + ", email2=" + email2 + ", qq=" + qq
				+ ", wx=" + weiXin + ", jobs=" + jobs + ", deptNames=" + deptNames
				+ "]";
	}
	
	

}
