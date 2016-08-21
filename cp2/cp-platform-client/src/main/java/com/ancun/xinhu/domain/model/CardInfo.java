package com.ancun.xinhu.domain.model;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * 名片信息表实体类
 */
public class CardInfo implements Serializable {
	private static final long serialVersionUID = 14325711230442L;

	private Integer id;// 用户id
	private Integer userId;// 用户id
	private Integer entId;// 企业ID
	private Date entEntryDate;// 入职时间
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
	private Date gmtCreate;// 创建时间
	private Date gmtModify;// 信息更改时间
	
	private String entPositionStr; //员工职位
	private String entDepartmentStr;//员工所在部门
	
	private String entPositionEnameStr;//英文中文名
	
	private List<EntPosition> entPositionList; //所在职位
	private List<EntDepartment> entDepartmentList;//所在部门
	private String tnFirstSpell;//真实姓名汉字首字母拼音
	private Date gmtDynamicLastRead;// 动态最后读取时间
	
	private Integer ifOnJob;//0 在职  1 离职
	
	private String searchKey;//搜索关键字
	
	private String unpassMes;//不通过原因
	
	public CardInfo() {
	}



	public String getSearchKey() {
		return searchKey;
	}



	public void setSearchKey(String searchKey) {
		this.searchKey = searchKey;
	}



	public String getUnpassMes() {
		return unpassMes;
	}



	public void setUnpassMes(String unpassMes) {
		this.unpassMes = unpassMes;
	}



	public List<EntPosition> getEntPositionList() {
		return entPositionList;
	}



	public Integer getIfOnJob() {
		return ifOnJob;
	}



	public void setIfOnJob(Integer ifOnJob) {
		this.ifOnJob = ifOnJob;
	}



	public void setEntPositionList(List<EntPosition> entPositionList) {
		this.entPositionList = entPositionList;
		if(entPositionList!=null){
			this.entPositionStr = "";
			this.entPositionEnameStr = "";
			for (EntPosition entPosition : entPositionList) {
				if(entPosition == null)continue;
				this.entPositionStr += entPosition.getPositionName()+",";
				this.entPositionEnameStr += entPosition.getPositionEname()+",";
			}
		}
		if(this.entPositionStr!=null && this.entPositionStr.indexOf(",")!=-1)
			this.entPositionStr = this.entPositionStr.substring(0, this.entPositionStr.length()-1);
		
		if(this.entPositionEnameStr!=null && this.entPositionEnameStr.indexOf(",")!=-1)
			this.entPositionEnameStr = this.entPositionEnameStr.substring(0, this.entPositionEnameStr.length()-1);
	}



	public String getEntPositionEnameStr() {
		return entPositionEnameStr;
	}



	public void setEntPositionEnameStr(String entPositionEnameStr) {
		this.entPositionEnameStr = entPositionEnameStr;
	}



	public List<EntDepartment> getEntDepartmentList() {
		return entDepartmentList;
	}



	public void setEntDepartmentList(List<EntDepartment> entDepartmentList) {
		this.entDepartmentList = entDepartmentList;
		if(entDepartmentList!=null){
			this.entDepartmentStr = "";
			for (EntDepartment entDepartment : entDepartmentList) {
				if(entDepartment == null)continue;
				this.entDepartmentStr += entDepartment.getDeptName()+",";
			}
		}
		if(this.entDepartmentStr!=null && this.entDepartmentStr.indexOf(",")!=-1)
			this.entDepartmentStr = this.entDepartmentStr.substring(0, this.entDepartmentStr.length()-1);
	}



	public String getEntPositionStr() {
		return entPositionStr;
	}



	public void setEntPositionStr(String entPositionStr) {
		this.entPositionStr = entPositionStr;
	}



	public String getEntDepartmentStr() {
		return entDepartmentStr;
	}



	public void setEntDepartmentStr(String entDepartmentStr) {
		this.entDepartmentStr = entDepartmentStr;
	}



	/**
	 *
	 * @param id
	 *            -- 用户id
	 */
	public CardInfo(Integer id) {
		this.id = id;
	}

	/** 用户id */
	public Integer getId() {
		return id;
	}

	/** 用户id */
	public void setId(Integer id) {
		this.id = id;
	}

	/** 用户id */
	public Integer getUserId() {
		return userId;
	}

	/** 用户id */
	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	/** 企业ID */
	public Integer getEntId() {
		return entId;
	}

	/** 企业ID */
	public void setEntId(Integer entId) {
		this.entId = entId;
	}

	/** 入职时间 */
	public Date getEntEntryDate() {
		return entEntryDate;
	}

	/** 入职时间 */
	public void setEntEntryDate(Date entEntryDate) {
		this.entEntryDate = entEntryDate;
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

	/** 创建时间 */
	public Date getGmtCreate() {
		return gmtCreate;
	}

	/** 创建时间 */
	public void setGmtCreate(Date gmtCreate) {
		this.gmtCreate = gmtCreate;
	}

	/** 信息更改时间 */
	public Date getGmtModify() {
		return gmtModify;
	}

	/** 信息更改时间 */
	public void setGmtModify(Date gmtModify) {
		this.gmtModify = gmtModify;
	}

	public String getTnFirstSpell() {
		return tnFirstSpell;
	}



	public void setTnFirstSpell(String tnFirstSpell) {
		this.tnFirstSpell = tnFirstSpell;
	}



	@Override
	public String toString() {
		return "CardInfo [ id=" + id + ", userId=" + userId + ", entId="
				+ entId + ", entEntryDate=" + entEntryDate + ", cardType="
				+ cardType + ", mobile=" + mobile + ", activationStatus="
				+ activationStatus + ", userIdcard=" + userIdcard
				+ ", trueName=" + trueName + ", nickName=" + nickName
				+ ", sex=" + sex + ", iconUrl=" + iconUrl + ", motto=" + motto
				+ ", ename=" + ename + ", phone=" + phone + ", phoneShort="
				+ phoneShort + ", fax=" + fax + ", qq=" + qq + ", weiXin="
				+ weiXin + ", email=" + email + ", email2=" + email2
				+ ", qrcodeUrl=" + qrcodeUrl + ", searchControlSwitch="
				+ searchControlSwitch + ", gmtCreate=" + gmtCreate
				+ ", gmtModify=" + gmtModify + "]";
	}
}
