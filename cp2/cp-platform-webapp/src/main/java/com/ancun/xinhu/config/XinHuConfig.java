package com.ancun.xinhu.config;

import com.ancun.products.core.query.AbstractQueryParam;

public class XinHuConfig {
	
	//名片动态
	public final static String[] cardDynamic=new String[3];
	public final static String cardDynamicTable="card_info";//名片动态表
	public final static String cardDynamic_mobile="dynamicMobile";//手机号
	public final static String cardDynamic_department="dynamicDepartment";//部门
	public final static String cardDynamic_position="dynamicPosition";//职位
	static{
		cardDynamic[0]=cardDynamic_mobile;
		cardDynamic[1]=cardDynamic_department;
		cardDynamic[2]=cardDynamic_position;
	}
	
	//名片动态样式
	public final static String cardDynamicCss="<font color=\"blue\">*</font>";
	
	public final static String[] searchControlSwitch=new String[6];
	static {
		searchControlSwitch[0]="cardId";//名片号
		searchControlSwitch[1]="mobile";//手机号
		searchControlSwitch[2]="email";//邮箱
		searchControlSwitch[3]="position";//职位
		searchControlSwitch[4]="nickName";//昵称
		searchControlSwitch[5]="orgName";//企业
	}
	/**
	 * 获取搜索控制开关索引
	* @param type
	* @return
	* <p>
	* author: <a href="mailto:gaobenbiao@ancun.com">GaoBenBiao</a><br>
	* create at: 2015年6月18日 下午2:06:25
	 */
	public static int getSearchControlSwitchIndex(String type){
//		card_id:名片号，mobile:手机号，email：邮箱，position：职位，nickName：昵称
		for(int i=0;i<searchControlSwitch.length;i++){
			if(searchControlSwitch[i].equals(type)){
				return i;
			}
		}
		return -1;
	}
	
	/**
	 * 获取搜索控制开关列表
	* @param scs
	* @return
	* <p>
	* author: <a href="mailto:gaobenbiao@ancun.com">GaoBenBiao</a><br>
	* create at: 2015年7月1日 上午9:43:08
	 */
	public static String[][] getSearchControlSwitchList(String scs){
		String[][] res = new String[searchControlSwitch.length][];
		for(int i=0;i<searchControlSwitch.length;i++){
			res[i]=new String[]{searchControlSwitch[i]+"Switch",scs.substring(i, i+1)};
		}
		return res;
	}
	
	/**
	 * 设置查询分页
	* @param query
	* <p>
	* author: <a href="mailto:gaobenbiao@ancun.com">GaoBenBiao</a><br>
	* create at: 2015年7月1日 上午10:30:43
	 */
	public static void setQueryPage(AbstractQueryParam query){
		if (query.getPageSize() == 0) {
			query.setPageSize(10);
		}
		query.setPageNo(query.getPageNo()/query.getPageSize()+1);
	}

}
