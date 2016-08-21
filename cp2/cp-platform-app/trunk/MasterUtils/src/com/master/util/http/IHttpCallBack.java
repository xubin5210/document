/**    
 * @author maomy  
 * @Description: http 回调
 * @Package com.master.util.http   
 * @Title: IHttpCallBack.java   
 * @date 2014年9月4日 下午2:43:52   
 * @version V2.0 
 * @说明  代码版权归 杭州反盗版中心有限公司 所有
 */ 
package com.master.util.http;


public interface IHttpCallBack {
	
	
	
	/**
	 * @Auther: maomy  
	 * @Description: 成功回调
	 * @Date:2014年9月4日下午2:44:35
	 * @param code
	 * @param message  
	 * @return void 
	 * @说明  代码版权归 杭州反盗版中心有限公司 所有
	 */
	public void onSuccess(String code,String message);
	
	
	
	
	/**
	 * @Auther: maomy  
	 * @Description: 失败回调
	 * @Date:2014年9月4日下午2:45:12
	 * @param code
	 * @param message  
	 * @return void 
	 * @说明  代码版权归 杭州反盗版中心有限公司 所有
	 */
	public void onFailed(String code,String message);
	

}
