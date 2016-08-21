/**    
 * @author maomy  
 * @Description: 常量类   
 * @Package com.master.util.common   
 * @Title: MasterConstants.java   
 * @date 2014年8月6日 上午10:34:39   
 * @version V2.0 
 * @说明  代码版权归 杭州反盗版中心有限公司 所有
 */ 
package com.master.util.common;




public class MasterConstants {
	
	
	/**
	 * 是否开启debug模式
	 */
	private static boolean debug = true;
	
	
	/**
	 * @Auther: maomy  
	 * @Description: 是否开启debug模式
	 * @Date:2014年8月6日上午10:36:53
	 * @param debug  
	 * @return void 
	 * @说明  代码版权归 杭州反盗版中心有限公司 所有
	 */
	public static void setDebug(boolean debug){
		MasterConstants.debug = debug;
	}
	
	public static boolean isDebug(){
		return debug;
	}
	
	/**
	 * OSS域名
	 */
	public final static String BUCKET = "gd-push"; 
	
	/**
	 * oss获取签名接口
	 */
	public static String OSS_GET_DOWNLOAD = "1011";


}
