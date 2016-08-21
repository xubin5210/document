/**    
 * @author maomy  
 * @Description: http 返回的结果对象
 * @Package com.master.util.gson   
 * @Title: HttpResult.java   
 * @date 2014年8月7日 下午2:25:04   
 * @version V2.0 
 * @说明  代码版权归 杭州反盗版中心有限公司 所有
 */ 
package com.master.util.gson;


public class GsonResult {
	
	
	/**
	 * 返回结果  成功-success  失败 -fiald 
	 */
	private String result;


	/**
	 * 返回码
	 */
	private String retCode;
	
	
	
	/**
	 * 返回说明  失败原因
	 */
	private String reason;
	
	/**
	 * 数据内容   格式 content = [{},{}....{}]
	 */
//	private ArrayList<String> content;


	public String getResult() {
		return result;
	}


	public void setResult(String result) {
		this.result = result;
	}


	public String getReason() {
		return reason;
	}


	public void setReason(String reason) {
		this.reason = reason;
	}


//	public ArrayList<String> getContent() {
//		return content;
//	}
//
//
//	public void setContent(ArrayList<String> content) {
//		this.content = content;
//	}


	public String getRetCode() {
		return retCode;
	}


	public void setRetCode(String retCode) {
		this.retCode = retCode;
	}


	
	
	
	
}
