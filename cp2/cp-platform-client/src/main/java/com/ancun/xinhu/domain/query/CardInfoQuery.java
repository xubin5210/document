package com.ancun.xinhu.domain.query;

import java.io.Serializable;
import java.util.Date;

import com.ancun.products.core.query.AbstractQueryParam;
import com.ancun.products.core.query.QueryParam;

/**
 * 名片信息表查询对象
 */
public class CardInfoQuery extends AbstractQueryParam implements QueryParam,
		Serializable {
	private static final long serialVersionUID = 14325711230441L;

	private Integer id;//用户id
	private Integer[] idArray;// 用户id
	private Integer userId;// 用户id
	private Integer entId;// 企业ID
	private Date entEntryDateFrom;// 入职时间
	private Date entEntryDateTo;// 入职时间
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
	private Date gmtCreateFrom;// 创建时间
	private Date gmtCreateTo;// 创建时间
	private Date gmtModifyFrom;// 信息更改时间
	private Date gmtModifyTo;// 信息更改时间
	
	private String[] activaArray;//激活状态
	private String[] deptArray;//部门
	private String[] positionArray;//职位
	
	private int status;//绑定状态
	
	private String keyword;//关键字
	
	private Integer ifOnJob;// 0  在职 1 离职 2 不操作
	

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	/*** 用户id */
	public Integer[] getIdArray() {
		return idArray;
	}

	
	
	public String getKeyword() {
		return keyword;
	}

	public Integer getIfOnJob() {
		return ifOnJob;
	}

	public void setIfOnJob(Integer ifOnJob) {
		this.ifOnJob = ifOnJob;
	}

	public void setKeyword(String keyword) {
		this.keyword = keyword;
	}

	/*** 用户id */
	public void setIdArray(Integer... idArray) {
		this.idArray = idArray;
	}

	/*** 用户id */
	public Integer getUserId() {
		return userId;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	/*** 用户id */
	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	/*** 企业ID */
	public Integer getEntId() {
		return entId;
	}

	/*** 企业ID */
	public void setEntId(Integer entId) {
		this.entId = entId;
	}

	/*** 入职时间 */
	public Date getEntEntryDateFrom() {
		return entEntryDateFrom;
	}

	/*** 入职时间 */
	public void setEntEntryDateFrom(Date entEntryDateFrom) {
		this.entEntryDateFrom = entEntryDateFrom;
	}

	/*** 入职时间 */
	public Date getEntEntryDateTo() {
		return entEntryDateTo;
	}

	/*** 入职时间 */
	public void setEntEntryDateTo(Date entEntryDateTo) {
		this.entEntryDateTo = entEntryDateTo;
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

	/*** 信息更改时间 */
	public Date getGmtModifyFrom() {
		return gmtModifyFrom;
	}

	/*** 信息更改时间 */
	public void setGmtModifyFrom(Date gmtModifyFrom) {
		this.gmtModifyFrom = gmtModifyFrom;
	}

	/*** 信息更改时间 */
	public Date getGmtModifyTo() {
		return gmtModifyTo;
	}

	/*** 信息更改时间 */
	public void setGmtModifyTo(Date gmtModifyTo) {
		this.gmtModifyTo = gmtModifyTo;
	}

	public String[] getActivaArray() {
		return activaArray;
	}

	public void setActivaArray(String[] activaArray) {
		this.activaArray = activaArray;
	}

	public String[] getDeptArray() {
		return deptArray;
	}

	public void setDeptArray(String[] deptArray) {
		this.deptArray = deptArray;
	}

	public String[] getPositionArray() {
		return positionArray;
	}

	public void setPositionArray(String[] positionArray) {
		this.positionArray = positionArray;
	}

	
	
}
