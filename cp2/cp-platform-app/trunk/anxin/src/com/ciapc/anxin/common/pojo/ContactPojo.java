package com.ciapc.anxin.common.pojo;

import java.io.Serializable;

/**
 * 
 * @author zhuwt
 * @Description: 企业联系人
 * @ClassName: ContactPojo.java
 * @date 2015年5月27日 下午4:44:07
 * @说明 代码版权归 杭州反盗版中心有限公司 所有
 */
public class ContactPojo implements Serializable {

	private static final long serialVersionUID = 1L;

	// 名字
	private String trueName;

	private String cardId;

	private String iconUrl;

	// 职位名称
	private String positionName;

	// 名字的首个拼音
	private String tnFirstSpell;

	// 部门
	private String deptName;
	
	//部门人数
	private String deptStaffNum;
	
	private String isExchange;
	
	private String deptId;
	
	public String getDeptId() {
		return deptId;
	}

	public void setDeptId(String deptId) {
		this.deptId = deptId;
	}

	public String getIsExchange() {
		return isExchange;
	}

	public void setIsExchange(String isExchange) {
		this.isExchange = isExchange;
	}

	public String getDeptStaffNum() {
		return deptStaffNum;
	}

	public void setDeptStaffNum(String deptStaffNum) {
		this.deptStaffNum = deptStaffNum;
	}

	public String getDeptName() {
		return deptName;
	}

	public void setDeptName(String deptName) {
		this.deptName = deptName;
	}

	public String getTnFirstSpell() {
		return tnFirstSpell;
	}

	public void setTnFirstSpell(String tnFirstSpell) {
		this.tnFirstSpell = tnFirstSpell;
	}

	public String getTrueName() {
		return trueName;
	}

	public void setTrueName(String trueName) {
		this.trueName = trueName;
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

	public String getPositionName() {
		return positionName;
	}

	public void setPositionName(String positionName) {
		this.positionName = positionName;
	}
	
}
