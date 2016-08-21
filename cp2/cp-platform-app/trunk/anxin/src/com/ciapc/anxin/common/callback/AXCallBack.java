/**    
 * @author maomy  
 * @Description: TODO(用一句话描述该文件做什么)   
 * @Package com.ciapc.anxin.common.callback   
 * @Title: AXCallBack.java   
 * @date 2015年5月25日 上午10:57:17   
 * @version V1.0 
 * @说明  代码版权归 杭州安存网络科技有限公司 所有
 */ 
package com.ciapc.anxin.common.callback;

import java.util.ArrayList;

import com.google.gson.JsonObject;

/**   
 * @author maomy   
 * @Description: TODO(这里用一句话描述这个类的作用)   
 * @ClassName: AXCallBack.java   
 * @date 2015年5月25日 上午10:57:17      
 * @说明  代码版权归 杭州安存网络科技有限公司 所有 
 */

public abstract class AXCallBack implements IAXCallBack{

	/**
	 * @Auther: maomy  
	 * @Description: 回调返回List对象
	 * @Date:2015年5月25日下午4:52:11
	 * @param retStr
	 * @return  
	 * @return ArrayList<T> 
	 * @说明  代码版权归 杭州安存网络科技有限公司 所有
	 */
	@Override
	public <T> ArrayList<T> onCallBackList(ArrayList<T> retStr) {
		return null;
	}

	/**
	 * @Auther: maomy  
	 * @Description: 回调返回json 对象 
	 * @Date:2015年5月25日下午4:53:02
	 * @param retStr  
	 * @return void 
	 * @说明  代码版权归 杭州安存网络科技有限公司 所有
	 */
	@Override
	public void onCallBackJsonObject(JsonObject retStr) {
		
	}

	/**
	 * @Auther: maomy  
	 * @Description: 回调返回String
	 * @Date:2015年5月25日下午4:54:42
	 * @param retStr  
	 * @return void 
	 * @说明  代码版权归 杭州安存网络科技有限公司 所有
	 */
	@Override
	public void onCallBackString(String retStr) {
		
	}


	/**********************
	 * 
	 * 测试调用 方法
	 * 
	 * 
	 */
/*	static class MyClass {
		
		public  void testCallBack(DnCallBack  callback){
			callback.onCallBackString("我返回了...");
//			callback.onCallBackList(retStr);
//			callback.onCallBackJsonObject(retStr);
		}
		
		
	}

	public static void main(String[] args) {
		MyClass m = new MyClass();
		m.testCallBack(new DnCallBack() {

			@Override
			public void onCallBackString(String retStr) {
//				super.onCallBackString(retStr);
				System.out.println(retStr);
			}
			
		});
	}*/
}
