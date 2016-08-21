/**    
 * @author maomy  
 * @Description: 示例Pojo
 * @Package com.ciapc.anxin.common.pojo   
 * @Title: SamplePojo.java   
 * @date 2015年5月25日 上午10:45:45   
 * @version V1.0 
 * @说明  代码版权归 杭州安存网络科技有限公司 所有
 */ 
package com.ciapc.anxin.common.pojo;

import java.io.Serializable;


public class SamplePojo implements Serializable{

	/**  
	 * @Fields serialVersionUID :
	 */
	private static final long serialVersionUID = -7904420962416181860L;
	
	
	private String name;

	private String company;
	
	private int flag;
	
	public int getFlag() {
		return flag;
	}


	public void setFlag(int flag) {
		this.flag = flag;
	}


	public String getCompany() {
		return company;
	}


	public void setCompany(String company) {
		this.company = company;
	}


	public String getName() {
		return name;
	}


	public void setName(String name) {
		this.name = name;
	}
	
	
	
	

}
