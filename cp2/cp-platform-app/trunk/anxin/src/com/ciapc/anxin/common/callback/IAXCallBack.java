/**    
 * @author maomy  
 * @Description: 回调标准 
 * @Package com.ciapc.anxin.common.callback   
 * @Title: IAXCallBack.java   
 * @date 2015年5月25日 上午10:55:35   
 * @version V1.0 
 * @说明  代码版权归 杭州安存网络科技有限公司 所有
 */ 
package com.ciapc.anxin.common.callback;

import java.util.ArrayList;

import com.google.gson.JsonObject;


public interface IAXCallBack {

	

	/**
	 * @Auther: maomy  
	 * @Description: 回调返回List对象
	 * @Date:2015年5月25日下午4:52:11
	 * @param retStr
	 * @return  
	 * @return ArrayList<T> 
	 * @说明  代码版权归 杭州安存网络科技有限公司 所有
	 */
	public  <T>  ArrayList<T> onCallBackList(ArrayList<T> retStr);
	
	
	
	
	/**
	 * @Auther: maomy  
	 * @Description: 回调返回json 对象 
	 * @Date:2015年5月25日下午4:53:02
	 * @param retStr  
	 * @return void 
	 * @说明  代码版权归 杭州安存网络科技有限公司 所有
	 */
	public void onCallBackJsonObject(JsonObject retStr);
	
	
	
	
	/**
	 * @Auther: maomy  
	 * @Description: 回调返回String
	 * @Date:2015年5月25日下午4:54:42
	 * @param retStr  
	 * @return void 
	 * @说明  代码版权归 杭州安存网络科技有限公司 所有
	 */
	public void onCallBackString(String retStr);
	
	
	
	
}
